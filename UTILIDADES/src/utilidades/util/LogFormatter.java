package utilidades.util;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter
{
	private boolean cabecera;
	
	public LogFormatter(boolean cabecera)
	{
		this.cabecera = cabecera;
	}

	@Override
	public String format(LogRecord record)
	{
		StringBuilder sb = new StringBuilder();
		
		if(this.cabecera)
		{
			if(record.getLevel().equals(Level.ALL))
			{
				sb.append("\nDEBUG: ");
			}
			else if(record.getLevel().equals(Level.WARNING))
			{
				sb.append("\nADVERTENCIA: ");
			}
			else if(record.getLevel().equals(Level.SEVERE))
			{
				sb.append("\nGRAVE: ");
			}
			else if(record.getLevel().equals(Level.INFO))
			{
				sb.append("\nINFO: ");
			}
		}
		
		sb.append(record.getMessage()).append("\n");
		
		return sb.toString();
	}
}