package meta.collections;

/**
 * Defines a list (random access) with linked nodes.
 * @author rodkulman@gmail.com
 */
public class ListLinked<T> extends BasicLinkedList<T> implements IListLinked<T>
{
	public ListLinked()
	{
		super();
	}
	
	@Override
	public void add(T e)
	{
		add(count, e);
	}
	
	@Override
	public void add(int index, T element)
	{
		Node<T> newNode = new Node<>(element);

		if (isEmpty())
		{
			//empty list, this new node is both head and tail
			head = newNode;
			tail = newNode;
		}
		else
		{
			if (index < 0 || index > count) { throw new IndexOutOfBoundsException(); }

			Node<T> aux = head;

			//search for the node an index before the specified
			for (int i = 0; i < index - 1; i++)
			{
				aux = aux.next;
			}

			//the new node's next is set for the node at the specified index
			newNode.next = aux.next;
			//newNode becomes the one at the specified index.
			aux.next = newNode;
		}

		count++;
	}

	@Override
	public T get(int index)
	{
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException(); }

		Node<T> aux = head;

		for (int i = 0; i < index; i++)
		{
			aux = aux.next;
		}

		return aux.element;

	}

	@Override
	public int indexOf(T e)
	{
		Node<T> aux = head;
		int index = 0;

		while (aux != null)
		{
			if (aux.element.equals(e)) { return index; }
			aux = aux.next;
			index++;
		}

		return -1;
	}

	@Override
	public void set(int index, T element)
	{
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException(); }

		Node<T> aux = head;

		for (int i = 0; i < index; i++)
		{
			aux = aux.next;
		}

		aux.element = element;
	}

	@Override
	public boolean remove(T e)
	{
		Node<T> aux = head;
		Node<T> prev = null;

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

	@Override
	public T removeAt(int index)
	{
		if (index < 0 || index >= count) { throw new IndexOutOfBoundsException(); }

		Node<T> aux = head;
		Node<T> prev = null;

		for (int i = 0; i < index; i++)
		{
			prev = aux;
			aux = aux.next;
		}

		removeNode(aux, prev);

		return aux.element;
	}

	private void removeNode(Node<T> remove, Node<T> prev)
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

	@Override
	public boolean contains(T e)
	{
		return indexOf(e) != -1;
	}
}
