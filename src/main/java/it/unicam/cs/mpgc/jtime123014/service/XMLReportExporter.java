package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Esporta report in formato XML.
 */
public class XMLReportExporter implements ReportExporter {

    /**
     * Costruttore di default.
     */
    public XMLReportExporter() {
    }

    /**
     * Esporta il documento in un file XML.
     *
     * @param document il documento da esportare.
     * @param filePath il percorso del file di destinazione.
     * @return true se l'esportazione ha successo, false altrimenti.
     */
    @Override
    public boolean export(Document document, String filePath) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = builder.newDocument();

            // Root
            Element root = xmlDoc.createElement("Report");
            xmlDoc.appendChild(root);

            appendTitle(xmlDoc, root, document.getTitle());
            appendSections(xmlDoc, root, document.getSections());

            writeToFile(xmlDoc, filePath);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Aggiunge l'elemento titolo al documento XML.
     *
     * @param xmlDoc    il documento XML W3C.
     * @param root      l'elemento radice.
     * @param titleText il testo del titolo.
     */
    private void appendTitle(org.w3c.dom.Document xmlDoc, Element root, String titleText) {
        Element title = xmlDoc.createElement("Title");
        title.setTextContent(titleText);
        root.appendChild(title);
    }

    /**
     * Aggiunge le sezioni del report al documento XML.
     *
     * @param xmlDoc   il documento XML W3C.
     * @param root     l'elemento radice.
     * @param sections la lista delle sezioni da aggiungere.
     */
    private void appendSections(org.w3c.dom.Document xmlDoc, Element root, java.util.List<ReportSection> sections) {
        for (ReportSection section : sections) {
            Element sectionElem = xmlDoc.createElement("Section");
            sectionElem.setAttribute("title", section.getSectionTitle());

            for (String line : section.getContent()) {
                Element contentElem = xmlDoc.createElement("Content");
                contentElem.setTextContent(line);
                sectionElem.appendChild(contentElem);
            }
            root.appendChild(sectionElem);
        }
    }

    /**
     * Scrive il documento XML su file.
     *
     * @param xmlDoc   il documento XML in memoria.
     * @param filePath il percorso file di output.
     * @throws TransformerException se c'è un errore durante la scrittura.
     */
    private void writeToFile(org.w3c.dom.Document xmlDoc, String filePath) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(filePath)));
    }
}
