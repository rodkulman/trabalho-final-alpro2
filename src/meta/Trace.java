package meta;

import java.io.IOException;

/**
 * Designed to print trace logs.
 * 
 * @author rodkulman@gmail.com
 */
public final class Trace
{
	/**
	 * Indicates whether it should trace messages.
	 */
	private static boolean tracing = false;

	/**
	 * The output stream to log messages to.
	 */
	private static Appendable output = System.out;

	/**
	 * Gets whether tracing is enabled.
	 * 
	 * @return true if is tracing, otherwise false.
	 */
	public static boolean isTracing()
	{
		return tracing;
	}

	/**
	 * Determines whether trace messages should be logged.
	 * 
	 * @param tracing
	 *            true to log trace messages, otherwise false.
	 */
	public static void setTracing(boolean tracing)
	{
		Trace.tracing = tracing;
	}

	/**
	 * Gets the output stream used to trace messages.
	 * 
	 * @return Returns the output stream used to trace messages.
	 */
	public static Appendable getOutput()
	{
		return output;
	}

	/**
	 * Sets the output stream used to trace messages;
	 * 
	 * @param output
	 *            The output stream used to trace messages;
	 */
	public static void setOutput(Appendable output)
	{
		Trace.output = output;
	}

	/**
	 * Sets the output stream to its default value.
	 */
	public static void resetOutput()
	{
		Trace.output = System.out;
	}

	/**
	 * Logs a message.
	 * 
	 * @param message
	 *            Message to be logged.
	 */
	public static void log(String message)
	{
		if (!tracing) { return; }

		try
		{
			output.append(message + System.lineSeparator());
		}
		catch (IOException e)
		{
			// meanwhile does nothing
		}
	}

	/**
	 * Logs an error message.
	 * 
	 * @param ex
	 *            The error that happened and needs to be logged.
	 */
	public static void log(Exception ex)
	{
		log(ex.getClass().getName() + ":");
		log(ex.getMessage());
		log(ex.getStackTrace().toString());
	}

	/**
	 * Logs a message, and formats it same as
	 * {@link java.lang.String#format(String, Object...) String.format}.
	 * 
	 * @param message
	 *            Message in format string style.
	 * @param args
	 *            Arguments referenced by the format specifiers in the format
	 *            string.
	 */
	public static void log(String message, Object... args)
	{
		log(String.format(message, args));
	}
}
