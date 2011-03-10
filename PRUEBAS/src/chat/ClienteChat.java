package chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import utilidades.util.Debug;
import utilidades.util.Errores;

public class ClienteChat
{
	JFrame frame;
	private JTextArea textoEntrada;
	private JTextField textoNombre;
	private JTextField textoSalida;
	private BufferedReader lector;
	private PrintWriter escritor;
	Socket socket;
	
	public ClienteChat()
	{
		super();
		this.inicializar();
	}
	
	public void inicializar()
	{
		this.frame = new JFrame("Cliente de Chat");
		JPanel panelPrincipal = new JPanel();
		
		this.textoEntrada = new JTextArea(15, 50);
		this.textoEntrada.setLineWrap(true);
		this.textoEntrada.setWrapStyleWord(true);
		this.textoEntrada.setEditable(false);
		
		JScrollPane scroller = new JScrollPane(textoEntrada);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.textoNombre = new JTextField(10);
		
		this.textoSalida = new JTextField(20);
		this.textoSalida.addKeyListener(new ListenerTextField());
		
		JButton botonEnviar = new JButton("Enviar");
		botonEnviar.addActionListener(new ListenerBotonEnviar());
		
		panelPrincipal.add(new JLabel("Nombre: "));
		panelPrincipal.add(this.textoNombre);
		panelPrincipal.add(scroller);
		panelPrincipal.add(this.textoSalida);
		panelPrincipal.add(botonEnviar);
		
		this.prepararConexion();
		
		Thread hiloLectura = new Thread(new LecturaEntrada());
		hiloLectura.start();
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().add(BorderLayout.CENTER, panelPrincipal);
		this.frame.setSize(600, 600);
		this.frame.setVisible(true);
	}
	
	private void prepararConexion()
	{
		try
		{
			this.socket = new Socket("172.18.200.50", 5000);
			InputStreamReader isr = new InputStreamReader(this.socket.getInputStream());
			this.lector = new BufferedReader(isr);
			this.escritor = new PrintWriter(this.socket.getOutputStream());
			
			Debug.imprimir("Conectado...");
		}
		catch(IOException e)
		{
			Errores.dialogo(new IOException("No se pudo conectar", e));
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args)
	{
		Debug.setDebug(true);
		new ClienteChat();
	}
	
	private class ListenerBotonEnviar implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String mensaje = textoNombre.getText();
			if(mensaje.equals("")) mensaje = "Anonimo";
			mensaje += ": " + textoSalida.getText();
			
			escritor.println(mensaje);
			escritor.flush();
			Debug.imprimir(textoSalida.getText());
			textoSalida.setText("");
			textoSalida.requestFocus();
		}
	}
	
	private class ListenerTextField implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e){}

		@Override
		public void keyReleased(KeyEvent e)
		{
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_ENTER)
			{
				String mensaje = textoNombre.getText();
				if(mensaje.equals("")) mensaje = "Anonimo";
				mensaje += ": " + textoSalida.getText();
				
				escritor.println(mensaje);
				escritor.flush();
				Debug.imprimir(textoSalida.getText());
				textoSalida.setText("");
				textoSalida.requestFocus();
			}
		}

		@Override
		public void keyTyped(KeyEvent e){}
	}
	
	private class LecturaEntrada implements Runnable
	{
		@Override
		public void run()
		{
			String mensaje;
			
			try
			{
				while((mensaje = lector.readLine()) != null)
				{
					Debug.imprimir("Leido Cliente: " + mensaje);
					textoEntrada.append(mensaje + "\n");
				}
				
				Debug.imprimir(mensaje);
			}
			catch(IOException e)
			{
				Errores.dialogo(e);
				e.printStackTrace();
			}
		}
	}
}