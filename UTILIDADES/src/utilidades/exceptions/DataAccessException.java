package utilidades.exceptions;

public final class DataAccessException extends Exception
{
	private static final long serialVersionUID = 1L;

	public DataAccessException() 
	{
		super("Excepcion en la capa de almacenamiento de datos");
	}

	public DataAccessException(String mensaje, Throwable causa)
	{
		super(mensaje, causa);
	}

	public DataAccessException(String mensaje)
	{
		super(mensaje);
	}

	public DataAccessException(Throwable causa) 
	{
		super(causa);
	}
}