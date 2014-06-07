package services;

import java.util.Iterator;

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
	
	public Cashier getFirstEmpty(boolean manager, boolean priority)
	{
		for (Cashier c : cashiers)
		{
			if (c.isEmpty() && c.isManager() == manager && c.isPriority() == priority)
			{
				return c;
			}
		}
		
		return null;
	}

	@Override
	public Iterator<Cashier> iterator() 
	{
		return cashiers.iterator();
	}
}
