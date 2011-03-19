package horarios_examen.principal;

import horarios_examen.ag.Materia;
import horarios_examen.ag.PoblacionHorarios;
import horarios_examen.datos.DatoGenotipo;
import horarios_examen.datos.SolucionHorario;
import horarios_examen.generadores.GeneradorSemestral;
import horarios_examen.mapeamiento.Carrera;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import libreriaAG.ag.Gen;
import libreriaAG.ag.Individuo;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;
import utilidades.util.Debug;
import utilidades.util.Utilidades;

public class Principal
{
	private static final int CICLOS = 5000;
	
	static
	{
		Inicializador.iniciarVariables();
	}
	
	public static void main(String[] args) throws InvalidValueException, ObjectNotFoundException, CloneNotSupportedException, ParseException
	{
		//TODO utilizar iterador
		Debug.setDebug(true);

		Date fechaInicial = Utilidades.stringToDate("10/05/2010", "dd/MM/yyyy");
		Date fechaFinal = Utilidades.stringToDate("22/05/2010", "dd/MM/yyyy");
		
		DatoGenotipo datoGenotipo1 = new DatoGenotipo(Carrera.INGENIERIA_DE_SISTEMAS, 1, fechaInicial, fechaFinal);
		DatoGenotipo datoGenotipo2 = (DatoGenotipo)datoGenotipo1.clone();
		datoGenotipo2.setSemestre(2);
		
		PoblacionHorarios pob1 = new PoblacionHorarios(new GeneradorSemestral(datoGenotipo1));
		PoblacionHorarios pob2 = new PoblacionHorarios(new GeneradorSemestral(datoGenotipo2));
		
		pob2.setCantidadIndividuos(21);
		
		pob1.generarPoblacion();
		pob2.generarPoblacion();
	
		System.out.println("Poblacion Inicial");
		System.out.println(pob1);
		System.out.println(pob2);
		System.out.println();
		System.out.println(pob1.toStringSoluciones());
		System.out.println(pob2.toStringSoluciones());
		
		for(int i = 0; i < CICLOS; i++)
		{
			pob1.cruce(pob1.getSoluciones());
			pob2.cruce(pob2.getSoluciones());
			pob1.mutacion();
			pob2.mutacion();
		}
		System.out.println("\nPoblacion Final");
		System.out.println(pob1);
		System.out.println(pob2);
		System.out.println("\nResultados");
		System.out.println(pob1.toStringSoluciones());
		System.out.println(pob2.toStringSoluciones());
		
		System.out.println();
		
		printSoluciones(pob1, datoGenotipo1);
		printSoluciones(pob2, datoGenotipo2);
	}
	
	private static void printSoluciones(PoblacionHorarios poblacion, DatoGenotipo datoGenotipo) throws CloneNotSupportedException, ParseException
	{
		Individuo mejorSolucion = (Individuo)poblacion.getSoluciones()[0].clone();
		Gen[] genotipo = mejorSolucion.getGenotipo();
		Arrays.sort(genotipo);
		
		for(int i = 0; i < genotipo.length; i++)
		{
			SolucionHorario solucion = new SolucionHorario((Materia)genotipo[i], datoGenotipo);
			System.out.println(solucion);
		}
		System.out.println();
	}
}