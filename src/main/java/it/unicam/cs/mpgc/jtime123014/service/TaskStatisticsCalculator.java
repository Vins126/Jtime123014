package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.Status;
import it.unicam.cs.mpgc.jtime123014.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Si occupa di calcolare le statistiche aggregate partendo da una lista di
 * task.
 */
public class TaskStatisticsCalculator {

    public record TaskStats(
            long totalEstimate,
            long totalActual,
            long totalDelta,
            Map<String, Integer> statusCounts,
            Map<String, List<String>> taskListsByStatus) {
    }

    /**
     * Raccoglie le statistiche da una lista di task.
     * Calcola totali, delta e raggruppa le task per stato.
     *
     * @param tasks la lista di task da analizzare.
     * @return un oggetto TaskStats con i risultati.
     */
    public TaskStats collect(List<Task<?>> tasks) {
        long totalEst = 0;
        long totalAct = 0;
        long totalDelta = 0;
        Map<String, Integer> statusCounts = new HashMap<>();
        Map<String, List<String>> taskListsByStatus = new HashMap<>();

        for (Task<?> t : tasks) {
            totalEst += t.getDurationEstimate();
            String statusName = t.getStatus().name();
            statusCounts.merge(statusName, 1, Integer::sum);

            taskListsByStatus.computeIfAbsent(statusName, k -> new ArrayList<>());
            taskListsByStatus.get(statusName).add(formatTaskDetail(t));

            if (t.getStatus() == Status.COMPLETED) {
                if (t.getDurationActual() != null)
                    totalAct += t.getDurationActual();
                try {
                    totalDelta += t.calculateDelta();
                } catch (Exception ignored) {
                }
            }
        }
        return new TaskStats(totalEst, totalAct, totalDelta, statusCounts, taskListsByStatus);
    }

    /**
     * Formatta una stringa con i dettagli essenziali della task.
     *
     * @param t la task da formattare.
     * @return stringa nel formato "Nome [Est: ... Act: ...]".
     */
    private String formatTaskDetail(Task<?> t) {
        if (t.getStatus() == Status.COMPLETED) {
            return t.getName() + " [Est: " + t.getDurationEstimate() + "m, Act: "
                    + (t.getDurationActual() != null ? t.getDurationActual() : "N/A") + "m]";
        }
        return t.getName() + " [Est: " + t.getDurationEstimate() + "m]";
    }
}
