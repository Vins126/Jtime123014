package it.unicam.cs.mpgc.jtime123014.report.dto;

import it.unicam.cs.mpgc.jtime123014.model.Priority;
import java.time.LocalDate;
import java.util.Map;

public class IntervalReportDTO {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final long totalMinutesWorked;
    private final double averageVelocity; // Tasks per day
    private final double averageBufferSaturation;
    private final Map<Priority, Long> timeDistributionByPriority;

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
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getTotalMinutesWorked() {
        return totalMinutesWorked;
    }

    public double getAverageVelocity() {
        return averageVelocity;
    }

    public double getAverageBufferSaturation() {
        return averageBufferSaturation;
    }

    public Map<Priority, Long> getTimeDistributionByPriority() {
        return timeDistributionByPriority;
    }
}
