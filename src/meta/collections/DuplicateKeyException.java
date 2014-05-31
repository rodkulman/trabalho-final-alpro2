package meta.collections;

/**
 * Thrown when there is an attempt to insert a duplicate key in a dictionary object.
 * @author rodkulman@gmail.com
 *
 */
public class DuplicateKeyException extends RuntimeException 
{
	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 1653447456337185737L;
	
	/**
	 * Initializes an exception with a preset message.
	 */
	public DuplicateKeyException()
	{
		super("There is another pairs in this dictionary with the same key.");
	}
}
