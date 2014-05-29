package services;

import java.util.Random;

/**
 * Defines a client generator, which creates them at random times.
 */
public class ClientGenerator
{
	/**
	 * Probability of generation, from 0.0 to 1.0
	 */
	private double probability;
	/**
	 * The amount of clients generated.
	 */
	private int amountGenerated;
	/**
	 * Random number generator.
	 */
	private static final Random rnd = new Random();

	/**
	 * Initializes a new instance with a specified probability.
	 * @param prob The probability of generating new clients.
	 */
	public ClientGenerator(double prob)
	{
		probability = prob;
		amountGenerated = 0;
	}

	/**
	 * Indicates whether a new client was generated.
	 * @return True if generated, otherwise false.
	 */
	public boolean generate()
	{
		boolean created = false;
		
		if (rnd.nextDouble() < probability)
		{
			amountGenerated++;
			created = true;
		}
		
		return created;
	}

	/**
	 * Gets the amount of clients generated.
	 * @return Returns the amount of client objects generated.
	 */
	public int getAmountGenerated()
	{
		return amountGenerated;
	}
}
