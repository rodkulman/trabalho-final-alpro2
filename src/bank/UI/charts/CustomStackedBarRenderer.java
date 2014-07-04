package bank.UI.charts;

import java.awt.Paint;
import meta.collections.*;

import org.jfree.chart.renderer.category.StackedBarRenderer;

public class CustomStackedBarRenderer extends StackedBarRenderer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7617727217947879143L;
	
	private Table<Integer, Integer, Paint> paints = new Table<>();
	
	@Override
	public Paint getItemPaint(int row, int column)
	{
		return paints.get(row, column);
	}
	
	public void setItemPaint(int row, int column, Paint p)
	{
		paints.add(row, column, p);
	}
}
