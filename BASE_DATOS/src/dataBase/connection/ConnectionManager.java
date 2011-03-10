package dataBase.connection;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import utilidades.exceptions.ObjectNotFoundException;


public final class ConnectionManager 
{
	private static Map<Thread, DBConnector> listaConexiones= new HashMap<Thread, DBConnector>();
	
	private ConnectionManager()
	{
		super();
	}
	
	public static DBConnector getConnection() throws ClassNotFoundException, SQLException, ObjectNotFoundException
	{
		Thread threadActual = Thread.currentThread();
		DBConnector conexion;
		
		synchronized (listaConexiones)
		{
			conexion = (DBConnector)listaConexiones.get(threadActual);
			if(conexion == null)
			{
				conexion = new DBConnector();
				conexion.conectar();
				listaConexiones.put(threadActual, conexion);
			}
			
			listaConexiones.notifyAll();
			return conexion;
		}
	}
	
	public static DBConnector getConnection(GestorBD gestorBD, String baseDatos, String usuario, String senha, String host) throws ClassNotFoundException, SQLException
	{
		Thread threadActual = Thread.currentThread();
		DBConnector conexionActual, conexionNueva, conexion;
		
		synchronized (listaConexiones)
		{
			conexionActual = (DBConnector)listaConexiones.get(threadActual);
			conexionNueva = new DBConnector(gestorBD, baseDatos, usuario, senha, host);
			
			if(conexionNueva.equals(conexionActual))
			{
				conexion = conexionActual;
			}
			else
			{
				if(conexionActual != null) conexionActual.desconectar();
				
				conexionNueva.conectar();
				listaConexiones.put(threadActual, conexionNueva);
				
				conexion = conexionNueva;
			}
			
			listaConexiones.notifyAll();
			return conexion;
		}
	}
	
	public static void closeConnection() throws SQLException
	{
		Thread threadActual = Thread.currentThread();
		
		synchronized (listaConexiones)
		{
			DBConnector conexion = (DBConnector)listaConexiones.get(threadActual);
			if (conexion != null)
			{
				conexion.desconectar();
				listaConexiones.remove(threadActual);
				listaConexiones.notifyAll();
			}
		}
	}
}