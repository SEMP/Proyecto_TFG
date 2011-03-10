package threads;


public class Tarea1 implements Runnable
{
	public boolean imprimir = true;
	public int valor = 0;
	
	@Override
	public void run()
	{
//		try
		{
			for(int i = 0; i < 50; i++)
			{
				int valor = this.valor;
//				Thread.sleep(50);
			 	valor++;
			 	this.valor = valor;
//				System.out.println(Thread.currentThread().getName() + ": " + valor);
				
			}
		}
//		catch(InterruptedException e)
		{
//			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ": " + valor);
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		Runnable tarea1 = new Tarea1();
		Thread thread = new Thread(tarea1);
		Thread thread2 = new Thread(tarea1);
		System.out.println("Inicio");
		thread.start();
		thread2.start();
		
		Thread.sleep(3000);
		
//		((Tarea1)tarea1).imprimir = false;
		System.out.println("Ultimo: " + ((Tarea1)tarea1).valor);
		System.out.println("Fin");
	}
}