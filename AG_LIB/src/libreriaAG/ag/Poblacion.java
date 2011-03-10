package libreriaAG.ag;

import java.util.Arrays;
import java.util.Collections;

import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.util.ComparadorDescendente;
import utilidades.util.Errores;
import utilidades.util.Utilidades;
import utilidades.util.Variables;

public abstract class Poblacion implements Cloneable
{
	static
	{
		iniciarVariables();
	}
	
	//Atributos
	private int cantidadIndividuos;
	private double porcentajeMutacion;
	private int cantidadSoluciones;
	private Individuo[] individuos;
	private Individuo[] soluciones;
	private boolean poblacionGenerada;
	
	//Constructores
	public Poblacion() throws InvalidValueException, ObjectNotFoundException
	{
		this.setCantidadSoluciones(Variables.getVariableInt("cantidadSoluciones"));
		this.setCantidadIndividuos(Variables.getVariableInt("cantidadIndividuos"));
		this.setPorcentajeMutacion(Variables.getVariableInt("porcentajeMutacion"));
		this.poblacionGenerada = false;
	}
	
	//Getters y Setters
	public double getPorcentajeMutacion()
	{
		return this.porcentajeMutacion;
	}

	public void setPorcentajeMutacion(double porcentajeMutacion) throws InvalidValueException
	{
		if(porcentajeMutacion < 0 || porcentajeMutacion > 100)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorPorcentajeMutacion"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		this.porcentajeMutacion = porcentajeMutacion;
	}

	public int getCantidadIndividuos()
	{
		return this.cantidadIndividuos;
	}

	public void setCantidadIndividuos(int cantidadIndividuos) throws InvalidValueException
	{
		if(cantidadIndividuos < 1)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorCantidadIndividuos"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		this.cantidadIndividuos = cantidadIndividuos;
		
		this.poblacionGenerada = false;
	}

	public int getCantidadSoluciones()
	{
		return this.cantidadSoluciones;
	}
	
	public void setCantidadSoluciones(int cantidadSoluciones) throws InvalidValueException
	{
		if(cantidadSoluciones < 1)
		{
			try
			{
				throw new InvalidValueException(Variables.getVariableString("errorCantidadSoluciones"));
			}
			catch (ObjectNotFoundException e)
			{
				throw new InvalidValueException();
			}
		}
		
		this.cantidadSoluciones = cantidadSoluciones;
		
		this.soluciones = new Individuo[this.cantidadSoluciones];
	}
	
	private Individuo[] getIndividuos()
	{
		if(!this.poblacionGenerada)
		{
			throw new IllegalStateException("Se debe generar la poblacion antes de obtener sus individuos");
		}
		
		return this.individuos;
	}
	
	public Individuo[] getSoluciones()
	{
		if(!this.poblacionGenerada)
		{
			throw new IllegalStateException("Se debe generar la poblacion antes de obtener las soluciones");
		}
		
		if(this.soluciones != null && !Utilidades.arrayContains(this.soluciones, null))
			Arrays.sort(this.soluciones, Collections.reverseOrder());
		
		Individuo[] soluciones = new Individuo[this.soluciones.length];
		
		for(int i = 0; i < soluciones.length; i++)
		{
			try
			{
				if(this.soluciones[i] != null)
					soluciones[i] = (Individuo)this.soluciones[i].clone();
			}
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
				Errores.dialogo(e);
				System.exit(1);
			}
		}
		
		return soluciones;
	}

	//Metodos
	protected abstract Individuo getNewIndividuoInstance() throws ObjectNotFoundException, InvalidValueException;
	
	public void generarPoblacion() throws InvalidValueException, ObjectNotFoundException
	{
		this.individuos = new Individuo[this.cantidadIndividuos];
		this.soluciones = new Individuo[this.cantidadSoluciones];
		
		for(int i = 0; i < this.individuos.length; i++)
		{
			this.individuos[i] = this.getNewIndividuoInstance();
		}
		
		this.asignarCalificacionRelativa(this.individuos);
		this.guardarMejoresSoluciones();
		
		this.poblacionGenerada = true;
	}
	
	public Poblacion mutacion() throws InvalidValueException
	{
		if(!this.poblacionGenerada)
		{
			throw new IllegalStateException("Se debe generar la poblacion antes de realizar mutacion");
		}
		
		for(int i = 0; i < this.individuos.length; i++)
		{
			double rand = Utilidades.drand(0, 100);
			if(rand < this.porcentajeMutacion)
			{
				this.individuos[i] = this.individuos[i].mutar();
			}
		}
		
		this.asignarCalificacionRelativa(this.individuos);
		this.guardarMejoresSoluciones();
		
		return this;
	}
	
	public Poblacion cruce() throws InvalidValueException
	{
		if(!this.poblacionGenerada)
		{
			throw new IllegalStateException("Se debe generar la poblacion antes de realizar cruce");
		}
		
		int tablaCruce[][] = new int[this.individuos.length][2];
		
		for(int f = 0; f < tablaCruce.length; f++)
		{
			for(int c = 0; c < tablaCruce[f].length; c++)
			{
				tablaCruce[f][c] = this.sortearIndividuo(this.individuos);
			}
		}
		
		Individuo auxiliar[] = new Individuo[this.individuos.length];
		
		for(int i = 0; i < auxiliar.length; i++)
		{
			auxiliar[i] = this.individuos[tablaCruce[i][0]].cruzar(this.individuos[tablaCruce[i][1]]);
		}
		
		this.individuos = auxiliar;
		
		this.asignarCalificacionRelativa(this.individuos);
		this.guardarMejoresSoluciones();
		
		return this;
	}
	
	public Poblacion cruce(int[] puntosCorte) throws InvalidValueException
	{
		if(!this.poblacionGenerada)
		{
			throw new IllegalStateException("Se debe generar la poblacion antes de realizar cruce");
		}
		
		int tablaCruce[][] = new int[this.individuos.length][2];
		
		for(int f = 0; f < tablaCruce.length; f++)
		{
			for(int c = 0; c < tablaCruce[f].length; c++)
			{
				tablaCruce[f][c] = this.sortearIndividuo(this.individuos);
			}
		}
		
		Individuo auxiliar[] = new Individuo[this.individuos.length];
		
		for(int i = 0; i < auxiliar.length; i++)
		{
			auxiliar[i] = this.individuos[tablaCruce[i][0]].cruzar(this.individuos[tablaCruce[i][1]], puntosCorte);
		}
		
		this.individuos = auxiliar;
		
		this.asignarCalificacionRelativa(this.individuos);
		this.guardarMejoresSoluciones();
		
		return this;
	}
	
	public Poblacion cruce(Individuo[] individuos) throws InvalidValueException
	{
		if(!this.poblacionGenerada)
		{
			throw new IllegalStateException("Se debe generar la poblacion antes de realizar cruce");
		}
		
		int tablaCruce[][] = new int[this.individuos.length][2];
		
		Individuo[] poblacionPadre = Utilidades.joinArrays(this.individuos, individuos);
		this.asignarCalificacionRelativa(poblacionPadre);
		
		for(int f = 0; f < tablaCruce.length; f++)
		{
			for(int c = 0; c < tablaCruce[f].length; c++)
			{
				tablaCruce[f][c] = this.sortearIndividuo(poblacionPadre);
			}
		}
		
		Individuo auxiliar[] = new Individuo[this.individuos.length];
		
		for(int i = 0; i < auxiliar.length; i++)
		{
			auxiliar[i] = poblacionPadre[tablaCruce[i][0]].cruzar(poblacionPadre[tablaCruce[i][1]]);
		}
		
		this.individuos = auxiliar;
		
		this.asignarCalificacionRelativa(this.individuos);
		this.guardarMejoresSoluciones();
		
		return this;
	}
	
	public Poblacion cruce(Individuo[] individuos, int[] puntosCruce) throws InvalidValueException
	{
		if(!this.poblacionGenerada)
		{
			throw new IllegalStateException("Se debe generar la poblacion antes de realizar cruce");
		}
		
		int tablaCruce[][] = new int[this.individuos.length][2];
		
		Individuo[] poblacionPadre = Utilidades.joinArrays(this.individuos, individuos);
		this.asignarCalificacionRelativa(poblacionPadre);
		
		for(int f = 0; f < tablaCruce.length; f++)
		{
			for(int c = 0; c < tablaCruce[f].length; c++)
			{
				tablaCruce[f][c] = this.sortearIndividuo(poblacionPadre);
			}
		}
		
		Individuo auxiliar[] = new Individuo[this.individuos.length];
		
		for(int i = 0; i < auxiliar.length; i++)
		{
			auxiliar[i] = poblacionPadre[tablaCruce[i][0]].cruzar(poblacionPadre[tablaCruce[i][1]], puntosCruce);
		}
		
		this.individuos = auxiliar;
		
		this.asignarCalificacionRelativa(this.individuos);
		this.guardarMejoresSoluciones();
		
		return this;
	}
	
	private void guardarMejoresSoluciones() throws InvalidValueException
	{
		this.guardarMejoresIndividuos(this.soluciones, this.individuos);
	}

	private void asignarCalificacionRelativa(Individuo[] individuos) throws InvalidValueException
	{
		if(individuos == null)
			throw new IllegalArgumentException("Arreglo de Individuos no puede ser null");
		
		int cantidadIndividuos = individuos.length;
		double sumaCalificaciones = 0;
		
		for(int i = 0; i < cantidadIndividuos; i++)
		{
			if(individuos[i] != null)
			{
				double calificacionAbsoluta = individuos[i].getCalificacionAbsoluta();
				
				if(calificacionAbsoluta < 0)
					throw new InvalidValueException("Calificacion Absoluta no puede ser negativa");
				
				sumaCalificaciones += calificacionAbsoluta;
			}
		}
		
		if(sumaCalificaciones == 0)
		{
			double calificacion = 100.0 / cantidadIndividuos;
			
			for(int i = 0; i < cantidadIndividuos; i++)
			{
				if(individuos[i] != null)
					individuos[i].setCalificacionRelativa(calificacion);
			}
		}
		else
		{
			sumaCalificaciones /= 100.0;
			
			for(int i = 0; i < cantidadIndividuos; i++)
			{
				if(individuos[i] != null)
				{
					double calificacionAbsoluta = individuos[i].getCalificacionAbsoluta();
					individuos[i].setCalificacionRelativa(calificacionAbsoluta / sumaCalificaciones);
				}
			}
		}
	}
	
	private void guardarMejoresIndividuos(Individuo[] mejores, Individuo[] individuos) throws InvalidValueException
	{
		if(mejores == null || individuos == null)
			throw new IllegalArgumentException("Los arreglos individuo y mejores no deben ser null");
		
		if(mejores.length == 0)
			throw new IllegalArgumentException("Arreglo de mejores individuos no debe ser de tamaño 0");
		
		for(int i = 0; i < individuos.length; i++)
		{
			if(individuos[i] != null && !Utilidades.arrayContains(mejores, individuos[i]))
			{
				int peorIndividuo = this.buscarPeorIndividuo(mejores);
				
				if(mejores[peorIndividuo] == null)
				{
					try
					{
						mejores[peorIndividuo] = (Individuo)individuos[i].clone();
					}
					catch (CloneNotSupportedException e) 
					{
						e.printStackTrace();
						Errores.dialogo(e);
						System.exit(1);
					}
				}
				else
				{
					if(individuos[i].getCalificacionAbsoluta() > mejores[peorIndividuo].getCalificacionAbsoluta())
					{
						try 
						{
							mejores[peorIndividuo] = (Individuo)individuos[i].clone();
						}
						catch (CloneNotSupportedException e) 
						{
							e.printStackTrace();
							Errores.dialogo(e);
							System.exit(1);
						}
					}
				}
			}
		}
		
		this.asignarCalificacionRelativa(mejores);
	}
	
	private int buscarPeorIndividuo(Individuo individuos[])
	{
		if(individuos == null)
			throw new IllegalArgumentException("Arreglo de individuos no debe ser null");
		
		if(individuos.length == 0)
			throw new IllegalArgumentException("Arreglo de individuos no debe ser de tamaño 0");
		
		int menorPosicion = 0;
		
		if(individuos[menorPosicion] == null) return menorPosicion;
		
		double menorCalificacion = individuos[menorPosicion].getCalificacionAbsoluta();
		
		for(int i = 0; i < individuos.length; i++)
		{
			if(individuos[i] == null) return i;
			
			double calificacionAbsoluta = individuos[i].getCalificacionAbsoluta();
			if(calificacionAbsoluta < menorCalificacion)
			{
				menorCalificacion = calificacionAbsoluta;
				menorPosicion = i;
			}
		}
		
		return menorPosicion;
	}
	
	private int sortearIndividuo(Individuo[] individuos)
	{
		double sumaCalificacionRelativa = 0;
		double rand = Utilidades.drand(0, 100);
		
		for(int i = 0; i < individuos.length - 1; i++)
		{
			if(individuos[i] != null)
				sumaCalificacionRelativa += individuos[i].getCalificacionRelativa();
			if(rand <= sumaCalificacionRelativa) return i;
		}
		
		if(individuos[individuos.length - 1] != null)
			return individuos.length - 1;
		else
			throw new IllegalStateException("sumatoria de calificaciones relativas es menor que 100");
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		
		if(obj == null || !obj.getClass().equals(this.getClass())) return false;
		
		boolean igual = false;
		
		Poblacion poblacion = (Poblacion)obj;
		
		igual = 
		(
			this.cantidadIndividuos == poblacion.cantidadIndividuos &&
			this.cantidadSoluciones == poblacion.cantidadSoluciones &&
			this.porcentajeMutacion == poblacion.porcentajeMutacion
		);
		
		if(this.individuos != null)
		{
			for(int i = 0; igual && i < this.individuos.length; i++)
			{
				if(this.individuos[i] != null)
				{
					igual = igual && this.individuos[i].equals(poblacion.individuos[i]);
				}
				else
				{
					igual = igual && (poblacion.individuos[i] == null);
				}
			}
		}
		else
		{
			igual = igual && (poblacion.individuos == null);
		}
		
		if(this.soluciones != null)
		{
			for(int i = 0; igual && i < this.soluciones.length; i++)
			{
				if(this.soluciones[i] != null)
				{
					igual = igual && this.soluciones[i].equals(poblacion.soluciones[i]);
				}
				else
				{
					igual = igual && (poblacion.soluciones[i] == null);
				}
			}
		}
		else
		{
			igual = igual && (poblacion.soluciones == null);
		}
		
		return igual;
	}
	
	@Override
	public int hashCode()
	{
		final int primo = 31;
		int hash = 1;
		
		for(int i = 0; this.individuos != null && i < this.individuos.length; i++)
		{
			hash = primo * hash + this.individuos[i].hashCode();
		}
		
		for(int i = 0; this.soluciones != null && i < this.soluciones.length; i++)
		{
			hash = primo * hash + this.soluciones[i].hashCode();
		}
		
		hash = primo * hash + (this.cantidadIndividuos + "").hashCode();
		hash = primo * hash + (this.cantidadSoluciones + "").hashCode();
		hash = primo * hash + (this.porcentajeMutacion + "").hashCode();
		
		hash = primo * hash + this.getClass().getCanonicalName().hashCode();
		return hash;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Poblacion clon = (Poblacion)super.clone();
		
		clon.cantidadIndividuos = this.cantidadIndividuos;
		clon.porcentajeMutacion = this.porcentajeMutacion;
		clon.cantidadSoluciones = this.cantidadSoluciones;
		
		if(this.individuos != null)
		{
			clon.individuos = new Individuo[this.individuos.length];
			
			for(int i = 0; i < clon.individuos.length; i++)
			{
				clon.individuos[i] = (Individuo)this.individuos[i].clone();
			}
		}
		
		if(this.soluciones != null)
		{
			clon.soluciones = new Individuo[this.soluciones.length];
			
			for(int i = 0; i < clon.soluciones.length; i++)
			{
				if(this.soluciones[i] != null)
					clon.soluciones[i] = (Individuo)this.soluciones[i].clone();
			}
		}
		
		return clon;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		Individuo[] individuos = this.getIndividuos();
		
		if(individuos != null)
		{
			for(int i = 0; i < individuos.length; i++)
			{
				sb.append(i + 1).append(". ").append(individuos[i]);
				if(i != individuos.length - 1) sb.append("\n");
			}
		}
		
		return sb.toString();
	}

	public String toStringSoluciones()
	{
		StringBuffer sb = new StringBuffer();

		Individuo[] soluciones = this.getSoluciones();
		
		if(soluciones != null)
		{
			Arrays.sort(soluciones, new ComparadorDescendente<Individuo>());
			
			for(int i = 0; i < soluciones.length; i++)
			{
				sb.append(i + 1).append(". ").append(soluciones[i]);
				if(i != soluciones.length - 1) sb.append("\n");
			}
		}
		
		return sb.toString();
	}

	private static void iniciarVariables()
	{
		Variables.setVariableInt("cantidadIndividuos", 20);
		Variables.setVariableString("errorCantidadIndividuos", "Debe haber almenos 1 individuo en la población");
		Variables.setVariableInt("porcentajeMutacion", 20);
		Variables.setVariableString("errorPorcentajeMutacion", "Valor de porcentaje de mutacion Inválido");
		Variables.setVariableInt("porcentajeCruce", 20);
		Variables.setVariableString("errorPorcentajeCruce", "Valor de porcentaje de cruce Inválido");
		Variables.setVariableInt("cantidadSoluciones", 5);
		Variables.setVariableString("errorCantidadSoluciones", "Debe haber almenos una solución");
	}
}