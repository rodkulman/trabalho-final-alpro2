package meta;

/**
 * Defines a class to perform averages.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class Counter
{
	/**
	 * Current value;
	 */
	private double value;
	/**
	 * Amount of times added.
	 */
	private int quantity;

	public Counter()
	{
		this.clear();
	}

	/**
	 * Gets the current value.
	 * 
	 * @return The current value
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * Gets the amount of times this instance added values.
	 * 
	 * @return The amount of times this instance added values.
	 */
	public int getQuantity()
	{
		return quantity;
	}

	/**
	 * Adds to value and increases quantity by one.
	 * 
	 * @param d
	 *            Value to be added.
	 */
	public void add(double d)
	{
		value += d;
		quantity++;
	}

	/**
	 * Calculates the average for the current values.
	 * 
	 * @return The arithmetic average for the current values.
	 */
	public double getAverage()
	{
		if (quantity != 0)
		{
			return value / quantity;
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * Clear all the information in the counter.
	 */
	public void clear()
	{
		this.quantity = 0;
		this.value = 0;
	}
}