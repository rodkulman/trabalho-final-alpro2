package io;

import java.io.*;
import meta.collections.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;

/**
 * Enables reading XML as a configuration file.
 * @author rodkulman@gmail.com
 *
 */
public class XMLConfig 
{
	LinkedDictionary<String, String> data;
	
	public XMLConfig(String filePath)
	{
		File file = new File(filePath);
		Document doc;
		
		try 
		{
			//opens and normalizes the xml document.
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = db.parse(file);
			doc.getDocumentElement().normalize();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			return;
		}
		
		//gets the nodes within the config file.
		Node config = doc.getElementsByTagName("config").item(0);
		NodeList dataNodes = config.getChildNodes();
		
		data = new LinkedDictionary<>();
		
		//adds them one by one to the internal dictionary.
		for (int i = 0; i < dataNodes.getLength(); i++) 
		{
			Node n = dataNodes.item(i);
			
			if (n.getNodeType() == Node.ELEMENT_NODE)
			{
				Element e = (Element)n;				
				
				data.add(n.getNodeName().toUpperCase(), e.getChildNodes().item(0).getNodeValue());
			}
		}
	}
	
	/**
	 * Gets the value of said configuration name.
	 * @param name Configuration name to search for.
	 * @return Returns the specified value. Null if the value wasn't in the original configuration.
	 */
	public String get(String name)
	{
		return data.getValue(name.toUpperCase());
	}
}
