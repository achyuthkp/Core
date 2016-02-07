package iamcore.services.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import iamcore.datamodel.Identity;
import iamcore.services.match.Matcher;
import iamcore.services.match.impl.ContainsIdentityMatcher;

public class IdentityXmlDAO implements IdentityDAO 
{

	Matcher<Identity> matcher = new ContainsIdentityMatcher();

	Document doc;

	/** Intermediate instance to build a document builder (the factory to
	 *  parse DOM documents)
	 */
	private static DocumentBuilderFactory dbf ;
	/**
	 *  DOM Document builder, to get an empty document or parse it from
	 *  An xml source
	 */
	
	private static DocumentBuilder db ;
	
	public IdentityXmlDAO() 
	{
		try 
		{
			
			if (db == null)
			{
				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
			}
			/**
			 *  checks if db value is equivalent to null
			 *  if true, then an instance for DocumentBuilderFactory is created
			 *  newDocumentBuilder() is called and returned to db
			 *  
			 *  Document representation in Java and parsed from the XML path
			 *    
			 */
			this.doc = db.parse("C:\\Users\\Moi Laptop\\git\\Core\\xml\\identities.xml");
		} catch (Exception e) 
		{
			e.printStackTrace();
			// TODO complete exception handling
		}
	}

	
	private List<Identity> searchInternal(Identity criteria, Matcher<Identity> matcher) 
	{
		/**
		 * results of type List<Identity> is created
		 * gets all the nodes called "identity" (see xml/identities.xml in the project)
		 * 
		 */
		List<Identity> results = new ArrayList<Identity>();
		NodeList nodes = this.doc.getElementsByTagName("identity");
		int nodesSize = nodes.getLength();
		/**
		 *  for every found identity
		 *   test if the found node is really an Element
		 *   in the DOM implementation a Node can represent an Element, an
		 *   Attribute or a TextContent
		 *   so we have to be sure that the found node is of type
		 *   "Element" using the instanceof operator
		 */
		for (int i = 0; i < nodesSize; i++)
		{
			Node node = nodes.item(i);
			
			if (node instanceof Element) 
			{
				Identity identity = readOneIdentityFromXmlElement(node);
				/**
				 *  usage of Matcher to filter only the wished identities.
				 */
				if (matcher == null || matcher.match(criteria, identity))
				{
					results.add(identity);
				}
			}
		}
		return results;
	}
	/**
	 * 
	 * @param criteria
	 * calls/returns searchInternal method
	 */
	
	@Override
	public List<Identity> search(Identity criteria)
	{
		return searchInternal(criteria, this.matcher);
	}
	
	
	private Identity readOneIdentityFromXmlElement(Node node) 
	{
		/**
		 *  Cast the node into an Element, as it is an instance of Element
		 *  Get the properties for the encountered identity
		 *  Declare and initialize several variables on the same line
		 */
		Element identity = (Element) node;
		NodeList properties = identity.getElementsByTagName("property");
		int length = properties.getLength();
		String displayName = "", guid = "", email = "";
		Date birthDate = null;
		/**
		 *  for every found property
		 *  Checks that the found property is really an element
		 *  To store the right value in the right property,
		 *  a switch-case structure is used to handle the 4 cases			
		 */
		for (int j = 0; j < length; j++) 
		{
			Node item = properties.item(j);
			if (item instanceof Element)
			{
				Element propertyElt = (Element) item;
				
				String textContent = propertyElt.getTextContent();
				switch (propertyElt.getAttribute("name")) 
				{
				case "displayName":
					displayName = textContent;
					break;
				case "guid":
					guid = textContent;
					break;
				case "email":
					email = textContent;
					break;
				case "birthDate":
					try 
					{
						birthDate = new SimpleDateFormat("dd/mm/yyyy").parse(textContent);
					} catch (Exception e) 
					{
						e.printStackTrace();
						//exception handling
					}
					break;

				default:
					// the encountered property name is not expected
					// so we use the "default" case
					break;
				}
			}
		}
		/**
		 * the current Identity is stored into currentIdentity
		 * and returned back
		 */
		Identity currentIdentity = new Identity(displayName, email, guid, birthDate);
		return currentIdentity;
	}

	@Override
	public void create(Identity identity)
	{
		/**
		 * root Identity is accessed and the element 'Identity' within is appended 
		 * using appendChild()
		 * 
		 */
		 Element rootIdentity =(Element)doc.createElement("identity"); 
		 doc.getDocumentElement().appendChild(rootIdentity);
		 /**
		  * 
		  * Element properties' name are created 
		  * Element properties' value are set using setValue
		  * the name is set through the AttributeNode and setTextContent
		  * Finally appendChild to rootIdnetity
		  * The same process is done for all properties
		  */
		 
		 Element propertyElm = doc.createElement("property");
		 Attr Attrbute = doc.createAttribute("name");
		 Attrbute.setValue("displayName");
		 propertyElm.setAttributeNode(Attrbute);
		 propertyElm.setTextContent(identity.getDisplayName());
		 rootIdentity.appendChild(propertyElm);
		 
		 propertyElm = doc.createElement("property");
		 Attrbute = doc.createAttribute("name");
		 Attrbute.setValue("email");
		 propertyElm.setAttributeNode(Attrbute);		 
		 propertyElm.setTextContent(identity.getEmailAddress());
		 rootIdentity.appendChild(propertyElm);
		 
		 propertyElm = doc.createElement("property");
		 Attrbute = doc.createAttribute("name");
		 Attrbute.setValue("guid");
		 propertyElm.setAttributeNode(Attrbute);		 
		 propertyElm.setTextContent(identity.getUid());
		 rootIdentity.appendChild(propertyElm);
		 
		 propertyElm = doc.createElement("property");
		 Attrbute = doc.createAttribute("name");
		 Attrbute.setValue("birthDate");
		 propertyElm.setAttributeNode(Attrbute);
		 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		 propertyElm.setTextContent(df.format(identity.getBirthDate()));
		 rootIdentity.appendChild(propertyElm);
        
		 /**
		  * TransformerFactory - new instance is used to store the data from DOM to XML
		  * StreamResult stores the data to XML path
		  */
		 
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer=null;
         try 
         {
        	transformer = transformerFactory.newTransformer();
        	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
         } catch (TransformerConfigurationException e) {
 			//  catch block
 			e.printStackTrace();
 		}
		DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult("C:\\Users\\Moi Laptop\\git\\Core\\xml\\identities.xml");
        try {
			transformer.transform(source, result);
			}
        catch (TransformerException e) 
        {
				//  catch block
        	e.printStackTrace();
		}
	}


	@Override
	public void update(Identity identity) throws IOException, ParseException 
	{
		/**
		 * NodeList is used to read all nodes in the Identity
		 * getLength method gets the entire length of the nodes 
		 */
		NodeList nodes = this.doc.getElementsByTagName("identity");
		int nodesSize = nodes.getLength();
		Scanner scanner = new Scanner(System.in);
		/**
		 *  for every found identity
		 *  test if the found node is really an Element
		 *  in the DOM implementation a Node can represent an Element, an
		 *  Attribute or a TextContent
		 *  To be sure that the found node is of type
		 *  "Element" using the instanceof operator
		 */
		Node node = null;
		boolean flag = false; 
		for (int i = 0; i < nodesSize; i++) 
		{
			node = nodes.item(i);
			
			if (node instanceof Element) 
			{
				/**
				 * cast the node into an Element, as we are sure it is an
				 * instance of Element
				 * property tagName is stored into NodeList using getElementsByTagName
				 */
				
				Element id = (Element) node;
				NodeList properties = id.getElementsByTagName("property");
				int length = properties.getLength();
				/**
				 *  The for loop goes through all the properties
				 *  The inner if condition searches if name is equal to guid
				 *  If it is true, then flag is stored as true. 
				 */
				for (int j = 0; j < length; j++) 
				{
					Node item = properties.item(j);
					
					if (item instanceof Element) 
					{
						Element propertyElt = (Element) item;
						if(propertyElt.getAttribute("name").equals("guid"))
						{	String TextContent = propertyElt.getTextContent();
							if(identity.getUid().equals(TextContent))
							{
								flag=true;
								break;
							}
							
						}
					}
				}
				
			}
			if(flag)
			{
				break;
			}
		}
		if (flag && node != null) 
		{
			/**
			 *  cast the node into an Element, as we are sure it is an
			 *  instance of Element
			 *  get the properties for the encountered identity
			 *  declare and initialize several variables on the same line
			 *  for every found property
			 *  we check that the found property is really an element
			 */
			Element id = (Element) node;
			NodeList properties = id.getElementsByTagName("property");
			int length = properties.getLength();

				for (int j = 0; j < length; j++) 
				{
					Node item = properties.item(j);
					
					
					if (item instanceof Element) 
					{
						Element propertyElt = (Element) item;
						 /**
						  * we need to store the right value in the right
						  * property so we use a switch-case structure
						  * to handle the 3 cases
						  */
						 
						switch (propertyElt.getAttribute("name")) 
						{
						case "displayName":
							propertyElt.setTextContent(identity.getDisplayName());
							break;
						case "guid":
							propertyElt.setTextContent(identity.getUid());
							break;
						case "email":
							propertyElt.setTextContent(identity.getEmailAddress());
							break;
						case "birthDate":
							try 
							{
								DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
								propertyElt.setTextContent(df.format(identity.getBirthDate()));
							} catch (Exception e) {
								e.printStackTrace();
								//exception handling
							}
							break;

						default:
							// the encountered property name is not expected
							// so we use the "default" case
							break;
						}

						 /**
						  * TransformerFactory - new instance is used to store the data from DOM to XML
						  * StreamResult stores the data to XML path
						  */
						
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
					    Transformer transformer=null;
					         try 
					         {
					        	transformer = transformerFactory.newTransformer();
					        	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
								transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
					         } catch (TransformerConfigurationException e)
					         {
					 			// catch block
					 			e.printStackTrace();
					 		 }
							DOMSource source = new DOMSource(doc);
					        StreamResult result = new StreamResult("C:\\Users\\Moi Laptop\\git\\Core\\xml\\identities.xml");
					        try {
								transformer.transform(source, result);
								}
					        catch (TransformerException e) 
					        {
									// catch block
					        	e.printStackTrace();
							}
					 
					}
				}
				
			}
		scanner.close();
		}
	/**
	 * 
	 * @param identity
	 * @throws IOException
	 */

	@Override
	public void delete(Identity identity) throws IOException 
	{
		// TODO Auto-generated method stub
				NodeList nodes = this.doc.getElementsByTagName("identity");
				int nodesSize = nodes.getLength();

				// for every found identity
				Node node = null;
				boolean flag = false; 
				for (int i = 0; i < nodesSize; i++) 
				{
					node = nodes.item(i);
					// test if the found node is really an Element
					// in the DOM implementation a Node can represent an Element, an
					// Attribute or a TextContent
					// so we have to be sure that the found node is of type
					// "Element" using the instanceof operator
					if (node instanceof Element) 
					{
						//Identity id = readOneIdentityFromXmlElement(node);
						// cast the node into an Element, as we are sure it is an
						// instance of Element
						Element id = (Element) node;

						// get the properties for the encountered identity
						NodeList properties = id.getElementsByTagName("property");
						int length = properties.getLength();

						// declare and initialize several variables on the same line
						
						// for every found property
						for (int j = 0; j < length; j++) 
						{
							Node item = properties.item(j);
							// we check that the found property is really an element
							// (see the explanation above)
							if (item instanceof Element) 
							{
								Element propertyElt = (Element) item;
								// we need to store the right value in the right
								// property so we use a switch-case structure
								// to handle the 3 cases
								if(propertyElt.getAttribute("name").equals("guid"))
								{	String TextContent = propertyElt.getTextContent();
									if(identity.getUid().equals(TextContent))
									{
										flag=true;
										break;
									}
									
								}
							}
						}
						
						}
					if(flag)
					{
						break;
					}
				}
				if (flag && node != null)
				{
					this.doc.getDocumentElement().removeChild(node);
					
				}
				 TransformerFactory transformerFactory = TransformerFactory.newInstance();
		         Transformer transformer=null;
		         try 
		         {
		        	transformer = transformerFactory.newTransformer();
		        	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		         } catch (TransformerConfigurationException e) {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 		}
				DOMSource source = new DOMSource(doc);
		        StreamResult result = new StreamResult("C:\\Users\\Moi Laptop\\git\\Core\\xml\\identities.xml");
		        try {
					transformer.transform(source, result);
					}
		        catch (TransformerException e) 
		        {
						// TODO Auto-generated catch block
		        	e.printStackTrace();
				}
	}

}
