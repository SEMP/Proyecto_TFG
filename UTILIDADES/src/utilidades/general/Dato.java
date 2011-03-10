package utilidades.general;

import java.io.Serializable;

import utilidades.util.Utilidades;

public abstract class Dato implements Cloneable, Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected String[] fieldNames;
	protected String[] fieldTitles;
	protected Object[] fieldValues;
	protected Dato[] foreignTables;
	
	public Dato()
	{
		super();
		
		this.fieldNames = this.getFieldNames();
		this.fieldTitles = this.getFieldTitles();
		this.fieldValues = new Object[this.fieldNames.length];
		this.foreignTables = this.getForeignTables();
	}
	
	public abstract String getEntityName();
	public abstract String[] getFieldNames();
	public abstract String[] getFieldTitles();
	public abstract int[] getPrimaryKeys();
	public abstract int[] getForeignKeys();
	public abstract Dato[] getForeignTables();
	public abstract int[] getMainFields();
	public abstract Object getDescription();
	public abstract Class<?> getFieldType(int campo);
	
	public int getNumberOfFields()
	{
		return this.fieldNames.length;
	}

	public String getFieldName(int campo)
	{
		return this.fieldNames[campo];
	}
	
	public String getFieldTitle(int columna) 
	{
		return this.fieldTitles[columna];
	}
	
	public Object getFieldValue(int campo)
	{
		return this.fieldValues[campo];
	}
	
	public Object[] getFieldValues()
	{
		return this.fieldValues;
	}
	
	public Dato getForeignTable(int posicion)
	{
		return this.foreignTables[posicion];
	}
	
	public void setFieldValue(Object valor, int campo)
	{ 
		this.fieldValues[campo] = valor;
	}
	
	public void loadValues(Object[] valores)
	{
		if(valores == null)
			throw new IllegalArgumentException("Array de valores null");
		
		if(valores.length != this.getNumberOfFields())
			throw new IllegalArgumentException("Cantidad de datos invalida");
		
		this.fieldValues = new Object[valores.length];
		
		for (int i = 0; i < valores.length; i++)
		{
			if(valores[i] == null) this.fieldValues[i] = null;
			else if(valores[i].getClass().equals(this.getFieldType(i)))
			{
				this.fieldValues[i] = valores[i]; 
			}
			else throw new IllegalArgumentException("Tipo de dato no coincide en posicion " + i);
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !obj.getClass().equals(this.getClass())) return false;
		
		boolean igual = true;
		
		Dato tabla = (Dato)obj;
		
		for(int i = 0; i < this.fieldValues.length; i++)
		{
			igual = igual && ((this.fieldValues[i] == null) ? tabla.fieldValues[i] == null : this.fieldValues[i].equals(tabla.fieldValues[i]));
		}
		
		for(int i = 0; i < this.foreignTables.length; i++)
		{
			igual = igual && ((this.foreignTables[i] == null) ? tabla.foreignTables[i] == null : this.foreignTables[i].equals(tabla.foreignTables[i]));
		}
		
		return igual;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		
		for(int i = 0; i < this.fieldValues.length; i++) 
		{
			result = prime * result + ((this.fieldValues[i] == null) ? 0 : this.fieldValues[i].hashCode());
		}
		
		for(int i = 0; i < this.foreignTables.length; i++) 
		{
			result = prime * result + ((this.foreignTables[i] == null) ? 0 : this.foreignTables[i].hashCode());
		}
		
		return result;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < this.fieldTitles.length; i++)
		{
			sb.append(this.getFieldTitle(i)).append(": ").append(this.getFieldValue(i));
			
			if(this.getFieldValue(i) != null)
			{
				int posicion = Utilidades.findIndex(this.getForeignKeys(), i);
				
				if(posicion != -1)
				{
					sb.append(" (").append(this.getForeignTable(posicion).getDescription()).append(")");
				}
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
}