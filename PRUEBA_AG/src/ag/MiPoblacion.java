package ag;

import libreriaAG.ag.Individuo;
import libreriaAG.ag.Poblacion;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;

public final class MiPoblacion extends Poblacion
{
	public MiPoblacion() throws InvalidValueException, ObjectNotFoundException
	{
		super();
	}
	
	@Override
	protected Individuo getNewIndividuoInstance() throws ObjectNotFoundException, InvalidValueException
	{
		return new MiIndividuo();
	}
}