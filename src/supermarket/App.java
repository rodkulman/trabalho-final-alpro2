package supermarket;

/**
 * Program initializer
 * @author rodkulman@gmail.com
 */
public class App
{
	public static void main(String[] args)
	{
		Simulator sim = new Simulator(true);
		sim.simulate();
		sim.printResults();
	}
}
