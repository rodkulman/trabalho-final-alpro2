package bank.services;

import meta.collections.*;
import services.*;

public class PriorityQueue extends QueueLinked<Client> 
{
	@Override
	public void enqueue(Client element) 
	{
		// if the list is empty, there is no reason not to enqueue normally
		if (element.requiresPriority() && !isEmpty())
		{
			// n has a priority
			LinkedNode<Client> n = getLastNodeWithPriority();
			LinkedNode<Client> newNode = new LinkedNode<>(element);
			
			if (n == null)
			{
				// n == null, no element has a priority
				newNode.next = head;
				head = newNode;
			}
			else
			{
				// n != null, n is the last element with a priority
				newNode.next = n.next;
				n.next = newNode;
			}
		}
		else
		{
			// normal enqueue
			super.enqueue(element);
		}
	}

	/**
	 * Iterates internally in search of the last node with a priority
	 * @return The last node with a priority, or null if there isn't any
	 */
	private LinkedNode<Client> getLastNodeWithPriority() 
	{
		//if the first element doesn't have a priority, no node has
		if (!head.element.requiresPriority()) { return null; }
		
		LinkedNode<Client> current = head;
		
		for (int i = 0; i < size(); i++) 
		{
			if (current.element.requiresPriority() && (current.next == null || !current.next.element.requiresPriority()))
			{
				return current;
			}
			
			current = current.next;
		}
		
		// didn't find any node with a priority
		return null;
	}
}
