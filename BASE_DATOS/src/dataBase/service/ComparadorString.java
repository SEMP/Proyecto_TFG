package dataBase.service;

import utilidades.general.ComparadorBD;

public enum ComparadorString implements ComparadorBD
{
	IGUAL("<dato1/> = <dato2/>", "<dato/>"),
	LIKE("<dato1/> LIKE <dato2/>", "<dato/>"),
	COMIENZA_CON("<dato1/> LIKE <dato2/>", "<dato/>%"),
	TERMINA_CON("<dato1/> LIKE <dato2/>", "%<dato/>"),
	CONTIENE("<dato1/> LIKE <dato2/>", "%<dato/>%");
	
	private final String formato;
	private final String dato;
	
	private ComparadorString(String formato, String dato)
	{
		this.formato = formato;
		this.dato = dato;
	}

	@Override
	public String comparar(String A, String B)
	{
		String cadena = this.formato;
		
		cadena = cadena.replaceAll("<dato1/>", A);
		cadena = cadena.replaceAll("<dato2/>", B);
		
		return cadena;
	}
	
	public String prepararDato(String dato)
	{
		return this.dato.replaceAll("<dato/>", dato);
	}
}