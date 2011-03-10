package horarios_examen.baseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.StringTokenizer;

import utilidades.exceptions.DataAccessException;
import utilidades.util.Debug;
import utilidades.util.Utilidades;
import dataBase.service.DataBase;

public class HorariosDAO extends DataBase
{
	private Map<String, Double> dificultades;
	
	public HorariosDAO()
	{
		super();
	}

	public Double getDificultad(String codigoMateria) throws DataAccessException
	{
		String sql = "SELECT materia, porciento FROM v_informe_resumen('v','200902',8)";
		
		Double dificultad;
		
		if(this.dificultades == null || !this.dificultades.containsKey(codigoMateria))
		{
			try
			{
				Debug.imprimir(sql);
				ResultSet rs = this.conexion.ejecutarConsulta(sql);
				this.dificultades = Utilidades.getMapInstance();
				
				while(rs.next())
				{
					String codigo = rs.getString(1);
					
					if(codigo != null && !codigo.equals("Materia"))
					{
						StringTokenizer st = new StringTokenizer(codigo, " ");
						
						codigo = st.nextToken();
						
						Double porciento = rs.getDouble(2);
						
						this.dificultades.put(codigo, porciento);
					}
				}
				
				Debug.agregar("Rowcount: " + this.dificultades.size());
			}
			catch(SQLException e )
			{
				throw new DataAccessException(e);
			}
		}
		
		dificultad = this.dificultades.get(codigoMateria);
		
		if(dificultad != null) dificultad = 5.01 - dificultad;
		
		return dificultad;
	}
}