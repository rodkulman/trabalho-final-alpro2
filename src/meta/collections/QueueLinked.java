package meta.collections;

/**
 * Defines a queue (first in, first out) with linked nodes.
 * 
 * @author rodkulman@gmail.com
 */
public class QueueLinked<T> extends BasicLinkedList<T> implements IQueue<T>
{
	public QueueLinked()
	{
		super();
	}
	
	@Override
	public T getHead()
	{
		if (isEmpty()) { throw new EmptyQueueException(); }
		return head.element;
	}

	@Override
	public void enqueue(T element)
	{
		Node<T> n = new Node<>(element);

		//if the only element in the queue, it becomes the first element
		if (head == null) 
		{
			head = n;
		}
		else
		{
			//otherwise, the tail's next receives the new node
			//because later, tail will be the new node
			tail.next = n;
		}

		//always add at the end of the list
		tail = n;
		count++;
	}

	@Override
	public T dequeue()
	{
		if (isEmpty()) { throw new EmptyQueueException(); }

		Node<T> target = head;
		T item = target.element;

		head = target.next;

		target.element = null;
		target.next = null;

		if (head == null)
		{
			tail = null;
		}

		count--;
		return item;
	}
}
