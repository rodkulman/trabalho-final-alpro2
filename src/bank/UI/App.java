package bank.UI;

import java.awt.*;

import meta.Trace;

/**
 * Application initializer.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public final class App
{
	public static void main(String[] args)
	{
		// enables tracing
		Trace.setTracing(true);

		// runs the UI
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					BankFrame frame = new BankFrame();
					frame.setVisible(true);
				}
				catch (Exception ex)
				{
					Trace.log(ex);
				}
			}
		});
	}
}
