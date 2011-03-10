package libreriaAG.ag;

public interface Gen extends Cloneable
{
	public abstract void generarGen();
	public boolean esIgualA(Gen gen);
	public Gen copiarGen();
}