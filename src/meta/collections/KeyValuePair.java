package meta.collections;

/**
 * Defines a generic Key-Value pair. 
 * @author rodkulman@gmail.com
 *
 */
public class KeyValuePair<TKey, TValue> 
{
	/**
	 * The immutable key.
	 */
	private final TKey key;
	/**
	 * The mutable value;
	 */
	private TValue value;
	
	/**
	 * Initializes the class with a immutable key and a value.
	 * @param key Immutable key.
	 * @param value Mutable value.
	 */
	public KeyValuePair(TKey key, TValue value)
	{
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Retrieves the key.
	 * @return Returns the key.
	 */
	public TKey getKey() 
	{
		return key;
	}
	
	/**
	 * Gets the value.
	 * @return Returns the value.
	 */
	public TValue getValue() 
	{
		return value;
	}
	
	/**
	 * Allows to change the value.
	 * @param value New value to the pair.
	 */
	public void setValue(TValue value) 
	{
		this.value = value;
	}
}
