package meta.collections;

/**
 * Defines the basic behavior of a queue.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public interface IQueue<T> extends IList<T>
{
	/**
	 * Returns the first element of the queue.
	 * 
	 * @return The first element of the queue.
	 */
	T getHead();

	/**
	 * Adds a new element to the queue.
	 * 
	 * @param element
	 *            New element.
	 */
	void enqueue(T element);

	/**
	 * Gets the oldest element in the list.
	 * 
	 * @return The oldest element in the list.
	 */
	T dequeue();
}
