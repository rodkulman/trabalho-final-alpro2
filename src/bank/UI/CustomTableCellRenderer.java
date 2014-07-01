package bank.UI;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9222262090034493006L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		String text = "";
		
		if (table.getValueAt(row, 2).toString().equalsIgnoreCase("probability"))
		{
			text = new Double(Double.parseDouble(value.toString()) * 100.0).toString() + "%";
		}
		else
		{
			text = value.toString();
		}
		
		return super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
	}
}
