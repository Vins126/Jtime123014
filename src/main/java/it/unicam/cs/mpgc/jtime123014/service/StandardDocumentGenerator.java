package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.util.Map;

/**
 * Generatore di documenti standard.
 * <p>
 * Crea un documento organizzato in tre sezioni principali: panoramica, analisi
 * dei tempi
 * e dettagli sullo stato delle attività.
 */
public class StandardDocumentGenerator extends AbstractDocumentGenerator {

    /**
     * Trasforma un report di progetto in un documento formattato.
     *
     * @param report il DTO con i dati del report.
     * @return il documento strutturato in sezioni.
     */
    @Override
    public Document transform(ProjectReportDTO report) {
        Document doc = new Document("Project Report: " + report.getProjectName());

        doc.addSection(createOverviewSection(report));
        doc.addSection(createTimeAnalysisSection(report));
        doc.addSection(createTaskStatusSection(report));

        return doc;
    }

    /**
     * Crea la sezione di panoramica con i dati principali del progetto.
     *
     * @param report il report di progetto.
     * @return la nuova sezione Overview.
     */
    private ReportSection createOverviewSection(ProjectReportDTO report) {
        ReportSection overview = createSection("Overview");
        addKeyValue(overview, "Project ID", report.getProjectId());
        addKeyValue(overview, "Completion", formatPercentage(report.getCompletionPercentage()));
        addKeyValue(overview, "Estimated End Date", report.getEstimatedEndDate());
        return overview;
    }

    /**
     * Crea la sezione di analisi temporale (stima vs tempo effettivo).
     *
     * @param report il report di progetto.
     * @return la nuova sezione Time Analysis.
     */
    private ReportSection createTimeAnalysisSection(ProjectReportDTO report) {
        ReportSection time = createSection("Time Analysis");
        addKeyValue(time, "Total Estimated", report.getTotalEstimatedTime());
        addKeyValue(time, "Total Actual", report.getTotalActualTime());
        addKeyValue(time, "Total Delta", report.getTotalDelta());
        return time;
    }

    /**
     * Crea la sezione con i dettagli sullo stato delle task.
     * Include conteggi e liste dettagliate.
     *
     * @param report il report di progetto.
     * @return la nuova sezione Task Status Details.
     */
    private ReportSection createTaskStatusSection(ProjectReportDTO report) {
        ReportSection status = createSection("Task Status Details");

        // Conteggi riepilogativi
        status.addContent("--- Summary ---");
        for (Map.Entry<String, Integer> entry : report.getTaskStatusCounts().entrySet()) {
            addKeyValue(status, entry.getKey(), entry.getValue());
        }

        // Liste dettagliate
        status.addContent("--- Detailed Lists ---");
        for (Map.Entry<String, java.util.List<String>> entry : report.getTaskListsByStatus().entrySet()) {
            status.addContent("Status: " + entry.getKey());
            for (String taskStr : entry.getValue()) {
                status.addContent(" - " + taskStr);
            }
        }
        return status;
    }

    /**
     * Trasforma un report di intervallo in un documento.
     *
     * @param report il report dell'intervallo.
     * @return il documento parziale (solo statistiche per ora).
     */
    @Override
    public Document transform(IntervalReportDTO report) {
        Document doc = new Document("Interval Report (" + report.getStartDate() + " - " + report.getEndDate() + ")");

        ReportSection stats = createSection("Statistics");
        addKeyValue(stats, "Total Minutes Worked", report.getTotalMinutesWorked());
        addKeyValue(stats, "Average Velocity (Tasks/Day)", formatDecimal(report.getAverageVelocity()));
        addKeyValue(stats, "Buffer Saturation", formatPercentage(report.getAverageBufferSaturation()));
        doc.addSection(stats);

        return doc;
    }
}
