package bank;

import config.XMLConfig;
import services.*;
import meta.*;
import meta.collections.*;

/**
 * Simulates a bank agency queue.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class Simulator extends BaseSimulator
{
	/**
	 * The client queue for regular cashiers.
	 */
	private IQueue<Client> cashierQueue;

	/**
	 * The client queue for managers.
	 */
	private IQueue<Client> managerQueue;

	/**
	 * The priority client queue for cashier.
	 */
	private IQueue<Client> pCashierQueue;

	/**
	 * The priority client queue for managers.
	 */
	private IQueue<Client> pManagerQueue;

	/**
	 * Measures the average waiting time
	 */
	private Counter waitingTime;

	/**
	 * Measures the average queue size
	 */
	private Counter queueSize;

	/**
	 * Stores the amount of clients that didn't wait to be served.
	 */
	private int amountOfNoWaitServings;

	/**
	 * Measures total average time queues were empty.
	 */
	private Counter emptyQueueTime;

	/*
	 * The inherited clientQueue object will be used as the initial queue
	 */

	public Simulator()
	{
		super();

		clientQueue = new QueueLinked<>();

		int cashierNumber = XMLConfig.getInt("cashierNumber");
		int priorityCashierNumber = XMLConfig.getInt("priorityCashierNumber");
		int managerNumber = XMLConfig.getInt("managerNumber");
		int priorityManagerNumber = XMLConfig.getInt("priorityManagerNumber");

		// creates the cashiers, first the regulars, then the managers
		// the amount of cashiers created per call is the sum of both
		// parameters.
		createCashiers(cashierNumber, priorityCashierNumber, false);
		createCashiers(managerNumber, priorityManagerNumber, true);

		clientGenerator = new ClientGenerator(arrivalProbability);
		cashierQueue = new QueueLinked<>();
		managerQueue = new QueueLinked<>();
		pCashierQueue = new QueueLinked<>();
		pManagerQueue = new QueueLinked<>();

		// counters
		waitingTime = new Counter();
		queueSize = new Counter();
		emptyQueueTime = new Counter();

		clear();
	}

	/**
	 * Gets the correct queue for the client
	 * 
	 * @param c
	 *            the client whose correct queue will be searched
	 * @return the correct queue for that client
	 */
	private IQueue<Client> getCorrectQueue(Client c)
	{
		if (c.requiresPriority())
		{
			if (c.isLookingForManager())
			{
				return pManagerQueue;
			}
			else
			{
				return pCashierQueue;
			}
		}
		else
		{
			if (c.isLookingForManager())
			{
				return managerQueue;
			}
			else
			{
				return cashierQueue;
			}
		}
	}

	/**
	 * Creates the cashiers as specified by the parameters.
	 * 
	 * @param regularCount
	 *            Number of regular cashiers.
	 * @param priorityCount
	 *            Number of cashiers with priority.
	 * @param isManager
	 *            Indicates whether the cashiers being created are managers.
	 */
	private void createCashiers(int regularCount, int priorityCount, boolean isManager)
	{
		// total amount to be created are the sum of both parameters.
		int total = regularCount + priorityCount;

		for (int i = 0; i < total; i++)
		{
			// reduce the amount of priority cashiers as they are added.
			Cashier c = new Cashier(priorityCount-- > 0, isManager);

			cashiers.add(c);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void simulation(int time)
	{
		/*
		 * Clients come from the clientQueue object Depending, they will be
		 * dequeued to one of the two queues From then, they will be served
		 * according to their priorities.
		 */

		if (clientGenerator.generate(time, XMLConfig.getDouble("priorityProbality"), XMLConfig.getDouble("managerProbality")))
		{
			Client c = clientGenerator.getGeneratedClient();
			clientQueue.enqueue(c);

			Trace.log("%s: Client %s arrived.", time, c.getID());

			// warns the simulator that a new client arrived
			onClientArrived(c, time);
		}

		// checks if there is any cashier free and if there is any client
		if (cashiers.isAnyEmpty() && !clientQueue.isEmpty())
		{
			// gather all of them in their respective queues
			while (!clientQueue.isEmpty())
			{
				Client c = clientQueue.dequeue();
				getCorrectQueue(c).enqueue(c);
			}

			/*
			 * Dequeues the clients into cashiers
			 */

			// empty, regular, non-priority
			dequeueClients(cashierQueue, new IQueryable<Cashier>()
			{
				public boolean where(Cashier item)
				{
					return item.isEmpty() && !item.isManager() && !item.isPriority();
				}
			}, time);

			// empty, manager, non-priority
			dequeueClients(managerQueue, new IQueryable<Cashier>()
			{
				public boolean where(Cashier item)
				{
					return item.isEmpty() && item.isManager() && !item.isPriority();
				}
			}, time);

			// empty, regular, priority
			dequeueClients(pCashierQueue, new IQueryable<Cashier>()
			{
				public boolean where(Cashier item)
				{
					return item.isEmpty() && !item.isManager() && item.isPriority();
				}
			}, time);

			// empty, manager, priority
			dequeueClients(pManagerQueue, new IQueryable<Cashier>()
			{
				public boolean where(Cashier item)
				{
					return item.isEmpty() && item.isManager() && item.isPriority();
				}
			}, time);
		}

		// iterates all the cashiers, decreasing serving time for each occupied
		// cashier
		for (Cashier c : cashiers)
		{
			if (!c.isEmpty())
			{
				if (c.getCurrentClient().getRemainigTime() == 0)
				{
					// warns the simulator a client a client has been served
					onClientServed(c, c.getCurrentClient(), time);

					Trace.log("%s: Client %s served.", time, c.getCurrentClient().getID());

					c.endServing();
				}
				else
				{
					c.getCurrentClient().decreaseRemaingTime();
				}
			}
		}

		queueSize.add(clientQueue.size() + pCashierQueue.size() + pManagerQueue.size() + cashierQueue.size() + managerQueue.size());
		
		checkEmptyQueue(clientQueue, pCashierQueue, pManagerQueue, cashierQueue, managerQueue);
	}
	
	@SuppressWarnings("unchecked")
	private void checkEmptyQueue(final IQueue<Client>... queues)
	{
		for (IQueue<Client> c : queues)
		{
			if (c.isEmpty())
			{
				emptyQueueTime.add(1);
			}
			else
			{
				emptyQueueTime.add(0);
			}
		}
	}

	/**
	 * Dequeues client into cashiers
	 * 
	 * @param queue
	 *            The queue being dequeued
	 * @param query
	 *            The where provider for filtering the cashiers
	 */
	private void dequeueClients(IQueue<Client> queue, IQueryable<Cashier> query, int time)
	{
		// it isn't checked whether the queue is not empty before calling this
		// method.
		if (queue.isEmpty()) { return; }

		for (Cashier c : cashiers.where(query))
		{
			// the queue can become empty during the dequeuing
			if (queue.isEmpty()) { return; }

			try
			{
				Client client = queue.dequeue();
				c.serveNewClient(client);
				onCashierStartedServing(c, client, time);

				// checks whether the client didn't wait be served
				if (client.getArrival() == time)
				{
					amountOfNoWaitServings++;
				}

				// calculates the time it took to get served
				waitingTime.add(time - client.getArrival());
			}
			catch (Exception ex)
			{
				Trace.log(ex);
				return;
			}
		}
	}

	@Override
	public void clear()
	{
		clientQueue.clear();
		cashierQueue.clear();
		managerQueue.clear();
		pCashierQueue.clear();
		pManagerQueue.clear();

		waitingTime.clear();
		queueSize.clear();		
		emptyQueueTime.clear();
		
		clientGenerator.clear();

		for (Cashier c : cashiers)
		{
			c.clear();
		}

		amountOfNoWaitServings = 0;
		
	}

	@Override
	public void printResults()
	{

	}

	@Override
	public double getAverageWaitingTime()
	{
		return waitingTime.getAverage();
	}

	@Override
	public double getAverageQueueSize()
	{
		return queueSize.getAverage();
	}

	@Override
	public boolean getClientStillBeingServed()
	{
		return cashiers.isAnyOccupied();
	}

	@Override
	public int getClientsInQueue()
	{
		return clientQueue.size() + cashierQueue.size() + managerQueue.size() + pCashierQueue.size() + pManagerQueue.size();
	}

	@Override
	public int getAmountOfNoWaitServings()
	{
		return amountOfNoWaitServings;
	}

	@Override
	public double getEmptyQueueTime()
	{
		return emptyQueueTime.getAverage();
	}
}
