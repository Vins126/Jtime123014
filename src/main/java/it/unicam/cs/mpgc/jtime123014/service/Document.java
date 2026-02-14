package it.unicam.cs.mpgc.jtime123014.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Rappresenta un documento generico, pronto per essere esportato.
 * <p>
 * Contiene i dati organizzati in sezioni, ma non dipende da un formato
 * specifico
 * (come PDF o XML). Questo permette di usare lo stesso modello per esportare
 * in formati diversi.
 */
public class Document {
    private final String title;
    private final List<ReportSection> sections;

    /**
     * Crea un nuovo documento vuoto.
     *
     * @param title il titolo del documento.
     */
    public Document(String title) {
        this.title = title;
        this.sections = new ArrayList<>();
    }

    /**
     * Crea un nuovo documento con sezioni preesistenti.
     *
     * @param title    il titolo del documento.
     * @param sections la lista delle sezioni.
     */
    public Document(String title, List<ReportSection> sections) {
        this.title = title;
        this.sections = new ArrayList<>(sections);
    }

    /**
     * Aggiunge una sezione al documento.
     *
     * @param section la sezione da aggiungere.
     */
    public void addSection(ReportSection section) {
        this.sections.add(section);
    }

    /** @return il titolo del documento. */
    public String getTitle() {
        return title;
    }

    /** @return la lista non modificabile delle sezioni. */
    public List<ReportSection> getSections() {
        return Collections.unmodifiableList(sections);
    }

}
