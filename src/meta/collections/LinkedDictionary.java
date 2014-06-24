package meta.collections;

/**
 * Defines a collection of KeyValuePairs
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class LinkedDictionary<TKey, TValue> extends ListLinked<KeyValuePair<TKey, TValue>>
{
	public LinkedDictionary()
	{
		super();
	}

	/**
	 * Adds a new Key-Value pair in the dictionary. An exception is thrown if
	 * the key already exists within the collection.
	 * 
	 * @param key
	 *            A key. Can't exist within the collection. Can't be null.
	 * @param value
	 *            A value.
	 */
	public void add(TKey key, TValue value)
	{
		add(new KeyValuePair<>(key, value));
	}

	/**
	 * Searches for the value of the specified key.
	 * 
	 * @param key
	 *            Key to search for.
	 * @return The value assigned to the specified key. If the key isn't within
	 *         the collection, returns null.
	 */
	public TValue getValue(TKey key)
	{
		for (KeyValuePair<TKey, TValue> pair : this)
		{
			if (pair.getKey().equals(key)) { return pair.getValue(); }
		}

		return null;
	}
}
