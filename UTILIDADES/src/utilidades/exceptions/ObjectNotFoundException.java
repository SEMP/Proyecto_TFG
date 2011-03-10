package utilidades.exceptions;

public final class ObjectNotFoundException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException()
	{
		super("Objeto no Encontrado");
	}

	public ObjectNotFoundException(String mensaje, Throwable causa)
	{
		super(mensaje, causa);
	}

	public ObjectNotFoundException(String mensaje) 
	{
		super(mensaje);
	}

	public ObjectNotFoundException(Throwable causa) 
	{
		super(causa);
	}
}