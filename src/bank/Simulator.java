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
		
		cashierQueue = new bank.services.PriorityQueue();
		managerQueue = new bank.services.PriorityQueue();
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
				(c.isLookingForManager() ? managerQueue : cashierQueue).enqueue(c);
			}
			
			
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
	
	@Override
	public void clear() 
	{
		
	}

	@Override
	public void printResults() 
	{
		
	}	
}
