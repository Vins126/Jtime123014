package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Si occupa di generare report relativi a uno specifico intervallo di tempo.
 */
public class IntervalAnalyzer {

    /**
     * Crea un nuovo analizzatore di intervalli.
     */
    public IntervalAnalyzer() {
    }

    /**
     * Genera un report contenente le statistiche per l'intervallo specificato.
     *
     * @param calendar il calendario da analizzare.
     * @param start    la data di inizio.
     * @param end      la data di fine.
     * @return il DTO con le statistiche calcolate.
     */
    public IntervalReportDTO generateReport(Calendar<?> calendar, LocalDate start, LocalDate end) {
        long totalMinutes = 0;
        long completedTaskCount = 0;
        Map<Priority, Long> prioMap = new HashMap<>();

        List<? extends Day<?>> days = filterDays(calendar, start, end);

        double totalSaturation = 0;
        int dayBufferCount = 0;

        for (Day<?> d : days) {
            // Calculate saturation
            if (d.getBuffer() > 0) {
                totalSaturation += (double) (d.getBuffer() - d.getFreeBuffer()) / d.getBuffer();
                dayBufferCount++;
            }

            // Calculate completed minutes
            for (Task<?> t : d.getTasks()) {
                if (t.getStatus() == Status.COMPLETED && t.getDurationActual() != null) {
                    totalMinutes += t.getDurationActual();
                    completedTaskCount++;
                }
            }
        }

        double velocity = days.isEmpty() ? 0 : (double) completedTaskCount / days.size();
        double avgSat = dayBufferCount == 0 ? 0 : (totalSaturation / dayBufferCount) * 100.0;

        return new IntervalReportDTO(start, end, totalMinutes, velocity, avgSat, prioMap);
    }

    private List<? extends Day<?>> filterDays(Calendar<?> calendar, LocalDate start, LocalDate end) {
        return calendar.getDays().stream()
                .filter(d -> d.getId() instanceof LocalDate date
                        && !date.isBefore(start) && !date.isAfter(end))
                .collect(Collectors.toList());
    }
}
