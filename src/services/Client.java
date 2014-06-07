package services;

import io.XMLConfig;

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
	 * Indicates whether this client is looking for a manager;
	 */
	private final boolean lookingForManager;

	/**
	 * Minimum service time.
	 */
	public static final int minServingTime = XMLConfig.getInt("minServingTime");
	/**
	 * Maximum service time.
	 */
	public static final int maxServingTime = XMLConfig.getInt("maxServingTime");

	/**
	 * Initializes a new client with an ID and moment of arrival.
	 * @param ID Its unique ID.
	 * @param arrival Moment of arrival.
	 * @param priority Indicates whether the client has a priority.
	 */
	protected Client(int ID, int arrival, boolean priority, boolean lookingForManager)
	{
		this.ID = ID;
		this.arrival = arrival;
		this.priority = priority;
		this.lookingForManager = lookingForManager;
		
		//Generates a number between 5 and 20
		remaingTime = new Random().nextInt(maxServingTime - minServingTime + 1) + minServingTime;
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
	
	/**
	 * Indicates whether this client is looking for a manager;
	 * @return True if this client is looking for a manager, otherwise false.
	 */
	public boolean isLookingForManager() 
	{
		return lookingForManager;
	}
}
