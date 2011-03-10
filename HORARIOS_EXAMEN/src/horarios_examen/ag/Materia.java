package horarios_examen.ag;

import libreriaAG.ag.Gen;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.util.Errores;
import utilidades.util.Utilidades;
import utilidades.util.Variables;

public final class Materia implements Gen, Comparable<Materia>
{
	static
	{
		iniciarVariables();
	}
	
	//Atributos
	private String nombre;
	private int horario;
	private double dificultad;
	private int[] disponibilidad;
	private int semanas;
	private int resolucionMinutos;
	private boolean genGenerado;
	
	public Materia() throws ObjectNotFoundException
	{
		super();
		this.setNombre("Materia " + this.getNumeroMateria());
		this.setDificultad(1);
		this.setSemanas(Variables.getVariableInt("semanas"));
		this.setResolucionMinutos(Variables.getVariableInt("resolucionMinutos"));
		this.genGenerado = false;
	}
	
	public Materia(int semanas, double dificultad, int[] disponibilidad) throws ObjectNotFoundException
	{
		super();
		this.setNombre("Materia " + this.getNumeroMateria());
		this.setDificultad(dificultad);
		this.setSemanas(semanas);
		this.setResolucionMinutos(Variables.getVariableInt("resolucionMinutos"));
		this.setDisponibilidad(disponibilidad);
		this.genGenerado = false;
	}
	
	public Materia(String nombre, int semanas, double dificultad, int[] disponibilidad) throws ObjectNotFoundException
	{
		super();
		this.setNombre(nombre);
		this.setDificultad(dificultad);
		this.setSemanas(semanas);
		this.setResolucionMinutos(Variables.getVariableInt("resolucionMinutos"));
		this.setDisponibilidad(disponibilidad);
		this.genGenerado = false;
	}

	public String getNombre()
	{
		return nombre;
	}

	private void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public int getHorario()
	{
		if(!this.genGenerado)
			throw new IllegalStateException("Debe generarse el horario antes de leer su valor");
		
		return horario;
	}
	
	public double getDificultad()
	{
		return this.dificultad;
	}

	private void setDificultad(double dificultad)
	{
		if(dificultad <= 0)
			throw new IllegalArgumentException("Dificultad debe ser mayor a 0");
		
		this.dificultad = dificultad;
	}
	
	public void setDisponibilidad(int[] disponibilidad)
	{
		this.genGenerado = false;
		
		if(disponibilidad == null)
		{
			this.disponibilidad = null;
			return;
		}
		
		if(disponibilidad.length % 2 != 0)
			throw new IllegalArgumentException("Todo rango debe tener inicio y final");
		
		this.disponibilidad = new int[disponibilidad.length];
		
		for(int i = 0; i < disponibilidad.length; i++)
		{
			if(disponibilidad[i] < 0 || disponibilidad[i] > this.valorMaximo())
				throw new IllegalArgumentException("Disponibilidad fuera del rango (0, " + this.valorMaximo() + "): " + disponibilidad[i]);
			
			if
			(
			   i < disponibilidad.length - 1 &&
			   i % 2 == 0 &&
			   disponibilidad[i] > disponibilidad[i + 1]
			) throw new IllegalArgumentException("Inicio y final de rangos incoherentes");
			
			this.disponibilidad[i] = disponibilidad[i];
		}
	}
	
	public int[] getDisponibilidad()
	{
		return this.disponibilidad.clone();
	}

	public int getSemanas()
	{
		return semanas;
	}

	public void setSemanas(int semanas)
	{
		if(semanas <= 0)
			throw new IllegalArgumentException("Cantidad de semanas debe ser mayor a 0");
		
		this.semanas = semanas;
		
		this.setDisponibilidad(this.disponibilidad);
	}

	public int getResolucionMinutos()
	{
		return resolucionMinutos;
	}

	public void setResolucionMinutos(int resolucionMinutos)
	{
		if(resolucionMinutos <= 0 || resolucionMinutos > (10080 * this.semanas))
			throw new IllegalArgumentException("Valor inválido para resolucion en minutos");
		
		this.resolucionMinutos = resolucionMinutos;
		
		this.genGenerado = false;
	}

	private int getNumeroMateria()
	{
		try
		{
			int numero = Variables.getVariableInt("numeroMateria");
			Variables.setVariableInt("numeroMateria", numero + 1);
			return numero;
		}
		catch (ObjectNotFoundException e)
		{
			Variables.setVariableInt("numeroMateria", 2);
			return 1;
		}
	}
	
	private int valorMaximo()
	{
		//10080 = 7 * 24 * 60
		return ((int)Math.ceil((10080.0 * this.semanas) / this.resolucionMinutos)) - 1;
	}

	@Override
	public void generarGen()
	{
		this.genGenerado = true;
		
		if(this.disponibilidad == null || this.disponibilidad.length <= 0)
		{
			//En caso que el intervalo divida en forma entera a la hora. 
			if(60 % this.resolucionMinutos == 0)
			{
				int limiteInferior, limiteSuperior;
				
				//Generar horario entre las 7:00 y las 19:00
				limiteInferior = 7 * (60 / this.resolucionMinutos);
				limiteSuperior = 19 * (60 / this.resolucionMinutos);
				int hora = Utilidades.irand(limiteInferior, limiteSuperior);
				
				//Generar dia entre lunes y sabado.
				limiteInferior = 0;
				limiteSuperior = 5;				
				int dia = Utilidades.irand(limiteInferior, limiteSuperior) * 24 * (60 / this.resolucionMinutos);
				
				//Generar entre semanas disponibles.
				limiteInferior = 0;
				limiteSuperior = this.semanas - 1;
				int semana = Utilidades.irand(limiteInferior, limiteSuperior) * 7 * 24 * (60 / this.resolucionMinutos);
				
				this.horario = semana + dia + hora;
			}
			else
			{
				this.horario = Utilidades.irand(0, this.valorMaximo());
			}
		}
		else
		{
			//Generacion de horarios dentro del rango.
			int suma = 0;
			for(int i = 0; i < this.disponibilidad.length - 1; i += 2)
			{
				suma += this.disponibilidad[i + 1] - this.disponibilidad[i] + 1;
			}
			
			int sorteo = Utilidades.irand(0, suma - 1);
			
			for(int i = 0; ; i += 2)
			{
				sorteo -= this.disponibilidad[i + 1] - this.disponibilidad[i] + 1;
				
				if(sorteo < 0)
				{
					this.horario = this.disponibilidad[i + 1] + (sorteo + 1);
					return;
				}
			}
		}
	}
	
	@Override
	public Gen copiarGen()
	{
		try
		{
			return (Gen)this.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			Errores.dialogo(e);
			System.exit(1);
		}
		
		return null;
	}

	@Override
	public boolean esIgualA(Gen gen)
	{
		return this.equals(gen);
	}

	@Override
	public int compareTo(Materia materia)
	{
		return this.getHorario() - materia.getHorario();
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		
		if(obj == null || !obj.getClass().equals(this.getClass())) return false;
		
		boolean igual = false;
		
		Materia materia = (Materia)obj;
		
		igual = 
		(
			((this.nombre == null) ? materia.nombre == null : this.nombre.equals(materia.nombre)) &&
			this.getHorario() == materia.getHorario() &&
			this.dificultad == materia.dificultad &&
			((this.disponibilidad == null) ? materia.disponibilidad == null : true) &&
			this.semanas == materia.semanas &&
			this.resolucionMinutos == materia.resolucionMinutos
		);
		
		for(int i = 0; igual && this.disponibilidad != null && i < this.disponibilidad.length; i++)
		{
			igual = igual && this.disponibilidad[i] == materia.disponibilidad[i];
		}
		
		return igual;
	}
	
	@Override
	public int hashCode()
	{
		final int primo = 31;
		int hash = 1;
		
		for(int i = 0; this.disponibilidad != null && i < this.disponibilidad.length; i++)
		{
			hash = primo * hash + this.disponibilidad[i];
		}
		
		hash = primo * hash + this.nombre.hashCode();
		hash = primo * hash + (this.getHorario() + "").hashCode();
		hash = primo * hash + (this.dificultad + "").hashCode();
		hash = primo * hash + (this.semanas + "").hashCode();
		hash = primo * hash + (this.resolucionMinutos + "").hashCode();
		hash = primo * hash + this.getClass().getCanonicalName().hashCode();
		
		return hash;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		Materia clon = (Materia)super.clone();
		
		clon.nombre = this.nombre;
		clon.horario = this.horario;
		clon.dificultad =  this.dificultad;
		
		clon.semanas = this.semanas;
		clon.resolucionMinutos = this.resolucionMinutos;
		if(this.disponibilidad != null) clon.disponibilidad = this.disponibilidad.clone();
		
		return clon;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.nombre).append(" (");
		sb.append(this.getHorario()).append(" - ");
		sb.append(this.dificultad).append(") ");
		
		return sb.toString();
	}
	
	private static void iniciarVariables()
	{
		Variables.setVariableInt("semanas", 2);
	}
}