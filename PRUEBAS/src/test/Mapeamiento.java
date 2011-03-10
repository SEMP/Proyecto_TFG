package test;

import java.util.Calendar;
import java.util.Date;

import utilidades.util.Utilidades;

public final class Mapeamiento
{
	public static int mapearTimeToInt(java.sql.Time sqlTime, int resolucionMinutos)
	{
		int horario;
		
		java.sql.Time mediaNoche = java.sql.Time.valueOf("00:00:00");
		sqlTime = java.sql.Time.valueOf(sqlTime.toString());
		
		horario = (int)(sqlTime.getTime() - mediaNoche.getTime());
		horario /= 60000 * resolucionMinutos;
	
		return horario;
	}
	
	/*public static Date mapearIntToDate(int hora, int resolucionMinutos)
	{
		java.sql.Time mediaNoche = java.sql.Time.valueOf("00:00:00");

		long horario = hora * 60000 * resolucionMinutos;
		
		horario += mediaNoche.getTime();

		return new Date(horario);
	}*/
	
	public static Date mapearIntToDate(int hora, Date fechaInicial, int resolucionMinutos)
	{
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaInicial);
		
		int dia_semana = calendario.get(Calendar.DAY_OF_WEEK) - 1;
		
		if(dia_semana == 0) dia_semana = 7;
		
		if(dia_semana != 1)
		{
			calendario.add(Calendar.DAY_OF_MONTH, 1 - dia_semana);
		}
		
		int minutos = hora * resolucionMinutos;
		
		int dias = minutos / 1440;
		
		minutos -= 1440 * dias;
		
		calendario.add(Calendar.DAY_OF_MONTH, dias);
		calendario.add(Calendar.MINUTE, minutos);
		
		return calendario.getTime();
	}
	
	public static int mapearDateToInt(Date fecha, Date fechaInicial, int resolucionMinutos)
	{
		Calendar calInicial = Calendar.getInstance();
		Calendar calFecha = Calendar.getInstance();
		calInicial.setTime(fechaInicial);
		calFecha.setTime(fecha);
		
		int dia_semana = calInicial.get(Calendar.DAY_OF_WEEK) - 1;
		
		if(dia_semana == 0) dia_semana = 7;
		
		if(dia_semana != 1)
		{
			calInicial.add(Calendar.DAY_OF_MONTH, 1 - dia_semana);
		}
		
		int dia = 0;
		
		if(calInicial.get(Calendar.YEAR) == calFecha.get(Calendar.YEAR))
		{
			dia = calFecha.get(Calendar.DAY_OF_YEAR) - calInicial.get(Calendar.DAY_OF_YEAR);
		}
		else
		{
			int anhoInicial = calInicial.get(Calendar.YEAR);
			int anhoFecha = calFecha.get(Calendar.YEAR);
			
			for(int i = anhoInicial; i <= anhoFecha; i++)
			{
				if(i == anhoInicial)
				{
					dia = calInicial.getActualMaximum(Calendar.DAY_OF_YEAR) - calInicial.get(Calendar.DAY_OF_YEAR); 
				}
				else
				{
					Calendar auxiliar = Calendar.getInstance();
					auxiliar.set(Calendar.YEAR, i);
					
					if(i != anhoFecha)
					{
						dia += auxiliar.getActualMaximum(Calendar.DAY_OF_YEAR);
					}
					else
					{
						dia += calFecha.get(Calendar.DAY_OF_YEAR);
					}
				}
			}
		}
		
		int hora = calFecha.get(Calendar.HOUR_OF_DAY);
		int minuto = calFecha.get(Calendar.MINUTE);
		
		int horario = dia * 1440;
		horario += hora * 60;
		horario += minuto;
		horario /= resolucionMinutos;
		
		return horario;
	}
	
	/*public static Date addDates(Date fecha1, Date fecha2)
	{
		java.sql.Time mediaNoche = java.sql.Time.valueOf("00:00:00");
		
		return new Date(fecha1.getTime() + fecha2.getTime() - mediaNoche.getTime());
	}*/
	
	public static int diaToInt(String dia)
	{
		String[] dias = new String[]
		{
			"LUN",
			"MAR",
			"MIE",
			"JUE",
			"VIE",
			"SAB",
			"DOM"
		};
		
		int resultado = Utilidades.findIndex(dias, dia);
		
		if(resultado < 0) throw new IllegalArgumentException("Codigo de dia invalido");
		
		return resultado;
	}
}