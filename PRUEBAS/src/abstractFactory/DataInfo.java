package abstractFactory;

public class DataInfo
{
	private String info;
	public DataInfo(String info)
	{
		this.info = info;
	}
	
	@Override
	public String toString()
	{
		return this.info;
	}
}