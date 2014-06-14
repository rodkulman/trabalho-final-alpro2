package bank.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import meta.*;
import meta.collections.*;
import bank.*;

/**
 * The UI for the bank simulator app.
 * 
 * @author rodkulman@gmail.com
 * 
 */

public class BankFrame extends JFrame implements AppendableListener
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

	private ListLinked<String> logs;

	private Simulator simulator;

	// Window components

	JButton btnRunSimulator;
	JList<String> lstLogs;

	// End of window components

	/**
	 * Creates the frame.
	 */
	public BankFrame()
	{
		setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setTitle("Bank Agency Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnRunSimulator = new JButton("Run Simulator");
		btnRunSimulator.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnRunSimulator.setBounds(269, 204, 121, 46);
		btnRunSimulator.addActionListener(btnRunSimulator_Click());
		contentPane.setLayout(null);

		lstLogs = new JList<>();
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
		lstLogs.setBounds(155, 10, 129, 122);

		contentPane.add(lstLogs);
		contentPane.add(btnRunSimulator);

		logger = new UILogger();
		logger.setAppendableListener(this);

		// sets the login output to be the logger
		Trace.setOutput(logger);
	}

	@Override
	public void Appended(String logMessage)
	{
		// adds logs to the logging thing-y
		logs.add(logMessage);
	}

	private ActionListener btnRunSimulator_Click()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// ran in another thread so the UI isn't looked.
				Runnable thread = new Runnable()
				{
					@Override
					public void run()
					{
						simulator = new Simulator();
						simulator.simulate();
					}
				};

				thread.run();
			}
		};
	}

}
