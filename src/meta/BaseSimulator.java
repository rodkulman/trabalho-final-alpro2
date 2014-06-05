package meta;

import io.XMLConfig;
import meta.collections.IQueue;
import services.Client;
import services.ClientGenerator;

/**
 * Defines the basic behavior for Simulator classes.
 * @author rodkulman@gmail.com
 *
 */
public abstract class BaseSimulator 
{
	/**
	 * Indicates for how long the simulation is going to last.
	 */
	protected final int duration = XMLConfig.getInt("duration");
	/**
	 * Indicates the probability of new clients arriving.
	 */
	protected final double arrivalProbability = XMLConfig.getDouble("arrivalProbability");
	/**
	 * The client queue waiting to be served.
	 */
	protected IQueue<Client> clientQueue;
	/**
	 * Indicates whether new clients arrived.
	 */
	protected ClientGenerator clientGenerator;
	
	/**
	 * Starts the simulation.
	 */
	public void simulate()
	{
		//each step simulates a minute
		for (int time = 0; time < duration; time++)
		{
			simulation(time);
		}
	}
	
	/**
	 * Calls the simulation
	 * @param time Current time
	 */
	protected abstract void simulation(int time);
	
	/**
	 * Clear all the data for another simulation.
	 */
	public abstract void clear();
	/**
	 * Print the results of the simulation.
	 */
	public abstract void printResults();
}
