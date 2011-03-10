package utilidades.exceptions;

public final class InvalidValueException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public InvalidValueException()
	{
		super("Valor no Valido");
	}

	public InvalidValueException(String mensaje, Throwable causa)
	{
		super(mensaje, causa);
	}

	public InvalidValueException(String mensaje) 
	{
		super(mensaje);
	}

	public InvalidValueException(Throwable causa) 
	{
		super(causa);
	}
}