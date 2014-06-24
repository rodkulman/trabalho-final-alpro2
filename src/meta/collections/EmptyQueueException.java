package meta.collections;

/**
 * Occurs when a trying a dequeue/enqueue operation on a empty queue.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class EmptyQueueException extends RuntimeException
{
	/**
	 * ID for serialization.
	 */
	private static final long serialVersionUID = -2827625018458723119L;

	/**
	 * Initializes a new EmptyQueueException.
	 */
	public EmptyQueueException()
	{
		super("The queue is empty.");
	}
}
