package libreriaAG.operadores;



import java.util.List;

import libreriaAG.ag.Gen;
import libreriaAG.ag.Individuo;
import libreriaAG.generales.Cruzador;





import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.util.Errores;
import utilidades.util.Utilidades;
import utilidades.util.Variables;

public final class Cruce implements Cruzador
{
	private int cantidadPuntosCruce;
	
	public Cruce()
	{
		super();
		
		try
		{
			this.cantidadPuntosCruce = Variables.getVariableInt("cantidadPuntosCruce");
		}
		catch (ObjectNotFoundException e)
		{
			this.cantidadPuntosCruce = 0;
		}
	}
	
	@Override
	public void setCantidadPuntosCruce(int cantidadPuntosCruce) throws InvalidValueException
	{
		if(cantidadPuntosCruce < 0)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorPuntosCruce"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		this.cantidadPuntosCruce = cantidadPuntosCruce;
	}

	@Override
	public int getPuntosCruce()
	{
		return this.cantidadPuntosCruce;
	}

	@Override
	public Individuo cruzar(Individuo individuo1, Individuo individuo2) throws InvalidValueException
	{
		if(individuo1 == null || individuo2 == null)
			throw new IllegalArgumentException("Individuos de Cruce no pueden ser null");
		
		if(individuo1.getGenotipo().length != individuo2.getGenotipo().length)
			throw new IllegalArgumentException("Parejas de Cruce Incompatibles");
		
		if(this.cantidadPuntosCruce > individuo1.getGenotipo().length - 1)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorPuntosCruce"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		Individuo resultado = null;
		
		if(this.cantidadPuntosCruce == 0)
		{
			try
			{
				resultado = (Individuo)individuo1.clone();
			}
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
				Errores.dialogo(e);
				System.exit(1);
			}
			
			Gen[] genotipo1 = resultado.getGenotipo();
			Gen[] genotipo2 = individuo2.getGenotipo();
			
			for(int i = 0; i < genotipo1.length; i++)
			{
				int rand = Utilidades.irand(0, 1);
				if(rand == 1)
				{
					genotipo1[i] = genotipo2[i].copiarGen();
				}
			}
		}
		else
		{
			int puntosCorte[] = new int[this.cantidadPuntosCruce];
			List<Integer> listaSorteo = Utilidades.getListInstance();
			
			for(int i = 0; i < individuo1.getGenotipo().length - 1; i++)
				listaSorteo.add(i);
			
			for(int i = 0; i < puntosCorte.length; i++)
			{
				int rand = Utilidades.irand(0, listaSorteo.size() - 1);
				puntosCorte[i] = listaSorteo.remove(rand);
			}
			
			resultado = this.cruzar(individuo1, individuo2, puntosCorte);
		}

		return resultado;
	}

	@Override
	public Individuo cruzar(Individuo individuo1, Individuo individuo2, int[] puntosCorte) throws InvalidValueException
	{
		if(individuo1 == null || individuo2 == null)
			throw new IllegalArgumentException("Individuos de Cruce no pueden ser null");
		
		if(puntosCorte == null)
			throw new IllegalArgumentException("Arreglo de Puntos de Corte no puede ser null");
		
		if(individuo1.getGenotipo().length != individuo2.getGenotipo().length)
			throw new IllegalArgumentException("Parejas de Cruce Incompatibles");
		
		if(puntosCorte.length > individuo1.getGenotipo().length - 1)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorPuntosCruce"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		for(int i = 0; i < puntosCorte.length; i++)
		{
			if(puntosCorte[i] < 0 || puntosCorte[i] > individuo1.getGenotipo().length - 2)
				throw new InvalidValueException("Valor de Puntos de Corte Invalido");
		}
		
		if(puntosCorte.length == 0)
		{
			int rand = Utilidades.irand(0, 1);
			
			try
			{
				if(rand == 0)
					return (Individuo)individuo1.clone();
				else
					return (Individuo)individuo2.clone();
			}
			catch(CloneNotSupportedException e)
			{
				e.printStackTrace();
				Errores.dialogo(e);
				System.exit(1);
			}
		}
		
		Individuo resultado = null;
		try
		{
			resultado = (Individuo)individuo1.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			Errores.dialogo(e);
			System.exit(1);
		}
		
		Gen[] genotipo1 = resultado.getGenotipo();
		Gen[] genotipo2 = individuo2.getGenotipo();
		
		boolean cambiarGen = false;
		
		for(int i = 0; i < genotipo1.length; i++)
		{
			if(cambiarGen) genotipo1[i] = genotipo2[i].copiarGen();
			
			if(Utilidades.arrayContains(puntosCorte, i))
			{
				cambiarGen = !cambiarGen;
			}
		}
		
		return resultado;
	}
}