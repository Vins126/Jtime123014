package it.unicam.cs.mpgc.jtime123014.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Una sezione del documento (es. "Riepilogo", "Dettagli").
 * 
 * Ogni sezione ha un titolo e una serie di righe di testo che contengono le
 * informazioni.
 */
public class ReportSection {
    private final String sectionTitle;
    private final List<String> content;

    /**
     * Crea una nuova sezione vuota.
     *
     * @param sectionTitle il titolo della sezione.
     */
    public ReportSection(String sectionTitle) {
        this.sectionTitle = sectionTitle;
        this.content = new ArrayList<>();
    }

    /**
     * Crea una nuova sezione con contenuto preesistente.
     *
     * @param sectionTitle il titolo della sezione.
     * @param content      la lista delle righe di contenuto.
     */
    public ReportSection(String sectionTitle, List<String> content) {
        this.sectionTitle = sectionTitle;
        this.content = new ArrayList<>(content);
    }

    /**
     * Aggiunge una riga di testo alla sezione.
     *
     * @param line la riga da aggiungere.
     */
    public void addContent(String line) {
        this.content.add(line);
    }

    /** @return il titolo della sezione. */
    public String getSectionTitle() {
        return sectionTitle;
    }

    /** @return la lista non modificabile delle righe di contenuto. */
    public List<String> getContent() {
        return Collections.unmodifiableList(content);
    }
}
