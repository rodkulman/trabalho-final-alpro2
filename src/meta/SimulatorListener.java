package meta;

import services.*;

/**
 * Allows for listening the events fired by the simulator class.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public interface SimulatorListener
{
	/**
	 * Fires when a client has been served.
	 * 
	 * @param c
	 *            The client served.
	 * @param time
	 *            The time the client has been served.
	 */
	void ClientServed(final Client c, final int time);

	/**
	 * Fires when a new client has arrived.
	 * 
	 * @param c
	 *            The Client the has arrived.
	 * @param time
	 *            The time the client has arrived.
	 */
	void ClientArrived(final Client c, final int time);

	/**
	 * Fires when the simulation has ended;
	 */
	void SimulationEnded();
}
