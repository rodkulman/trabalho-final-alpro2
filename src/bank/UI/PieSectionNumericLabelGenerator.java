package bank.UI;

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
	@Override
	public AttributedString generateAttributedSectionLabel(PieDataset dataset, @SuppressWarnings("rawtypes") Comparable key)
	{
		return null;
	}

	@Override
	public String generateSectionLabel(PieDataset dataset, @SuppressWarnings("rawtypes") Comparable key)
	{
		return dataset.getValue(key).toString();
	}
}
