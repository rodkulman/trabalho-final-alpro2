package supermarket;

import services.*;
import meta.*;
import meta.collections.*;

/**
 * Defines the supermarket simulator.
 * 
 * @author rodkulman@gmail.com
 */
public class Simulator extends BaseSimulator
{
	/**
	 * The cashier serving the clients.
	 */
	private Cashier cashier;
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
	 * 
	 * @param trace
	 */
	public Simulator()
	{
		clear();
	}

	@Override
	protected void simulation(int time)
	{
		// check if a new client arrived.
		if (clientGenerator.generate(time, 0.0, 0.0))
		{
			// if arrived, add to the cashier queue.
			Client c = clientGenerator.getGeneratedClient();
			clientQueue.enqueue(c);

			Trace.log(time + ": cliente " + c.getID() + " (" + c.getRemainigTime() + " min) entra na fila - " + clientQueue.size() + " pessoa(s)");
		}

		// checks if the cashier is free.
		if (cashier.isEmpty())
		{
			// if the cashier is free, checks if there is a client waiting
			if (!clientQueue.isEmpty())
			{
				// gets the first client in the queue
				cashier.serveNewClient(clientQueue.dequeue());
				watingTime.add(time - cashier.getCurrentClient().getArrival());

				Trace.log(time + ": cliente " + cashier.getCurrentClient().getID() + " chega ao caixa.");
			}
		}
		else
		{
			// if the cashier is busy, reduce the remaining time until it
			// reaches 0
			if (cashier.getCurrentClient().getRemainigTime() == 0)
			{
				Trace.log(time + ": cliente " + cashier.getCurrentClient().getID() + " deixa o caixa.");

				// when it reaches 0, ends the service
				cashier.endServing();
			}
			else
			{
				cashier.getCurrentClient().decreaseRemaingTime();
			}
		}

		queueSize.add(clientQueue.size());
	}

	@Override
	public void clear()
	{
		clientQueue = new QueueLinked<Client>();
		cashier = new Cashier();
		clientGenerator = new ClientGenerator(arrivalProbability);
		watingTime = new Counter();
		queueSize = new Counter();
	}

	@Override
	public void printResults()
	{
		System.out.println();
		System.out.println("Resultados da Simulacao");
		System.out.println("Duracao:" + duration);
		System.out.println("Probabilidade de chegada de clientes:" + arrivalProbability);
		System.out.println("Tempo de atendimento minimo:" + Client.minServingTime);
		System.out.println("Tempo de atendimento maximo:" + Client.maxServingTime);
		System.out.println("Cliente atendidos:" + cashier.getServed());
		System.out.println("Clientes ainda na fila:" + clientQueue.size());
		System.out.println("Cliente ainda no caixa:" + (cashier.getCurrentClient() != null));
		System.out.println("Total de clientes gerados:" + clientGenerator.getAmountGenerated());
		System.out.println("Tempo medio de espera:" + watingTime.getAverage());
		System.out.println("Comprimento medio da fila:" + queueSize.getAverage());
	}

	@Override
	public double getAverageWaitingTime()
	{
		return watingTime.getAverage();
	}

	@Override
	public double getAverageQueueSize()
	{
		return queueSize.getAverage();
	}

	@Override
	public boolean getClientStillBeingServed()
	{
		return cashier.getCurrentClient() != null;
	}

	@Override
	public int getClientsInQueue()
	{
		return clientQueue.size();
	}

}
