package abstractFactory;

public class RemoteMode implements Remote
{
	@Override
	public void connect2WWW(String url)
	{
		System.out.println("Connect to a remote site ");
	}

	@Override
	public DataInfo[] loadDB(String fileName)
	{
		System.out.println("Load from a remote database");
		return new DataInfo[] {new DataInfo("dato remoto1"), new DataInfo("dato remoto2")};
	}
}