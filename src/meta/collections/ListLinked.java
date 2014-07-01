package meta.collections;

/**
 * Defines a list (random access) with linked nodes.
 * 
 * @author rodkulman@gmail.com
 */
public class ListLinked<T> extends BasicLinkedList<T> implements IListLinked<T>
{
	private boolean readOnly;
	
	/**
	 * Initializes a instance of a ListLinked with no elements.
	 */
	public ListLinked()
	{
		super();
		
		readOnly = false;
	}

	/**
	 * Adds an element to the end of the list.
	 * 
	 * @param e
	 *            Element to be added.
	 */
	@Override
	public void add(T e)
	{
		add(count, e);
	}

	/**
	 * Adds a new element at a specified index.
	 * 
	 * @param index
	 *            Index to insert the element at.
	 * @param element
	 *            Element to be inserted.
	 */
	@Override
	public void add(int index, T element)
	{
		checkReadOnly();
		
		LinkedNode<T> newNode = new LinkedNode<>(element);

		if (isEmpty())
		{
			// empty list, this new node is both head and tail
			head = newNode;
			tail = newNode;
		}
		else
		{
			if (index < 0 || index > count) { throw new IndexOutOfBoundsException(); }

			LinkedNode<T> aux = head;

			// search for the node an index before the specified
			for (int i = 0; i < index - 1; i++)
			{
				aux = aux.next;
			}

			// the new node's next is set for the node at the specified index
			newNode.next = aux.next;
			// newNode becomes the one at the specified index.
			aux.next = newNode;
		}

		count++;
	}

	/**
	 * Returns the element at the specified index.
	 * 
	 * @param index
	 *            Index to search for the element.
	 * @return The element at the specified index.
	 */
	@Override
	public T get(int index)
	{
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException(); }

		LinkedNode<T> aux = head;

		for (int i = 0; i < index; i++)
		{
			aux = aux.next;
		}

		return aux.element;

	}

	/**
	 * Determines the index of a certain element.
	 * 
	 * @param element
	 *            Element to search for.
	 * @return The index of the element. If the element isn't within the list,
	 *         returns -1.
	 */
	@Override
	public int indexOf(T e)
	{
		LinkedNode<T> aux = head;
		int index = 0;

		while (aux != null)
		{
			if (aux.element.equals(e)) { return index; }
			aux = aux.next;
			index++;
		}

		return -1;
	}

	/**
	 * Replaces a element at the specified index.
	 * 
	 * @param index
	 *            Index to be replaced.
	 * @param element
	 *            Element which will replace the current one.
	 */
	@Override
	public void set(int index, T element)
	{
		checkReadOnly();
		
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException(); }

		LinkedNode<T> aux = head;

		for (int i = 0; i < index; i++)
		{
			aux = aux.next;
		}

		aux.element = element;
	}

	/**
	 * Removes the specified element.
	 * 
	 * @param element
	 *            Element to be removed.
	 * @return True if removed. False if the element doesn't exist in the list.
	 */
	@Override
	public boolean remove(T e)
	{
		checkReadOnly();
		
		LinkedNode<T> aux = head;
		LinkedNode<T> prev = null;

		while (aux != null)
		{
			if (aux.element.equals(e))
			{
				removeNode(aux, prev);
				return true;
			}

			prev = aux;
			aux = aux.next;
		}

		return false;
	}

	/**
	 * Removes a element at the specified index.
	 * 
	 * @param index
	 *            Index to search the element for.
	 * @return The element which was removed.
	 */
	@Override
	public T removeAt(int index)
	{
		checkReadOnly();
		
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException(); }

		LinkedNode<T> aux = head;
		LinkedNode<T> prev = null;

		for (int i = 0; i < index; i++)
		{
			prev = aux;
			aux = aux.next;
		}

		removeNode(aux, prev);

		return aux.element;
	}

	/**
	 * Removes the specified node and corrects the missing link issue.
	 * 
	 * @param remove
	 *            Node to be removed.
	 * @param prev
	 *            Node previous to the one being removed.
	 */
	private void removeNode(LinkedNode<T> remove, LinkedNode<T> prev)
	{
		if (prev == null) // removing first
		{
			head = remove.next;
		}
		else if (remove.next == null) // removing last
		{
			tail = prev;
		}
		else
		{
			prev.next = remove.next;
		}

		count--;
	}

	/**
	 * Determines if a certain element is within the list.
	 * 
	 * @param element
	 *            Element to search for.
	 * @return True if the element is within the list, otherwise false.
	 */
	@Override
	public boolean contains(T e)
	{
		return indexOf(e) != -1;
	}

	/**
	 * Indicates whether the list in read-only mode.
	 * @return True if the list is in read-only mode, otherwise false.
	 */
	@Override
	public boolean isReadOnly()
	{
		return this.readOnly;
	}

	/**
	 * Makes this list enter read-only mode.
	 */
	@Override
	public void makeReadOnly()
	{
		this.readOnly = true;
	}
	
	/**
	 * Checks whether the list is in read-only mode, if so, throws an {@link ReadOnlyException}
	 */
	private void checkReadOnly()
	{
		if (readOnly)
		{
			throw new ReadOnlyException();
		}
	}
}
