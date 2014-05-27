package atendimento;

import java.util.Random;

public class Cliente
{
	// numero do cliente
	private int numero;
	private int instanteChegada;

	// quantidade de tempo que resta para o cliente no caixa
	private int tempoAtendimento;
	private static final Random gerador = new Random();
	public static final int tempoMinAtendimento = 5;
	public static final int tempoMaxAtendimento = 10;

	public Cliente(int n, int c)
	{
		numero = n;
		instanteChegada = c;

		// gera valores entre 5 e 20
		tempoAtendimento = gerador.nextInt(tempoMaxAtendimento - tempoMinAtendimento + 1) + tempoMinAtendimento;
	}

	public int getNumero()
	{
		return numero;
	}

	public int getInstanteChegada()
	{
		return instanteChegada;
	}

	public void decrementarTempoAtendimento()
	{
		tempoAtendimento--;
	}

	public int getTempoAtendimento()
	{
		return tempoAtendimento;
	}
}
