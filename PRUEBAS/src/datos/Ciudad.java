package datos;

import utilidades.general.Dato;

public class Ciudad extends Dato
{
	private static final long serialVersionUID = 1L;

	public Ciudad()
	{
		super();
	}
	
	@Override
	public String getEntityName()
	{
		return "ciudades";
	}

	@Override
	public String[] getFieldNames()
	{
		return new String[]
		{
			"ciu_codigo",
			"ciu_nombre"
		};
	}

	@Override
	public String[] getFieldTitles()
	{
		return new String[]
	    {
			"Codigo",
			"Ciudad",
		};
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
	public Class<?> getFieldType(int campo)
	{
		Class<?>[] tipos = new Class<?>[]
		{
			Integer.class,
			String.class
		};
		
		return tipos[campo];
	}

	@Override
	public int[] getMainFields()
	{
		return new int[]{0, 1};
	}
	
	@Override
	public Object getDescription()
	{
		return this.fieldValues[1];
	}
}