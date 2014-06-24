package meta.collections;

/**
 * Allows custom querying for iterators
 * 
 * @author rodkulman@gmail.com
 * 
 */
public interface IQueryable<T>
{
	/**
	 * Allows for custom filtering
	 * 
	 * @param item
	 *            Item currently being analyzed.
	 * @return Returns true if item is to be included in the end result,
	 *         otherwise false.
	 */
	boolean where(T item);
}
