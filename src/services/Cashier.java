package services;

/**
 * Defines a cashier serving a client.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class Cashier
{
	/**
	 * The client current being served.
	 */
	private Client currentClient;
	/**
	 * Amount of clients served.
	 */
	private int served;

	/**
	 * Indicates whether this cashier has a priority.
	 */
	private final boolean priority;
	/**
	 * Indicates whether this cashier is a manager.
	 */
	private final boolean manager;
	/**
	 * Indicates the cashier id.
	 */
	private final int id;

	/**
	 * Indicates the number of cashiers created.
	 */
	private static int cashiersCreated;

	/**
	 * Initializes a new instance with no client, and no clients served.
	 */
	public Cashier()
	{
		this(false, false);
	}

	/**
	 * Instances a cashier, determining priority and manager status.
	 * 
	 * @param priority
	 *            Indicates the priority.
	 * @param manager
	 *            Indicates whether this cashier is a manager.
	 */
	public Cashier(boolean priority, boolean manager)
	{
		this.priority = priority;
		this.manager = manager;
		this.currentClient = null;
		this.served = 0;

		// increments and add to the cashier id
		this.id = ++cashiersCreated;
	}

	/**
	 * Indicates whether this cashier has priority.
	 * 
	 * @return True if this cashier has priority, otherwise false.
	 */
	public boolean isPriority()
	{
		return priority;
	}

	/**
	 * Indicates whether this cashier is a manager.
	 * 
	 * @return True if this cashier is a actually a manager, false if is a
	 *         regular cashier.
	 */
	public boolean isManager()
	{
		return manager;
	}

	/**
	 * Starts serving a new client.
	 * 
	 * @param c
	 *            A new client to be served.
	 */
	public void serveNewClient(Client c) throws Exception
	{
		if (currentClient != null) { throw new Exception("Cashier is already serving a client."); }

		currentClient = c;
	}

	/**
	 * End the client service, and increase the served amount.
	 * 
	 * @return The served client.
	 */
	public Client endServing()
	{
		Client c = currentClient;

		currentClient = null;
		served++;

		return c;
	}

	/**
	 * Indicates whether the cashier is serving anyone.
	 * 
	 * @return True if there is a current client, otherwise false.
	 */
	public boolean isEmpty()
	{
		return (currentClient == null);
	}

	/**
	 * Gets the current client.
	 * 
	 * @return The current client. Null if there is none.
	 */
	public Client getCurrentClient()
	{
		return currentClient;
	}

	/**
	 * The amount of clients served.
	 * 
	 * @return Returns the amount of clients served.
	 */
	public int getAmountServed()
	{
		return served;
	}

	/**
	 * Gets the ID of the cashier.
	 * 
	 * @return Returns a unique integer indicating the cashier Id.
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Clears the cashier information.
	 */
	public void clear()
	{
		this.currentClient = null;
		this.served = 0;
	}
}
