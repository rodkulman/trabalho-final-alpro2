package supermarket;

import services.*;
import meta.*;
import meta.collections.*;

/**
 * Defines the supermarket simulator.
 * @author rodkulman@gmail.com 
 */
public class Simulator
{
	/**
	 * Indicates for how long the simulation is going to last.
	 */
	private static final int duration = 200;
	/**
	 * Indicates the probability of new clients arriving.
	 */
	private static final double arrivalProbability = 0.1;
	/**
	 * The client queue waiting to be served.
	 */
	private IQueue<Client> queue;
	/**
	 * The cashier serving the clients.
	 */
	private Cashier cashier;
	/**
	 * Indicates whether new clients arrived.
	 */
	private ClientGenerator clientGenerator;
	/**
	 * Calculates the average waiting time.
	 */
	private Counter watingTime;
	/**
	 * Calculates the average queue size.
	 */
	private Counter queueSize;

	/**
	 * Initializes a new simulator, indicating whether it should trace.
	 * @param trace
	 */
	public Simulator(boolean trace)
	{
		queue = new QueueLinked<Client>();
		cashier = new Cashier();
		clientGenerator = new ClientGenerator(arrivalProbability);
		watingTime = new Counter();
		queueSize = new Counter();
		Trace.setTracing(trace);
	}

	public void simulate()
	{
		//each step simulates a minute
		for (int time = 0; time < duration; time++)
		{
			//check if a new client arrived.
			if (clientGenerator.generate())
			{
				//if arrived, add to the cashier queue.
				Client c = new Client(clientGenerator.getAmountGenerated(), time);
				queue.enqueue(c);

				Trace.log(time + ": cliente " + c.getID() + " (" + c.getRemainigTime() + " min) entra na fila - " + queue.size() + " pessoa(s)");
			}

			//checks if the cashier is free.
			if (cashier.isEmpty())
			{
				//if the cashier is free, checks if there is a client waiting
				if (!queue.isEmpty())
				{
					//gets the first client in the queue
					cashier.serveNewClient(queue.dequeue());
					watingTime.add(time - cashier.getCurrentClient().getArrival());

					Trace.log(time + ": cliente " + cashier.getCurrentClient().getID() + " chega ao caixa.");

				}
			}
			else
			{
				//if the cashier is busy, reduce the remaining time until it reaches 0
				if (cashier.getCurrentClient().getRemainigTime() == 0)
				{
					Trace.log(time + ": cliente " + cashier.getCurrentClient().getID() + " deixa o caixa.");

					//when it reaches 0, ends the service
					cashier.endServing();
				}
				else
				{
					cashier.getCurrentClient().decreaseRemaingTime();
				}
			}

			queueSize.add(queue.size());
		}
	}

	public void clear()
	{
		queue = new QueueLinked<Client>();
		cashier = new Cashier();
		clientGenerator = new ClientGenerator(arrivalProbability);
		watingTime = new Counter();
		queueSize = new Counter();
	}

	public void printResults()
	{
		System.out.println();
		System.out.println("Resultados da Simulacao");
		System.out.println("Duracao:" + duration);
		System.out.println("Probabilidade de chegada de clientes:" + arrivalProbability);
		System.out.println("Tempo de atendimento minimo:" + Client.tempoMinAtendimento);
		System.out.println("Tempo de atendimento maximo:" + Client.tempoMaxAtendimento);
		System.out.println("Cliente atendidos:" + cashier.getServed());
		System.out.println("Clientes ainda na fila:" + queue.size());
		System.out.println("Cliente ainda no caixa:" + (cashier.getCurrentClient() != null));
		System.out.println("Total de clientes gerados:" + clientGenerator.getAmountGenerated());
		System.out.println("Tempo medio de espera:" + watingTime.getAverage());
		System.out.println("Comprimento medio da fila:" + queueSize.getAverage());
	}
}
