package utilidades.general;

import utilidades.exceptions.DataAccessException;

public interface DataAccessObject 
{
	public int insertar(Dato tabla) throws DataAccessException;
	public int actualizar(Dato tabla) throws DataAccessException;
	public int eliminar(Dato tabla) throws DataAccessException;
	public boolean cargar(Dato tabla) throws DataAccessException;
	public Dato[] consultar(Dato tabla) throws DataAccessException;
	public Dato[] consultar(Dato tabla, ComparadorBD[] comparadores) throws DataAccessException;
	public void commit() throws DataAccessException;
	public void rollback() throws DataAccessException;
	public void desconectar() throws DataAccessException;
}