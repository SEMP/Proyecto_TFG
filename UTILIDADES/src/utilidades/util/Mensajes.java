package utilidades.util;

import java.util.Date;

import javax.swing.JOptionPane;

public final class Mensajes
{
	public static void imprimir(String mensaje)
	{
		System.out.println(addInfo(mensaje));
	}
	
	public static void imprimir(StringBuffer mensaje)
	{
		System.out.println(addInfo(mensaje.toString()));
	}
	
	public static void imprimir(int[] array)
	{
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < array.length; i++)
		{
			sb.append(array[i]).append(" ");
		}
		
		System.out.println(addInfo(sb.toString()));
	}
	
	public static <E> void imprimir(E[] array)
	{
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < array.length; i++)
		{
			sb.append(array[i]).append(" ");
		}
		
		System.out.println(addInfo(sb.toString()));
	}
	
	public static void dialogo(Object mensaje)
	{
		if(mensaje != null)
		{
			dialogo(mensaje.toString());
		}
		else
		{
			dialogo("null");
		}
	}
	
	public static void dialogo(String mensaje)
	{
		StringBuffer titulo = new StringBuffer();
		StringBuffer mensajeDialogo = new StringBuffer();
		
		titulo.append("Mensaje - ");
		titulo.append(Utilidades.dateToString(new Date(), "dd/MM/yyyy HH:mm:ss"));
		
		mensajeDialogo.append(mensaje);
		
		JOptionPane.showMessageDialog(null, mensajeDialogo, titulo.toString(), JOptionPane.QUESTION_MESSAGE);
	}
	
	private static String addInfo(String mensaje)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(Utilidades.dateToString(new Date(), "dd/MM/yyyy HH:mm:ss")).append("\n");
		
		sb.append(mensaje).append("\n");
		
		return sb.toString();
	}
}