package services;

/**
 * Defines a cashir serving a client.
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
	 * Initializes a new instance with no client, and no clients served.
	 */
	public Cashier()
	{
		currentClient = null;
		served = 0;
	}

	/**
	 * Starts serving a new client.
	 * @param c A new client to be served.
	 */
	public void serveNewClient(Client c)
	{
		currentClient = c;
	}

	/**
	 * End the client service, and increase the served amount.
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
	 * @return True if there is a current client, otherwise false.
	 */
	public boolean isEmpty()
	{
		return (currentClient == null);
	}

	/**
	 * Gets the current client.
	 * @return The current client. Null if there is none.
	 */
	public Client getCurrentClient()
	{
		return currentClient;
	}

	/**
	 * The amount of clients served.
	 * @return Returns the amount of clients served.
	 */
	public int getServed()
	{
		return served;
	}
}
