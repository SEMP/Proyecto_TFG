package ag;

import libreriaAG.ag.Poblacion;
import libreriaAG.operadores.Iterador;
import libreriaAG.operadores.Parametros;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;

public final class Principal
{
	public static void main(String[] args) throws InvalidValueException, ObjectNotFoundException, CloneNotSupportedException
	{
		MiPoblacion poblacion = new MiPoblacion();
		
		poblacion.setCantidadIndividuos(40);
		
		poblacion.setCantidadSoluciones(10);
		
		poblacion.generarPoblacion();
	
		System.out.println("Poblacion Inicial");
		System.out.println(poblacion);
		
//		System.out.println(poblacion.toStringSoluciones());
		
		Parametros[] parametros = new Parametros[]
		{
			new Parametros(100, 90, true),
			new Parametros(200, 90, true),
			new Parametros(500, 90, true)
			//new Parametros(1000, 20, true)
		};
		
		Iterador iterador = new Iterador(poblacion, parametros);
		iterador.iterar();
		
//		for(int i = 0; i < CICLOS; i++)
//		{
//			poblacion.cruce(poblacion.getSoluciones());
//			poblacion.mutacion();
//		}
		
		System.out.println("\nPoblacion Final");
		System.out.println(iterador.getPoblacion());
		System.out.println("\nResultados");
		System.out.println(iterador.getPoblacion().toStringSoluciones());
		
		System.out.println(poblacion.equals(((Poblacion)poblacion.clone()).mutacion()));
	}
}