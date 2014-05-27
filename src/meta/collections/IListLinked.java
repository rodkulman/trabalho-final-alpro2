package meta.collections;

/**
 * Defines the basic behavior for a LinkedList 
 * @author rodkulman@gmail.com
 *
 */
public interface IListLinked<T> extends IList
{
	/**
	 * Adds a new element at a specified index.
	 * @param index Index to insert the element at.
	 * @param element Element to be inserted.
	 */
	void add(int index, T element);
	/**
	 * Adds an element to the end of the list.
	 * @param element Element to be inserted.
	 */
	void add(T element);
	/**
	 * Determines if a certain element is within the list.
	 * @param element Element to search for.
	 * @return True if the element is within the list, otherwise false.
	 */
	boolean contains(T element);
	/**
	 * Returns the element at the specified index.
	 * @param index Index to search for the element.
	 * @return The element at the specified index.
	 */
	T get(int index);
	/**
	 * Determines the index of a certain element.
	 * @param element Element to search for.
	 * @return The index of the element. If the element isn't within the list, returns -1.
	 */
	int indexOf(T element);
	/**
	 * Removes the specified element.
	 * @param element Element to be removed. 
	 * @return True if removed. False if the element doesn't exist in the list.
	 */
	boolean remove(T element);
	/**
	 * Removes a element at the specified index.
	 * @param index Index to search the element for.
	 * @return The element which was removed.
	 */
	T removeAt(int index);
	/**
	 * Replaces a element at the specified index.
	 * @param index Index to be replaced.
	 * @param element Element which will replace the current one.
	 */
	void set(int index, T element);
}
