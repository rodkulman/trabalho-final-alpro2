package supermarket;

import meta.Trace;

/**
 * Program initializer
 * @author rodkulman@gmail.com
 */
public final class App
{
	public static void main(String[] args)
	{
		Trace.setTracing(true);
		Simulator sim = new Simulator();
		sim.simulate();
		sim.printResults();
	}
}
