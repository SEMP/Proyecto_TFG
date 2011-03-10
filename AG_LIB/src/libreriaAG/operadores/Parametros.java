package libreriaAG.operadores;

import java.io.Serializable;

import utilidades.util.Utilidades;

public final class Parametros implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int ciclos;
	private double porcentajeMutacion;
	private boolean elitismo;
	
	public Parametros()
	{
		super();
		this.ciclos = Utilidades.irand(0, 10000);
		this.porcentajeMutacion = Utilidades.drand(0, 100);
		this.elitismo = Utilidades.irand(0, 1) == 1;
	}
	
	public Parametros(int ciclos, double porcentajeMutacion, boolean elitismo)
	{
		super();
		this.ciclos = ciclos;
		this.porcentajeMutacion = porcentajeMutacion;
		this.elitismo = elitismo;
	}

	public int getCiclos()
	{
		return ciclos;
	}

	public void setCiclos(int ciclo)
	{
		this.ciclos = ciclo;
	}
	
	public double getPorcentajeMutacion()
	{
		return porcentajeMutacion;
	}

	public void setPorcentajeMutacion(double porcentajeMutacion)
	{
		this.porcentajeMutacion = porcentajeMutacion;
	}

	public boolean getElitismo()
	{
		return elitismo;
	}

	public void setElitismo(boolean elitismo)
	{
		this.elitismo = elitismo;
	}
}