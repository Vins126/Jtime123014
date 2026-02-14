package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.Status;
import it.unicam.cs.mpgc.jtime123014.model.Task;
import it.unicam.cs.mpgc.jtime123014.util.ServiceConstants;

import java.time.LocalDate;
import java.util.List;

/**
 * Si occupa di stimare il progresso del progetto e la data di fine prevista.
 */
public class ProgressEstimator {

    /**
     * Crea un nuovo stimatore di progresso.
     */
    public ProgressEstimator() {
    }

    /**
     * Calcola la percentuale di completamento basata sul tempo stimato.
     *
     * @param tasks         la lista delle task.
     * @param totalEstimate la stima totale in minuti.
     * @return la percentuale di completamento (0.0 - 100.0).
     */
    public double calculateCompletion(List<Task<?>> tasks, long totalEstimate) {
        if (totalEstimate == 0)
            return 0;
        long completedEst = tasks.stream()
                .filter(t -> t.getStatus() == Status.COMPLETED)
                .mapToLong(Task::getDurationEstimate)
                .sum();
        return ((double) completedEst / totalEstimate) * 100.0;
    }

    /**
     * Stima la data di fine prevista in base alla velocità attuale.
     *
     * @param tasks la lista delle task da analizzare.
     * @return la data di fine stimata.
     */
    public LocalDate estimateEndDate(List<Task<?>> tasks) {
        double avgDelta = calculateAverageDelta(tasks);
        long remainingWork = calculateRemainingWork(tasks, avgDelta);

        long daysRemaining = remainingWork / ServiceConstants.WORK_MINUTES_PER_DAY;
        return LocalDate.now().plusDays(Math.max(0, daysRemaining));
    }

    /**
     * Calcola lo scostamento medio (delta) delle task completate.
     *
     * @param tasks la lista delle task.
     * @return il delta medio (positivo se in ritardo, negativo se in anticipo).
     */
    private double calculateAverageDelta(List<Task<?>> tasks) {
        return tasks.stream()
                .filter(t -> t.getStatus() == Status.COMPLETED)
                .mapToLong(t -> {
                    try {
                        return t.calculateDelta();
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .average().orElse(0);
    }

    /**
     * Calcola il lavoro rimanente stimato, considerando la velocità attuale (delta
     * medio).
     *
     * @param tasks    la lista delle task.
     * @param avgDelta il delta medio calcolato sulle task completate.
     * @return i minuti di lavoro stimati rimanenti.
     */
    private long calculateRemainingWork(List<Task<?>> tasks, double avgDelta) {
        long pendingEst = tasks.stream()
                .filter(t -> t.getStatus() != Status.COMPLETED)
                .mapToLong(Task::getTimeConsuming).sum();
        long pendingCount = tasks.stream()
                .filter(t -> t.getStatus() != Status.COMPLETED).count();

        long predictedExtra = (long) (pendingCount * avgDelta);
        return pendingEst + predictedExtra;
    }
}
