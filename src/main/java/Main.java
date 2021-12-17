import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(args[0]);
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder xmlStringBuilder = new StringBuilder();
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null) {
            xmlStringBuilder.append(line);
        }

        ByteArrayInputStream input = new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));
        Document document = builder.parse(input);

        NodeList xNodes = document.getElementsByTagName("X");
        for(int i = 0; i< xNodes.getLength(); i++) {
            Node targetNode = xNodes.item(i).getChildNodes().item(0);
            int currentValue = Integer.parseInt(targetNode.getNodeValue());
            targetNode.setNodeValue(String.valueOf(currentValue*2));
        }

        writeXmlDocumentToXmlFile(document, file.getName());
    }


    private static void writeXmlDocumentToXmlFile(Document xmlDocument, String fileName)
    {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();

            //Uncomment if you do not require XML declaration
            //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            //Write XML to file
            FileOutputStream outStream = new FileOutputStream(new File(fileName));

            transformer.transform(new DOMSource(xmlDocument), new StreamResult(outStream));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
