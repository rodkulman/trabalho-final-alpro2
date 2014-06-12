package bank;

import java.awt.*;

import meta.Trace;
import bank.UI.*;

/**
 * Application initializer.
 * @author rodkulman@gmail.com
 *
 */
public final class App
{
	public static void main(String[] args)
	{
		Trace.setTracing(true);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankFrame frame = new BankFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
