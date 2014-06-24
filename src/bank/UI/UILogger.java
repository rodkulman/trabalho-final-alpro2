package bank.UI;

import java.io.IOException;

/**
 * Allows for logging into the UI.
 * 
 * @author rodkulman@gmail.com
 * 
 */

public class UILogger implements Appendable
{
	private AppendableListener listener;

	public void setAppendableListener(AppendableListener listener)
	{
		this.listener = listener;
	}

	@Override
	public Appendable append(CharSequence arg0) throws IOException
	{
		listener.Appended(arg0.toString());
		return this;
	}

	@Override
	public Appendable append(char arg0) throws IOException
	{
		listener.Appended(new String(new char[] { arg0 }));
		return this;
	}

	@Override
	public Appendable append(CharSequence arg0, int arg1, int arg2) throws IOException
	{
		listener.Appended(arg0.subSequence(arg1, arg2).toString());
		return this;
	}

}
