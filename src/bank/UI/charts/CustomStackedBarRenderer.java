package bank.UI.charts;

import java.awt.Paint;
import meta.collections.*;

import org.jfree.chart.renderer.category.StackedBarRenderer;

/**
 * Allows to paint different columns with different Paints.
 * @author rodkulman@gmail.com
 *
 */
public class CustomStackedBarRenderer extends StackedBarRenderer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7617727217947879143L;
	
	/**
	 * Internal colection of paints
	 */
	private Table<Integer, Integer, Paint> paints = new Table<>();
	
	@Override
	public Paint getItemPaint(int row, int column)
	{
		return paints.get(row, column);
	}
	
	/**
	 * Sets a paint for a row-column.
	 */
	public void setItemPaint(int row, int column, Paint p)
	{
		paints.add(row, column, p);
	}
}
