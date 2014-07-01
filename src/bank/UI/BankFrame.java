package bank.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import meta.*;
import meta.collections.*;
import bank.*;

import org.jfree.chart.*;

import config.XMLConfig;
import services.*;

import javax.swing.table.DefaultTableModel;
import javax.xml.soap.MessageFactory;

/**
 * The UI for the bank simulator app.
 * 
 * @author rodkulman@gmail.com
 * 
 */

public class BankFrame extends JFrame implements AppendableListener, SimulatorListener, TableModelListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -108639763223961561L;
	private JPanel contentPane;

	/**
	 * Necessary for some Interface calls
	 */
	private BankFrame thisIntance = this;

	/**
	 * Receives the log messages
	 */
	private UILogger logger;

	/**
	 * The bank simulator class;
	 */
	private Simulator simulator;

	// Window components

	private JButton btnRunSimulator;

	private JList<String> lstLogs;
	private DefaultListModel<String> listModel;

	private CustomPieChart clientChart;
	private ChartPanel clientChartPanel;

	private CustomPieChart cashiersChart;
	private ChartPanel cashiersChartPanel;

	private CustomPieChart clientsByCashierChart;
	private ChartPanel clientsByCashierChartPanel;

	private CustomPieChart clientsServedByCashierChart;
	private ChartPanel clientsServedByCashierChartPanel;

	private JTabbedPane tabs;
	private JPanel currentSimulationTab;
	private JPanel eventsTab;
	private JPanel configuationTab;

	private JTable configTable;

	// End of window components

	// standard colors for the categories

	private final Color servedColor = new Color(0x00, 0x00, 0x66);
	private final Color notServedColor = new Color(0xCC, 0x00, 0x00);

	private final Color regularsColor = new Color(0xCC, 0x00, 0xCC);
	private final Color priritiesColor = new Color(0x00, 0xCC, 0x00);
	private final Color managersColor = new Color(0x33, 0x33, 0xFF);
	private final Color priorityManagersColor = new Color(0xFF, 0x99, 0x00);

	/**
	 * Creates the frame.
	 */
	public BankFrame()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(BankFrame.class.getResource("/resources/bank.png")));
		// frame, contentPane

		setResizable(false);
		setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setTitle("Simula&Emula - Bank Agency Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 654);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// lstLogs

		listModel = new DefaultListModel<>();

		lstLogs = new JList<>(listModel);
		lstLogs.setLocation(0, 0);
		lstLogs.setSize(511, 364);
		lstLogs.setBackground(Color.LIGHT_GRAY);
		lstLogs.setBorder(new LineBorder(new Color(0, 0, 0)));
		lstLogs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lstLogs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// clients chart

		clientChart = new CustomPieChart("Clients Served");
		clientChart.addNewCategory("Served");
		clientChart.addNewCategory("Not Served");

		clientChart.setSectionPaint("Served", servedColor);
		clientChart.setSectionPaint("Not Served", notServedColor);

		clientChartPanel = new ChartPanel(clientChart);
		clientChartPanel.setBounds(385, 11, 360, 246);

		// cashiers chart

		cashiersChart = new CustomPieChart("Cashiers");
		cashiersChart.addNewCategory("Regulars", XMLConfig.getDouble("cashierNumber"));
		cashiersChart.addNewCategory("Priorities", XMLConfig.getDouble("priorityCashierNumber"));
		cashiersChart.addNewCategory("Managers", XMLConfig.getDouble("managerNumber"));
		cashiersChart.addNewCategory("Priority Managers", XMLConfig.getDouble("priorityManagerNumber"));

		cashiersChart.setSectionPaint("Regulars", regularsColor);
		cashiersChart.setSectionPaint("Priorities", priritiesColor);
		cashiersChart.setSectionPaint("Managers", managersColor);
		cashiersChart.setSectionPaint("Priority Managers", priorityManagersColor);

		cashiersChartPanel = new ChartPanel(cashiersChart);
		cashiersChartPanel.setBounds(10, 11, 340, 223);

		// clients by cashier chart

		clientsByCashierChart = new CustomPieChart("Clients By Cashiers");
		clientsByCashierChart.addNewCategory("Regulars");
		clientsByCashierChart.addNewCategory("Priorities");
		clientsByCashierChart.addNewCategory("Managers");
		clientsByCashierChart.addNewCategory("Priority Managers");

		clientsByCashierChart.setSectionPaint("Regulars", regularsColor);
		clientsByCashierChart.setSectionPaint("Priorities", priritiesColor);
		clientsByCashierChart.setSectionPaint("Managers", managersColor);
		clientsByCashierChart.setSectionPaint("Priority Managers", priorityManagersColor);

		clientsByCashierChartPanel = new ChartPanel(clientsByCashierChart);
		clientsByCashierChartPanel.setBounds(10, 11, 365, 246);

		// clients served by cashier chart

		clientsServedByCashierChart = new CustomPieChart("Clients Served By Cashiers");
		clientsServedByCashierChart.addNewCategory("Regulars");
		clientsServedByCashierChart.addNewCategory("Priorities");
		clientsServedByCashierChart.addNewCategory("Managers");
		clientsServedByCashierChart.addNewCategory("Priority Managers");

		clientsServedByCashierChart.setSectionPaint("Regulars", regularsColor);
		clientsServedByCashierChart.setSectionPaint("Priorities", priritiesColor);
		clientsServedByCashierChart.setSectionPaint("Managers", managersColor);
		clientsServedByCashierChart.setSectionPaint("Priority Managers", priorityManagersColor);

		clientsServedByCashierChartPanel = new ChartPanel(clientsServedByCashierChart);
		clientsServedByCashierChartPanel.setBounds(10, 268, 365, 246);
		clientsServedByCashierChartPanel.setBackground(Color.WHITE);

		// btnRunSimulator

		btnRunSimulator = new JButton("Run Simulator");
		btnRunSimulator.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnRunSimulator.setBounds(649, 568, 121, 46);
		btnRunSimulator.addActionListener(btnRunSimulator_Click());
		contentPane.add(btnRunSimulator);

		// tabs panels

		currentSimulationTab = new JPanel();
		currentSimulationTab.setBackground(Color.WHITE);
		currentSimulationTab.setLayout(null);

		currentSimulationTab.add(clientChartPanel);
		currentSimulationTab.add(clientsByCashierChartPanel);
		currentSimulationTab.add(clientsServedByCashierChartPanel);

		eventsTab = new JPanel();
		eventsTab.setBackground(Color.WHITE);
		eventsTab.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(lstLogs);
		scrollPane.setBounds(10, 11, 511, 364);

		eventsTab.add(scrollPane);

		// tabs

		tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tabs.setBounds(10, 11, 760, 546);

		configuationTab = new JPanel();
		configuationTab.setBackground(Color.WHITE);
		configuationTab.setLayout(null);

		configuationTab.add(cashiersChartPanel);

		tabs.addTab("Configuration", configuationTab);

		// configTable = new JTable(getConfigurations(), new String[] { "Descr",
		// "Value", "type", "name" });
		configTable = new JTable(new Object[][] {}, new String[] { "Descr", "Value", "type", "name" });
		configTable.setModel(new DefaultTableModel(getConfigurations(), new String[] { "Descr", "Value", "type", "name" })
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 6020278288117553762L;

			boolean[] columnEditables = new boolean[] { false, true, false, false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		
		configTable.getModel().addTableModelListener(this);
		
		configTable.getColumnModel().getColumn(2).setPreferredWidth(0);
		configTable.getColumnModel().getColumn(2).setMinWidth(0);
		configTable.getColumnModel().getColumn(2).setMaxWidth(0);
		configTable.getColumnModel().getColumn(3).setPreferredWidth(0);
		configTable.getColumnModel().getColumn(3).setMinWidth(0);
		configTable.getColumnModel().getColumn(3).setMaxWidth(0);

		configTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		configTable.getColumn("Descr").setHeaderValue("Name");

		configTable.getColumn("Value").setCellRenderer(new CustomTableCellRenderer());

		configTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		configTable.setBounds(0, 0, 323, 188);

		JScrollPane tablePane = new JScrollPane(configTable);
		tablePane.setBounds(360, 11, 385, 223);

		configuationTab.add(tablePane);
		tabs.addTab("Current Simulation", currentSimulationTab);
		tabs.addTab("Simulation Events", eventsTab);

		contentPane.add(tabs);

		// logger

		logger = new UILogger();
		logger.setAppendableListener(this);

		// sets the login output to be the logger
		Trace.setOutput(logger);
	}

	@Override
	public void Appended(final String logMessage)
	{
		// adds logs to the logging thing-y
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				listModel.addElement(logMessage);
			}
		});
	}

	/**
	 * Searches for all values in the XML config
	 * 
	 * @return Returns an object matrix containing all the config values.
	 */
	private Object[][] getConfigurations()
	{
		Object[][] retVal;

		ListLinked<String> configs = XMLConfig.getNames();

		retVal = new Object[configs.size()][4];

		for (int i = 0; i < configs.size(); i++)
		{
			retVal[i][0] = XMLConfig.getDescr(configs.get(i));
			retVal[i][1] = XMLConfig.get(configs.get(i));
			retVal[i][2] = XMLConfig.getType(configs.get(i));
			retVal[i][3] = configs.get(i);
		}

		return retVal;
	}

	private ActionListener btnRunSimulator_Click()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				clientChart.clearValues();
				clientsByCashierChart.clearValues();
				clientsServedByCashierChart.clearValues();

				btnRunSimulator.setEnabled(false);

				// ran in another thread so the UI isn't looked.
				Runnable thread = new Runnable()
				{
					@Override
					public void run()
					{
						simulator = new Simulator();

						simulator.addListener(thisIntance);

						simulator.simulate();
					}
				};

				thread.run();
			}
		};
	}

	private String getClientCategory(final Client c)
	{
		if (c.isLookingForManager())
		{
			if (c.requiresPriority())
			{
				return "Priority Managers";
			}
			else
			{
				return "Managers";
			}
		}
		else
		{
			if (c.requiresPriority())
			{
				return "Priorities";
			}
			else
			{
				return "Regulars";
			}
		}
	}

	@Override
	public void ClientServed(final Client c, final int time)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				clientChart.increaseCategory("Served");
				clientChart.decreaseCategory("Not Served");
				clientsServedByCashierChart.increaseCategory(getClientCategory(c));
			}
		});
	}

	@Override
	public void ClientArrived(final Client c, final int time)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				clientChart.increaseCategory("Not Served");
				clientsByCashierChart.increaseCategory(getClientCategory(c));
			}
		});
	}

	@Override
	public void SimulationEnded()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				btnRunSimulator.setEnabled(true);
			}
		});
	}

	@Override
	public void tableChanged(TableModelEvent e)
	{
		String name = configTable.getValueAt(e.getFirstRow(), 3).toString();
		String value = configTable.getValueAt(e.getFirstRow(), 1).toString();
		
		XMLConfig.set(name, value);
	}
}
