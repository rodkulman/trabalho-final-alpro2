package bank.UI;

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

	// End of window components

	/**
	 * Creates the frame.
	 */
	public BankFrame()
	{
		setResizable(false);
		setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setTitle("Bank Agency Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 585);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		logs = new ListLinked<>();
		lstLogs = new JList<>();
		lstLogs.setBackground(Color.LIGHT_GRAY);
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
		lstLogs.setBorder(new LineBorder(new Color(0, 0, 0)));
		lstLogs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lstLogs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstLogs.setBounds(10, 36, 214, 411);

		// clients chart

		dsClients = new DefaultPieDataset();
		dsClients.setValue("Served", 0.0);
		dsClients.setValue("Not Served", 0.0);

		clientChart = ChartFactory.createPieChart("Clients Served", dsClients, false, true, false);
		clientChart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));

		PiePlot clientPlot = (PiePlot) clientChart.getPlot();

		clientPlot.setSectionPaint("Served", new Color(0x00, 0x00, 0x66));
		clientPlot.setSectionPaint("Not Served", new Color(0xCC, 0x00, 0x00));

		clientPlot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

		clientChartPanel = new ChartPanel(clientChart);
		clientChartPanel.setSize(307, 239);
		clientChartPanel.setLocation(234, 11);

		// end clients chart

		// clients over time chart

		dsClientsTime = new DefaultCategoryDataset();

		clientTimeChart = ChartFactory.createBarChart("Clients Served Over Time", "Simulations", "Clients", dsClientsTime, PlotOrientation.VERTICAL, false, true, false);
		clientTimeChart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));

		CategoryPlot plot = (CategoryPlot) clientTimeChart.getPlot();

		plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
		plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));

		clientTimeChartPanel = new ChartPanel(clientTimeChart);
		clientTimeChartPanel.setSize(462, 186);
		clientTimeChartPanel.setLocation(234, 261);

		// end clients over time chart

		// btnRunSimulator

		btnRunSimulator = new JButton("Run Simulator");
		btnRunSimulator.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnRunSimulator.setBounds(649, 499, 121, 46);
		btnRunSimulator.addActionListener(btnRunSimulator_Click());

		// btnRunSimulator

		contentPane.add(clientChartPanel);
		contentPane.add(clientTimeChartPanel);
		contentPane.add(lstLogs);
		contentPane.add(btnRunSimulator);

		JLabel lblNewLabel = new JLabel("Events");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel.setBounds(85, 11, 62, 19);
		contentPane.add(lblNewLabel);

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

	private ActionListener btnRunSimulator_Click()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dsClients.setValue("Served", 0.0);
				dsClients.setValue("Not Served", 0.0);

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
			}
		});
	}
}
