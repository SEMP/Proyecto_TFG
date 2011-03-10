package utilidades.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utilidades.exceptions.ObjectNotFoundException;

public final class Variables 
{
	private static Map<String, String> variablesString = Collections.synchronizedMap(new HashMap<String, String>());
	private static Map<String, Enum<?>> variablesEnum = Collections.synchronizedMap(new HashMap<String, Enum<?>>());
	private static Map<String, Integer> variablesInteger = Collections.synchronizedMap(new HashMap<String, Integer>());
	private static Map<String, Double> variablesDouble = Collections.synchronizedMap(new HashMap<String, Double>());
	private static Map<String, Boolean> variablesBoolean = Collections.synchronizedMap(new HashMap<String, Boolean>());
	private static Map<String, Object> variablesObject = Collections.synchronizedMap(new HashMap<String, Object>());
	
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
		
		return variablesInteger.get(nombre);
	}
	
	public static void setVariableInt(String nombre, Integer valor)
	{
		variablesInteger.put(nombre, valor);
	}
	
	public static double getVariableDouble(String nombre) throws ObjectNotFoundException
	{
		if(!variablesDouble.containsKey(nombre))
			throw new ObjectNotFoundException("Variable \"" + nombre + "\" no encontrada");
		
		return variablesDouble.get(nombre);
	}
	
	public static void setVariableDouble(String nombre, Double valor)
	{
		variablesDouble.put(nombre, valor);
	}

	public static boolean getVariableBoolean(String nombre) throws ObjectNotFoundException
	{
		if(!variablesBoolean.containsKey(nombre))
			throw new ObjectNotFoundException("Variable \"" + nombre + "\" no encontrada");
		
		return variablesBoolean.get(nombre);
	}
	
	public static void setVariableBoolean(String nombre, Boolean valor)
	{
		variablesBoolean.put(nombre, valor);
	}
	
	public static Object getVariableObject(String nombre) throws ObjectNotFoundException
	{
		if(!variablesObject.containsKey(nombre))
			throw new ObjectNotFoundException("Variable \"" + nombre + "\" no encontrada");
		
		return variablesObject.get(nombre);
	}
	
	public static void setVariableObject(String nombre, Object valor)
	{
		variablesObject.put(nombre, valor);
	}
	
	public static String[] listVariables()
	{
		List<String> variables = new LinkedList<String>();
		
		for(String key : variablesString.keySet())
		{
			variables.add(key);
		}
		
		for(String key : variablesEnum.keySet())
		{
			variables.add(key);
		}
		
		for(String key : variablesInteger.keySet())
		{
			variables.add(key);
		}
		
		for(String key : variablesDouble.keySet())
		{
			variables.add(key);
		}
		
		for(String key : variablesBoolean.keySet())
		{
			variables.add(key);
		}
		
		for(String key : variablesObject.keySet())
		{
			variables.add(key);
		}
		
		return variables.toArray(new String[variables.size()]);
	}
	
	public static String[] listVariableDetails()
	{
		List<String> variables = new LinkedList<String>();
		
		for(String key : variablesString.keySet())
		{
			Object variable = variablesString.get(key);
			variables.add(variable.getClass().getSimpleName() + " " + key + " = " + variable);
		}
		
		for(String key : variablesEnum.keySet())
		{
			Object variable = variablesEnum.get(key);
			variables.add(variable.getClass().getSimpleName() + " " + key + " = " + variable);
		}
		
		for(String key : variablesInteger.keySet())
		{
			Object variable = variablesInteger.get(key);
			variables.add(variable.getClass().getSimpleName() + " " + key + " = " + variable);
		}
		
		for(String key : variablesDouble.keySet())
		{
			Object variable = variablesDouble.get(key);
			variables.add(variable.getClass().getSimpleName() + " " + key + " = " + variable);
		}
		
		for(String key : variablesBoolean.keySet())
		{
			Object variable = variablesBoolean.get(key);
			variables.add(variable.getClass().getSimpleName() + " " + key + " = " + variable);
		}
		
		for(String key : variablesObject.keySet())
		{
			Object variable = variablesObject.get(key);
			variables.add(variable.getClass().getSimpleName() + " " + key + " = " + variable);
		}
		
		return variables.toArray(new String[variables.size()]);
	}
}