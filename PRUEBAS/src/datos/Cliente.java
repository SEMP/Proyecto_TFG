package datos;

import utilidades.general.Dato;

public class Cliente extends Dato
{
	private static final long serialVersionUID = 1L;

	public Cliente()
	{
		super();
	}

	@Override
	public String getEntityName()
	{
		return "clientes";
	}

	@Override
	public String[] getFieldNames()
	{
		return new String[]
		{
			"cli_codigo",
			"cli_nombre",
			"cli_direccion",
			"cli_ciudad",
			"cli_telefono",
			"cli_obs"
		};
	}

	@Override
	public String[] getFieldTitles()
	{
		return new String[]
	    {
			"Codigo",
			"Nombre",
			"Direccion",
			"Ciudad",
			"Telefono",
			"Obs"
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
		return new int[]{3};
	}

	@Override
	public Dato[] getForeignTables()
	{
		return new Dato[]
	    {
			new Ciudad()
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
			Integer.class,
			String.class,
			String.class
		};
		
		return tipos[campo];
	}

	@Override
	public int[] getMainFields()
	{
		return new int[]{0, 1, 3, 4};
	}

	@Override
	public Object getDescription()
	{
		return this.fieldValues[1];
	}
}