package horarios_examen.generadores;

import horarios_examen.ag.Materia;
import horarios_examen.baseDatos.HorariosDAO;
import horarios_examen.datos.DatoGenotipo;
import horarios_examen.datos.TablaHorarioClase;
import horarios_examen.mapeamiento.Mapeamiento;
import horarios_examen.operadores.CalificarHorarioSemestre;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import libreriaAG.ag.Gen;
import libreriaAG.generales.Calificador;
import utilidades.exceptions.DataAccessException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.general.Dato;
import utilidades.util.Errores;
import utilidades.util.Utilidades;

public final class GeneradorSemestral implements Generador
{
	private static Map<DatoGenotipo, Gen[]> genotipos = Utilidades.getMapInstance(); 
	private static HorariosDAO dao = new HorariosDAO();
	private boolean considerarDificultad;
	private DatoGenotipo datoGenotipo;
	
	public GeneradorSemestral(DatoGenotipo datoGenotipo)
	{
		super();
		this.datoGenotipo = datoGenotipo;
		this.considerarDificultad = true;//TODO true
	}
	
	public void setConsiderarDificultad(boolean considerarDificultad)
	{
		this.considerarDificultad = considerarDificultad; 
	}

	@Override
	public Gen getGen()
	{
		try
		{
			Materia materia = new Materia();
			
			materia.setResolucionMinutos(5);
			materia.generarGen();
			
			return materia;
		}
		catch (ObjectNotFoundException e) 
		{
			e.printStackTrace();
			Errores.dialogo(e);
			System.exit(1);
			return null;
		}
	}

	@Override
	public Gen[] getGenotipo()
	{
		Gen[] genotipo;
		
		try
		{
			if(!genotipos.containsKey(this.datoGenotipo))
			{
				TablaHorarioClase horarioClase = new TablaHorarioClase();
				horarioClase.setSemestre(this.datoGenotipo.getSemestre());
				horarioClase.setCodigoCarrera(this.datoGenotipo.getCarrera().getCodigo());
				
				Dato[] datos = dao.consultar(horarioClase);
				
				if(datos.length <= 0)
				{
					throw new ObjectNotFoundException("No se cuenta con el horario en la base de datos");
				}
				
				genotipo = this.datosToGenes(datos);
				
				genotipos.put(this.datoGenotipo, genotipo);
			}
			else
			{
				genotipo = genotipos.get(this.datoGenotipo);
			}
			
			for(int i = 0; i < genotipo.length; i++)
			{
				genotipo[i].generarGen();
			}
		}
		catch (ObjectNotFoundException e) 
		{
			e.printStackTrace();
			Errores.dialogo(e);
			System.exit(1);
			return null;
		}
		catch(DataAccessException e)
		{
			e.printStackTrace();
			Errores.dialogo(e);
			System.exit(1);
			return null;
		}
		
		return genotipo;
	}

	private Gen[] datosToGenes(Dato[] datos) throws ObjectNotFoundException, DataAccessException
	{
		List<String> codigoMaterias = Utilidades.getListInstance();
		List<Materia> genes = Utilidades.getListInstance();
		int semanas = this.obtenerSemanas();
		
		for(int i = 0; i < datos.length; i++)
		{
			TablaHorarioClase horario = (TablaHorarioClase)datos[i];
			
			int posicion = codigoMaterias.indexOf(horario.getCodigoMateria());
			
			if(posicion < 0)
			{
				double dificultad = 1;
				
				if(this.considerarDificultad) dificultad = this.obtenerDificultad(horario);
				String nombre = horario.getMateria().trim();			
				Materia materia = new Materia(nombre, semanas, dificultad, null);
				int[] disponibilidad = this.obtenerDisponibilidad(materia, horario, semanas);
				
				materia.setSemanas(semanas);
				materia.setDisponibilidad(disponibilidad);
				
				codigoMaterias.add(horario.getCodigoMateria());
				genes.add(materia);
			}
			else
			{
				Materia materia = genes.get(posicion);
				
				int[] nuevaDisponibilidad = Utilidades.joinArrays(materia.getDisponibilidad(), obtenerDisponibilidad(materia, horario, semanas));
				
				materia.setDisponibilidad(nuevaDisponibilidad);
			}
		}
		
		for(Materia materia : genes)
		{
			materia.setDisponibilidad(this.acotarDisponibilidad(materia.getDisponibilidad(), materia.getResolucionMinutos()));
		}
		
		for(Materia materia : genes)
		{
			materia.setDisponibilidad(this.manejarDisponibilidadVacias(materia.getDisponibilidad(), materia.getResolucionMinutos()));
		}
		
		
		
		return genes.toArray(new Gen[genes.size()]);
	}
	
	private int[] obtenerDisponibilidad(Materia materia, TablaHorarioClase horario, int semanas)
	{
		int[] disponibilidad = new int[2 * semanas];
		
		int hora = Mapeamiento.mapearTimeToInt(horario.getHoraInicio(), materia.getResolucionMinutos());
		int dia = Mapeamiento.diaToInt(horario.getCodigoDia());
		dia *= 24 * (60 / materia.getResolucionMinutos());
		
		for(int i = 0; i < disponibilidad.length; i += 2)
		{
			int semana = (i / 2) * 7 * 24 * (60 / materia.getResolucionMinutos());
			disponibilidad[i] = disponibilidad[i + 1] = semana + dia + hora;
		}
		
		return disponibilidad;
	}
	
	private double obtenerDificultad(TablaHorarioClase thc) throws DataAccessException
	{
		Double dificultad = dao.getDificultad(thc.getCodigoMateria());
		
		if(dificultad == null) dificultad = 2.5;
		
		return dificultad;
	}
	
	private int obtenerSemanas()
	{
		Date fechaInicial = this.datoGenotipo.getFechaInicio();
		Date fechaFinal =  this.datoGenotipo.getFechaFinal();
		
		Calendar calInicial = Calendar.getInstance();
		Calendar calFinal = Calendar.getInstance();

		calInicial.setTime(fechaInicial);
		calFinal.setTime(fechaFinal);
		
		int semanas = 0;
		
		if(calInicial.get(Calendar.YEAR) == calFinal.get(Calendar.YEAR))
		{
			int semana2 = calFinal.get(Calendar.WEEK_OF_YEAR);
			int semana1 = calInicial.get(Calendar.WEEK_OF_YEAR);
			int maximoSemana = calInicial.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			if(semana1 == 1 && calInicial.get(Calendar.DAY_OF_YEAR) > 7) semana1 = maximoSemana + 1;
			if(semana2 == 1 && calFinal.get(Calendar.DAY_OF_YEAR) > 7) semana2 = maximoSemana + 1;
			
			semanas = semana2 - semana1;
		}
		else
		{
			int anhoInicial = calInicial.get(Calendar.YEAR);
			int anhoFinal = calFinal.get(Calendar.YEAR);
			
			for(int i = anhoInicial; i <= anhoFinal; i++)
			{
				if(i == anhoInicial)
				{
					int semana = calInicial.get(Calendar.WEEK_OF_YEAR);
					int maximoSemana = calInicial.getActualMaximum(Calendar.WEEK_OF_YEAR);
					
					if(semana == 1 && calInicial.get(Calendar.DAY_OF_YEAR) > 7) semana = maximoSemana + 1;
					
					semanas = maximoSemana - semana;
				}
				else
				{
					Calendar auxiliar = Calendar.getInstance();
					auxiliar.set(Calendar.YEAR, i);
					
					if(i != anhoFinal)
					{
						semanas += auxiliar.getActualMaximum(Calendar.WEEK_OF_YEAR);
					}
					else
					{
						semanas += calFinal.get(Calendar.WEEK_OF_YEAR);
					}
				}
			}
		}
		
		semanas++;
		
		return semanas;
	}
	
	private int[] acotarDisponibilidad(int[] disponibilidad, int resolucionMinutos)
	{
		List<Integer> listaDisponibilidad = Utilidades.getListInstance();
		int menorValor = Mapeamiento.mapearDateToInt(this.datoGenotipo.getFechaInicio(), this.datoGenotipo.getFechaInicio(), resolucionMinutos);
		
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.setTime(this.datoGenotipo.getFechaFinal());
		fechaFinal.set(Calendar.HOUR_OF_DAY, 23);
		fechaFinal.set(Calendar.MINUTE, 59);
		
		int mayorValor = Mapeamiento.mapearDateToInt(fechaFinal.getTime(), this.datoGenotipo.getFechaInicio(), resolucionMinutos);
		
		if(disponibilidad == null || disponibilidad.length == 0) return new int[]{};
		
		for(int i = 0; i < disponibilidad.length; i += 2)
		{
			int rangoMenor = menorValor;
			int rangoMayor = mayorValor;
			
			if(disponibilidad[i] <= mayorValor && disponibilidad[i + 1] >= menorValor)
			{
				if(disponibilidad[i] >= menorValor) rangoMenor = disponibilidad[i];
				if(disponibilidad[i + 1] <= mayorValor) rangoMayor = disponibilidad[i + 1];
				
				listaDisponibilidad.add(rangoMenor);
				listaDisponibilidad.add(rangoMayor);
			}
		}
		
		int newDisponibilidad[] = new int[listaDisponibilidad.size()];
		
		for(int i = 0; i < newDisponibilidad.length; i++)
		{
			newDisponibilidad[i] = listaDisponibilidad.get(i);
		}
		
		return newDisponibilidad;
	}
	
	private int[] manejarDisponibilidadVacias(int[] disponibilidad, int resolucionMinutos)
	{
		if(disponibilidad != null && disponibilidad.length != 0) return disponibilidad;
		
		int menorValor = Mapeamiento.mapearDateToInt(this.datoGenotipo.getFechaInicio(), this.datoGenotipo.getFechaInicio(), resolucionMinutos);
		
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.setTime(this.datoGenotipo.getFechaFinal());
		fechaFinal.set(Calendar.HOUR_OF_DAY, 23);
		fechaFinal.set(Calendar.MINUTE, 59);
		
		int mayorValor = Mapeamiento.mapearDateToInt(fechaFinal.getTime(), this.datoGenotipo.getFechaInicio(), resolucionMinutos);
		
		return new int[]{menorValor, mayorValor};
	}

	@Override
	public Calificador getCalificador()
	{
		return new CalificarHorarioSemestre();
	}
}