package trunk.meta;

/**
 * Designed to print trace logs.
 * 
 * @author rodkulman@gmail.com 
*/
public final class Trace
{
	private static boolean tracing = false;

	/**
	 * Gets whether tracing is enabled.
	 * @return true if is tracing, otherwise false.
	 */
	public static boolean isTracing()
	{
		return tracing;
	}

	/**
	 * Determines whether trace messages should be logged.
	 * @param tracing true to log trace messages, otherwise false.
	 */
	public static void setTracing(boolean tracing)
	{
		Trace.tracing = tracing;
	}
	
	/**
	 * Logs a message.
	 * @param message Message to be logged.
	 */
	public static void log(String message)
	{
		if (!tracing) { return; }
		
		System.out.println(message);
	}
	
	/**
	 * Logs a message, and formats it same as {@link java.lang.String#format(String, Object...) String.format}.
	 * @param message Message in format string style.
	 * @param args Arguments referenced by the format specifiers in the format string.  
	 */
	public static void log(String message, Object... args)
	{
		log(String.format(message, args));
	}
}
