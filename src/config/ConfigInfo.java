package config;

/**
 * Contains information about a config value.
 * 
 * @author rodkulman@gmail.com
 * 
 */
final class ConfigInfo
{
	/**
	 * Determines the config type.
	 */
	private final String type;
	/**
	 * Determines the config description.
	 */
	private final String descr;
	/**
	 * The config value.
	 */
	private Object value;

	public ConfigInfo(String type, String descr, Object value)
	{
		this.type = type;
		this.descr = descr;
		this.value = value;
	}

	/**
	 * Gets the value
	 * 
	 * @return Returns the value.
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * Sets a value.
	 * 
	 * @param value
	 *            Sets the value.
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}

	/**
	 * Gets the type.
	 * 
	 * @return Returns a string representing the value type.
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Gets the description
	 * 
	 * @return Returns a description associated with the value.
	 */
	public String getDescr()
	{
		return descr;
	}
}
