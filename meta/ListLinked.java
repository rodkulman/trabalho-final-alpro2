package trunk.meta;

import java.util.Iterator;

public class ListLinked<T> implements Iterable<T>
{
	private Node<T> head;
	@SuppressWarnings("unused")
	private Node<T> tail;
	private int count;

	public ListLinked()
	{
		clear();
	}

	public void add(T e)
	{
		add(count, e);
	}

	public void add(int index, T element)
	{
		Node<T> newNode = new Node<>(element);

		if (isEmpty())
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			if (index < 0 || index > count) { throw new IndexOutOfBoundsException(); }

			Node<T> aux = head;

			for (int i = 0; i < index - 1; i++)
			{
				aux = aux.next;
			}

			newNode.next = aux.next;
			aux.next = newNode;
		}

		count++;
	}

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

	public boolean isEmpty()
	{
		return count == 0;
	}

	public int size()
	{
		return count;
	}

	public boolean contains(T e)
	{
		return indexOf(e) != -1;
	}

	public void clear()
	{
		head = null;
		tail = null;
		count = 0;
	}

	private final class Node<E>
	{
		public Node(E element)
		{
			this.element = element;
		}

		public E element;
		public Node<E> next;
		
	}

	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{
			private Node<T> current = head;
			
			@Override
			public boolean hasNext()
			{
				return current != null;
			}

			@Override
			public T next()
			{
				T item = current.element;				
				current = current.next;
				
				return item;
			}

			@Override
			public void remove()
			{
								
			}			
		};
	}
}
