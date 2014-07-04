package services;

import java.util.Random;

/**
 * Defines a client generator, which creates them at random times.
 * 
 * @author rodkulman@gmail.com
 */
public class ClientGenerator
{
	/**
	 * Probability of generation, from 0.0 to 1.0
	 */
	private final double probability;
	/**
	 * The amount of clients generated.
	 */
	private int amountGenerated;
	/**
	 * Random number generator.
	 */
	private static final Random rnd = new Random();

	/**
	 * The last generated client.
	 */
	private Client generatedClient = null;

	/**
	 * Initializes a new instance with a specified probability.
	 * 
	 * @param prob
	 *            The probability of generating new clients.
	 */
	public ClientGenerator(double prob)
	{
		probability = prob;
		amountGenerated = 0;
	}

	/**
	 * Indicates whether a new client was generated.
	 * 
	 * @param arrival
	 *            If a clients gets generated, sets its arrival time.
	 * @param priorityProbability
	 *            The probability of the generated client to have a priority.
	 * @return True if a client object was generated, otherwise false.
	 */
	public boolean generate(int arrival, double priorityProbability, double managerProbability)
	{
		if (rnd.nextDouble() < probability)
		{
			generatedClient = new Client(++amountGenerated, arrival, rnd.nextDouble() < priorityProbability, rnd.nextDouble() < managerProbability);
		}

		return generatedClient != null;
	}

	/**
	 * Gets the client generated by the generate() method.
	 * 
	 * @return The generated client.
	 */
	public Client getGeneratedClient()
	{
		if (generatedClient == null) { throw new RuntimeException("No Client Generated."); }

		Client retVal = generatedClient;
		generatedClient = null;

		return retVal;
	}

	/**
	 * Gets the amount of clients generated.
	 * 
	 * @return Returns the amount of client objects generated.
	 */
	public int getAmountGenerated()
	{
		return amountGenerated;
	}
	
	/**
	 * Resets the client generator information.
	 */
	public void clear()
	{
		amountGenerated = 0;
	}
}
