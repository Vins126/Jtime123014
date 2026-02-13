package it.unicam.cs.mpgc.jtime123014.report;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import java.io.File;

public class XMLReportExporter implements ReportExporter {

    @Override
    public boolean export(Document document, String filePath) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = builder.newDocument();

            // Root
            Element root = xmlDoc.createElement("Report");
            xmlDoc.appendChild(root);

            // Title
            Element title = xmlDoc.createElement("Title");
            title.setTextContent(document.getTitle());
            root.appendChild(title);

            // Sections
            for (ReportSection section : document.getSections()) {
                Element sectionElem = xmlDoc.createElement("Section");
                sectionElem.setAttribute("title", section.getSectionTitle());

                for (String line : section.getContent()) {
                    Element contentElem = xmlDoc.createElement("Content");
                    contentElem.setTextContent(line);
                    sectionElem.appendChild(contentElem);
                }
                root.appendChild(sectionElem);
            }

            // Write to File
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(filePath)));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
