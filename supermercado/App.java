package trunk.supermercado;

/*
 * Programa principal da simulacao
 */
public class App
{
	public static void main(String[] args)
	{
		Simulacao sim = new Simulacao(true);
		sim.simular();
		sim.imprimirResultados();
	}
}
