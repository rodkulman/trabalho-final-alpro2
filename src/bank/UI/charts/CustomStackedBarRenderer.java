package bank.UI.charts;

import java.awt.Paint;

import meta.collections.*;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.renderer.category.StackedBarRenderer;

/**
 * Allows to paint different columns with different Paints.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class CustomStackedBarRenderer extends StackedBarRenderer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7617727217947879143L;

	/*
	 * Necessary for multiple legends
	 */
	private ListLinked<String> legends = new ListLinked<>();
	private ListLinked<Paint> legendsColors = new ListLinked<>();

	/**
	 * Internal collection of paints
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

	@Override
	public LegendItemCollection getLegendItems()
	{
		LegendItemCollection retVal = new LegendItemCollection();

		for (int i = 0; i < legends.size(); i++)
		{
			retVal.add(new LegendItem(legends.get(i), legendsColors.get(i)));
		}

		return retVal;
	}

	/**
	 * Adds a legend item to the legends.
	 * 
	 * @param name
	 *            String to display.
	 * @param paint
	 *            Color to display.
	 */
	public void addLegendItem(String name, Paint paint)
	{
		legends.add(name);
		legendsColors.add(paint);
	}
}
