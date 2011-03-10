package libreriaAG.generales;

import libreriaAG.ag.Individuo;
import utilidades.exceptions.InvalidValueException;


public interface Cruzador
{
	public void setCantidadPuntosCruce(int puntosCruce) throws InvalidValueException;
	public int getPuntosCruce();
	public Individuo cruzar(Individuo individuo1, Individuo individuo2) throws InvalidValueException;
	public Individuo cruzar(Individuo individuo1, Individuo individuo2, int[] puntosCorte) throws InvalidValueException;
}