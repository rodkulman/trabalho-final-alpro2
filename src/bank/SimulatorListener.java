package bank;

import services.*;

/**
 * Allows for listening the events fired by the simulator class.
 * @author rodkulman@gmail.com
 *
 */
public interface SimulatorListener 
{
	/**
	 * Fires when a client has been served.
	 * @param c The client served.
	 * @param time The time the client has been served.
	 */
	void ClientServed(final Client c, int time);
	
	/**
	 * Fires when the simulation has ended;
	 */
	void SimulationEnded();
}
