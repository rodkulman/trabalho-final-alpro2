package bank.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import meta.*;
import bank.*;
import bank.UI.charts.*;

import org.jfree.chart.*;

import config.XMLConfig;
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

	private CustomStackedBarChart newClientsByCashierChart;
	private ChartPanel newClientsByCashierChartPanel;

	private JTabbedPane tabs;
	private JPanel currentSimulationTab;
	private JPanel eventsTab;
	private JPanel configuationTab;

	// End of window components

	// standard colors for the categories

	private final Color servedColor = new Color(0x00, 0x00, 0x66);
	private final Color notServedColor = new Color(0xCC, 0x00, 0x00);

	private final Color regularsColor = new Color(0xCC, 0x00, 0xCC);
	private final Color prioritiesColor = new Color(0x00, 0xCC, 0x00);
	private final Color managersColor = new Color(0x33, 0x33, 0xFF);
	private final Color priorityManagersColor = new Color(0xFF, 0x99, 0x00);

	/**
	 * Creates the frame.
	 */
	public BankFrame()
	{
		// frame, contentPane

		setIconImage(Toolkit.getDefaultToolkit().getImage(BankFrame.class.getResource("/resources/bank.png")));
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

		// simulator

		simulator = new Simulator();
		simulator.addListener(this);

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
		clientChartPanel.setBounds(10, 268, 360, 246);

		// cashiers chart

		cashiersChart = new CustomPieChart("Cashiers");
		cashiersChart.addNewCategory("Regulars", XMLConfig.getDouble("cashierNumber"));
		cashiersChart.addNewCategory("Priorities", XMLConfig.getDouble("priorityCashierNumber"));
		cashiersChart.addNewCategory("Managers", XMLConfig.getDouble("managerNumber"));
		cashiersChart.addNewCategory("Priority Managers", XMLConfig.getDouble("priorityManagerNumber"));

		cashiersChart.setSectionPaint("Regulars", regularsColor);
		cashiersChart.setSectionPaint("Priorities", prioritiesColor);
		cashiersChart.setSectionPaint("Managers", managersColor);
		cashiersChart.setSectionPaint("Priority Managers", priorityManagersColor);

		cashiersChartPanel = new ChartPanel(cashiersChart);
		cashiersChartPanel.setBounds(10, 11, 402, 255);

		// newClientsByCashierChart

		newClientsByCashierChart = new CustomStackedBarChart("Clients By Cashier", "Cashiers", "Clients");

		newClientsByCashierChart.addCategory("Served", "Waiting");
		newClientsByCashierChart.addCategory("Not Served", "Waiting");
		newClientsByCashierChart.setCatagoryPaint("Not Served", "Waiting", notServedColor);
		
		for (Cashier c : simulator.getCashiers())
		{
			newClientsByCashierChart.addCategory("Served", "Cashier " + c.getId());
			newClientsByCashierChart.setCatagoryPaint("Served", "Cashier " + c.getId(), c.isManager() ? (c.isPriority() ? priorityManagersColor : managersColor) : (c.isPriority() ? prioritiesColor : regularsColor));
			
			newClientsByCashierChart.addCategory("Not Served", "Cashier " + c.getId());
			newClientsByCashierChart.setCatagoryPaint("Not Served", "Cashier " + c.getId(), notServedColor);
		}

		newClientsByCashierChartPanel = new ChartPanel(newClientsByCashierChart);
		newClientsByCashierChartPanel.setLocation(10, 11);
		newClientsByCashierChartPanel.setSize(735, 246);

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
		currentSimulationTab.add(newClientsByCashierChartPanel);

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

	private ActionListener btnRunSimulator_Click()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				clientChart.clearValues();
				newClientsByCashierChart.clearValues();

				btnRunSimulator.setEnabled(false);

				// ran in another thread so the UI isn't looked.
				Runnable thread = new Runnable()
				{
					@Override
					public void run()
					{
						simulator.clear();
						simulator.simulate();
					}
				};

				thread.run();
			}
		};
	}

	@Override
	public void ClientServed(final Cashier cashier, final Client client, final int time)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				clientChart.increaseCategory("Served");
				clientChart.decreaseCategory("Not Served");

				newClientsByCashierChart.increaseCategory("Served", "Cashier " + cashier.getId());
				newClientsByCashierChart.decreaseCategory("Not Served", "Cashier " + cashier.getId());
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
				newClientsByCashierChart.increaseCategory("Not Served", "Waiting");
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
	public void CashierStartedServing(final Cashier cashier, final Client c, int time)
	{
		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				newClientsByCashierChart.increaseCategory("Not Served", "Cashier " + cashier.getId());
				newClientsByCashierChart.decreaseCategory("Not Served", "Waiting");
			}
		});
	}
}
