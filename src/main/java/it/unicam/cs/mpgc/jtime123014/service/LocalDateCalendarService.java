package it.unicam.cs.mpgc.jtime123014.service;

import java.time.LocalDate;

public class LocalDateCalendarService extends SimpleCalendarService {

    @Override
    protected LocalDate plusDays(LocalDate day, int i) {
        return day.plusDays(i);
    }

}
