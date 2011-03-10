package utilidades.util;

import javax.swing.JOptionPane;

public class Errores
{
	public static void dialogo(Throwable throwable)
	{
		StringBuffer titulo = new StringBuffer();
		StringBuffer mensajeDialogo = new StringBuffer();
		
		String metodo = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
		String clase = new Throwable().getStackTrace()[1].getClassName();
		
		titulo.append(throwable.getClass().getSimpleName());
		
		mensajeDialogo.append(clase).append(".").append(metodo).append("()\n");
		mensajeDialogo.append(throwable.getMessage());
		
		JOptionPane.showMessageDialog(null, mensajeDialogo, titulo.toString(), JOptionPane.INFORMATION_MESSAGE);
	}
}