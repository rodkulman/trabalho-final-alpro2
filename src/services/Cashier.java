package services;

public class Cashier
{
	private Client currentClient; 
	private int served;

	public Cashier()
	{
		currentClient = null;
		served = 0;
	}

	public void serveNewClient(Client c)
	{
		currentClient = c;
	}

	public Client endServing()
	{
		Client c = currentClient;
		
		currentClient = null;
		served++;
		
		return c;
	}

	public boolean isEmpty()
	{
		return (currentClient == null);
	}

	public Client getCurrentClient()
	{
		return currentClient;
	}

	public int getServed()
	{
		return served;
	}
}
