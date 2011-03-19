package dataBase.connection;

public enum GestorBD 
{
	MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://<host/>/<baseDatos/>"),
	POSTGRES("org.postgresql.Driver", "jdbc:postgresql://<host/>/<baseDatos/>");
	//TODO agregar formato para oracle
	
	private final String driver;
	private final String url;
	
	private GestorBD(String driver, String url)
	{
		this.driver = driver;
		this.url = url;
	}

	public String getDriver()
	{
		return this.driver;
	}

	public String getUrl(String host, String baseDatos)
	{
		String url = this.url;
		
		url = url.replaceAll("<host/>", host);
		url = url.replaceAll("<baseDatos/>", baseDatos);
		
		return url;
	}
}