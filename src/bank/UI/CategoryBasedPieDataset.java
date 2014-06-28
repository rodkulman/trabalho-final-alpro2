package bank.UI;

import meta.collections.IList;
import meta.collections.ListLinked;

import org.jfree.data.general.DefaultPieDataset;

/**
 * Allows for better manipulation of the PieDataset, using categories. 
 * @author rodkulman@gmail.com
 *
 */
public class CategoryBasedPieDataset extends DefaultPieDataset 
{
	private static final long serialVersionUID = 5870762449493504721L;

	/**
	 * Categories in this dataset.
	 */
	private ListLinked<String> categories;
	
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
	public CategoryBasedPieDataset()
	{
		super();
		
		categories = new ListLinked<>();
	}
	
	/**
	 * Adds a new category with value of 0;
	 * @param category Category to be added.
	 */
	public void addNewCategory(String category)
	{
		this.setValue(category, 0.0);
		categories.add(category);
	}
	
	/**
	 * Increases the value of the category by 1.
	 * @param category Category whose value will be increased.
	 * @return Returns the current value of this category.
	 */
	public double increaseCategory(String category)
	{
		double retVal = this.getValue(category).doubleValue();
		
		this.setValue(category, ++retVal);
		
		return retVal;
	}
	
	/**
	 * Decreases the value of the category by 1.
	 * @param category Category whose value will be decreased.
	 * @return Returns the current value of this category.
	 */
	public double decreaseCategory(String category)
	{
		double retVal = this.getValue(category).doubleValue();
		
		this.setValue(category, --retVal);
		
		return retVal;
	}

	/**
	 * Clears the values but preserved the categories;
	 */
	public void clearValues() 
	{
		for (String category : categories) 
		{
			this.setValue(category, 0.0);
		}		
	}
}
