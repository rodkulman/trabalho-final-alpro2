package bank;

import services.*;
import io.XMLConfig;
import meta.*;

public class Simulator implements ISimulator
{
	XMLConfig config;
	final int duration;
	Cashiers cashiers;
	
	public Simulator()
	{
		config = new XMLConfig("config.xml");
		cashiers = new Cashiers();
		
		duration = Integer.parseInt(config.get("duration"));
		int cashierNumber = Integer.parseInt(config.get("cashierNumber"));
		int priorityCashierNumber = Integer.parseInt(config.get("priorityCashierNumber"));
		int managerNumber = Integer.parseInt(config.get("managerNumber"));
		int priorityManagerNumber = Integer.parseInt(config.get("priorityManagerNumber"));
		
		createCashiers(cashierNumber,priorityCashierNumber, false);		
		createCashiers(managerNumber, priorityManagerNumber, true);
	}
	
	private void createCashiers(int regularCount, int priorityCount, boolean isManager)
	{
		int total = regularCount + priorityCount;
		
		for (int i = 0; i < total; i++)
		{
			Cashier c = new Cashier(priorityCount-- > 0, isManager);
			
			cashiers.add(c);
		}
	}
	
	@Override
	public void simulate() 
	{
		
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
