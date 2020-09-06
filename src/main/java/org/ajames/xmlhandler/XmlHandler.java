package org.ajames.xmlhandler;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlHandler {
    
    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><data></data>";
        System.out.println(isXmlValid(xml));
    }

    private static boolean isXmlValid(String xml) {
        boolean xmlValid = true;
        try {
            validate(xml);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            xmlValid = false;
            System.err.print(ex);
        }
        return xmlValid;
    }

    private static void validate(String xml) throws SAXException, ParserConfigurationException, IOException {
        final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final Document document = builder.parse(new InputSource(new StringReader(xml)));
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        
        // Example 1: file is placed in current package:
        //final URL url = App.class.getResource("weather.xsd"); 
        
        // Example 2: file is placed in default package:
        final URL url = XmlHandler.class.getResource("/weather.xsd"); 
        
        factory.newSchema(url).newValidator().validate(new DOMSource(document));
    }
}
