package utilidades.util;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import utilidades.general.Dato;

public final class Factory
{
	private Factory()
	{
		super();
	}
	
	public static Object createClass(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		Class<?> c = Class.forName(className);
		return c.newInstance();
	}
	
	public static Dato createDataByArray(String className, Object[] valores) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		Dato dato = null;
		
		Class<?> c = Class.forName(className);
		dato = (Dato)c.newInstance();
		dato.loadValues(valores);
		
		return dato;
	}
	
	public static Dato[] createDataByResultSet(String className, ResultSet rs) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		List<Dato> resultados = Utilidades.getListInstance();		
		
		if (rs !=  null)
		{
			while (rs.next())
			{
				Class<?> c = Class.forName(className);
				Dato dato = (Dato)c.newInstance();
				Object[] fieldValues = new Object[dato.getNumberOfFields()];
				
				for(int i = 0; i < fieldValues.length; i++)
				{
					if(dato.getFieldType(i).equals(String.class))
					{
						fieldValues[i] = rs.getString(dato.getFieldName(i));
					}
					else if(dato.getFieldType(i).equals(Integer.class))
					{
						fieldValues[i] = rs.getInt(dato.getFieldName(i));
					}
					else if(dato.getFieldType(i).equals(Double.class))
					{
						fieldValues[i] = rs.getDouble(dato.getFieldName(i));
					}
					else if(dato.getFieldType(i).equals(java.sql.Date.class))
					{
						fieldValues[i] = rs.getDate(dato.getFieldName(i));
					}
					else if(dato.getFieldType(i).equals(java.sql.Time.class))
					{
						fieldValues[i] = rs.getTime(dato.getFieldName(i));
					}
					else if(dato.getFieldType(i).equals(java.sql.Timestamp.class))
					{
						fieldValues[i] = rs.getTimestamp(dato.getFieldName(i));
					}
					else if(dato.getFieldType(i).equals(java.util.Date.class))
					{
						java.sql.Timestamp sqlTimeStamp = rs.getTimestamp(dato.getFieldName(i));
						fieldValues[i] = new java.util.Date(sqlTimeStamp.getTime());
					}
					else
					{
						fieldValues[i] = rs.getObject(dato.getFieldName(i));
					}
				}
				
				dato.loadValues(fieldValues);
				resultados.add(dato);
			}
		}
		
		return resultados.toArray(new Dato[resultados.size()]);
	}
}