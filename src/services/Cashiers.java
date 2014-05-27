package services;

import meta.collections.ListLinked;

/**
 * Represents a collection of cashiers.
 * @author rodkulman@gmail.com
 *
 */
public abstract class Cashiers
{
	protected ListLinked<Cashier> cashiers;
	
	protected Cashiers()
	{
		cashiers = new ListLinked<>();
	}
	
	public boolean isEmpty()
	{
		for (Cashier c : cashiers)
		{
			if (c.isEmpty()) { return true; }
		}
		
		return false;
	}
}
