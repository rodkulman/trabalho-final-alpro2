package bank;

import java.util.*;

import services.*;
import io.XMLConfig;
import meta.*;
import meta.collections.*;

public class Simulator extends BaseSimulator
{
	/**
	 * The multiple cashiers the bank has.
	 */
	Cashiers cashiers;
	
	/**
	 * The client queue for regular cashiers.
	 */
	IQueue<Client> cashierQueue;
	
	/**
	 * The client queue for managers.
	 */
	IQueue<Client> managerQueue;
	
	/**
	 * The priority client queue for cashier.
	 */
	IQueue<Client> pCashierQueue;
	
	/**
	 * The priority client queue for managers.
	 */
	IQueue<Client> pManagerQueue;
	
	/*
	 * The inherited clientQueue object will be used as the initial queue
	 */
	
	/**
	 * Random generator.
	 */
	Random rnd;
	
	public Simulator()
	{
		cashiers = new Cashiers();
		clientQueue = new QueueLinked<>();
		
		int cashierNumber = XMLConfig.getInt("cashierNumber");
		int priorityCashierNumber = XMLConfig.getInt("priorityCashierNumber");
		int managerNumber = XMLConfig.getInt("managerNumber");
		int priorityManagerNumber = XMLConfig.getInt("priorityManagerNumber");
		
		// creates the cashiers, first the regulars, then the managers
		// the amount of cashiers created per call is the sum of both parameters.
		createCashiers(cashierNumber, priorityCashierNumber, false);		
		createCashiers(managerNumber, priorityManagerNumber, true);
		
		rnd = new Random();
		
		//cashierQueue = new bank.services.PriorityQueue();
		//managerQueue = new bank.services.PriorityQueue();
		cashierQueue = new QueueLinked<>();
		managerQueue = new QueueLinked<>();
		pCashierQueue = new QueueLinked<>();
		pManagerQueue = new QueueLinked<>();
	}
	
	/**
	 * Gets the correct queue for the client
	 * @param c the client whose correct queue will be searched
	 * @return the correct queue for that client
	 */
	private IQueue<Client> getCorrectQueue(Client c)
	{
		if (c.requiresPriority())
		{
			if (c.isLookingForManager()) { return pManagerQueue; }
			else { return pCashierQueue; }
		}
		else
		{
			if (c.isLookingForManager()) { return managerQueue; }
			else { return cashierQueue; }
		}
	}
	
	/**
	 * Creates the cashiers as specified by the parameters.
	 * @param regularCount Number of regular cashiers.
	 * @param priorityCount Number of cashiers with priority.
	 * @param isManager Indicates whether the cashiers being created are managers.
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
	
	@Override
	protected void simulation(int time) 
	{
		/*
		 * Clients come from the clientQueue object
		 * Depending, they will be dequeued to one of the two queues
		 * From then, they will be served according to their priorities. 
		 */
		
		if (clientGenerator.generate(time, XMLConfig.getDouble("priorityProbality"), XMLConfig.getDouble("managerProbality")))
		{
			Client c = clientGenerator.getGeneratedClient();			
			clientQueue.enqueue(c);
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
			dequeueClients(cashierQueue, new IQueryable<Cashier>() {
				public boolean where(Cashier item) 
				{ return item.isEmpty() && !item.isManager() && !item.isPriority(); } 
			});
			
			// empty, manager, non-priority
			dequeueClients(managerQueue, new IQueryable<Cashier>() {
				public boolean where(Cashier item) 
				{ return item.isEmpty() && item.isManager() && !item.isPriority(); } 
			});
			
			// empty, regular, priority
			dequeueClients(pCashierQueue, new IQueryable<Cashier>() {
				public boolean where(Cashier item) 
				{ return item.isEmpty() && !item.isManager() && item.isPriority(); } 
			});
			
			// empty, manager, priority
			dequeueClients(pManagerQueue, new IQueryable<Cashier>() {
				public boolean where(Cashier item) 
				{ return item.isEmpty() && item.isManager() && item.isPriority(); } 
			});
		}
		
		// iterates all the cashiers, decreasing serving time for each occupied cashier
		for (Cashier c : cashiers)
		{
			if (!c.isEmpty())
			{
				if (c.getCurrentClient().getRemainigTime() == 0)
				{
					c.endServing();
				}
				else
				{
					c.getCurrentClient().decreaseRemaingTime();
				}
			}
		}
	}
	
	/**
	 * Dequeues client into cashiers
	 * @param queue The queue being dequeued
	 * @param query The where provider for filtering the cashiers
	 */
	private void dequeueClients(IQueue<Client> queue, IQueryable<Cashier> query)
	{
		// it isn't checked whether the queue is not empty before calling this method.
		if (queue.isEmpty()) { return; }
		
		for (Cashier c : cashiers.where(query))
		{
			//the queue can become empty during the dequeuing
			if (queue.isEmpty()) { return; }
			
			c.serveNewClient(queue.dequeue());
		}
	}
	
	@Override
	public void clear() 
	{
		
	}

	@Override
	public void printResults() 
	{
		
	}	
}
