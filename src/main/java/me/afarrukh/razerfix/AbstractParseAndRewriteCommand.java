package me.afarrukh.razerfix;

import com.github.rvesse.airline.annotations.Option;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractParseAndRewriteCommand implements Runnable {
    @Option(name = "--file")
    protected String fileName;

    @Option(name = "--outputDir")
    protected String outputDir;

    @Override
    public void run() {
        try {
            File file = determineFile(fileName);
            Document document = getDocument(file);
            execute(document);
            writeXmlDocumentToXmlFile(document, file.getName());
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

    }

    public abstract void execute(Document document);

    File determineFile(String fileName) throws IOException {
        if (fileName != null)
            return new File(fileName);
        JFileChooser chooser = new JFileChooser();
        Path desiredPath;
        String userDir = System.getProperty("user.dir");
        if(outputDir == null)
            desiredPath = Path.of(System.getProperty("user.dir"));
        else {
            Path outputDirPath = Path.of(userDir, outputDir);
            if(!Files.exists(outputDirPath))
                Files.createDirectory(outputDirPath);
            desiredPath = outputDirPath;
        }
        File startLocation = desiredPath.toFile();
        chooser.setCurrentDirectory(startLocation);
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("XML", "xml");
        chooser.setFileFilter(fileNameExtensionFilter);
        chooser.addChoosableFileFilter(fileNameExtensionFilter);
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

    void writeXmlDocumentToXmlFile(Document xmlDocument, String fileName) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        fileName = fileName.replace(".xml", "_" +this.getClass().getSimpleName() + ".xml");
        Path path = generatePath(fileName);
        try {
            transformer = tf.newTransformer();
            FileOutputStream outStream = new FileOutputStream(path.toFile());
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(outStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Path generatePath(String fileName) {
        if (outputDir != null)
            return Path.of(outputDir, fileName);
        else
            return Path.of(fileName);
    }

    Document getDocument(File file) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder xmlStringBuilder = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null) {
            xmlStringBuilder.append(line);
        }

        ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        return builder.parse(input);
    }
}
