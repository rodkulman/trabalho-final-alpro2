package meta.collections;

/**
 * Defines the basic behavior of a List.
 * @author rodkulman@gmail.com
 *
 */
public interface IList
{
	/**
	 * Used to retrieve the size of a list.
	 * @return The number of elements in the list.
	 */
	int size();
	/**
	 * Determines if the list is empty.
	 * @return True if the list has no elements, otherwise false.
	 */
    boolean isEmpty();
    /**
     * Empties the list.
     */
    void clear();
}
