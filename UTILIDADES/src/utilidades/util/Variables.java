package utilidades.util;

import java.util.HashMap;
import java.util.Map;

import utilidades.exceptions.ObjectNotFoundException;


public final class Variables 
{
	private static Map<String, String> variablesString = new HashMap<String, String>();
	private static Map<String, Enum<?>> variablesEnum = new HashMap<String, Enum<?>>();
	private static Map<String, Integer> variablesInteger = new HashMap<String, Integer>();
	
	private Variables()
	{
		super();
	}
	
	public static String getVariableString(String nombre) throws ObjectNotFoundException
	{
		if(!variablesString.containsKey(nombre))
			throw new ObjectNotFoundException("Variable \"" + nombre + "\" no encontrada");

		return variablesString.get(nombre);
	}
	
	public static void setVariableString(String nombre, String valor)
	{
		variablesString.put(nombre, valor);
	}
	
	public static Enum<?> getVariableEnum(String nombre) throws ObjectNotFoundException
	{
		if(!variablesEnum.containsKey(nombre))
			throw new ObjectNotFoundException("Variable \"" + nombre + "\" no encontrada");
		
		return variablesEnum.get(nombre);
	}
	
	public static void setVariableEnum(String nombre, Enum<?> valor)
	{
		variablesEnum.put(nombre, valor);
	}
	
	public static int getVariableInt(String nombre) throws ObjectNotFoundException
	{
		if(!variablesInteger.containsKey(nombre))
			throw new ObjectNotFoundException("Variable \"" + nombre + "\" no encontrada");
		
		return variablesInteger.get(nombre).intValue();
	}
	
	public static void setVariableInt(String nombre, Integer valor)
	{
		variablesInteger.put(nombre, valor);
	}
}