package bank.UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BankFrame extends JFrame implements AppendableListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -108639763223961561L;
	private JPanel contentPane;
	private UILogger logger;

	/**
	 * Create the frame.
	 */
	public BankFrame() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		logger = new UILogger();
		logger.setAppendableListener(this);
	}

	@Override
	public void Appended(String logMessage) 
	{
		// adds logs to the logging thing-y	
	}

}
