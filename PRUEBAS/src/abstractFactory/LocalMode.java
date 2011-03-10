package abstractFactory;

public class LocalMode implements Local
{
	@Override
	public DataInfo[] loadDB(String fileName)
	{
		System.out.println("Load from a local Database ");
		return new DataInfo[] {new DataInfo("dato local1"), new DataInfo("dato local2")};
	}
}