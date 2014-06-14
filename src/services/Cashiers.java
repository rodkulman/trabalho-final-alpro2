package services;

import java.util.Iterator;

import meta.collections.IQueryable;
import meta.collections.ListLinked;

/**
 * Represents a collection of cashiers.
 * @author rodkulman@gmail.com
 *
 */
public class Cashiers implements Iterable<Cashier>
{
	/**
	 * The list of cashiers.
	 */
	protected ListLinked<Cashier> cashiers;
	
	/**
	 * Initializes an empty list of cashier objects.
	 */
	public Cashiers()
	{
		cashiers = new ListLinked<>();
	}
	
	public void add(Cashier c)
	{
		cashiers.add(c);
	}
	
	/**
	 * Looks for cashiers with no clients.
	 * @return True if there is a single cashier has no client.
	 */
	public boolean isAnyEmpty()
	{
		for (Cashier c : cashiers)
		{
			if (c.isEmpty()) { return true; }
		}
		
		return false;
	}
	
	/**
	 * Iterates through the list looking for cashier with the specified parameter
	 * @param query Allows for custom filtering
	 * @return An Iterable that iterates though the list looking for cashier with the specified parameters
	 */
	public Iterable<Cashier> where(final IQueryable<Cashier> query)
	{
		return new Iterable<Cashier>() 
		{
			@Override
			public Iterator<Cashier> iterator() 
			{
				return new Iterator<Cashier>()
				{
					int index = 0;
					
					@Override
					public void remove() { }
					
					@Override
					public Cashier next() 
					{
						return cashiers.get(index);
					}
					
					@Override
					public boolean hasNext() 
					{
						for (; index < cashiers.size(); index++)
						{
							if (query.where(cashiers.get(index)))
							{
								return true;
							}
						}
						
						return false;
					}
				};
			}
		};
	}

	@Override
	public Iterator<Cashier> iterator() 
	{
		return cashiers.iterator();
	}
	
	/**
	 * Indicates whether there is any cashier serving a client.
	 * @return True if any cashier is serving a client, otherwise false.
	 */
	public boolean isAnyOccupied()
	{
		for (Cashier c : this)
		{
			if (!c.isEmpty())
			{
				return true;
			}
		}
		
		return false;
	}
}
