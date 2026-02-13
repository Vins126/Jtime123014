package it.unicam.cs.mpgc.jtime123014.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Rappresenta una sezione del documento (es. "Panoramica", "Statistiche").
 * Contiene un titolo e una lista di contenuti (linee di testo).
 */
public class ReportSection {
    private final String sectionTitle;
    private final List<String> content;

    public ReportSection(String sectionTitle) {
        this.sectionTitle = sectionTitle;
        this.content = new ArrayList<>();
    }

    public void addContent(String line) {
        this.content.add(line);
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public List<String> getContent() {
        return Collections.unmodifiableList(content);
    }
}
