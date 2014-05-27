package supermarket;

/*
 * Programa principal da simulacao
 */
public class App
{
	public static void main(String[] args)
	{
		Simulator sim = new Simulator(true);
		sim.simular();
		sim.imprimirResultados();
	}
}
