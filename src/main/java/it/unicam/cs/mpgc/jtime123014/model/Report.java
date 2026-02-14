package it.unicam.cs.mpgc.jtime123014.model;

import it.unicam.cs.mpgc.jtime123014.service.Document;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Rappresenta un report (resoconto) che è stato creato dall'applicazione.
 * 
 * Contiene il titolo, il file fisico salvato nel computer e, se disponibile,
 * il documento con i dati effettivi.
 * Viene usato per mostrare la lista dei report salvati e per aprirli.
 */
public class Report {

    private final String title;
    private final File file;
    private Document document; // può essere null se caricato solo da file

    /**
     * Costruttore per report appena generato (con Document).
     *
     * @param title    il titolo del report.
     * @param document il documento strutturato con i dati.
     * @param file     il file su disco dove è salvato il report.
     */
    public Report(String title, Document document, File file) {
        this.title = title;
        this.document = document;
        this.file = file;
    }

    /**
     * Costruttore per report caricato dal filesystem (senza Document).
     *
     * @param title il titolo del report.
     * @param file  il file su disco.
     */
    public Report(String title, File file) {
        this.title = title;
        this.document = null;
        this.file = file;
    }

    /** @return il titolo del report. */
    public String getTitle() {
        return title;
    }

    /** @return il file su disco associato a questo report. */
    public File getFile() {
        return file;
    }

    /** @return il documento strutturato contenente i dati (può essere null). */
    public Document getDocument() {
        return document;
    }

    /**
     * Imposta il documento strutturato.
     *
     * @param document il nuovo documento.
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return title;
    }
}
