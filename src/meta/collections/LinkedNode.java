package meta.collections;

/**
 * Defines nodes for Linked classes.
 * 
 * @author rodkulman@gmail.com
 */
public final class LinkedNode<T>
{
	/**
	 * Element being held by the node.
	 */
	public T element;
	/**
	 * Indicates the next node. If null, it means it is the last in the list.
	 */
	public LinkedNode<T> next;

	/**
	 * Creates a new instance of this class, with no next node and with an
	 * element.
	 * 
	 * @param e
	 *            Element to be stored be this node.
	 */
	public LinkedNode(T e)
	{
		element = e;
		next = null;
	}
}
