package utilidades.util;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utilidades.exceptions.ObjectNotFoundException;


/**
 * Esta es una clase no instanciable. Contiene metodos estaticos de uso general.
 * <br>Se utiliza como una libreria de funciones.
 * @author Sergio Morel
 */
public final class Utilidades
{
	private static SimpleDateFormat sdf;
	
	static
	{
		try
		{
			sdf = new SimpleDateFormat(Variables.getVariableString("formatoFecha"));
		}
		catch (ObjectNotFoundException e)
		{
			sdf = new SimpleDateFormat();
		}
	}
	
	private Utilidades()
	{
		super();
	}
	
	/**
	 * Genera un valor aleatorio del tipo <b>int</b> entre los valores
	 * recibidos como parametros, incluyendo los límites.
	 * @param int, int
	 * @return int (Valor aleatorio)
	 * @author Sergio Morel
	 */
	public static int irand(int x,int y)
	{
		int rand, a = 1;
		if(x > y) a = -1;
		rand = (int)(Math.random() * ( (y - x) + a) ) + x;
		return rand;
	}
	
	/**
	 * Genera un valor aleatorio del tipo <b>double</b> entre los valores
	 * recibidos como parametros, incluyendo los límites.
	 * @param double, double
	 * @return double (Valor aleatorio)
	 * @author Sergio Morel
	 */
	public static double drand(double x, double y)
	{
		double rand = Math.random() * (y - x) + x;
		return rand;
	}
	
	/**
	 * La funcion devuelve un arreglo del tipo <b>int[]</b> que contiene los
	 * elementos del arreglo recibido por parametro, pero en orden aleatoria.
	 * <br>La funcion no modifica el arreglo original.
	 * @param int[]
	 * @return int[] (Arreglo ordenado aleatoriamente)
	 * @author Sergio Morel
	 */
	public static int[] desordenarArray(int[] array)
	{
		
		if(array == null) return null;
		
		List<Integer> fuente = getListInstance();
		int[] desordenado = new int[array.length];
		
		for(int i = 0; i < array.length; i++)
		{
			fuente.add(array[i]);
		}
		
		for(int i = 0; i < array.length; i++)
		{
			int rand = irand(0, fuente.size() - 1);
			desordenado[i] = fuente.remove(rand);
		}
		
		return desordenado;
	}
	
	public static Date stringToDate(String fecha) throws ParseException
	{
		try
		{
			sdf.applyPattern(Variables.getVariableString("formatoFecha"));
		}
		catch (ObjectNotFoundException e) {}
		
		return sdf.parse(fecha);
	}
	
	public static Date stringToDate(String fecha, String formato) throws ParseException
	{
		sdf.applyPattern(formato);
		
		return sdf.parse(fecha);
	}
	
	public static String dateToString(Date fecha)
	{
		try 
		{
			sdf.applyPattern(Variables.getVariableString("formatoFecha"));
		} 
		catch (ObjectNotFoundException e) {}
		
		return sdf.format(fecha);
	}
	
	public static String dateToString(Date fecha, String formato)
	{
		sdf.applyPattern(formato);
		
		return sdf.format(fecha);
	}
	
	/**
	 * Esta funcion verifica si un numero del tipo <b>int</b> se encuetra dentro
	 * de un arreglo del tipo <b>int[]</b>
	 * @param int[], int
	 * @return boolean:
	 * <br> - <b>true</b> si el valor se encuentra dentro del arreglo
	 * <br> - <b>false</b> si el valor no se encuentra dentro del arreglo
	 * @author Sergio Morel
	 */
	public static boolean arrayContains(int[] array, int valor)
	{
		if(array == null) return false;
		
		for (int i = 0; i < array.length; i++) 
		{
			if(array[i] == valor) return true;
		}
		
		return false;
	}
	
	public static int findIndex(int[] array, int valor)
	{
		if(array == null) return -1;
		
		for (int i = 0; i < array.length; i++) 
		{
			if(array[i] == valor) return i;
		}
		
		return -1;
	}
	
	/**
	 * Esta funcion verifica si un objeto se encuetra dentro
	 * de un arreglo del mismo tipo
	 * @param E[], E
	 * @return boolean:
	 * <br> - <b>true</b> si el objeto se encuentra dentro del arreglo
	 * <br> - <b>false</b> si el objeto no se encuentra dentro del arreglo
	 * @author Sergio Morel
	 */
	public static <E> boolean arrayContains(E[] array, E valor)
	{
		if(array == null) return false;
		
		for (int i = 0; i < array.length; i++) 
		{
			if(((valor == null) ? array[i] == null : valor.equals(array[i])))
				return true;
		}
		
		return false;
	}
	
	public static <E> int findIndex(E[] array, E valor)
	{
		if(array == null) return -1;
		
		for (int i = 0; i < array.length; i++) 
		{
			if(((valor == null) ? array[i] == null : valor.equals(array[i])))
				return i;
		}
		
		return -1;
	}
	
	public static <E> List<E> arrayToList(E[] array)
	{
		if(array == null) return null;
		
		List<E> lista = getListInstance();
		
		for (int i = 0; i < array.length; i++)
		{
			lista.add(array[i]);
		}
		return lista;
	}
	
	public static int[] joinArrays(int[] array1, int[] array2)
	{
		int[] union = new int[array1.length + array2.length];
		
		for(int i = 0; i < union.length; i++)
		{
			if(i < array1.length)
			{
				union[i] = array1[i];
			}
			else
			{
				union[i] = array2[i - array1.length];
			}
		}
		
		return union;
	}
	
	public static <E> E[] joinArrays(E[] array1, E[] array2)
	{
		@SuppressWarnings("unchecked")
		E[] union = (E[])Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
		
		for(int i = 0; i < union.length; i++)
		{
			if(i < array1.length)
			{
				union[i] = array1[i];
			}
			else
			{
				union[i] = array2[i - array1.length];
			}
		}
		
		return union;
	}
	
	public static <E> List<E> getListInstance()
	{
		return new LinkedList<E>();
	}
	
	public static <K, V> Map<K, V> getMapInstance()
	{
		return new HashMap<K, V>();
	}
}