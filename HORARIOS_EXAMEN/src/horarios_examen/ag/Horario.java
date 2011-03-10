package horarios_examen.ag;

import horarios_examen.generadores.Generador;
import libreriaAG.ag.Gen;
import libreriaAG.ag.Individuo;
import libreriaAG.generales.Calificador;
import utilidades.exceptions.InvalidValueException;
import utilidades.exceptions.ObjectNotFoundException;

public final class Horario extends Individuo
{
	private Generador generador;
	
	public Horario(Generador generador) throws ObjectNotFoundException, InvalidValueException
	{
		super(generador.getGenotipo());
		this.generador = generador;
	}
	
	@Override
	protected Gen getNewGenInstance()
	{
		return this.generador.getGen();
	}

	@Override
	protected Calificador getCalificadorInstance()
	{
		return this.generador.getCalificador();
	}
}