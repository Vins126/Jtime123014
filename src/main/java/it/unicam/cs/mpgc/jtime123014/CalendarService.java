package it.unicam.cs.mpgc.jtime123014;

import java.time.LocalDate;

public interface CalendarService {
    void updateRollingWindow(Calendar<?> calendar, LocalDate today, int buffer);

    boolean cleanupPastDays(Calendar<?> calendar, LocalDate today);
}
