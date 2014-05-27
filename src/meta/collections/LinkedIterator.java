package meta.collections;

import java.util.Iterator;

/**
 * Defines a common iterator for linked classes.
 * @author rodkulman@gmail.com
 */
class LinkedIterator<T> implements Iterator<T>
{
	private Node<T> current;

	/**
	 * Creates a new instance of this class with a node to start.
	 * @param head First node in the linked list.
	 */
	public LinkedIterator(Node<T> head)
	{
		current = head;
	}

	/**
	 * Indicates whether there is a next element.
	 */
	@Override
	public boolean hasNext()
	{
		return current != null;
	}
	
	/**
	 * Retrieves the next element in the list.
	 */
	@Override
	public T next()
	{
		T item = current.element;
		current = current.next;

		return item;
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public void remove()
	{
		
	}
}
