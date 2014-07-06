package bank.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import meta.*;
import meta.collections.KeyValuePair;
import meta.collections.LinkedDictionary;
import bank.*;
import bank.UI.charts.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

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
	private JPanel statisticsPanel;

	private JList<String> lstLogs;
	private DefaultListModel<String> listModel;

	private CustomPieChart clientChart;
	private ChartPanel clientChartPanel;

	private CustomPieChart cashiersChart;
	private ChartPanel cashiersChartPanel;

	private CustomStackedBarChart cashierPerformanceChart;
	private ChartPanel cashierPerformanceChartPanel;

	private CustomPieChart probabilityChart;
	private ChartPanel probabilityChartPanel;

	private JFreeChart cashierPerformanceOTChart;
	private DefaultCategoryDataset dsCashierPerformanceOT;
	private ChartPanel cashierPerformanceOTChartPanel;

	private JTabbedPane tabs;
	private JPanel currentSimulationTab;
	private JPanel eventsTab;
	private JPanel configuationTab;
	private JPanel simulationOverTimeTab;

	private JLabel lblAverageWaitingTime;
	private JLabel lblAverageQueueSize;
	private JLabel lblAmountOfNoWaitServings;

	// End of window components

	// standard colors for the categories

	private final Color servedColor = new Color(0x00, 0x00, 0x66);
	private final Color notServedColor = new Color(0xCC, 0x00, 0x00);
	private final Color notFinishedServingColor = new Color(0x5C, 0x00, 0x5C);

	private final Color regularsColor = new Color(0xCC, 0x99, 0x00);
	private final Color prioritiesColor = new Color(0x00, 0xCC, 0x00);
	private final Color managersColor = new Color(0x33, 0x33, 0xFF);
	private final Color priorityManagersColor = new Color(0x99, 0x33, 0x33);
	private JLabel lblTotalEmptyQueueTime;
	private JLabel lblDuration;
	private JLabel lblArrivalProbability;
	private JLabel lblAverageClientsPer;
	private JLabel lblMinimumServingTime;
	private JLabel lblMaximumServingTime;
	private JLabel lblEstimatedAverageServing;

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
		setLocationRelativeTo(null);

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception ex)
		{
			Trace.log(ex);
		}

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

		// probability Chart

		/*
		 * a thanks to Rafael Wasilewski <wasilewski1991@gmail.com> for the math
		 * on probabilities
		 */

		probabilityChart = new CustomPieChart("Probability of New Clients");
		probabilityChart.addNewCategory("Regular", (1 - XMLConfig.getDouble("priorityProbality")) * (1 - XMLConfig.getDouble("managerProbality")));
		probabilityChart.addNewCategory("Priority", XMLConfig.getDouble("priorityProbality") * (1 - XMLConfig.getDouble("managerProbality")));
		probabilityChart.addNewCategory("Manager", (1 - XMLConfig.getDouble("priorityProbality")) * XMLConfig.getDouble("managerProbality"));
		probabilityChart.addNewCategory("Priority Manager", XMLConfig.getDouble("priorityProbality") * XMLConfig.getDouble("managerProbality"));

		probabilityChart.setSectionPaint("Regular", regularsColor);
		probabilityChart.setSectionPaint("Priority", prioritiesColor);
		probabilityChart.setSectionPaint("Manager", managersColor);
		probabilityChart.setSectionPaint("Priority Manager", priorityManagersColor);

		probabilityChart.setLabelMode(PieSectionNumericLabelGenerator.PERCENTAGE_MODE);

		probabilityChartPanel = new ChartPanel(probabilityChart);
		probabilityChartPanel.setBounds(10, 277, 402, 229);

		// newClientsByCashierChart

		cashierPerformanceChart = new CustomStackedBarChart("Cashier Performance", "", "Clients");

		cashierPerformanceChart.addCategory("Served", "Waiting");
		cashierPerformanceChart.addCategory("Not Served", "Waiting");
		cashierPerformanceChart.setCatagoryPaint("Not Served", "Waiting", notServedColor);

		for (Cashier c : simulator.getCashiers())
		{
			cashierPerformanceChart.addCategory("Served", "Cashier " + c.getId());
			cashierPerformanceChart.setCatagoryPaint("Served", "Cashier " + c.getId(), getCashierColor(c));

			cashierPerformanceChart.addCategory("Not Served", "Cashier " + c.getId());
			cashierPerformanceChart.setCatagoryPaint("Not Served", "Cashier " + c.getId(), notFinishedServingColor);
		}

		cashierPerformanceChart.addLegend("Waiting", notServedColor);
		cashierPerformanceChart.addLegend("Regulars", regularsColor);
		cashierPerformanceChart.addLegend("Priorities", prioritiesColor);
		cashierPerformanceChart.addLegend("Managers", managersColor);
		cashierPerformanceChart.addLegend("Priority Managers", priorityManagersColor);
		cashierPerformanceChart.addLegend("Not Finshed Serving", notFinishedServingColor);

		cashierPerformanceChartPanel = new ChartPanel(cashierPerformanceChart);
		cashierPerformanceChartPanel.setLocation(10, 11);
		cashierPerformanceChartPanel.setSize(735, 246);

		// clientsServedOverTime

		dsCashierPerformanceOT = new DefaultCategoryDataset();
		cashierPerformanceOTChart = ChartFactory.createLineChart("Cashier Performance Over Time", "Simulations", "Clients", dsCashierPerformanceOT, PlotOrientation.VERTICAL, true, true, false);
		cashierPerformanceOTChart.getTitle().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cashierPerformanceOTChart.setBackgroundPaint(Color.WHITE);

		CategoryPlot plotCPOT = cashierPerformanceOTChart.getCategoryPlot();

		plotCPOT.setBackgroundPaint(Color.WHITE);
		plotCPOT.setRangeGridlinePaint(Color.BLACK);
		plotCPOT.setDomainGridlinesVisible(true);
		plotCPOT.setDomainGridlinePaint(Color.BLACK);

		LineAndShapeRenderer rendererCPOT = (LineAndShapeRenderer) cashierPerformanceOTChart.getCategoryPlot().getRenderer();

		for (int i = 0; i < 6; i++)
		{
			rendererCPOT.setSeriesShapesVisible(i, true);
		}

		rendererCPOT.setSeriesPaint(0, regularsColor);
		rendererCPOT.setSeriesPaint(1, prioritiesColor);
		rendererCPOT.setSeriesPaint(2, managersColor);
		rendererCPOT.setSeriesPaint(3, priorityManagersColor);
		rendererCPOT.setSeriesPaint(4, notServedColor);
		rendererCPOT.setSeriesPaint(5, notFinishedServingColor);

		cashierPerformanceOTChartPanel = new ChartPanel(cashierPerformanceOTChart);
		cashierPerformanceOTChartPanel.setSize(735, 496);
		cashierPerformanceOTChartPanel.setLocation(10, 10);

		// btnRunSimulator

		btnRunSimulator = new JButton("Run Simulation");
		btnRunSimulator.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnRunSimulator.setBounds(649, 568, 121, 46);
		btnRunSimulator.addActionListener(btnRunSimulator_Click());
		contentPane.add(btnRunSimulator);

		// tabs panels

		currentSimulationTab = new JPanel();
		currentSimulationTab.setBackground(Color.WHITE);
		currentSimulationTab.setLayout(null);

		currentSimulationTab.add(clientChartPanel);
		currentSimulationTab.add(cashierPerformanceChartPanel);

		eventsTab = new JPanel();
		eventsTab.setBackground(Color.WHITE);
		eventsTab.setLayout(null);

		simulationOverTimeTab = new JPanel();
		simulationOverTimeTab.setBackground(Color.WHITE);
		simulationOverTimeTab.setLayout(null);

		simulationOverTimeTab.add(cashierPerformanceOTChartPanel);

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
		configuationTab.add(probabilityChartPanel);

		tabs.addTab("Configuration", configuationTab);

		JPanel configPanel = new JPanel();
		configPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Other Configuration", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Segoe UI", Font.PLAIN, 18), null));
		configPanel.setBackground(Color.WHITE);
		configPanel.setBounds(422, 11, 323, 495);
		configuationTab.add(configPanel);
		configPanel.setLayout(new GridLayout(10, 1, 0, 5));

		lblDuration = new JLabel("Duration: " + XMLConfig.getInt("duration"));
		lblDuration.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		configPanel.add(lblDuration);

		lblArrivalProbability = new JLabel("Arrival Probability: " + (XMLConfig.getDouble("arrivalProbability") * 100) + "%");
		lblArrivalProbability.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		configPanel.add(lblArrivalProbability);

		lblAverageClientsPer = new JLabel("Estimated Clients per Simulation: " + XMLConfig.getInt("duration") * XMLConfig.getDouble("arrivalProbability"));
		lblAverageClientsPer.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		configPanel.add(lblAverageClientsPer);

		lblMinimumServingTime = new JLabel("Minimum Serving Time: " + XMLConfig.getInt("minServingTime"));
		lblMinimumServingTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		configPanel.add(lblMinimumServingTime);

		lblMaximumServingTime = new JLabel("Maximum Serving Time " + XMLConfig.getInt("maxServingTime"));
		lblMaximumServingTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		configPanel.add(lblMaximumServingTime);

		lblEstimatedAverageServing = new JLabel("Estimated Average Serving Time: " + ((XMLConfig.getInt("minServingTime") + XMLConfig.getInt("maxServingTime")) / 2));
		lblEstimatedAverageServing.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		configPanel.add(lblEstimatedAverageServing);
		tabs.addTab("Current Simulation", currentSimulationTab);
		tabs.addTab("Simulations Over Time", simulationOverTimeTab);

		statisticsPanel = new JPanel();
		statisticsPanel.setBackground(Color.WHITE);
		statisticsPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Statistics", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Segoe UI", Font.PLAIN, 18), null));

		statisticsPanel.setBounds(380, 268, 365, 238);
		currentSimulationTab.add(statisticsPanel);
		statisticsPanel.setLayout(new GridLayout(8, 1, 0, 0));

		lblAverageWaitingTime = new JLabel("Average Waiting Time: 0.0");
		lblAverageWaitingTime.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		statisticsPanel.add(lblAverageWaitingTime);

		lblAverageQueueSize = new JLabel("Average Queues Size: 0.0");
		lblAverageQueueSize.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		statisticsPanel.add(lblAverageQueueSize);

		lblAmountOfNoWaitServings = new JLabel("Amount of Clients That didn't Wait: 0");
		lblAmountOfNoWaitServings.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		statisticsPanel.add(lblAmountOfNoWaitServings);

		lblTotalEmptyQueueTime = new JLabel("Total Time Queues Were Empty: 0");
		lblTotalEmptyQueueTime.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		statisticsPanel.add(lblTotalEmptyQueueTime);

		tabs.addTab("Simulation Events", eventsTab);

		contentPane.add(tabs);

		// logger

		logger = new UILogger();
		logger.setAppendableListener(this);

		// sets the login output to be the logger
		Trace.setOutput(logger);
	}

	private Color getCashierColor(Cashier c)
	{
		return c.isManager() ? (c.isPriority() ? priorityManagersColor : managersColor) : (c.isPriority() ? prioritiesColor : regularsColor);
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
				cashierPerformanceChart.clearValues();

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

				cashierPerformanceChart.increaseCategory("Served", "Cashier " + cashier.getId());
				cashierPerformanceChart.decreaseCategory("Not Served", "Cashier " + cashier.getId());
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
				cashierPerformanceChart.increaseCategory("Not Served", "Waiting");
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
				lblAverageWaitingTime.setText(String.format("Average Waiting Time: %s", simulator.getAverageWaitingTime()));
				lblAverageQueueSize.setText(String.format("Average Queues Size: %s", simulator.getAverageQueueSize()));
				lblAmountOfNoWaitServings.setText(String.format("Amount of Clients That didn't Wait: %s", simulator.getAmountOfNoWaitServings()));
				lblTotalEmptyQueueTime.setText(String.format("Total Time Queues Were Empty: %s", Math.round(simulator.getEmptyQueueTime() * 100)) + "%");

				// gets the number of the simulation
				int simulation = 1;

				if (dsCashierPerformanceOT.getColumnCount() > 0)
				{
					simulation = Integer.parseInt(dsCashierPerformanceOT.getColumnKey(dsCashierPerformanceOT.getColumnCount() - 1).toString()) + 1;
				}

				double notFinishedServing = 0.0;

				LinkedDictionary<String, Double> values = new LinkedDictionary<>();
				values.add("Regular", 0.0);
				values.add("Priority", 0.0);
				values.add("Manager", 0.0);
				values.add("Priority Manager", 0.0);

				for (Cashier c : simulator.getCashiers())
				{
					double value = cashierPerformanceChart.getValue("Served", "Cashier " + c.getId());
					String key;
					
					notFinishedServing += cashierPerformanceChart.getValue("Not Served", "Cashier " + c.getId());

					if (c.isManager())
					{
						if (c.isPriority())
						{
							key = "Priority Manager";
						}
						else
						{
							key = "Manager";
						}
					}
					else
					{
						if (c.isPriority())
						{
							key = "Priority";
						}
						else
						{
							key = "Regular";
						}
					}

					values.setValue(key, values.getValue(key) + value);
				}

				if (simulation > 15)
				{
					dsCashierPerformanceOT.removeColumn(String.valueOf(simulation - 15));
				}

				for (KeyValuePair<String, Double> pair : values)
				{
					dsCashierPerformanceOT.addValue(pair.getValue(), pair.getKey(), String.valueOf(simulation));
				}
				
				double waiting = cashierPerformanceChart.getValue("Not Served", "Waiting");
				
				dsCashierPerformanceOT.addValue(waiting, "Waiting", String.valueOf(simulation));
				dsCashierPerformanceOT.addValue(notFinishedServing, "Not Finished Serving", String.valueOf(simulation));
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
				cashierPerformanceChart.increaseCategory("Not Served", "Cashier " + cashier.getId());
				cashierPerformanceChart.decreaseCategory("Not Served", "Waiting");
			}
		});
	}
}
