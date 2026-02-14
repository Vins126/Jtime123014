package it.unicam.cs.mpgc.jtime123014.model;

import java.time.LocalDate;
import java.util.Map;

/**
 * Oggetto che contiene tutti i dati riassuntivi di un progetto per la
 * generazione del report.
 * <p>
 * Serve solo per trasportare i dati (come nome, percentuali, stime) dal sistema
 * al generatore di report, senza contenere logica complessa.
 */
public class ProjectReportDTO {
    private final String projectId;
    private final String projectName;
    private final double completionPercentage;
    private final long totalEstimatedTime;
    private final long totalActualTime;
    private final long totalDelta; // Actual - Estimated
    private final Map<String, Integer> taskStatusCounts; // e.g., "COMPLETED" -> 5
    private final Map<String, java.util.List<String>> taskListsByStatus; // e.g., "COMPLETED" -> ["Task A", "Task B"]
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

    // Getters

    /** @return l'ID univoco del progetto. */
    public String getProjectId() {
        return projectId;
    }

    /** @return il nome del progetto. */
    public String getProjectName() {
        return projectName;
    }

    /** @return la percentuale di completamento globale. */
    public double getCompletionPercentage() {
        return completionPercentage;
    }

    /** @return la somma delle stime di tempo di tutte le task. */
    public long getTotalEstimatedTime() {
        return totalEstimatedTime;
    }

    /** @return la somma del tempo effettivamente impiegato per le task. */
    public long getTotalActualTime() {
        return totalActualTime;
    }

    /** @return la differenza totale tra tempo effettivo e stimato (+/-). */
    public long getTotalDelta() {
        return totalDelta;
    }

    /**
     * @return una mappa che associa ogni stato al numero di task in quello stato.
     */
    public Map<String, Integer> getTaskStatusCounts() {
        return taskStatusCounts;
    }

    /** @return una mappa che associa ogni stato alla lista dei nomi delle task. */
    public Map<String, java.util.List<String>> getTaskListsByStatus() {
        return taskListsByStatus;
    }

    /** @return una mappa che mostra quante task ci sono per ogni priorità. */
    public Map<Priority, Integer> getPriorityDistribution() {
        return priorityDistribution;
    }

    /** @return la data stimata di completamento del progetto. */
    public LocalDate getEstimatedEndDate() {
        return estimatedEndDate;
    }
}
