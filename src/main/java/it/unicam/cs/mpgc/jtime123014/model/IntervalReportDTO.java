package it.unicam.cs.mpgc.jtime123014.model;

import java.time.LocalDate;
import java.util.Map;

/**
 * Oggetto che contiene i dati riassuntivi di un intervallo di tempo per il
 * report.
 * <p>
 * Raccoglie informazioni come il totale delle ore lavorate, la velocità media
 * e come è stato distribuito il tempo tra le varie priorità.
 */
public class IntervalReportDTO {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final long totalMinutesWorked;
    private final double averageVelocity; // Tasks per day
    private final double averageBufferSaturation;
    private final Map<Priority, Long> timeDistributionByPriority;

    /**
     * Costruisce un nuovo DTO per il report di intervallo.
     *
     * @param startDate                  la data di inizio.
     * @param endDate                    la data di fine.
     * @param totalMinutesWorked         il totale dei minuti lavorati.
     * @param averageVelocity            la velocità media (task/giorno).
     * @param averageBufferSaturation    la saturazione media del buffer.
     * @param timeDistributionByPriority la distribuzione del tempo per priorità.
     */
    public IntervalReportDTO(LocalDate startDate, LocalDate endDate, long totalMinutesWorked,
            double averageVelocity, double averageBufferSaturation,
            Map<Priority, Long> timeDistributionByPriority) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalMinutesWorked = totalMinutesWorked;
        this.averageVelocity = averageVelocity;
        this.averageBufferSaturation = averageBufferSaturation;
        this.timeDistributionByPriority = timeDistributionByPriority;
    }

    // Getters

    /** @return la data di inizio dell'intervallo. */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** @return la data di fine dell'intervallo. */
    public LocalDate getEndDate() {
        return endDate;
    }

    /** @return il numero totale di minuti lavorati nell'intervallo. */
    public long getTotalMinutesWorked() {
        return totalMinutesWorked;
    }

    /** @return la velocità media di completamento (task per giorno). */
    public double getAverageVelocity() {
        return averageVelocity;
    }

    /** @return la percentuale media di utilizzo del buffer (0.0 - 1.0). */
    public double getAverageBufferSaturation() {
        return averageBufferSaturation;
    }

    /** @return una mappa con i minuti lavorati per ogni livello di priorità. */
    public Map<Priority, Long> getTimeDistributionByPriority() {
        return timeDistributionByPriority;
    }
}
