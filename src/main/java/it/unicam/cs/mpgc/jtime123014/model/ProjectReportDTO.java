package it.unicam.cs.mpgc.jtime123014.model;

import java.time.LocalDate;
import java.util.Map;

import lombok.Getter;

/**
 * Oggetto che contiene tutti i dati riassuntivi di un progetto per la
 * generazione del report.
 * 
 * Serve solo per trasportare i dati (come nome, percentuali, stime) dal sistema
 * al generatore di report, senza contenere logica complessa.
 */
@Getter
public class ProjectReportDTO {
    private final String projectId;
    private final String projectName;
    private final double completionPercentage;
    private final long totalEstimatedTime;
    private final long totalActualTime;
    private final long totalDelta;
    private final Map<String, Integer> taskStatusCounts;
    private final Map<String, java.util.List<String>> taskListsByStatus;
    private final Map<Priority, Integer> priorityDistribution;
    private final LocalDate estimatedEndDate;

    /**
     * Costruisce un nuovo DTO per il report di progetto.
     *
     * @param projectId            l'ID del progetto.
     * @param projectName          il nome del progetto.
     * @param completionPercentage la percentuale di completamento (0.0 - 100.0).
     * @param totalEstimatedTime   il tempo totale stimato.
     * @param totalActualTime      il tempo totale effettivo.
     * @param totalDelta           la differenza totale (Actual - Estimated).
     * @param taskStatusCounts     il conteggio delle task per stato.
     * @param taskListsByStatus    le liste di task raggruppate per stato.
     * @param priorityDistribution la distribuzione delle task per priorità.
     * @param estimatedEndDate     la data prevista di fine progetto.
     */
    public ProjectReportDTO(String projectId, String projectName, double completionPercentage,
            long totalEstimatedTime, long totalActualTime, long totalDelta,
            Map<String, Integer> taskStatusCounts,
            Map<String, java.util.List<String>> taskListsByStatus,
            Map<Priority, Integer> priorityDistribution,
            LocalDate estimatedEndDate) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.completionPercentage = completionPercentage;
        this.totalEstimatedTime = totalEstimatedTime;
        this.totalActualTime = totalActualTime;
        this.totalDelta = totalDelta;
        this.taskStatusCounts = taskStatusCounts;
        this.taskListsByStatus = taskListsByStatus;
        this.priorityDistribution = priorityDistribution;
        this.estimatedEndDate = estimatedEndDate;
    }

}
