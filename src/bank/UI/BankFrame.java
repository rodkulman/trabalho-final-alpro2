package bank.UI;

import io.XMLConfig;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import meta.*;
import bank.*;

import org.jfree.chart.*;

import services.*;

/**
 * The UI for the bank simulator app.
 * 
 * @author rodkulman@gmail.com
 * 
 */

public class BankFrame extends JFrame implements AppendableListener, SimulatorListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -108639763223961561L;
	private JPanel contentPane;

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
		// frame, contentPane

		setResizable(false);
		setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setTitle("Bank Agency Simulator");
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

		JLabel lblDuration = new JLabel("Duration:");
		lblDuration.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblDuration.setBounds(360, 38, 66, 20);
		configuationTab.add(lblDuration);

		txtDuration = new JTextField();
		txtDuration.setEditable(false);
		lblDuration.setLabelFor(txtDuration);
		txtDuration.setBounds(436, 41, 86, 20);
		configuationTab.add(txtDuration);
		txtDuration.setColumns(10);
		txtDuration.setText(XMLConfig.get("duration"));
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

	BankFrame thisIntance = this;
	private JTextField txtDuration;

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
}
