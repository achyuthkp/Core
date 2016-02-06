package fr.tbr.iamcore.tests.services.dao.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import iamcore.datamodel.Identity;
import iamcore.services.dao.IdentityXmlDAO;

//Serialization: memory to persistable representation
//Deserialization : persistable representation to memory
public class TestXmlDeSerialization {

	public static void main(String[] args) throws Exception {
		//testXmlDeserialization();
		
		IdentityXmlDAO xmlDAO = new IdentityXmlDAO();
		Identity criteria = new Identity("Quen", "cle", null);
		System.out.println(xmlDAO.search(criteria));
		

	}

	private static void testXmlDeserialization() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse("F:\\Work\\Dev\\Git\\Epita\\Fundamentals\\2015s2f2\\IamCore\\xml\\identities.xml");

		NodeList nodes = doc.getElementsByTagName("identity");
		int nodesSize = nodes.getLength();
		for (int i = 0; i < nodesSize; i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Element identity = (Element) node;
				NodeList properties = identity.getElementsByTagName("property");
				int length = properties.getLength();
				for (int j = 0; j < length; j++) {
					Node item = properties.item(j);
					if (item instanceof Element) {
						Element propertyElt = (Element) item;
						System.out.println(propertyElt.getAttribute("name") + " : "+ item.getTextContent());
					}
				}
				System.out.println("-------------");

			}
		}
	}
}
