package services;

import java.util.Random;

/**
 * Represents a client.
 * @author rodkulman@gmail.com
 *
 */
public class Client
{
	/**
	 * The unique client ID.
	 */
	private final int ID;
	/**
	 * The moment of arrival.
	 */
	private final int arrival;

	/**
	 * Remaining time to end the serving.  
	 */
	private int remaingTime;
	
	/**
	 * Indicates whether this client requires priority.
	 */
	private final boolean priority;
	
	/**
	 * Minimum service time.
	 */
	public static final int tempoMinAtendimento = 5;
	/**
	 * Maximum service time.
	 */
	public static final int tempoMaxAtendimento = 10;

	/**
	 * Initializes a new client with an ID and moment of arrival.
	 * @param ID
	 * @param arrival
	 */
	public Client(int ID, int arrival)
	{
		this(ID, arrival, false);
	}
	
	public Client(int ID, int arrival, boolean priority)
	{
		this.ID = ID;
		this.arrival = arrival;
		this.priority = priority;
		
		//Generates a number between 5 and 20
		remaingTime = new Random().nextInt(tempoMaxAtendimento - tempoMinAtendimento + 1) + tempoMinAtendimento;
	}

	/**
	 * Gets the Unique ID for the client.
	 * @return The client's unique ID.
	 */
	public int getID()
	{
		return ID;
	}

	/**
	 * Gets the moment of arrival.
	 * @return The moment the client arrived.
	 */
	public int getArrival()
	{
		return arrival;
	}

	/**
	 * Reduces the remaining time.
	 */
	public void decreaseRemaingTime()
	{
		remaingTime--;
	}

	/**
	 * Gets the remaining time.
	 * @return Returns the remaining time.
	 */
	public int getRemainigTime()
	{
		return remaingTime;
	}
	
	/**
	 * Indicates whether this client requires priority.
	 * @return True if this client requires priority, otherwise false.
	 */
	public boolean requiresPriority()
	{
		return priority;
	}
}
