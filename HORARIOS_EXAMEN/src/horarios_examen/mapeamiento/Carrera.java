package horarios_examen.mapeamiento;

public enum Carrera
{
	ANALISIS_DE_SISTEMAS(1),
	INGENIERIA_ELECTRICA(2),
	LICENCIATURA_EN_TURISMO(3),
	INGENIERIA_DE_SISTEMAS(4),
	INGENIERIA_ELECTRICA_5(5);
	
	private final int codigo;
	
	private Carrera(int codigo)
	{
		this.codigo = codigo;
	}
	
	public int getCodigo()
	{
		return this.codigo;
	}
}