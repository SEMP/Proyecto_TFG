package ag;

import libreriaAG.ag.Gen;
import utilidades.util.Utilidades;

public final class MiGen implements Gen
{
	private int valor;
	
	public MiGen()
	{
		super();
		this.generarGen();
	}
	
	public int getValor()
	{
		return valor;
	}

	@Override
	public Gen copiarGen()
	{
		Gen copia = null;
		
		try
		{
			copia = (Gen)this.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		return copia;
	}

	@Override
	public void generarGen()
	{
		this.valor = Utilidades.irand(0, 100);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		
		if(obj == null || !obj.getClass().equals(this.getClass())) return false;
		
		MiGen gen = (MiGen)obj;
		
		return this.valor == gen.valor;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		MiGen clon = (MiGen)super.clone();
		
		clon.valor = this.valor;
		
		return clon;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.valor);

		return sb.toString();
	}

	@Override
	public boolean esIgualA(Gen gen)
	{
		return this.equals(gen);
	}
}