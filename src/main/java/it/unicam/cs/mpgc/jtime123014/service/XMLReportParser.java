package it.unicam.cs.mpgc.jtime123014.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser per file XML di report.
 * 
 * Responsabilità unica: leggere un file XML di report e convertirlo
 * in un oggetto {@link Document}.
 */
public class XMLReportParser {

    /**
     * Costruttore di default.
     */
    public XMLReportParser() {
    }

    /**
     * Parsa un file XML di report in un oggetto {@link Document}.
     *
     * @param file il file XML da leggere.
     * @return il {@link Document} risultante, oppure un Document vuoto in caso di
     *         errore.
     */
    public Document parse(File file) {
        try {
            javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder builder = dbf.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = builder.parse(file);
            xmlDoc.getDocumentElement().normalize();

            String title = "";
            org.w3c.dom.NodeList titleNodes = xmlDoc.getElementsByTagName("Title");
            if (titleNodes.getLength() > 0) {
                title = titleNodes.item(0).getTextContent();
            }

            List<ReportSection> sections = new ArrayList<>();
            org.w3c.dom.NodeList sectionNodes = xmlDoc.getElementsByTagName("Section");
            for (int i = 0; i < sectionNodes.getLength(); i++) {
                org.w3c.dom.Element sectionElem = (org.w3c.dom.Element) sectionNodes.item(i);
                String sectionTitle = sectionElem.getAttribute("title");

                List<String> content = new ArrayList<>();
                org.w3c.dom.NodeList contentNodes = sectionElem.getElementsByTagName("Content");
                for (int j = 0; j < contentNodes.getLength(); j++) {
                    content.add(contentNodes.item(j).getTextContent());
                }
                sections.add(new ReportSection(sectionTitle, content));
            }

            return new Document(title, sections);
        } catch (Exception e) {
            System.err.println("Errore nel parsing del file XML: " + file.getName());
            e.printStackTrace();
            return new Document(file.getName(), List.of());
        }
    }
}
