package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import utilidades.util.Debug;
import utilidades.util.Errores;
import utilidades.util.Utilidades;

public class ServidorChat
{
	private List<PrintWriter> streamsClientes;
	
	public ServidorChat()
	{
		super();
		this.inicializar();
	}
	
	private void inicializar()
	{
		this.streamsClientes = Utilidades.getListInstance();
		
		try
		{
			ServerSocket serverSocket = new ServerSocket(5000);
			
			for(;;)
			{
				Socket socketCliente = serverSocket.accept();
				PrintWriter escritor = new PrintWriter(socketCliente.getOutputStream());
				this.streamsClientes.add(escritor);
				
				Thread t = new Thread(new ManegadorClientes(socketCliente));
				t.start();
				
				Debug.imprimir("Cliente Conectado");
			}
			
		}
		catch(IOException e)
		{
			Errores.dialogo(new IOException("No se pudo iniciar el servidor", e));
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args)
	{
		Debug.setDebug(true);
		new ServidorChat();
		Debug.imprimir("Servidor Terminado");
	}
	
	private class ManegadorClientes implements Runnable
	{
		private BufferedReader lector;
		private Socket socket;
		
		private void broadCastMensaje(String mensaje)
		{
			for(PrintWriter escritor : streamsClientes)
			{
				escritor.println(mensaje);
				escritor.flush();
			}
		}
		
		public ManegadorClientes(Socket socketCliente)
		{
			super();

			try
			{
				this.socket = socketCliente;
				InputStreamReader isr = new InputStreamReader(this.socket.getInputStream());
				this.lector = new BufferedReader(isr);
			}
			catch(IOException e)
			{
				Errores.dialogo(e);
				e.printStackTrace();
			}
		}

		@Override
		public void run()
		{
			String mensaje;
			try
			{
				while((mensaje = this.lector.readLine()) != null)
				{
					Debug.imprimir("Leido: " + mensaje);
					broadCastMensaje(mensaje);
				}
			}
			catch(IOException e)
			{
				Errores.dialogo(e);
				e.printStackTrace();
			}
		}
	}
}