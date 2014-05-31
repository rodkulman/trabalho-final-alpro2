package meta;

/**
 * Defines the basic behavior for Simulator classes.
 * @author rodkulman@gmail.com
 *
 */
public interface ISimulator 
{
	/**
	 * Starts the simulation.
	 */
	void simulate();
	/**
	 * Clear all the data for another simulation.
	 */
	void clear();
	/**
	 * Print the results of the simulation.
	 */
	void printResults();
}
