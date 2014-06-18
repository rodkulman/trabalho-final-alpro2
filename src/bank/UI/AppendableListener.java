package bank.UI;

/**
 * Used to listen to appending messages.
 * @author rodkulman@gmail.com
 * 
 */
public interface AppendableListener
{
	/**
	 * Fired when a new value has been appended to the object.
	 * @param args The message being appended.
	 */
	void Appended(final String args);
}
