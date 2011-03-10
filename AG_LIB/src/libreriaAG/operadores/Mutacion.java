package libreriaAG.operadores;

import libreriaAG.ag.Gen;
import libreriaAG.ag.Individuo;
import libreriaAG.generales.Mutador;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.util.Errores;
import utilidades.util.Utilidades;
import utilidades.util.Variables;


public final class Mutacion implements Mutador
{
	private int intensidadMutacion;
	private boolean modificarOriginal;
	
	public Mutacion()
	{
		super();
		
		this.modificarOriginal = true;
		
		try
		{
			this.intensidadMutacion = Variables.getVariableInt("intensidadMutacion");
		}
		catch (ObjectNotFoundException e)
		{
			this.intensidadMutacion = 0;
		}
	}

	@Override
	public void setModificarOriginal(boolean modificarOriginal)
	{
		this.modificarOriginal = modificarOriginal;
	}
	
	@Override
	public double getIntensidadMutacion()
	{
		return this.intensidadMutacion;
	}

	@Override
	public void setIntensidadMutacion(int intensidadMutacion) throws InvalidValueException
	{
		if(intensidadMutacion > 100 || intensidadMutacion < 0)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorIntensidadMutacion"));
			}
			catch(ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		this.intensidadMutacion = intensidadMutacion;
	}

	@Override
	public Individuo mutar(Individuo individuo)
	{
		if(individuo == null)
			throw new IllegalArgumentException("no se puede mutar un individuo null");
		
		Individuo resultado = null;
		
		try
		{
			resultado = (this.modificarOriginal) ? individuo : (Individuo)individuo.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			Errores.dialogo(e);
			System.exit(1);
		}
		
		Gen[] genotipo = resultado.getGenotipo();
		
		if(this.intensidadMutacion == 0)
		{
			int rand = Utilidades.irand(0, genotipo.length - 1);
			genotipo[rand].generarGen();
		}
		else
		{
			for(int i = 0; i < genotipo.length; i++)
			{
				double rand = Utilidades.drand(0, 100);
				if(rand < this.intensidadMutacion)
				{
					genotipo[i].generarGen();
				}
			}
		}
		
		return resultado;
	}

	@Override
	public Individuo mutar(Individuo individuo, int intensidadMutacion) throws InvalidValueException
	{
		if(individuo == null)
			throw new IllegalArgumentException("no se puede mutar un individuo null");
		
		if(intensidadMutacion > 100 || intensidadMutacion < 0)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorIntensidadMutacion"));
			}
			catch(ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		Individuo resultado = null;
		
		try
		{
			resultado = (this.modificarOriginal) ? individuo : (Individuo)individuo.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			Errores.dialogo(e);
			System.exit(1);
		}
		
		Gen[] genotipo = resultado.getGenotipo();
		
		if(intensidadMutacion == 0)
		{
			int rand = Utilidades.irand(0, genotipo.length - 1);
			genotipo[rand].generarGen();
		}
		else
		{
			for(int i = 0; i < genotipo.length; i++)
			{
				double rand = Utilidades.drand(0, 100);
				if(rand <= intensidadMutacion)
				{
					genotipo[i].generarGen();
				}
			}
		}
		
		return resultado;
	}
}