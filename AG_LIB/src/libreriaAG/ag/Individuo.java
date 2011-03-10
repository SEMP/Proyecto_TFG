package libreriaAG.ag;

import libreriaAG.generales.Calificador;
import libreriaAG.generales.Cruzador;
import libreriaAG.generales.Mutador;
import libreriaAG.operadores.GerenciadorOperaciones;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.util.Variables;

public abstract class Individuo implements Cloneable, Comparable<Individuo>
{
	static
	{
		iniciarVariables();		
	}
	
	//Atributos
	private Gen[] genotipo;
	private double calificacionAbsoluta;
	private double calificacionRelativa;
	private Mutador mutador;
	private Cruzador cruzador;
	private boolean calificado;
	
	//Constructores
	public Individuo() throws ObjectNotFoundException, InvalidValueException
	{
		super();
		
		int cantidadGenes = Variables.getVariableInt("cantidadGenes");
		this.mutador = GerenciadorOperaciones.getMutadorInstance();
		this.cruzador = GerenciadorOperaciones.getCruzadorInstance();
		this.calificacionRelativa = 0;
		this.calificado = false;
		
		if(cantidadGenes < 1)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorGenotipo"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}

		this.genotipo = new Gen[cantidadGenes];
		for(int i = 0; i < this.genotipo.length; i++)
		{
			genotipo[i] = getNewGenInstance();
		}
	}
	
	public Individuo(int cantidadGenes) throws InvalidValueException, ObjectNotFoundException
	{
		this.mutador = GerenciadorOperaciones.getMutadorInstance();
		this.cruzador = GerenciadorOperaciones.getCruzadorInstance();
		this.calificacionRelativa = 0;
		this.calificado = false;
		
		if(cantidadGenes < 1)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorGenotipo"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		this.genotipo = new Gen[cantidadGenes];
		for(int i = 0; i < this.genotipo.length; i++)
		{
			this.genotipo[i] = getNewGenInstance();
		}
	}
	
	public Individuo(Gen[] genotipo) throws InvalidValueException, ObjectNotFoundException
	{
		if(genotipo == null)
			throw new IllegalArgumentException("Arreglo de Genes no debe ser null");
		
		if(genotipo.length < 1)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorGenotipo"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		for(int i = 0; i < genotipo.length; i++)
		{
			if(genotipo[i] == null)
				throw new IllegalArgumentException("Arreglo de Genes no debe contener elementos null");
		}
		
		this.mutador = GerenciadorOperaciones.getMutadorInstance();
		this.cruzador = GerenciadorOperaciones.getCruzadorInstance();
		this.calificado = false;
		
		this.genotipo = new Gen[genotipo.length];
		for(int i = 0; i < genotipo.length; i++)
		{
			this.genotipo[i] = genotipo[i].copiarGen();
		}
		
		this.calificacionRelativa = 0;
	}

	//Getters Setters
	public Gen[] getGenotipo()
	{
		return this.genotipo;
	}

	public double getCalificacionRelativa()
	{
		return this.calificacionRelativa;
	}
	
	public void setCalificacionRelativa(double calificacionRelativa)
	{
		this.calificacionRelativa = calificacionRelativa;
	}
	
	public double getCalificacionAbsoluta()
	{
		this.calificarIndividuo();
		return this.calificacionAbsoluta;
	}
	
	//Metodos
	protected abstract Gen getNewGenInstance();
	
	public void calificarIndividuo()
	{
		if(!this.calificado)
		{
			this.calificacionAbsoluta = this.getCalificadorInstance().calificar(this.genotipo);
			this.calificado = true;
		}
	}
	
	protected abstract Calificador getCalificadorInstance();
	
	public Individuo mutar()
	{
		Individuo mutado = this.mutador.mutar(this);
		
		this.calificado = false;
		mutado.calificado = false;
		
		return mutado;
	}
	
	public Individuo mutar(int intensidadMutacion) throws InvalidValueException
	{
		Individuo mutado = this.mutador.mutar(this, intensidadMutacion);
		
		this.calificado = false;
		mutado.calificado = false;
		
		return mutado;
	}
	
	public Individuo cruzar(Individuo pareja) throws InvalidValueException
	{
		Individuo hijo = this.cruzador.cruzar(this, pareja);
		
		hijo.calificado = false;
		
		return hijo;
	}
	
	public Individuo cruzar(Individuo pareja, int[] puntosCorte) throws InvalidValueException
	{
		Individuo hijo = this.cruzador.cruzar(this, pareja, puntosCorte);
		
		hijo.calificado = false;
		
		return hijo;
	}
	
	/**
	 * Realiza la comparacion de la calificacion absoluta del individuo.
	 * <br>Que <b>a.compareTo(b)</b> sea cero no implica que
	 * <b>a.equals(b)</b> sea verdadero
	 * 
	 * @author Sergio Morel
	 */
	@Override
	public int compareTo(Individuo individuo)
	{
		double calificacionAbsoluta1 = this.getCalificacionAbsoluta();
		double calificacionAbsoluta2 = individuo.getCalificacionAbsoluta();
		
		if(calificacionAbsoluta1 < calificacionAbsoluta2)
		{
			return -1;
		}
		else if(calificacionAbsoluta1 > calificacionAbsoluta2)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		
		if(obj == null || !obj.getClass().equals(this.getClass())) return false;
		
		boolean igual = false;
		
		Individuo individuo = (Individuo)obj;
		
		igual = this.getCalificacionAbsoluta() == individuo.getCalificacionAbsoluta();
		
		for(int i = 0; igual && i < this.genotipo.length; i++)
		{
			igual = igual && this.genotipo[i].esIgualA(individuo.genotipo[i]);
		}
		
		return igual;
	}
	
	@Override
	public int hashCode()
	{
		final int primo = 31;
		int hash = 1;
		
		for(int i = 0; i < this.genotipo.length; i++)
		{
			hash = primo * hash + this.genotipo[i].hashCode();
		}
		
		hash = primo * hash + (this.getCalificacionAbsoluta() + "").hashCode();
		hash = primo * hash + this.getClass().getCanonicalName().hashCode();
		
		return hash;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Individuo clon = (Individuo)super.clone();
		
		clon.calificacionAbsoluta = this.calificacionAbsoluta;
		clon.calificacionRelativa = this.calificacionRelativa;
		clon.genotipo =  new Gen[this.genotipo.length];
		clon.mutador = this.mutador;
		clon.cruzador = this.cruzador;
		
		for(int i = 0; i < clon.genotipo.length; i++)
		{
			clon.genotipo[i] = this.genotipo[i].copiarGen();
		}
		
		return clon;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(" | ");
		
		for(int i = 0; i < this.genotipo.length; i++)
		{
			sb.append(this.genotipo[i]).append(" ");
		}
		
		sb.append(" | ").append(this.getCalificacionAbsoluta());
		sb.append(" | ").append(this.calificacionRelativa).append("%");
		
		return sb.toString();
	}
	
	private static void iniciarVariables()
	{
		Variables.setVariableInt("cantidadGenes", 6);
		Variables.setVariableString("errorGenotipo", "Genotipo Debe Contener Almenos 1 Gen");
	}
}