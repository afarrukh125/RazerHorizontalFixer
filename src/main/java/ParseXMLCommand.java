import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

@Command(name = "parse")
public class ParseXMLCommand implements Runnable {
    @Option(name = "--file")
    private String fileName;

    private static void writeXmlDocumentToXmlFile(Document xmlDocument, String fileName) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            fileName = fileName.replace(".xml", "_updated.xml");
            FileOutputStream outStream = new FileOutputStream(fileName);
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(outStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        File file = determineFile(fileName);

        try {
            Document document = getDocument(file);

            NodeList xNodes = document.getElementsByTagName("X");
            for (int i = 0; i < xNodes.getLength(); i++) {
                Node targetNode = xNodes.item(i).getChildNodes().item(0);
                int currentValue = Integer.parseInt(targetNode.getNodeValue());
                targetNode.setNodeValue(String.valueOf(currentValue * 2));
            }

            writeXmlDocumentToXmlFile(document, file.getName());
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private File determineFile(String fileName) {
        if (fileName != null)
            return new File(fileName);
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            if (!selectedFile.getName().contains(".xml")) {
                JOptionPane.showMessageDialog(null, "Please select a razer macro exported as an XML.");
                System.exit(0);
            }
            return selectedFile;
        }
        throw new IllegalStateException("No file selected");
    }

    private Document getDocument(File file) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder xmlStringBuilder = new StringBuilder();
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null) {
            xmlStringBuilder.append(line);
        }

        ByteArrayInputStream input = new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));
        return builder.parse(input);
    }
}
