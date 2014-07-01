package meta;

import config.XMLConfig;
import meta.collections.IQueue;
import services.Client;
import services.ClientGenerator;

/**
 * Defines the basic behavior for Simulator classes.
 * 
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
	 * The class listening the events fired by this class;
	 */
	private SimulatorListener listener;

	/**
	 * Adds a new listener to this simulator.
	 * 
	 * @param listener
	 *            The object listening to the events fired by this simulator.
	 */
	public void addListener(SimulatorListener listener)
	{
		this.listener = listener;
	}

	/**
	 * Starts the simulation.
	 */
	public void simulate()
	{
		// each step simulates a minute
		for (int time = 0; time < duration; time++)
		{
			simulation(time);
		}

		// warns the listener the simulation ended
		onSimulationEnded();
	}

	// event firing

	protected void onClientServed(final Client c, final int time)
	{
		if (listener != null)
		{
			listener.ClientServed(c, time);
		}
	}

	protected void onClientArrived(final Client c, final int time)
	{
		if (listener != null)
		{
			listener.ClientArrived(c, time);
		}
	}

	protected void onSimulationEnded()
	{
		if (listener != null)
		{
			listener.SimulationEnded();
		}
	}

	// end event firing

	/**
	 * Calls the simulation
	 * 
	 * @param time
	 *            Current time
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

	/**
	 * Indicates the average time clients had to wait in queue.
	 * 
	 * @return A double value indicating the average waiting time.
	 */
	public abstract double getAverageWaitingTime();

	/**
	 * Indicates the average size of the queue throughout the duration.
	 * 
	 * @return A double value indicating the average queue size.
	 */
	public abstract double getAverageQueueSize();

	/**
	 * Indicates whether there are clients yet begin served.
	 * 
	 * @return Returns true if any cashier is occupied, otherwise false.
	 */
	public abstract boolean getClientStillBeingServed();

	/**
	 * Indicates the amount of clients yet waiting.
	 * 
	 * @return A integer value indicating the amount of clients yet in queue.
	 */
	public abstract int getClientsInQueue();
}
