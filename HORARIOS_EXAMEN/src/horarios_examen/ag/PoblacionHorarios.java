package horarios_examen.ag;

import libreriaAG.ag.Individuo;
import libreriaAG.ag.Poblacion;
import horarios_examen.generadores.Generador;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;

public final class PoblacionHorarios extends Poblacion
{
	private Generador generador;
	
	public PoblacionHorarios(Generador generador) throws InvalidValueException, ObjectNotFoundException
	{
		super();
		
		this.generador = generador;
		this.setCantidadIndividuos(20);
		this.setCantidadSoluciones(5);
	}
	
	@Override
	protected Individuo getNewIndividuoInstance() throws ObjectNotFoundException, InvalidValueException
	{
		return new Horario(this.generador);
	}
}