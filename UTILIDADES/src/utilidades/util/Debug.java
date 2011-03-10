package utilidades.util;

import java.util.Date;

import javax.swing.JOptionPane;

public final class Debug
{
	private static boolean debug = false;
	private static int llamadasInternas = 0;
	
	public static void imprimir(Object mensaje)
	{
		if(debug)
		{
			llamadasInternas = 1;
			System.out.println(addInfo(mensaje.toString()));
			llamadasInternas = 0;
		}
	}
	
	public static void agregar(Object mensaje)
	{
		if(debug)
		{
			System.out.println(mensaje);
		}
	}
	
	public static void imprimir(int[] array)
	{
		if(debug)
		{
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < array.length; i++)
			{
				sb.append(array[i]).append(" ");
			}
			
			llamadasInternas = 1;
			System.out.println(addInfo(sb.toString()));
			llamadasInternas = 0;
		}
	}
	
	public static void agregar(int[] array)
	{
		if(debug)
		{
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < array.length; i++)
			{
				sb.append(array[i]).append(" ");
			}
			
			System.out.println(sb.toString());
		}
	}
	
	public static <E> void imprimir(E[] array)
	{
		if(debug)
		{
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < array.length; i++)
			{
				sb.append(array[i]).append(" ");
			}
			
			llamadasInternas = 1;
			System.out.println(addInfo(sb.toString()));
			llamadasInternas = 0;
		}
	}
	
	public static <E> void agregar(E[] array)
	{
		if(debug)
		{
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < array.length; i++)
			{
				sb.append(array[i]).append(" ");
			}
			
			System.out.println(sb.toString());
		}
	}
	
	public static void dialogo(Object mensaje)
	{
		if(mensaje != null)
		{
			llamadasInternas = 1;
			dialogo(mensaje.toString());
			llamadasInternas = 0;
		}
		else
		{
			dialogo("null");
		}
	}
	
	public static void dialogo(String mensaje)
	{
		if(debug)
		{
			StringBuffer titulo = new StringBuffer();
			StringBuffer mensajeDialogo = new StringBuffer();
			
			String metodo = new Throwable().fillInStackTrace().getStackTrace()[llamadasInternas + 1].getMethodName();
			String clase = new Throwable().getStackTrace()[llamadasInternas + 1].getClassName();
			
			titulo.append(clase).append(".").append(metodo).append("()");
			
			mensajeDialogo.append(Utilidades.dateToString(new Date(), "dd/MM/yyyy HH:mm:ss\n"));
			mensajeDialogo.append(mensaje);
			
			
			JOptionPane.showMessageDialog(null, mensajeDialogo, titulo.toString(), JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static void setDebug(boolean debug)
	{
		Debug.debug = debug;
	}

	private static String addInfo(String mensaje)
	{
		StringBuffer sb = new StringBuffer("\n");
		
		String metodo = new Throwable().fillInStackTrace().getStackTrace()[llamadasInternas + 1].getMethodName();
		String clase = new Throwable().getStackTrace()[llamadasInternas + 1].getClassName();
		
		sb.append(Utilidades.dateToString(new Date(), "dd/MM/yyyy HH:mm:ss")).append(" - ");
		
		sb.append(clase).append(".");
		sb.append(metodo).append("()\n");
		
		sb.append(mensaje);
		
		return sb.toString();
	}
}