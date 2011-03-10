package dataBase.connection;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utilidades.exceptions.ObjectNotFoundException;
import utilidades.util.Variables;

public final class DBConnector
{
	private Connection conexion;
	private GestorBD gestorBD;
	private String baseDatos;
	private String usuario;
	private String senha;
	private String host;
	
	DBConnector() throws ObjectNotFoundException
	{
		super();
		this.conexion = null;
		
		this.gestorBD = (GestorBD)Variables.getVariableEnum("gestorBD");
		this.baseDatos = Variables.getVariableString("baseDatos");
		this.usuario = Variables.getVariableString("usuario");
		this.senha = Variables.getVariableString("senha");
		this.host = Variables.getVariableString("host");
	}
	
	DBConnector(GestorBD gestorBD, String baseDatos, String usuario, String senha, String host)
	{
		super();
		this.conexion = null;
		
		this.gestorBD = gestorBD;
		this.baseDatos = baseDatos;
		this.usuario = usuario;
		this.senha = senha;
		this.host = host;
	}

	void conectar() throws ClassNotFoundException, SQLException
	{
		Class.forName(this.gestorBD.getDriver());
		String url = this.gestorBD.getUrl(this.host, this.baseDatos);
		
		this.conexion = DriverManager.getConnection(url, this.usuario, this.senha);
		this.conexion.setAutoCommit(false);
	}
	
	void desconectar() throws SQLException
	{
		if(this.conexion != null && !this.conexion.isClosed()) this.conexion.close();		
	}
	
	public int ejecutarSQL(String sql) throws SQLException
	{
		Statement stm = this.conexion.createStatement();
		int rowCount = stm.executeUpdate(sql);
		stm.close();
		return rowCount;
	}
	
	public int ejecutarUpdate(String sql, Object[] parametros) throws SQLException
	{
		PreparedStatement ps = this.conexion.prepareStatement(sql);

		for (int i = 0; i < parametros.length; i++)
		{
			this.setParameterStatement(ps, i + 1, parametros[i]);
		}
		
		int rowCount = ps.executeUpdate();
		ps.close();
		return rowCount;
	}
	
	public ResultSet ejecutarConsulta(String sql) throws SQLException
	{
		Statement stm = this.conexion.createStatement();
		return stm.executeQuery(sql);
	}
	
	public ResultSet ejecutarConsulta(String sql, Object[] parametros) throws SQLException
	{
		PreparedStatement ps = this.conexion.prepareStatement(sql);

		for (int i = 0; i < parametros.length; i++)
		{
			this.setParameterStatement(ps, i + 1, parametros[i]);
		}
		return ps.executeQuery();
	}
	
	private void setParameterStatement(PreparedStatement ps, int posicion, Object parametro) throws SQLException
	{
		if(parametro instanceof String)
		{
			ps.setString(posicion, (String)parametro);
		}
		else if(parametro instanceof Integer)
		{
			ps.setInt(posicion, (Integer)parametro);
		}
		else if(parametro instanceof Double)
		{
			ps.setDouble(posicion, (Double)parametro);
		}
		else if(parametro instanceof java.sql.Date)
		{
			ps.setDate(posicion, (java.sql.Date)parametro);
		}
		else if(parametro instanceof java.sql.Time)
		{
			ps.setTime(posicion, (java.sql.Time)parametro);
		}
		else if(parametro instanceof java.sql.Timestamp)
		{
			ps.setTimestamp(posicion, (java.sql.Timestamp)parametro);
		}
		else if(parametro instanceof java.util.Date)
		{
			java.util.Date fecha = (java.util.Date)parametro;
			java.sql.Timestamp sqlTimeStamp = new java.sql.Timestamp(fecha.getTime());
			ps.setTimestamp(posicion, sqlTimeStamp);
		}
		else
		{
			ps.setObject(posicion, parametro);
		}
	}
	
	public void commit() throws SQLException
	{
		this.conexion.commit();
	}

	public void rollback() throws SQLException
	{
		this.conexion.rollback();
	}

	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !obj.getClass().equals(this.getClass())) return false;
		
		boolean igual = false;
		
		DBConnector conexion = (DBConnector)obj;
		
		igual =
		(
			((this.gestorBD == null) ? conexion.gestorBD == null : this.gestorBD.equals(conexion.gestorBD)) &&
			((this.baseDatos == null) ? conexion.baseDatos == null : this.baseDatos.equals(conexion.baseDatos)) &&
			((this.usuario == null) ? conexion.usuario == null : this.usuario.equals(conexion.usuario)) &&
			((this.senha == null) ? conexion.senha == null : this.senha.equals(conexion.senha)) &&
			((this.host == null) ? conexion.host == null : this.host.equals(conexion.host))
		);
		
		return igual;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((baseDatos == null) ? 0 : baseDatos.hashCode());
		result = prime * result + ((gestorBD == null) ? 0 : gestorBD.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		
		return result;
	}
}