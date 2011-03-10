package abstractFactory;

public class Test
{
	public static void main(String[] args)
	{
		String argumentos[] = 
		{
			"local"
		};
		test1(argumentos);
//		test2(argumentos);
	}
	
	public static void test1(String[] args)
	{
		DataManager dm = new DataManager();
		@SuppressWarnings("unused")
		DataInfo[] di = null;
		String dbFileName = "db.db";
		
		if(args.length == 1)
		{
			dm.setConnection(true);
			Local lm = dm.getLocalConnection();
			di = lm.loadDB(dbFileName);
		}
		else
		{
			Remote rm = dm.getRemoteConnection();
			rm.connect2WWW("www.algunlado.com");
			di = rm.loadDB(dbFileName);
		}
	}
	
	public static void test2(String[] args)
	{
		DataManager dm = new DataManager();
		DataInfo[] di = null;
		
		if(args.length == 1)
		{
			dm.setConnection(true);
			dm.loadData();
			di = dm.getData();
		}
		else
		{
			dm.setConnection(false);
			dm.loadData();
			di = dm.getData();
		}
		
		for (int i = 0; di != null && i < di.length; i++)
		{
			System.out.println(di[i]);
		}
	}
}