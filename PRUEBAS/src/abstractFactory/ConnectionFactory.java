package abstractFactory;
/**
 * Abstract Factory
 */
public interface ConnectionFactory
{
	public Local getLocalConnection();
	public Remote getRemoteConnection();
}