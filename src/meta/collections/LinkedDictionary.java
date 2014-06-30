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
		if (key == null) { throw new IllegalArgumentException("key cannot be null"); }

		// throws an exception if another key is found
		checkDuplicateKeys(key);

		// adds to the collection
		add(new KeyValuePair<>(key, value));
	}

	/**
	 * Checks whether key is already in the collection.
	 * 
	 * @param key
	 *            Key to search for.
	 */
	private void checkDuplicateKeys(TKey key)
	{
		for (KeyValuePair<TKey, TValue> pair : this)
		{
			if (pair.getKey().equals(key)) { throw new DuplicateKeyException(); }
		}
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

	/**
	 * Collects all the keys in the dictionary.
	 * 
	 * @return Returns a collection of TKey containing all the keys in this
	 *         dictionary.
	 */
	public IList<TKey> getKeys()
	{
		ListLinked<TKey> retVal = new ListLinked<>();

		for (KeyValuePair<TKey, TValue> pair : this)
		{
			retVal.add(pair.getKey());
		}

		return retVal;
	}
}
