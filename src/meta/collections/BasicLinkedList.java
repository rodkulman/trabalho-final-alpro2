package meta.collections;

import java.util.Iterator;

/**
 * Implements the basic behavior for linked lists.
 * @author rodkulman@gmail.com
 */
abstract class BasicLinkedList<T> implements IList, Iterable<T>
{
	protected BasicLinkedList()
	{
		clear();
	}
	
	/**
	 * Number of elements current in the list.
	 */
	protected int count;
	/**
	 * First element in the list.
	 */
	protected LinkedNode<T> head;
	/**
	 * Last element in the list.
	 */
	protected LinkedNode<T> tail;

	/**
	 * Returns the number of elements currently in the list.
	 */
	@Override
	public int size()
	{
		return count;
	}

	/**
	 * Determines whether the list is empty.
	 */
	@Override
	public boolean isEmpty()
	{
		return count == 0;
	}

	/**
	 * Clears the list.
	 */
	@Override
	public void clear()
	{
		head = tail = null;
		count = 0;
	}
	
	/**
	 * Returns the iterator for this list.
	 */
	@Override
	public Iterator<T> iterator()
	{
		return new LinkedIterator<>(head);
	}
}
