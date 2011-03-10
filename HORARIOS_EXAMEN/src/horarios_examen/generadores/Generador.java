package horarios_examen.generadores;

import libreriaAG.ag.Gen;
import libreriaAG.generales.Calificador;

public interface Generador
{
	public Gen[] getGenotipo();
	public Gen getGen();
	public Calificador getCalificador();
}