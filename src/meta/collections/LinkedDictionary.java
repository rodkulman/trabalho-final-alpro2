package meta.collections;

/**
 * Defines a collection of KeyValuePairs
 * @author rodkulman@gmail.com
 *
 */
public class LinkedDictionary<TKey, TValue> implements IList
{
	/**
	 * Internal KeyValuePair collection.
	 */
	ListLinked<KeyValuePair<TKey, TValue>> pairs;
	
	public LinkedDictionary()
	{
		pairs = new ListLinked<>();
	}
	
	/**
	 * Adds a new Key-Value pair in the dictionary. An exception is thrown if the key already exists within the collection.
	 * @param key A key. Can't exist within the collection. Can't be null.
	 * @param value A value.
	 */
	public void add(TKey key, TValue value)
	{
		if (key == null)
		{
			throw new NullPointerException("key can't be null!");
		}
		
		//checks for duplicate keys
		for(KeyValuePair<TKey, TValue> pair : pairs)
		{
			if (pair.getKey().equals(key))
			{
				throw new DuplicateKeyException();
			}
		}
		
		//if successfully checked, add a new pair
		pairs.add(new KeyValuePair<TKey, TValue>(key, value));
	}
	
	/**
	 * Searches for the value of the specified key.
	 * @param key Key to search for.
	 * @return The value assigned to the specified key. If the key isn't within the collection, returns null.
	 */
	public TValue getValue(TKey key)
	{
		for(KeyValuePair<TKey, TValue> pair : pairs)
		{
			if (pair.getKey().equals(key))
			{
				return pair.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the size of the collection.
	 * @return The size of the internal KeyValuePair collection.
	 */
	@Override
	public int size()
	{
		return pairs.size();
	}
	
	/**
	 * Determines whether the collection is empty.
	 * @return Returns true if there are no elements in the collection, otherwise false.
	 */
	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}
	
	/**
	 * Clears the collection.
	 */
	@Override
	public void clear()
	{
		pairs.clear();
	}
}
