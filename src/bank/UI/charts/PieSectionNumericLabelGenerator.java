package bank.UI.charts;

import java.text.AttributedString;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

/**
 * Allows pie charts to show number in labels rather than legends.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public class PieSectionNumericLabelGenerator implements PieSectionLabelGenerator
{
	/**
	 * Sets the label mode to display the number itself.
	 */
	public static final int NUMBER_MODE = 0;
	
	/**
	 * Sets the label mode to display the value * 100 followed by a "%" sign.
	 */
	public static final int PERCENTAGE_MODE = 1;

	private int labelMode = NUMBER_MODE;

	/**
	 * Gets the current Label Mode being used. Default is NUMBER_MODE.
	 */
	public int getLabelMode()
	{
		return labelMode;
	}

	/**
	 * Sets a new label mode to be used.
	 */
	public void setLabelMode(int labelMode)
	{
		this.labelMode = labelMode;
	}

	@Override
	public AttributedString generateAttributedSectionLabel(PieDataset dataset, @SuppressWarnings("rawtypes") Comparable key)
	{
		return null;
	}

	@Override
	public String generateSectionLabel(PieDataset dataset, @SuppressWarnings("rawtypes") Comparable key)
	{
		switch (labelMode)
		{
		case PERCENTAGE_MODE:
			return Math.round((dataset.getValue(key).doubleValue() * 100)) + "%";
		case NUMBER_MODE:
		default:
			return dataset.getValue(key).toString();
		}
	}
}
