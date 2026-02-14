package it.unicam.cs.mpgc.jtime123014.model;

import java.time.LocalDate;
import java.util.Map;

import lombok.Getter;

/**
 * Oggetto che contiene i dati riassuntivi di un intervallo di tempo per il
 * report.
 * 
 * Raccoglie informazioni come il totale delle ore lavorate, la velocità media
 * e come è stato distribuito il tempo tra le varie priorità.
 */
@Getter
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

}
