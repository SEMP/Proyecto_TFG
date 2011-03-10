package test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import utilidades.general.Dato;
import utilidades.util.Debug;
import utilidades.util.Utilidades;
import datos.Ciudad;

public class Prueba2
{
	public static void main(String[] args) throws ParseException
	{
		prueba13();
		/*
		for(int i = 0; i <= 7; i++)
		{
			prueba6(i);
		}*/
	}
	
	public static void prueba()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 32);
		
		switch(cal.get(Calendar.DAY_OF_WEEK))
		{
			case 1:
			{
				System.out.println("Domingo");
				break;
			}
			
			case 2:
			{
				System.out.println("Lunes");
				break;
			}
			
			case 3:
			{
				System.out.println("Martes");
				break;
			}
			
			case 4:
			{
				System.out.println("Miercoles");
				break;
			}
			
			case 5:
			{
				System.out.println("Jueves");
				break;
			}
			
			case 6:
			{
				System.out.println("Viernes");
				break;
			}
			
			case 7:
			{
				System.out.println("Sabado");
				break;
			}
			
			default: System.out.println("error"); break;
		}
		
		System.out.println(cal.getTime());
	}
	
	public static void prueba2() throws ParseException
	{
		Calendar cal = Calendar.getInstance();
		
		java.util.Date utilDate = new java.util.Date(cal.getTime().getTime());
		java.sql.Timestamp sqlTimeStamp = new Timestamp(cal.getTime().getTime());
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
		java.sql.Time sqlTime = new java.sql.Time(cal.getTime().getTime());
		java.sql.Time mediaNoche = java.sql.Time.valueOf("00:00:00");
		
		System.out.println(utilDate.getClass().getName() + ": " + utilDate + " - " + utilDate.getTime());
		System.out.println(sqlTimeStamp.getClass().getName() + ": " + sqlTimeStamp + " - " + sqlTimeStamp.getTime());
		System.out.println(sqlDate.getClass().getName() + ": " + sqlDate + " - " + sqlDate.getTime());
		System.out.println(sqlTime.getClass().getName() + ": " + sqlTime + " - " + sqlTime.getTime());
		
		int mapeamiento = mapearTimeToInt(sqlTime, 30);
		
		Date fechaInicial = Utilidades.stringToDate("01/03/2010", "dd/MM/yyyy");
		System.out.println("fechaInicial: " + fechaInicial + " - " + fechaInicial.getTime());
		
		Date deltaFecha = mapearIntToDate(mapeamiento, 30);
		System.out.println("deltaFecha: " + deltaFecha + " - " + deltaFecha.getTime());
		
		System.out.println("Mapeamiento: " + mapeamiento);
		System.out.println("Reconversion: " + new Date(deltaFecha.getTime() + fechaInicial.getTime() - mediaNoche.getTime()));
	}
	
	private static int mapearTimeToInt(java.sql.Time sqlTime, int resolucionMinutos)
	{
		int mapeamiento;
		
		java.sql.Time mediaNoche = java.sql.Time.valueOf("00:00:00");
		sqlTime = java.sql.Time.valueOf(sqlTime.toString());
		
		mapeamiento = (int)(sqlTime.getTime() - mediaNoche.getTime());
		mapeamiento /= 60000 * resolucionMinutos;
	
		return mapeamiento;
	}
	
	private static Date mapearIntToDate(int hora, int resolucionMinutos)
	{
		Date mapeamiento = new Date();
		
		long horario = hora * 60000 * resolucionMinutos;
		
		java.sql.Time mediaNoche = java.sql.Time.valueOf("00:00:00");
		
		horario += mediaNoche.getTime();
		
		mapeamiento.setTime(horario);
	
		return mapeamiento;
	}
	
	public static void prueba3()
	{
		//Generacion de horarios dentro del rango.
		Debug.setDebug(true);
		
		int[] disponibilidad = new int[]{33, 33, 369, 369};
		
		int suma = 0, horario = 0;
		for(int i = 0; i < disponibilidad.length - 1; i += 2)
		{
			suma += disponibilidad[i + 1] - disponibilidad[i] + 1;
		}
		
		int sorteo = Utilidades.irand(0, suma - 1);
		Debug.imprimir(sorteo + "");
		
		for(int i = 0; i <= suma; i += 2)
		{
			sorteo -= disponibilidad[i + 1] - disponibilidad[i] + 1;
			
			if(sorteo < 0)
			{
				horario = disponibilidad[i + 1] + (sorteo + 1);
				Debug.imprimir(horario + "");
				return;
			}
		}
	}
	
	public static void prueba4()
	{
		String[] array1= new String[]{"hola,", "que"};
		String[] array2= new String[]{"tal?.", "Como", "te", "va?."};
		
		String[] union = Utilidades.joinArrays(array1, array2);
		
		for(int i = 0; i < union.length; i++)
		{
			System.out.print(union[i]);
			System.out.print(" ");
		}
	}
	
	@SuppressWarnings("unused")
	public static void prueba5()
	{
//		ArrayList<Ciudad> ciudad1 = new ArrayList<Dato>();
//		ArrayList<Dato> dato1 = new ArrayList<Ciudad>();
		List<Dato> list = new ArrayList<Dato>();
		ArrayList<Ciudad> ciudad = new ArrayList<Ciudad>();
//		ArrayList<Dato> dato = ciudad;
		List<Ciudad> ciudadList = ciudad;
		ArrayList<Object> objects = new ArrayList<Object>();
		List<Object> objList = objects;
//		ArrayList<Object> objs = new ArrayList<Ciudad>();
	}
	
	public static int prueba6(int sorteo)
	{
		Debug.setDebug(false);
		int[] disponibilidad = new int[]{444, 444, 2460, 2460, 1020, 1020, 3036, 3036};
		
		int horario = 0;
		
		//Generacion de horarios dentro del rango.
		int suma = 0;
		for(int i = 0; i < disponibilidad.length - 1; i += 2)
		{
			suma += disponibilidad[i + 1] - disponibilidad[i] + 1;
		}
		
//		int sorteo = Utilidades.irand(0, suma - 1);
		int sorteoOriginal = sorteo;
		
//		System.out.println(suma);
		
		for(int i = 0;/* i <= suma*/; i += 2)
		{
			sorteo -= disponibilidad[i + 1] - disponibilidad[i] + 1;
			
			if(sorteo < 0)
			{
				horario = disponibilidad[i + 1] + (sorteo + 1);
				Debug.dialogo("arriba");
				System.out.println(horario + " - " + sorteoOriginal);
				return horario;
			}
		}
//		return 0;
//		Debug.dialogo("abajo");
//		System.out.println(horario + " - " + sorteoOriginal);
	}
	
	public static void prueba7() throws ParseException
	{
		Date fechaInicial = Utilidades.stringToDate("16/04/2010", "dd/MM/yyyy");
		Date fechaFinal =  Utilidades.stringToDate("30/04/2010", "dd/MM/yyyy");
		
//		Calendar calendario = new GregorianCalendar();
		Calendar calendario = Calendar.getInstance();
		System.out.println(calendario.getClass().getSimpleName());
		
		calendario.setTime(fechaInicial);
//		calendario.
//		System.out.println(calendario.getFirstDayOfWeek());
		
		long diferencia = fechaFinal.getTime() - fechaInicial.getTime();
		
		System.out.println(diferencia / 86400000);
//		System.out.println(fechaInicial.compareTo(fechaFinal));
		
		
		System.out.println(calendario.get(Calendar.DAY_OF_WEEK));
	}
	
	public static void prueba8() throws ParseException
	{
		Date fechaInicial = Utilidades.stringToDate("18/04/2010", "dd/MM/yyyy");
		Date fechaFinal =  Utilidades.stringToDate("18/04/2010", "dd/MM/yyyy");
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal1.setTime(fechaInicial);
		cal2.setTime(fechaFinal);
		
		int diaInicial = cal1.get(Calendar.DAY_OF_WEEK) - 1;
		int diaFinal = cal2.get(Calendar.DAY_OF_WEEK) - 1;
		
		if(diaInicial == 0) diaInicial = 7;
		if(diaFinal == 0) diaFinal = 7;
		
		long diferencia = fechaFinal.getTime() - fechaInicial.getTime();
		System.out.println(diferencia / 86400000);
		
		System.out.println(cal1.get(Calendar.WEEK_OF_MONTH));
		System.out.println(cal2.get(Calendar.WEEK_OF_MONTH));
		
		System.out.print("Semanas: ");
		System.out.println(cal2.get(Calendar.WEEK_OF_YEAR) - cal1.get(Calendar.WEEK_OF_YEAR) + 1);
		System.out.println(cal1.getFirstDayOfWeek());
		
//		System.out.println(cal1.get(Calendar.DAY_OF_WEEK));
//		System.out.println(cal2.get(Calendar.DAY_OF_WEEK));
	}
	
	public static void prueba9() throws ParseException
	{
		Date fechaInicial = Utilidades.stringToDate("15/05/2010", "dd/MM/yyyy"); 
		
		Date fecha = Mapeamiento.mapearIntToDate(212, fechaInicial, 30);
		
		System.out.println(fecha);
	}
	
	public static void prueba10() throws ParseException
	{
		Date fecha = Utilidades.stringToDate("03/05/2010", "dd/MM/yyyy");
		Calendar calendario = Calendar.getInstance();
		calendario.setFirstDayOfWeek(Calendar.TUESDAY);
		
		calendario.setTime(fecha);
		
		System.out.println(calendario.get(Calendar.DAY_OF_WEEK));
	}
	
	public static void prueba11() throws ParseException
	{
		Date fechaInicial = Utilidades.stringToDate("31/12/2010", "dd/MM/yyyy");
		Date fecha = Utilidades.stringToDate("26/12/2010 - 15:30", "dd/MM/yyyy - HH:mm");
		
		int horario = Mapeamiento.mapearDateToInt(fecha, fechaInicial, 30);
		
		System.out.println(horario);
	}
	
	public static void prueba12() throws ParseException
	{
		Calendar calendario = Calendar.getInstance();
		
		calendario.setTime(Utilidades.stringToDate("01/01/2010","dd/MM/yyyy"));
		
		System.out.println(calendario.getTime() + " - " + calendario.get(Calendar.WEEK_OF_YEAR));
		System.out.println(calendario.getTime() + " - " + calendario.getActualMaximum(Calendar.WEEK_OF_YEAR));
		
		Integer[] array = new Integer[]{5, 4, 3, null, 1};
		
		Arrays.sort(array);
		
		System.out.println(Arrays.toString(array));
	}
	public static void prueba13()
	{
		System.out.println(System.getProperties());
	}
}