package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.time.LocalDate;

/**
 * Interfaccia per creare i report.
 * Serve per calcolare le statistiche dei progetti o degli intervalli di tempo.
 */
public interface ReportService {
    /**
     * Genera un report dettagliato per un singolo progetto.
     *
     * @param project il progetto da analizzare.
     * @return il DTO con i dati del progetto.
     */
    ProjectReportDTO generateProjectReport(Project<?> project);

    /**
     * Genera un report aggregato per un intervallo di tempo.
     *
     * @param start la data di inizio.
     * @param end   la data di fine.
     * @return il DTO con i dati dell'intervallo.
     */
    IntervalReportDTO generateIntervalReport(LocalDate start, LocalDate end);
}
