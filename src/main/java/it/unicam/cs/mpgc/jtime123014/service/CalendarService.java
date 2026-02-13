package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.Calendar;
import java.time.LocalDate;

public interface CalendarService {
    void updateRollingWindow(Calendar<?> calendar, LocalDate today, int buffer);

    boolean cleanupPastDays(Calendar<?> calendar, LocalDate today);
}
