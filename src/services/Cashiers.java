package services;

import meta.collections.ListLinked;

/**
 * Represents a collection of cashiers.
 * @author rodkulman@gmail.com
 *
 */
public class Cashiers
{
	/**
	 * The list of cashiers.
	 */
	protected ListLinked<Cashier> cashiers;
	
	/**
	 * Initializes an empty list of cashier objects.
	 */
	protected Cashiers()
	{
		cashiers = new ListLinked<>();
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
}
