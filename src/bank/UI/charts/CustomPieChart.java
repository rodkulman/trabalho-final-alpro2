package bank.UI.charts;

import java.awt.*;

import meta.collections.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.general.*;

/**
 * Allows for better manipulation of the PieChart.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class CustomPieChart extends JFreeChart
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8661030932641402434L;

	/**
	 * Categories in this dataset.
	 */
	private ListLinked<String> categories;

	/**
	 * Dataset holding data.
	 */
	private DefaultPieDataset dataset;

	/**
	 * The plot that draws the stuff.
	 */
	private PiePlot plot;

	private PieSectionNumericLabelGenerator labelGenerator;

	/**
	 * Gets the categories stored in this dataset.
	 */
	public IList<String> getCategories()
	{
		return categories;
	}

	/**
	 * Creates a new instance of this class.
	 */
	public CustomPieChart(String title)
	{
		super(title, new Font("Segoe UI", Font.PLAIN, 18), new PiePlot(new DefaultPieDataset()), true);

		this.plot = (PiePlot) super.getPlot();
		this.dataset = (DefaultPieDataset) plot.getDataset();
		this.categories = new ListLinked<>();

		this.labelGenerator = new PieSectionNumericLabelGenerator();

		this.plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
		this.plot.setLabelGenerator(labelGenerator);

		// no shadow
		this.plot.setShadowXOffset(0);
		this.plot.setShadowYOffset(0);

		this.setBackgroundPaint(Color.WHITE);
	}

	/**
	 * Adds a new category with value of 0;
	 * 
	 * @param category
	 *            Category to be added.
	 */
	public void addNewCategory(String category)
	{
		addNewCategory(category, 0.0);
	}

	/**
	 * Adds a new category with value of quantity;
	 * 
	 * @param category
	 *            Category to be added.
	 * @param quantity
	 *            Amount to be shown.
	 */
	public void addNewCategory(String category, double quantity)
	{
		dataset.setValue(category, quantity);
		categories.add(category);
	}

	/**
	 * Increases the value of the category by 1.
	 * 
	 * @param category
	 *            Category whose value will be increased.
	 * @return Returns the current value of this category.
	 */
	public double increaseCategory(String category)
	{
		double retVal = dataset.getValue(category).doubleValue();

		dataset.setValue(category, ++retVal);

		return retVal;
	}

	/**
	 * Decreases the value of the category by 1.
	 * 
	 * @param category
	 *            Category whose value will be decreased.
	 * @return Returns the current value of this category.
	 */
	public double decreaseCategory(String category)
	{
		double retVal = dataset.getValue(category).doubleValue();

		dataset.setValue(category, --retVal);

		return retVal;
	}

	/**
	 * Clears the values but preserved the categories;
	 */
	public void clearValues()
	{
		for (String category : categories)
		{
			dataset.setValue(category, 0.0);
		}
	}

	/**
	 * Sets the color for a section.
	 * 
	 * @param category
	 *            Category whose color will be set.
	 * @param color
	 *            Color to set.
	 */
	public void setSectionPaint(String category, Color color)
	{
		this.plot.setSectionPaint(category, color);
	}

	/**
	 * Sets the label mode.
	 * 
	 * @see {@link PieSectionNumericLabelGenerator} for more details.
	 */
	public void setLabelMode(int mode)
	{
		this.labelGenerator.setLabelMode(mode);
	}
}
