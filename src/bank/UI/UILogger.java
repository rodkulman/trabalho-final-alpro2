package bank.UI;

import java.io.IOException;

import meta.collections.ListLinked;

/**
 * Allows for logging into the UI.
 * 
 * @author rodkulman@gmail.com
 * 
 */

public class UILogger implements Appendable
{
	/**
	 * Object listening to the events
	 */
	private ListLinked<AppendableListener> listeners;

	public UILogger()
	{
		listeners = new ListLinked<>();
	}

	/**
	 * Adds a listener to listen to the events
	 * 
	 * @param listener
	 *            Objects listening to the events.
	 */
	public void setAppendableListener(AppendableListener listener)
	{
		if (!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	@Override
	public Appendable append(CharSequence arg0) throws IOException
	{
		for (AppendableListener l : listeners)
		{
			l.Appended(arg0.toString());
		}

		return this;
	}

	@Override
	public Appendable append(char arg0) throws IOException
	{
		for (AppendableListener l : listeners)
		{
			l.Appended(new String(new char[] { arg0 }));
		}

		return this;
	}

	@Override
	public Appendable append(CharSequence arg0, int arg1, int arg2) throws IOException
	{
		for (AppendableListener l : listeners)
		{
			l.Appended(arg0.subSequence(arg1, arg2).toString());
		}

		return this;
	}

}
