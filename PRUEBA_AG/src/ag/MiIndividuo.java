package ag;

import libreriaAG.ag.Gen;
import libreriaAG.ag.Individuo;
import libreriaAG.generales.Calificador;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;

public final class MiIndividuo extends Individuo
{
	public MiIndividuo() throws ObjectNotFoundException, InvalidValueException
	{
		super(10);
	}

	@Override
	protected Gen getNewGenInstance()
	{
		return new MiGen();
	}

	@Override
	protected Calificador getCalificadorInstance()
	{
		return new CalificadorIndividuo();
	}
}