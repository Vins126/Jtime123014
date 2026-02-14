package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

/**
 * Interfaccia per la creazione del contenuto dei documenti.
 * 
 * Trasforma i dati grezzi (DTO) dei report in una struttura a sezioni
 * (Titoli, Paragrafi, Tabelle) pronta per essere esportata (es. in XML).
 */
public interface DocumentGenerator {
    /**
     * Converte un DTO di progetto in un documento strutturato.
     *
     * @param report il report del progetto.
     * @return il documento generato.
     */
    Document transform(ProjectReportDTO report);

    /**
     * Converte un DTO di intervallo in un documento strutturato.
     *
     * @param report il report dell'intervallo.
     * @return il documento generato.
     */
    Document transform(IntervalReportDTO report);
}
