package test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import utilidades.exceptions.DataAccessException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.general.DataAccessObject;
import utilidades.general.Dato;
import utilidades.util.Debug;
import utilidades.util.Errores;
import utilidades.util.Variables;
import dataBase.connection.ConnectionManager;
import dataBase.connection.DBConnector;
import dataBase.connection.GestorBD;
import dataBase.service.DataBase;
import datos.Cliente;
import datos.TablaHorarioClase;

public class Prueba
{
	public static void main(String[] args) throws Exception
	{
		inicializarVariables();
		prueba2();
	}
	
	public static void prueba1() throws DataAccessException
	{
		inicializarVariables();
		
		Debug.setDebug(true);
		
		DataAccessObject baseDatos = new DataBase();
		
		Dato cliente = new Cliente();
		
		cliente.setFieldValue(2, 0);
		
		System.out.println(cliente);
		
		baseDatos.cargar(cliente);
		
		System.out.println(cliente);
		
		cliente.setFieldValue(4, 3);
		
		cliente.setFieldValue(0, 0);
		
		baseDatos.insertar(cliente);
		baseDatos.commit();
		
		baseDatos.cargar(cliente);
		
		System.out.println(cliente);
		
		baseDatos.desconectar();
	}
	
	public static void prueba2() throws ClassNotFoundException, SQLException, ObjectNotFoundException, InstantiationException, IllegalAccessException
	{
		DBConnector con = ConnectionManager.getConnection();
		
		String sql = "SELECT * FROM horario_clase WHERE semestre = 2 AND id_carrera = 4";
//		String sql = "SELECT id_carrera, carrera FROM horario_clase";
//		String sql = "SELECT * FROM v_informe_resumen('v','200902',8)";
//		String sql = "SELECT porciento FROM v_informe_resumen('v','200902',8) WHERE materia LIKE '" + "1123" + "%'";
//		String sql = "SELECT materia, porciento FROM v_informe_resumen('v','200902',8)";
		
		ResultSet rs = con.ejecutarConsulta(sql);
		
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
	
	public static void prueba3()
	{
		try
		{
			System.out.println(3 / 0);
		}
		catch (Exception e)
		{
			Errores.dialogo(e);
		}
	}
	
	public static void prueba4() throws DataAccessException
	{
		DataAccessObject dao = new DataBase();
		TablaHorarioClase thc = new TablaHorarioClase();
		
//		Comparador[] cmp = new Comparador[thc.getNumberOfFields()];
//		cmp[2] = ComparadorString.CONTIENE;
		
		thc.setCodigoCarrera(4);
		thc.setSemestre(1);
//		thc.setMateria("EST");
		
//		Dato[] resultado = dao.consultar(thc, cmp);
		Dato[] resultado = dao.consultar(thc);

//		System.out.println(Arrays.toString(resultado));
		
		for (int i = 0; i < resultado.length; i++)
		{
			System.out.println(resultado[i]);
		}
		
		dao.desconectar();
	}
	
	private static void inicializarVariables()
	{
		Variables.setVariableString("formatoFecha", "dd/MM/yyyy");
		
		Variables.setVariableString("baseDatos", "alicia");
		Variables.setVariableString("usuario", "sergiomorel");
		Variables.setVariableString("senha", "sergio");
		Variables.setVariableString("host", "172.18.200.7");
		
		Variables.setVariableEnum("gestorBD", GestorBD.POSTGRES);
		
//		Variables.setVariableString("formatoFecha", "dd/MM/yyyy");
//		
//		Variables.setVariableString("baseDatos", "Stock");
//		Variables.setVariableString("schema", "Stock");
//		Variables.setVariableString("usuario", "root");
//		Variables.setVariableString("senha", "admin");
//		Variables.setVariableString("host", "localhost");
//		
//		Variables.setVariableEnum("gestorBD", GestorBD.MYSQL);
	}
}