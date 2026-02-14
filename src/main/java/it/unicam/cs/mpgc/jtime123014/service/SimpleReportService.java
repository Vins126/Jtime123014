package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.util.ServiceConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementazione concreta di {@link ReportService}.
 * 
 * Questo servizio coordina il calcolo delle statistiche utilizzando diversi
 * componenti per analizzare task, stimare progressi e generare report
 * temporali.
 */
public class SimpleReportService implements ReportService {

    private final Calendar<?> calendar;
    private final TaskStatisticsCalculator statsCalculator = new TaskStatisticsCalculator();
    private final ProgressEstimator progressEstimator = new ProgressEstimator();
    private final IntervalAnalyzer intervalAnalyzer = new IntervalAnalyzer();

    public SimpleReportService(Calendar<?> calendar) {
        this.calendar = calendar;
    }

    // ── generateProjectReport ────────────────────────────────────────────

    /**
     * Genera un report dettagliato per un singolo progetto.
     * Calcola statistiche, avanzamento e stima la data di fine.
     *
     * @param project il progetto da analizzare.
     * @return un oggetto DTO contenente tutti i dati del report.
     */
    @Override
    public ProjectReportDTO generateProjectReport(Project<?> project) {
        List<Task<?>> tasks = project.getTasks();

        TaskStatisticsCalculator.TaskStats stats = statsCalculator.collect(tasks);

        Map<Priority, Integer> prioDist = new EnumMap<>(Priority.class);
        prioDist.put(project.getPriority(), tasks.size());

        double completion = progressEstimator.calculateCompletion(tasks, stats.totalEstimate());
        LocalDate estimatedEnd = progressEstimator.estimateEndDate(tasks);

        return new ProjectReportDTO(
                project.getId().toString(),
                project.getName(),
                completion,
                stats.totalEstimate(),
                stats.totalActual(),
                stats.totalDelta(),
                stats.statusCounts(),
                stats.taskListsByStatus(),
                prioDist,
                estimatedEnd);
    }

    // ── generateIntervalReport ───────────────────────────────────────────

    /**
     * Genera un report aggregato per un intervallo di date.
     *
     * @param start data di inizio.
     * @param end   data di fine.
     * @return il report dell'intervallo.
     */
    @Override
    public IntervalReportDTO generateIntervalReport(LocalDate start, LocalDate end) {
        return intervalAnalyzer.generateReport(calendar, start, end);
    }
}
