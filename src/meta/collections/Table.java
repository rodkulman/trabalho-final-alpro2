package meta.collections;

import java.util.*;

/**
 * Enables storing values table-like and dictionary-like. (Poorly written).
 * 
 * @author rodkulman@gmail.com
 */
public class Table<TRow, TColumn, TValue>
{
	/**
	 * Stores the row values.
	 */
	private ArrayList<TRow> rows;
	/**
	 * Stores the Column values.
	 */
	private ArrayList<TColumn> columns;
	/**
	 * Stores the actual values.
	 */
	private TValue[][] values;
	/**
	 * Gets the amount of values there is.
	 */
	private int size;

	@SuppressWarnings("unchecked")
	public Table()
	{
		rows = new ArrayList<>();
		columns = new ArrayList<>();
		values = (TValue[][]) new Object[8][8];

		size = 0;
	}

	/**
	 * Increments the size of arrays that form the matrix
	 */
	@SuppressWarnings("unchecked")
	private void checkSize()
	{
		if (values.length == size)
		{
			TValue[][] tmp = (TValue[][]) new Object[size * 2][size * 2];

			for (int i = 0; i < size; i++)
			{
				tmp[i] = Arrays.copyOf(values[i], size * 2);
			}

			values = tmp;
		}
	}

	/**
	 * Adds a new value to the collection
	 * 
	 * @param row
	 *            Row-key to the value.
	 * @param column
	 *            Column-key to the value.
	 * @param value
	 *            Value to be added.
	 */
	public void add(TRow row, TColumn column, TValue value)
	{
		checkForDuplicateKey(row, column);
		checkSize();

		rows.add(row);
		columns.add(column);

		values[rows.size() - 1][columns.size() - 1] = value;
		size++;
	}

	/**
	 * Gets a specified value in the collection. Null if not found.
	 * 
	 * @param row
	 *            Row to find.
	 * @param column
	 *            Column to find.
	 * @return The value at the location, otherwise null.
	 */
	public TValue get(TRow row, TColumn column)
	{
		for (int i : getIndexOfAll(row))
		{
			if (columns.get(i) == column) { return values[i][i]; }
		}

		return null;
	}

	/**
	 * Sets or add a value to the collection at the specified row-column.
	 */
	public void set(TRow row, TColumn column, TValue value)
	{
		ArrayList<Integer> references = getIndexOfAll(row);

		// if there are no column references to the row, add a new
		if (references.size() == 0)
		{
			add(row, column, value);
		}

		// checks for column references
		for (int i : references)
		{
			if (columns.get(i) == column)
			{
				// if founds set
				values[i][i] = value;
				return;
			}
		}

		// if it reaches here, add a new one because no column was found.
		add(row, column, value);
	}

	/**
	 * Gets the index of all references to row
	 */
	private ArrayList<Integer> getIndexOfAll(TRow row)
	{
		ArrayList<Integer> retVal = new ArrayList<>();

		for (int i = 0; i < rows.size(); i++)
		{
			if (rows.get(i) == row)
			{
				retVal.add(i);
			}
		}

		return retVal;
	}

	/**
	 * Checks if that same row-column combination was already added.
	 * 
	 * @param row
	 *            Row to check.
	 * @param column
	 *            Column to check.
	 */
	private void checkForDuplicateKey(TRow row, TColumn column)
	{
		for (int i : getIndexOfAll(row))
		{
			if (columns.get(i) == column) { throw new DuplicateKeyException(); }
		}
	}
}
