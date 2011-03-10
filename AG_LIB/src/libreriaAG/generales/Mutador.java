package libreriaAG.generales;

import libreriaAG.ag.Individuo;
import utilidades.exceptions.InvalidValueException;


public interface Mutador extends Cloneable
{
	public void setModificarOriginal(boolean modificarOriginal);
	public void setIntensidadMutacion(int intensidadMutacion) throws InvalidValueException;
	public double getIntensidadMutacion();
	public Individuo mutar(Individuo individuo);
	public Individuo mutar(Individuo individuo, int intensidadMutacion) throws InvalidValueException;
}