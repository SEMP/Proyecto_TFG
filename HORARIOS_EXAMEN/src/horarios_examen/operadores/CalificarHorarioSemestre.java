package horarios_examen.operadores;



import horarios_examen.ag.Materia;

import java.util.Arrays;

import libreriaAG.ag.Gen;
import libreriaAG.generales.Calificador;



public final class CalificarHorarioSemestre implements Calificador
{
	@Override
	public double calificar(Gen[] genotipo)
	{
		Materia[] materias = new Materia[genotipo.length];
		
		for(int i = 0; i < materias.length; i++)
		{
			materias[i] = (Materia)genotipo[i];
		}
		
		Arrays.sort(materias);
		
		double calificacion = 0;
		double castigo = 1;
		
		for(int i = 0; i < materias.length; i++)
		{
			if(i != materias.length - 1)
			{
				double dificultad = materias[i + 1].getDificultad();
				double distancia = (Math.abs(materias[i].compareTo(materias[i + 1]))
									- 2 * (60.0 / materias[i].getResolucionMinutos())) + 1;
				
				if(distancia <= 0)
				{
					distancia = 1;
					castigo /= 10;
				}
				
				calificacion += dificultad / (distancia * distancia);
			}
		}
		
		return castigo / calificacion;
	}
}