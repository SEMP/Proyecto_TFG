package horarios_examen.datos;

import horarios_examen.ag.Materia;
import horarios_examen.mapeamiento.Carrera;
import horarios_examen.mapeamiento.Mapeamiento;

import java.util.Date;

import utilidades.util.Utilidades;

public final class SolucionHorario
{
	private String nombre;
	private Date horario;
	private Carrera carrera;
	private int semestre;
	
	public SolucionHorario(Materia materia, DatoGenotipo datoGenotipo)
	{
		super();
		
		this.horario = Mapeamiento.mapearIntToDate(materia.getHorario(), datoGenotipo.getFechaInicio(), materia.getResolucionMinutos());
		this.nombre = materia.getNombre();
		this.carrera = datoGenotipo.getCarrera();
		this.semestre = datoGenotipo.getSemestre();
	}

	public String getNombre()
	{
		return nombre;
	}

	public Date getHorario()
	{
		return horario;
	}
	
	public Carrera getCarrera()
	{
		return carrera;
	}

	public int getSemestre()
	{
		return semestre;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.nombre);
		sb.append(" (").append(this.carrera).append(" - ");
		sb.append(this.semestre).append("º semestre)").append(" : ");
		sb.append(Utilidades.dateToString(this.horario, "EEEE, dd/MM/yyyy - HH:mm"));
		
		return sb.toString();
	}
}