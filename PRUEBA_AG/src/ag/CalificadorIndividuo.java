package ag;

import libreriaAG.ag.Gen;
import libreriaAG.generales.Calificador;

public final class CalificadorIndividuo implements Calificador
{
	@Override
	public double calificar(Gen[] genotipo)
	{
		double calificacion = 0;
		
		for (int i = 0; i < genotipo.length; i++)
		{
			MiGen gen = (MiGen)genotipo[i];
			calificacion += gen.getValor();
		}
		
		return calificacion;
	}
}