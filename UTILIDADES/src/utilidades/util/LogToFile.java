package utilidades.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import utilidades.exceptions.ObjectNotFoundException;

public final class LogToFile
{
	private LogToFile()
	{
		super();
	}
	
	public static void log(String mensaje)
	{
		log(Level.ALL, mensaje, true);
	}
	
	public static void log(Level level, String mensaje)
	{
		log(level, mensaje, true);
	}
	
	public static void log(String mensaje, final boolean cabecera)
	{
		log(Level.ALL, mensaje, cabecera);
	}

	public static synchronized void log(Level level, String mensaje, final boolean cabecera)
	{
		String nombreAplicacion = System.getProperty("user.name");

		try
		{
			nombreAplicacion = Variables.getVariableString("nombreAplicacion");
		}
		catch(ObjectNotFoundException e){}

		Logger logger = Logger.getLogger(nombreAplicacion);
		logger.setUseParentHandlers(false);

		try
		{
			StringBuilder nombreArchivo = new StringBuilder();

			nombreArchivo.append(nombreAplicacion.replaceAll(" ", "_"));
			nombreArchivo.append(".log");
			
			FileHandler fh = new FileHandler(nombreArchivo.toString(), true);
			logger.addHandler(fh);
			logger.setLevel(Level.ALL);

			Formatter formatter = new LogFormatter(cabecera);
			
			fh.setFormatter(formatter);

			logger.log(level, mensaje);
			
			logger.removeHandler(fh);
			fh.close();
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void prueba()
	{
		Debug.setDebug(true);
		LogToFile.log("esto es una advertencia!!!!!");
		Debug.imprimir("un debug... jejejeje");
		Debug.dialogo("desde Prueba...");
	}
}