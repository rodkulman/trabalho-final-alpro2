package meta.collections;

/**
 * Thrown when an attempt is made to change a read-only list.
 * @author rodkulman@gmail.com
 *
 */
public class ReadOnlyException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8213212789869893600L;
	
	/**
	 * Creates a new ReadOnlyException object.
	 */
	public ReadOnlyException()
	{
		super("An attempt was made to change a read-only list.");
	}
}
