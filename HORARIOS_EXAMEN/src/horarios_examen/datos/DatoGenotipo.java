package horarios_examen.datos;

import horarios_examen.mapeamiento.Carrera;

import java.util.Date;

public final class DatoGenotipo implements Cloneable
{
	private Date fechaInicio;
	private Date fechaFinal;
	private Carrera carrera;
	private int semestre;
	
	public DatoGenotipo()
	{
		super();
	}
	
	public DatoGenotipo(Carrera carrera, int semestre, Date fechaInicio, Date fechaFinal)
	{
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
		this.carrera = carrera;
		this.semestre = semestre;
	}
	
	public Date getFechaInicio()
	{
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio)
	{
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal()
	{
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal)
	{
		this.fechaFinal = fechaFinal;
	}

	public Carrera getCarrera()
	{
		return carrera;
	}

	public void setCarrera(Carrera carrera)
	{
		this.carrera = carrera;
	}

	public int getSemestre()
	{
		return semestre;
	}

	public void setSemestre(int semestre)
	{
		this.semestre = semestre;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !obj.getClass().equals(this.getClass())) return false;
		
		boolean igual = false;
		
		DatoGenotipo datoGenotipo = (DatoGenotipo)obj;
		
		igual =
		(
			((this.fechaInicio == null) ? datoGenotipo.fechaInicio == null : this.fechaInicio.equals(datoGenotipo.fechaInicio)) &&
			((this.fechaFinal == null) ? datoGenotipo.fechaFinal == null : this.fechaFinal.equals(datoGenotipo.fechaFinal)) &&
			((this.carrera == null) ? datoGenotipo.carrera == null : this.carrera == datoGenotipo.carrera) &&
			(this.semestre == datoGenotipo.semestre)
		);
		
		return igual;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((this.fechaInicio == null) ? 0 : this.fechaInicio.hashCode());
		result = prime * result	+ ((this.fechaFinal == null) ? 0 : this.fechaFinal.hashCode());
		result = prime * result	+ ((this.carrera == null) ? 0 : this.carrera.hashCode());
		result = prime * result + (Integer.valueOf(this.semestre).hashCode());
		
		return result;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		DatoGenotipo clon = (DatoGenotipo)super.clone();
		
		if(this.fechaInicio != null) clon.fechaInicio = (Date)this.fechaInicio.clone();
		if(this.fechaFinal != null) clon.fechaFinal = (Date)this.fechaFinal.clone();
		clon.carrera = this.carrera;
		clon.semestre = this.semestre;
		
		return clon;
	}
}