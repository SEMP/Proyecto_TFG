package utilidades.util;

import java.util.Date;

import javax.swing.JOptionPane;

public final class Debug
{
	private static boolean debug = false;
	private static int llamadasInternas = 0;
	
	public static void imprimir(Object mensaje)
	{
		llamadasInternas = 1;
		mostrarMensaje(addInfo(mensaje.toString()), true);
		llamadasInternas = 0;
	}
	
	public static void agregar(Object mensaje)
	{
		mostrarMensaje(mensaje.toString(), false);
	}
	
	public static void imprimir(int[] array)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < array.length; i++)
		{
			sb.append(array[i]).append(" ");
		}
		
		llamadasInternas = 1;
		mostrarMensaje(addInfo(sb.toString()), true);
		llamadasInternas = 0;
	}
	
	public static void agregar(int[] array)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < array.length; i++)
		{
			sb.append(array[i]).append(" ");
		}
		
		mostrarMensaje(sb.toString(), false);
	}
	
	public static <E> void imprimir(E[] array)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < array.length; i++)
		{
			sb.append(array[i]).append(" ");
		}
		
		llamadasInternas = 1;
		mostrarMensaje(addInfo(sb.toString()), true);
		llamadasInternas = 0;
	}
	
	public static <E> void agregar(E[] array)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < array.length; i++)
		{
			sb.append(array[i]).append(" ");
		}
		
		mostrarMensaje(sb.toString(), false);
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
		llamadasInternas = 1;
		mostrarMensaje(addInfo(mensaje), true);
		llamadasInternas = 0;
		
		if(debug)
		{
			StringBuilder titulo = new StringBuilder();
			StringBuilder mensajeDialogo = new StringBuilder();
			
			String metodo = new Throwable().fillInStackTrace().getStackTrace()[llamadasInternas + 1].getMethodName();
			String clase = new Throwable().getStackTrace()[llamadasInternas + 1].getClassName();
			
			titulo.append(clase).append("::").append(metodo).append("()");
			
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
		StringBuilder sb = new StringBuilder();
		
		String metodo = new Throwable().fillInStackTrace().getStackTrace()[llamadasInternas + 1].getMethodName();
		String clase = new Throwable().getStackTrace()[llamadasInternas + 1].getClassName();
		
		sb.append(Utilidades.dateToString(new Date(), "dd/MM/yyyy HH:mm:ss")).append(" - ");
		
		sb.append(clase).append("::");
		sb.append(metodo).append("()\n");
		
		sb.append(mensaje);

		return sb.toString();
	}
	
	private static void mostrarMensaje(String mensaje, final boolean cabecera)
	{
		LogToFile.log(mensaje, cabecera);
		
		if(debug)
		{
			System.out.println(mensaje + "\n");
		}
	}
}