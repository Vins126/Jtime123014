package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.time.LocalDate;

/**
 * Implementazione concreta del servizio calendario che utilizza
 * {@link LocalDate}.
 */
public class LocalDateCalendarService extends SimpleCalendarService {

    /**
     * Crea un nuovo servizio di calendario basato su LocalDate.
     */
    public LocalDateCalendarService() {
    }

    @Override
    protected LocalDate plusDays(LocalDate day, int i) {
        return day.plusDays(i);
    }

}
