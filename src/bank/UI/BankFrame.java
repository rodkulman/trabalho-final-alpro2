package bank.UI;

import io.XMLConfig;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import meta.*;
import meta.collections.*;
import bank.*;

import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.data.general.*;
import org.jfree.chart.plot.*;

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
	 * Stores the log messages received.
	 */
	private ListLinked<String> logs;

	/**
	 * The bank simulator class;
	 */
	private Simulator simulator;

	// Window components

	JButton btnRunSimulator;
	JList<String> lstLogs;
	JFreeChart clientChart;
	ChartPanel clientChartPanel;
	DefaultPieDataset dsClients;
	JFreeChart clientTimeChart;
	ChartPanel clientTimeChartPanel;
	DefaultCategoryDataset dsClientsTime;

	JFreeChart cashiersChart;
	ChartPanel cashiersChartPanel;
	DefaultPieDataset dsCashiers;

	JTabbedPane tabs;
	JPanel currentSimulationTab;
	JPanel allSimulationsTab;
	JPanel eventsTab;
	JPanel configuationTab;

	// End of window components

	/**
	 * Creates the frame.
	 */
	public BankFrame()
	{
		// frame

		setResizable(false);
		setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setTitle("Bank Agency Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 524);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// lstLogs

		logs = new ListLinked<>();
		lstLogs = new JList<>();
		lstLogs.setLocation(0, 0);
		lstLogs.setSize(511, 364);
		lstLogs.setBackground(Color.LIGHT_GRAY);
		lstLogs.setBorder(new LineBorder(new Color(0, 0, 0)));
		lstLogs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lstLogs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstLogs.setModel(new AbstractListModel<String>()
		{
			private static final long serialVersionUID = -6267293639226535378L;

			public int getSize()
			{
				return logs.size();
			}

			public String getElementAt(int index)
			{
				return logs.get(index);
			}
		});
		// clients chart

		dsClients = new DefaultPieDataset();
		dsClients.setValue("Served", 0.0);
		dsClients.setValue("Not Served", 0.0);

		clientChart = ChartFactory.createPieChart("Clients Served", dsClients, true, true, false);
		clientChart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));

		PiePlot clientPlot = (PiePlot) clientChart.getPlot();

		clientPlot.setSectionPaint("Served", new Color(0x00, 0x00, 0x66));
		clientPlot.setSectionPaint("Not Served", new Color(0xCC, 0x00, 0x00));

		clientPlot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
		clientPlot.setLabelGenerator(new PieSectionNumericLabelGenerator());

		clientChartPanel = new ChartPanel(clientChart);
		clientChartPanel.setBounds(10, 11, 400, 246);

		// clients over time chart

		dsClientsTime = new DefaultCategoryDataset();

		clientTimeChart = ChartFactory.createBarChart("Clients Served Over Time", "Simulations", "Clients", dsClientsTime, PlotOrientation.VERTICAL, false, true, false);
		clientTimeChart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));

		CategoryPlot plot = (CategoryPlot) clientTimeChart.getPlot();

		plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
		plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));

		clientTimeChartPanel = new ChartPanel(clientTimeChart);
		clientTimeChartPanel.setBounds(10, 11, 511, 309);

		// cashiers chart

		dsCashiers = new DefaultPieDataset();
		dsCashiers.setValue("Regulars", XMLConfig.getDouble("cashierNumber"));
		dsCashiers.setValue("Priorities", XMLConfig.getDouble("priorityCashierNumber"));
		dsCashiers.setValue("Managers", XMLConfig.getDouble("managerNumber"));
		dsCashiers.setValue("Priority Managers", XMLConfig.getDouble("priorityManagerNumber"));

		cashiersChart = ChartFactory.createPieChart("Cashiers", dsCashiers, true, true, false);
		cashiersChart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));

		PiePlot cashierPlot = (PiePlot) cashiersChart.getPlot();

		cashierPlot.setSectionPaint("Regulars", new Color(0x00, 0x00, 0x66));
		cashierPlot.setSectionPaint("Priorities", new Color(0xCC, 0x00, 0x00));
		cashierPlot.setSectionPaint("Managers", new Color(0x00, 0x66, 0x00));
		cashierPlot.setSectionPaint("Priority Managers", new Color(0xCC, 0xCC, 0x00));

		cashierPlot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
		cashierPlot.setLabelGenerator(new PieSectionNumericLabelGenerator());

		// btnRunSimulator

		btnRunSimulator = new JButton("Run Simulator");
		btnRunSimulator.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnRunSimulator.setBounds(649, 437, 121, 46);
		btnRunSimulator.addActionListener(btnRunSimulator_Click());
		contentPane.add(btnRunSimulator);

		// tabs panels

		currentSimulationTab = new JPanel();
		currentSimulationTab.setBackground(Color.WHITE);
		currentSimulationTab.setLayout(null);

		currentSimulationTab.add(clientChartPanel);

		allSimulationsTab = new JPanel();
		allSimulationsTab.setBackground(Color.WHITE);
		allSimulationsTab.setLayout(null);

		allSimulationsTab.add(clientTimeChartPanel);

		eventsTab = new JPanel();
		eventsTab.setBackground(Color.WHITE);
		eventsTab.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(lstLogs);
		scrollPane.setBounds(10, 11, 511, 364);
		scrollPane.setLayout(null);
		eventsTab.add(scrollPane);

		// tabs

		tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tabs.setBounds(10, 11, 760, 415);

		cashiersChartPanel = new ChartPanel(cashiersChart);
		cashiersChartPanel.setBounds(10, 11, 340, 223);

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
		tabs.addTab("All Simulations", allSimulationsTab);
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
				logs.add(logMessage);
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
				dsClients.setValue("Served", 0.0);
				dsClients.setValue("Not Served", 0.0);

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

	// listens to the simulator and
	@Override
	public void ClientServed(final Client c, final int time)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				dsClients.setValue("Served", dsClients.getValue("Served").doubleValue() + 1);
				dsClients.setValue("Not Served", dsClients.getValue("Not Served").doubleValue() - 1);
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
				dsClients.setValue("Not Served", dsClients.getValue("Not Served").doubleValue() + 1);
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
				double totalServed = dsClients.getValue("Served").doubleValue();
				dsClientsTime.addValue(totalServed, "Simulation " + dsClientsTime.getRowCount(), "");

				btnRunSimulator.setEnabled(true);
			}
		});
	}
}
