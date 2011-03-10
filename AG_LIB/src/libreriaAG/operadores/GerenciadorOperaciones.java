package libreriaAG.operadores;

import libreriaAG.generales.Cruzador;
import libreriaAG.generales.Mutador;
import utilidades.exceptions.InvalidValueException;
import utilidades.util.Variables;

public final class GerenciadorOperaciones
{
	private static Mutador mutador = new Mutacion();
	private static Cruzador cruzador = new Cruce();
	
	static
	{
		iniciarVariables();
	}
	
	public static Mutador getMutadorInstance()
	{
		return mutador;
	}
	
	public static Cruzador getCruzadorInstance()
	{
		return cruzador;
	}
	
	public static void setIntensidadMutacion(int intensidadMutacion) throws InvalidValueException
	{
		mutador.setIntensidadMutacion(intensidadMutacion);
	}
	
	public static void setCantidadPuntosCruce(int cantidadPuntosCruce) throws InvalidValueException
	{
		cruzador.setCantidadPuntosCruce(cantidadPuntosCruce);
	}
	
	private static void iniciarVariables()
	{
		Variables.setVariableInt("intensidadMutacion", 20);
		Variables.setVariableString("errorIntensidadMutacion", "Valor de intencidad de Mutacion Inválido");
		Variables.setVariableInt("cantidadPuntosCruce", 0);
		Variables.setVariableString("errorPuntosCruce", "Cantidad de Puntos de Cruce Inválido");
	}
}