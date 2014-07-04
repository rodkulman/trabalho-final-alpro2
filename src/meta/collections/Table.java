package meta.collections;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 'Tis sorcery.
 * @author rodkulman@gmail.com
 *
 * @param <TKey1>
 * @param <TKey2>
 * @param <TValue>
 */
public class Table<TKey1, TKey2, TValue>
{
	private ArrayList<TKey1> keys1;
	private ArrayList<TKey2> keys2;
	private TValue[][] values;
	private int size;
	
	@SuppressWarnings("unchecked")
	public Table()
	{
		keys1 = new ArrayList<>();
		keys2 = new ArrayList<>();
		values = (TValue[][]) new Object[8][8];
		
		size = 0;
	}
	
	@SuppressWarnings("unchecked")
	private void checkSize()
	{
		if (values.length == size)
		{
			TValue[][] tmp = (TValue[][]) new Object[size * 2][size * 2];
			
			for (int i = 0; i < size; i++)
			{
				tmp[i] = Arrays.copyOf(values[i], size * 2);
			}
			
			values = tmp;
		}
	}
	
	public void add(TKey1 key1, TKey2 key2, TValue value)
	{
		checkForDuplicateKey(key1, key2);
		checkSize();
		
		keys1.add(key1);
		keys2.add(key2);
		
		values[keys1.size() - 1][keys2.size() - 1] = value;
		size++;
	}
	
	public TValue get(TKey1 key1, TKey2 key2)
	{
		for (int i : getIndexOfAll(key1))
		{
			if (keys2.get(i) == key2)
			{
				return values[i][i];
			}
		}
		
		return null;
	}
	
	private ArrayList<Integer> getIndexOfAll(TKey1 key)
	{
		ArrayList<Integer> retVal = new ArrayList<>();
		
		for (int i = 0; i < keys1.size(); i++)
		{
			if (keys1.get(i) == key)
				{
			retVal.add(i);
				}
		}
		
		return retVal;
	}

	private void checkForDuplicateKey(TKey1 key1, TKey2 key2)
	{
		// TODO Auto-generated method stub
		
	}
}
