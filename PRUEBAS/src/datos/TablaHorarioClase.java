package datos;

import utilidades.general.Dato;

public class TablaHorarioClase extends Dato
{
	private static final long serialVersionUID = 1L;
	
	public TablaHorarioClase()
	{
		super();
	}
	
	public Integer getCodigoHorario()
	{
		return (Integer)this.fieldValues[0];
	}

	public void setCodigoHorario(Integer codigoHorario)
	{
		this.fieldValues[0] = codigoHorario;
	}

	public String getCodigoMateria()
	{
		return (String)this.fieldValues[1];
	}

	public void setCodigoMateria(String codigoMateria)
	{
		this.fieldValues[1] = codigoMateria;
	}

	public String getMateria()
	{
		return (String)this.fieldValues[2];
	}

	public void setMateria(String materia)
	{
		this.fieldValues[2] = materia;
	}

	public String getDescripcionMateria()
	{
		return (String)this.fieldValues[3];
	}

	public void setDescripcionMateria(String descripcionMateria)
	{
		this.fieldValues[3] = descripcionMateria;
	}

	public Integer getSemestre()
	{
		return (Integer)this.fieldValues[4];
	}

	public void setSemestre(Integer semestre)
	{
		this.fieldValues[4] = semestre;
	}

	public Integer getCodigoCarrera()
	{
		return (Integer)this.fieldValues[5];
	}

	public void setCodigoCarrera(Integer codigoCarrera)
	{
		this.fieldValues[5] = codigoCarrera;
	}

	public String getCarrera()
	{
		return (String)this.fieldValues[6];
	}

	public void setCarrera(String carrera)
	{
		this.fieldValues[6] = carrera;
	}

	public String getCodigoDia()
	{
		return (String)this.fieldValues[7];
	}

	public void setCodigoDia(String codigoDia)
	{
		this.fieldValues[7] = codigoDia;
	}

	public String getDia()
	{
		return (String)this.fieldValues[8];
	}

	public void setDia(String dia)
	{
		this.fieldValues[8] = dia;
	}

	public java.sql.Time getHoraInicio()
	{
		return (java.sql.Time)this.fieldValues[9];
	}

	public void setHoraInicio(java.sql.Time horaInicio)
	{
		this.fieldValues[9] = horaInicio;
	}

	public java.sql.Time getHoraFin()
	{
		return (java.sql.Time)this.fieldValues[10];
	}

	public void setHoraFin(java.sql.Time horaFin)
	{
		this.fieldValues[10] = horaFin;
	}
	
	@Override
	public String getEntityName()
	{
		return "horario_clase";
	}

	@Override
	public String[] getFieldNames()
	{
		return new String[]
		{
			"id_horario_clase",
			"id_materia",
			"materia",
			"materia_completa",
			"semestre",
			"id_carrera",
			"carrera",
			"id_dia",
			"dia",
			"desde_hora",
			"hasta_hora" 
		};
	}

	@Override
	public String[] getFieldTitles()
	{
		return new String[]
  		{
  			"Codigo Horario",
  			"Codigo Materia",
  			"Materia",
  			"Descripcion Materia",
  			"Semestre",
  			"Codigo Carrera",
  			"Carrera",
  			"Codigo Dia",
  			"Dia",
  			"Hora Inicio",
  			"Hora Fin" 
  		};
	}

	@Override
	public Class<?> getFieldType(int campo)
	{
		Class<?>[] tipos = new Class<?>[]
		{
			Integer.class,
			String.class,
			String.class,
			String.class,
			Integer.class,
			Integer.class,
			String.class,
			String.class,
			String.class,
			java.sql.Time.class,
			java.sql.Time.class
		};
		
		return tipos[campo];
	}

	@Override
	public int[] getPrimaryKeys()
	{
		return new int[]{0};
	}

	@Override
	public int[] getForeignKeys()
	{
		return new int[]{};
	}

	@Override
	public Dato[] getForeignTables()
	{
		return new Dato[]{};
	}

	@Override
	public Object getDescription()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.fieldValues[2]).append(" | ");
		sb.append(this.fieldValues[9]).append(" | ");
		sb.append(this.fieldValues[10]);
		
		return sb.toString();
	}

	@Override
	public int[] getMainFields()
	{
		return new int[]{0, 2, 9, 10};
	}
}