package abstractFactory;

public class DataManager implements ConnectionFactory
{
	private boolean local = false;
	private DataInfo[] data;
	
	@Override
	public Local getLocalConnection()
	{
		return new LocalMode();
	}

	@Override
	public Remote getRemoteConnection()
	{
		return new RemoteMode();
	}
	
	public void loadData()
	{
		if(this.local)
		{
			Local conn = this.getLocalConnection();
			this.data = conn.loadDB("db.db");
		}
		else
		{
			Remote conn = this.getRemoteConnection();
			conn.connect2WWW("www.datos.com");
			this.data = conn.loadDB("db.db");
		}
	}
	
	public DataInfo[] getData()
	{
		return this.data;
	}
	
	public void setConnection(boolean b)
	{
		this.local = b;
	}
}