package meta;

/*
 * Classe utilitaria que realiza calculos de media aritmetica
 */
public class Acumulador
{
	private double valor;
	private int contador;

	public Acumulador()
	{
		valor = 0;
		contador = 0;
	}

	public double getValor()
	{
		return valor;
	}

	public int getContagem()
	{
		return contador;
	}

	public void adicionar(double d)
	{
		valor += d;
		contador++;
	}

	public double getMedia()
	{
		if (contador != 0)
		{
			return valor / contador;
		}
		else
		{
			return 0;
		}
	}
}