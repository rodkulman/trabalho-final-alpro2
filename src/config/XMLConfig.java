package config;

import java.io.*;

import meta.Trace;
import meta.collections.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

/**
 * Enables reading XML as a configuration file.
 * 
 * @author rodkulman@gmail.com
 * 
 */
public final class XMLConfig
{
	/**
	 * Dictionary with the data.
	 */
	private static LinkedDictionary<String, ConfigInfo> data;

	static
	{
		init();
	}

	/**
	 * Initializes the static XML config
	 */
	private static void init()
	{
		Document doc;

		try
		{
			doc = getConfigFile();
		}
		catch (Exception ex)
		{
			Trace.log(ex);
			return;
		}

		// gets the nodes within the config file.
		Node config = doc.getElementsByTagName("config").item(0);
		NodeList dataNodes = config.getChildNodes();

		data = new LinkedDictionary<>();

		// adds them one by one to the internal dictionary.
		for (int i = 0; i < dataNodes.getLength(); i++)
		{
			Node n = dataNodes.item(i);

			if (n.getNodeType() == Node.ELEMENT_NODE)
			{
				Element e = (Element) n;

				String type = e.getAttribute("type");
				String descr = e.getAttribute("descr");

				data.add(n.getNodeName().toUpperCase(), new ConfigInfo(type, descr, e.getChildNodes().item(0).getNodeValue()));
			}
		}
	}

	/**
	 * Searches for and returns the XML config.
	 * 
	 * @return Returns the Document object holding the config file.
	 * @throws Exception
	 *             Some error occurred reading the doc
	 */
	private static Document getConfigFile() throws Exception
	{
		File file = new File("config.xml");
		Document doc;

		try
		{
			// opens and normalizes the xml document.
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = db.parse(file);
			doc.getDocumentElement().normalize();
		}
		catch (Exception ex)
		{
			throw ex;
		}

		return doc;
	}

	/**
	 * Searches for all the configuration names loaded.
	 * 
	 * @return Returns a read-only collection containing all the configuration
	 *         names.
	 */
	public static ListLinked<String> getNames()
	{
		return data.getKeys();
	}

	/**
	 * Gets the value of said configuration name.
	 * 
	 * @param name
	 *            Configuration name to search for.
	 * @return Returns the specified value. Null if the value wasn't in the
	 *         original configuration.
	 */
	public static String get(String name)
	{
		return data.getValue(name.toUpperCase()).getValue().toString();
	}

	/**
	 * Gets the description of said configuration name.
	 * 
	 * @param name
	 *            Configuration name to search for.
	 * @return Returns the specified description. Null if the value wasn't in
	 *         the original configuration.
	 */
	public static String getDescr(String name)
	{
		return data.getValue(name.toUpperCase()).getDescr();
	}

	/**
	 * Gets the type of said configuration name.
	 * 
	 * @param name
	 *            Configuration name to search for.
	 * @return Returns the specified type. Null if the value wasn't in the
	 *         original configuration.
	 */
	public static String getType(String name)
	{
		return data.getValue(name.toUpperCase()).getType();
	}

	/**
	 * Gets the value of said configuration name.
	 * 
	 * @param name
	 *            Configuration name to search for.
	 * @return Returns the specified value. Null if the value wasn't in the
	 *         original configuration.
	 */
	public static int getInt(String name)
	{
		return Integer.parseInt(get(name));
	}

	/**
	 * Gets the value of said configuration name.
	 * 
	 * @param name
	 *            Configuration name to search for.
	 * @return Returns the specified value. Null if the value wasn't in the
	 *         original configuration.
	 */
	public static double getDouble(String name)
	{
		return Double.parseDouble(get(name));
	}

	/**
	 * Sets a value to the config.
	 * 
	 * @param name
	 *            Name of the config value.
	 * @param value
	 *            Value to be inserted.
	 */
	public static void set(String name, String value)
	{
		if (data.getValue(name.toUpperCase()).getType().equalsIgnoreCase("number") || data.getValue(name.toUpperCase()).getType().equalsIgnoreCase("probability"))
		{
			// tries to parse the value to double to see if it is valid.
			Double.parseDouble(value);
		}

		Document doc;

		try
		{
			doc = getConfigFile();
		}
		catch (Exception ex)
		{
			Trace.log(ex);
			return;
		}

		// gets the nodes within the config file.
		Node config = doc.getElementsByTagName("config").item(0);
		NodeList dataNodes = config.getChildNodes();

		// adds them one by one to the internal dictionary.
		for (int i = 0; i < dataNodes.getLength(); i++)
		{
			Node n = dataNodes.item(i);

			if (n.getNodeName().equalsIgnoreCase(name) && n.getNodeType() == Node.ELEMENT_NODE)
			{
				n.setNodeValue(value);
				break;
			}
		}

		Transformer transformer;
		Source source;
		Result result;

		try
		{
			transformer = TransformerFactory.newInstance().newTransformer();

			result = new StreamResult(new File("config.xml"));
			source = new DOMSource(doc);

			transformer.transform(source, result);
		}
		catch (Exception ex)
		{
			Trace.log(ex);
			return;
		}

		data.getValue(name).setValue(value);
	}
}
