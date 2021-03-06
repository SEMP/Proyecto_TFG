package dataBase.service;

import dataBase.connection.ConnectionManager;
import dataBase.connection.DBConnector;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import utilidades.exceptions.DataAccessException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.general.ComparadorBD;
import utilidades.general.DataAccessObject;
import utilidades.general.Dato;
import utilidades.util.Debug;
import utilidades.util.Factory;
import utilidades.util.Utilidades;
import utilidades.util.Variables;

public class DataBase implements DataAccessObject 
{
	protected DBConnector conexion;
	
	public DataBase()
	{
		super();
	}

	@Override
	public int insertar(Dato tabla) throws DataAccessException
	{
		if(tabla == null) throw new IllegalArgumentException("Dato no puede ser null");
		
		try
		{
			this.conexion = ConnectionManager.getConnection();
			List<Object> fieldValues = Utilidades.getListInstance();
			int numberFields = tabla.getNumberOfFields();
			boolean agregarComa;
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("INSERT INTO ");
			
			try
			{
				sql.append(Variables.getVariableString("schema")).append(".");
			}
			catch (ObjectNotFoundException e){}
			
			sql.append(tabla.getEntityName()).append("(");
			
			agregarComa = false;
			for (int i = 0; i < numberFields; i++)
			{
				Object fieldValue = tabla.getFieldValue(i);
				String fieldName = tabla.getFieldName(i);
				
				if(fieldValue != null)
				{
					if(!agregarComa) agregarComa = true;
					else sql.append(", ");
					
					sql.append(fieldName);
				}
			}
			
			sql.append(") VALUES (");

			agregarComa = false;
			for (int i = 0; i < numberFields; i++)
			{
				Object fieldValue = tabla.getFieldValue(i);
				
				if(fieldValue != null)
				{
					if(!agregarComa) agregarComa = true;
					else sql.append(", ");
					
					sql.append("?");
					fieldValues.add(fieldValue);
				}
			}
			
			sql.append(")");
			
			Debug.imprimir(sql);
			Debug.agregar(Arrays.toString(fieldValues.toArray()));
			
			int rowCount = this.conexion.ejecutarUpdate(sql.toString(), fieldValues.toArray());
			
			Debug.agregar("Rowcount: " + rowCount);
			
			return rowCount;
		}
		catch (Exception e)
		{
			throw new DataAccessException(e);
		}
	}
	
	@Override
	public int actualizar(Dato tabla) throws DataAccessException
	{
		if(tabla == null) throw new IllegalArgumentException("Dato no puede ser null");
		
		try
		{
			this.conexion = ConnectionManager.getConnection();
			List<Object> fieldValues = Utilidades.getListInstance();
			int[] primaryKeys = tabla.getPrimaryKeys();
			int numberFields = tabla.getNumberOfFields();
			boolean agregarAND, agregarComa;
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE ");
			
			try
			{
				sql.append(Variables.getVariableString("schema")).append(".");
			}
			catch (ObjectNotFoundException e){}
			
			sql.append(tabla.getEntityName());
			
			agregarComa = false;
			for (int i = 0; i < numberFields; i++)
			{
				Object fieldValue = tabla.getFieldValue(i);
				String fieldName = tabla.getFieldName(i);
				
				if(fieldValue != null && !Utilidades.arrayContains(primaryKeys, i))
				{
					if(!agregarComa)
					{
						agregarComa = true;
						sql.append(" SET ");
					}
					else sql.append(", ");
					
					sql.append(fieldName).append(" = ?");
					fieldValues.add(fieldValue);
				}
			}
			
			if(!agregarComa)
				throw new DataAccessException("No hay campos que actualizar");
			
			sql.append(" WHERE ");
			
			agregarAND = false;
			for (int i = 0; i < primaryKeys.length; i++) 
			{
				Object keyValue = tabla.getFieldValue(primaryKeys[i]);
				String keyName = tabla.getFieldName(primaryKeys[i]);
				
				if(keyValue == null)
					throw new DataAccessException("Debe ingresar " + keyName);
				
				if(!agregarAND) agregarAND = true;
				else sql.append(" AND ");
				
				sql.append(keyName);
				sql.append(" = ?");
				
				fieldValues.add(keyValue);
			}
			
			Debug.imprimir(sql);
			Debug.agregar(Arrays.toString(fieldValues.toArray()));
			
			int rowCount = this.conexion.ejecutarUpdate(sql.toString(), fieldValues.toArray());
			
			Debug.agregar("Rowcount: " + rowCount);
			
			return rowCount;
		}
		catch (Exception e)
		{
			throw new DataAccessException(e);
		}
	}

	@Override
	public int eliminar(Dato tabla) throws DataAccessException
	{
		if(tabla == null) throw new IllegalArgumentException("Dato no puede ser null");
		
		try
		{
			this.conexion = ConnectionManager.getConnection();
			int[] primaryKeys = tabla.getPrimaryKeys();
			Object[] primaryKeyValues = new Object[primaryKeys.length];
			boolean agregarAND;
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("DELETE FROM ");
			
			try
			{
				sql.append(Variables.getVariableString("schema")).append(".");
			}
			catch (ObjectNotFoundException e){}
			
			sql.append(tabla.getEntityName());
			sql.append(" WHERE ");
			
			agregarAND = false;
			for (int i = 0; i < primaryKeys.length; i++) 
			{
				Object keyValue = tabla.getFieldValue(primaryKeys[i]);
				String keyName = tabla.getFieldName(primaryKeys[i]);
				
				if(keyValue == null)
					throw new DataAccessException("Debe ingresar " + keyName);
				
				if(!agregarAND) agregarAND = true;
				else sql.append(" AND ");
				
				sql.append(keyName);
				sql.append(" = ?");
				
				primaryKeyValues[i] = keyValue;
			}
			
			Debug.imprimir(sql);
			Debug.agregar(Arrays.toString(primaryKeyValues));
			
			int rowCount = this.conexion.ejecutarUpdate(sql.toString(), primaryKeyValues);
			
			Debug.agregar("Rowcount: " + rowCount);
			
			return rowCount;
		}
		catch (Exception e)
		{
			throw new DataAccessException(e);
		}
	}

	/**
	 * Carga valores de la base de datos a las tablas consultando
	 * con las claves primarias de la tabla, y utiliza recursividad
	 * para cargar los datos de las claves foraneas. La tabla original
	 * se modifica. (<b>se asume que las tablas foraneas constan con
	 * exactamente 1 clave primaria</b>)
	 * @author Sergio Morel
	 * @return true - si existe un elemento con la clave primaria ingresada<br>
	 * false - si no existe un elemento con la clave ingresada para cargar (o
	 * si no la consulta devolvió mas de 1 resultado)
	 */
	@Override
	public boolean cargar(Dato tabla) throws DataAccessException
	{
		if(tabla == null) throw new IllegalArgumentException("Dato no puede ser null");
		
		try
		{
			this.conexion = ConnectionManager.getConnection();
			int[] primaryKeys = tabla.getPrimaryKeys();
			int[] foreignKeys = tabla.getForeignKeys();
			Object[] primaryKeyValues = new Object[primaryKeys.length];
			int numberFields = tabla.getNumberOfFields();
			boolean agregarComa, agregarAND;
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT ");
			
			agregarComa = false;
			for (int i = 0; i < numberFields; i++)
			{
				String fieldName = tabla.getFieldName(i);
				
				if(!agregarComa) agregarComa = true;
				else sql.append(", ");
				
				sql.append(fieldName);
			}
			
			if(!agregarComa)
				throw new DataAccessException("No hay campos que cargar");
			
			sql.append(" FROM ");
			
			try
			{
				sql.append(Variables.getVariableString("schema")).append(".");
			}
			catch (ObjectNotFoundException e){}
			
			sql.append(tabla.getEntityName());
			sql.append(" WHERE ");
			
			agregarAND = false;
			for (int i = 0; i < primaryKeys.length; i++) 
			{
				Object keyValue = tabla.getFieldValue(primaryKeys[i]);
				String keyName = tabla.getFieldName(primaryKeys[i]);
				
				if(!agregarAND) agregarAND = true;
				else sql.append(" AND ");
				
				sql.append(keyName);
				sql.append(" = ?");
				
				primaryKeyValues[i] = keyValue;
			}
			
			Debug.imprimir(sql);
			Debug.agregar(Arrays.toString(primaryKeyValues));
			
			ResultSet rs = this.conexion.ejecutarConsulta(sql.toString(), primaryKeyValues);
			
			Dato[] resultados = Factory.createDataByResultSet(tabla.getClass().getName(), rs);
			rs.getStatement().close();
			
			Debug.agregar("Rowcount: " + resultados.length);
			
			if(resultados.length != 1)
			{
				return false;
			}
			
			tabla.loadValues(resultados[0].getFieldValues());
			
			for (int i = 0; i < foreignKeys.length; i++) 
			{
				Object foreignKey = tabla.getFieldValue(foreignKeys[i]);
				
				if(foreignKey != null)
				{
					Dato foreignTable = tabla.getForeignTable(i);
					
					foreignTable.setFieldValue(foreignKey, foreignTable.getPrimaryKeys()[0]);
					this.cargar(foreignTable);
				}
			}
			
			return true;
		}
		catch (Exception e)
		{
			throw new DataAccessException(e);
		}
	}

	@Override
	public Dato[] consultar(Dato tabla) throws DataAccessException
	{
		if(tabla == null) throw new IllegalArgumentException("Dato no puede ser null");
		
		return this.consultar(tabla, null);
	}

	@Override
	public Dato[] consultar(Dato tabla, ComparadorBD[] comparadores) throws DataAccessException
	{
		if(tabla == null) throw new IllegalArgumentException("Dato no puede ser null");
		
		if(comparadores != null && comparadores.length != tabla.getNumberOfFields())
			throw new IllegalArgumentException("Array comparador debe tener igual cantidad de campos que el dato");
		
		try
		{
			this.conexion = ConnectionManager.getConnection();
			List<Object> fieldValues = Utilidades.getListInstance();
			int numberFields = tabla.getNumberOfFields();
			boolean agregarComa, agregarWHERE;
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT ");
			
			agregarComa = false;
			for (int i = 0; i < numberFields; i++)
			{
				String fieldName = tabla.getFieldName(i);
				
				if(!agregarComa) agregarComa = true;
				else sql.append(", ");
				
				sql.append(fieldName);
			}
			
			sql.append(" FROM ");
			
			try
			{
				sql.append(Variables.getVariableString("schema")).append(".");
			}
			catch (ObjectNotFoundException e){}
			
			sql.append(tabla.getEntityName());
			
			agregarWHERE = true;
			for (int i = 0; i < numberFields; i++) 
			{
				Object fieldValue = tabla.getFieldValue(i);
				String fieldName = tabla.getFieldName(i);

				if(fieldValue != null)
				{
					if(agregarWHERE)
					{
						agregarWHERE = false;
						sql.append(" WHERE ");
					}
					else
					{
						sql.append(" AND ");
					}
					
					if(comparadores != null)
					{
						if(comparadores[i] != null)
						{
							ComparadorBD comparador = comparadores[i];
							
							if(comparador instanceof ComparadorString && !(fieldValue instanceof String))
							{
								throw new DataAccessException("Solo se pueden usar los comparadores String con datos tipo String");
							}
							
							if(fieldValue instanceof String && !(comparador instanceof ComparadorString))
							{
								throw new DataAccessException("los datos tipo String deben compararse con comparadores String");
							}
							
							if(fieldValue instanceof String && comparador instanceof ComparadorString)
							{
								fieldValue = ((ComparadorString)comparador).prepararDato((String)fieldValue);
							}
							
							sql.append(comparador.comparar(fieldName, "?"));
							
							fieldValues.add(fieldValue);
						}
					}
					else
					{
						sql.append(fieldName).append(" = ?");
						
						fieldValues.add(fieldValue);
					}
				}
			}
			
			Debug.imprimir(sql);
			Debug.agregar(Arrays.toString(fieldValues.toArray()));
			
			ResultSet rs = this.conexion.ejecutarConsulta(sql.toString(), fieldValues.toArray());
			
			Dato[] resultado = Factory.createDataByResultSet(tabla.getClass().getName(), rs);
			rs.getStatement().close();
			
			Debug.agregar("Rowcount: " + resultado.length);
			
			for (int i = 0; i < resultado.length; i++)
			{
				Dato row = resultado[i];
				int[] foreignKeys = row.getForeignKeys();
				
				for (int j = 0; j < foreignKeys.length; j++) 
				{
					Object foreignKeyValue = row.getFieldValue(foreignKeys[j]);
					
					if(foreignKeyValue != null)
					{
						Dato foreignTable = row.getForeignTable(j);
						
						foreignTable.setFieldValue(foreignKeyValue, foreignTable.getPrimaryKeys()[0]);
						this.cargar(foreignTable);
					}
				}
			}
			
			return resultado;
		}
		catch (Exception e)
		{
			throw new DataAccessException(e);
		}
	}
	
	public static void imprimirResultSet(ResultSet rs) throws SQLException, ClassNotFoundException
	{
		int elemento = 1;
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnas = rsmd.getColumnCount();
		
		for(int i = 1; i <= columnas; i++)
		{
			System.out.print(rsmd.getColumnName(i));
			System.out.print(" | ");
		}
		
		System.out.println();
		
		while(rs.next())
		{
			Object[] fieldValues = new Object[columnas];
			
			for(int i = 1; i <= columnas; i++)
			{
				String className = rsmd.getColumnClassName(i);
				Class<?> tipoDato = Class.forName(className);
				
				if(tipoDato.equals(String.class))
				{
					fieldValues[i - 1] = rs.getString(rsmd.getColumnName(i));
				}
				else if(tipoDato.equals(Integer.class))
				{
					fieldValues[i - 1] = rs.getInt(rsmd.getColumnName(i));
				}
				else if(tipoDato.equals(Double.class))
				{
					fieldValues[i - 1] = rs.getDouble(rsmd.getColumnName(i));
				}
				else if(tipoDato.equals(java.sql.Time.class))
				{
					java.sql.Time sqlTime = rs.getTime(rsmd.getColumnName(i));
					fieldValues[i - 1] = sqlTime;
				}
				else
				{
					fieldValues[i - 1] = rs.getObject(rsmd.getColumnName(i));
				}
			}

			System.out.print((elemento++) + ". ");
			for (int i = 0; i < fieldValues.length; i++)
			{
				if(fieldValues[i] != null)
					System.out.print(fieldValues[i].toString().trim());
				else
					System.out.print(fieldValues[i]);
				System.out.print(" | ");
			}
			System.out.println();
		}
	}
	
	@Override
	public void commit() throws DataAccessException
	{
		try
		{
			this.conexion.commit();
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
	}
	
	@Override
	public void rollback() throws DataAccessException
	{
		try
		{
			this.conexion.rollback();
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
	}
	
	@Override
	public void desconectar() throws DataAccessException
	{
		try
		{
			ConnectionManager.closeConnection();
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
	}
}