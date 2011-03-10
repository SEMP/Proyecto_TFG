package dataBase.service;

import utilidades.general.ComparadorBD;

public enum ComparadorNumero implements ComparadorBD
{
	IGUAL("<dato1/> = <dato2/>"),
	MAYOR("<dato1/> > <dato2/>"),
	MENOR("<dato1/> < <dato2/>"),
	MAYOR_IGUAL("<dato1/> >= <dato2/>"),
	MENOR_IGUAL("<dato1/> <= <dato2/>");
	
	private final String formato;
	
	private ComparadorNumero(String formato)
	{
		this.formato = formato;
	}

	@Override
	public String comparar(String A, String B)
	{
		String cadena;
		cadena = this.formato.replaceAll("<dato1/>", A);
		cadena = cadena.replaceAll("<dato2/>", B);
		return cadena;
	}
}