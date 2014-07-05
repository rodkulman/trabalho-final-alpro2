package bank.UI.charts;

import java.awt.*;
import java.security.InvalidParameterException;

import meta.collections.ListLinked;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Allows better manipulation on the StackedBarChart.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class CustomStackedBarChart extends JFreeChart
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4660135813805468962L;

	private CategoryPlot plot;
	private DefaultCategoryDataset dataset;
	private ListLinked<String> rowCategories;
	private ListLinked<String> columnCategories;
	private CustomStackedBarRenderer renderer;

	public CustomStackedBarChart(String title, String categoryTitle, String rangeTitle)
	{
		super(title, new Font("Segoe UI", Font.PLAIN, 18), new CategoryPlot(new DefaultCategoryDataset(), new CategoryAxis(categoryTitle), new NumberAxis(rangeTitle), new StackedBarRenderer()), true);

		this.plot = (CategoryPlot) super.getPlot();
		this.dataset = (DefaultCategoryDataset) this.plot.getDataset();
		this.rowCategories = new ListLinked<>();
		this.columnCategories = new ListLinked<>();
		this.renderer = new CustomStackedBarRenderer();

		// sets the plot to paint flat
		this.renderer.setBarPainter(new StandardBarPainter());
		this.renderer.setShadowVisible(false);
		
		this.plot.setRenderer(renderer);
		this.plot.setRangeGridlinePaint(Color.BLACK);

		this.setBackgroundPaint(Color.WHITE);
	}

	/**
	 * Adds a new category to the chart.
	 */
	public void addCategory(String row, String column)
	{
		rowCategories.add(row);
		columnCategories.add(column);

		dataset.addValue(0.0, row, column);
	}
	
	/**
	 * Adds a new legend to the chart.
	 */
	public void addLegend(String name, Color color)
	{
		this.renderer.addLegendItem(name, color);
	}

	/**
	 * Sets a color to a category.
	 */
	public void setCatagoryPaint(String row, String column, Color color)
	{
		if (dataset.getRowIndex(row) < 0) { throw new InvalidParameterException("row doesn't belong to this chart."); }

		if (dataset.getColumnIndex(column) < 0) { throw new InvalidParameterException("column doesn't belong to this chart."); }

		renderer.setItemPaint(dataset.getRowIndex(row), dataset.getColumnIndex(column), color);
	}

	/**
	 * Clears the chart, setting all categories to 0.
	 */
	public void clearValues()
	{
		for (String row : rowCategories)
		{
			for (String column : columnCategories)
			{
				dataset.setValue(0.0, row, column);
			}
		}
	}

	/**
	 * Increase by one the value of a category.
	 * 
	 * @return Returns the new value.
	 */
	public double increaseCategory(String row, String column)
	{
		double retVal = dataset.getValue(row, column).doubleValue();
		retVal += 1;

		dataset.setValue(retVal, row, column);

		return retVal;
	}

	/**
	 * Decreases by one the value of a category.
	 * 
	 * @return Returns the new value.
	 */
	public double decreaseCategory(String row, String column)
	{
		double retVal = dataset.getValue(row, column).doubleValue();
		retVal -= 1;

		dataset.setValue(retVal, row, column);

		return retVal;
	}
	
	/**
	 * Gets the value of the specified category.
	 */
	public double getValue(String row, String column)
	{
		return dataset.getValue(row, column).doubleValue();
	}
}
