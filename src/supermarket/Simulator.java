package supermarket;

import services.*;
import meta.*;
import meta.collections.*;

/*
 * Classe com a logica da simulacao passo-a-passo
 */
public class Simulator
{
	private static final int duracao = 200;
	private static final double probabilidadeChegada = 0.1;
	private IQueue<Client> fila;
	private Cashier caixa;
	private ClientGenerator geradorClientes;
	private Counter statTemposEsperaFila;
	private Counter statComprimentosFila;

	public Simulator(boolean t)
	{
		fila = new QueueLinked<Client>();
		caixa = new Cashier();
		geradorClientes = new ClientGenerator(probabilidadeChegada);
		statTemposEsperaFila = new Counter();
		statComprimentosFila = new Counter();
		Trace.setTracing(t);
	}

	public void simular()
	{
		// realizar a simulacao por um certo numero de passos de duracao
		for (int tempo = 0; tempo < duracao; tempo++)
		{
			// verificar se um cliente chegou
			if (geradorClientes.gerar())
			{
				// se cliente chegou, criar um cliente e inserir na fila do
				// caixa
				Client c = new Client(geradorClientes.getQuantidadeGerada(), tempo);
				fila.enqueue(c);

				Trace.log(tempo + ": cliente " + c.getNumero() + " (" + c.getTempoAtendimento() + " min) entra na fila - " + fila.size() + " pessoa(s)");
			}

			// verificar se o caixa esta vazio
			if (caixa.isEmpty())
			{
				// se o caixa esta vazio, atender o primeiro cliente da fila se
				// ele existir
				if (!fila.isEmpty())
				{
					// tirar o cliente do inicio da fila e atender no caixa
					caixa.serveNewClient(fila.dequeue());
					statTemposEsperaFila.add(tempo - caixa.getCurrentClient().getInstanteChegada());

					Trace.log(tempo + ": cliente " + caixa.getCurrentClient().getNumero() + " chega ao caixa.");

				}
			}
			else
			{
				// se o caixa ja esta ocupado, diminuir de um em um o tempo de
				// atendimento ate chegar a zero
				if (caixa.getCurrentClient().getTempoAtendimento() == 0)
				{

					Trace.log(tempo + ": cliente " + caixa.getCurrentClient().getNumero() + " deixa o caixa.");

					caixa.endServing();
				}
				else
				{
					caixa.getCurrentClient().decrementarTempoAtendimento();
				}
			}

			statComprimentosFila.add(fila.size());
		}
	}

	public void limpar()
	{
		fila = new QueueLinked<Client>();
		caixa = new Cashier();
		geradorClientes = new ClientGenerator(probabilidadeChegada);
		statTemposEsperaFila = new Counter();
		statComprimentosFila = new Counter();
	}

	public void imprimirResultados()
	{
		System.out.println();
		System.out.println("Resultados da Simulacao");
		System.out.println("Duracao:" + duracao);
		System.out.println("Probabilidade de chegada de clientes:" + probabilidadeChegada);
		System.out.println("Tempo de atendimento minimo:" + Client.tempoMinAtendimento);
		System.out.println("Tempo de atendimento maximo:" + Client.tempoMaxAtendimento);
		System.out.println("Cliente atendidos:" + caixa.getServed());
		System.out.println("Clientes ainda na fila:" + fila.size());
		System.out.println("Cliente ainda no caixa:" + (caixa.getCurrentClient() != null));
		System.out.println("Total de clientes gerados:" + geradorClientes.getQuantidadeGerada());
		System.out.println("Tempo medio de espera:" + statTemposEsperaFila.getAverage());
		System.out.println("Comprimento medio da fila:" + statComprimentosFila.getAverage());
	}
}
