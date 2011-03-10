package libreriaAG.operadores;

import libreriaAG.ag.Individuo;
import libreriaAG.ag.Poblacion;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.util.Errores;
import utilidades.util.Utilidades;

public final class Iterador
{
	private Poblacion poblacion;
	private Parametros[] parametros;
	private boolean iterado;

	public Iterador(Poblacion poblacion, Parametros[] parametros)
	{
		super();
		this.setPoblacion(poblacion);
		this.setParametros(parametros);
		this.iterado = false;
	}

	public void iterar() throws InvalidValueException, ObjectNotFoundException
	{
		this.poblacion.generarPoblacion();
		
		for(int i = 0, j = 0; j < this.parametros.length; j++)
		{
			int ciclos = this.parametros[j].getCiclos();
			boolean elitismo = this.parametros[j].getElitismo();
			double porcentajeMutacion = this.parametros[j].getPorcentajeMutacion();
			
			this.poblacion.setPorcentajeMutacion(porcentajeMutacion);
			
			for(; i < ciclos; i++)
			{
				if(elitismo)
					this.poblacion.cruce(this.poblacion.getSoluciones());
				else
					this.poblacion.cruce();
				
				this.poblacion.mutacion();
			}
		}
		
		this.iterado = true;
	}
	
	public void iterar(Poblacion poblacion, Parametros[] parametros) throws InvalidValueException, ObjectNotFoundException
	{
		this.setParametros(parametros);
		this.setPoblacion(poblacion);
		
		this.iterar();
	}
	
	private void setParametros(Parametros[] parametros)
	{
		if(parametros == null || parametros.length < 1 || Utilidades.arrayContains(parametros, null))
			throw new IllegalArgumentException("Vector de parametros invalido");
		
		this.parametros = parametros;
	}
	
	private void setPoblacion(Poblacion poblacion)
	{
		if(poblacion == null)
			throw new IllegalArgumentException("Poblacion no puede ser null");
		
		try
		{
			this.poblacion = (Poblacion)poblacion.clone();
		}
		catch(CloneNotSupportedException e)
		{
			Errores.dialogo(e);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Poblacion getPoblacion()
	{
		try
		{
			return (Poblacion)this.poblacion.clone();
		}
		catch(CloneNotSupportedException e)
		{
			Errores.dialogo(e);
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	
	public Individuo[] getResultados()
	{
		if(!this.iterado) throw new IllegalStateException("Debe iterarse para poder obtener resultado");
		return this.poblacion.getSoluciones();
	}
}