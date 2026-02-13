package it.unicam.cs.mpgc.jtime123014;


import java.time.LocalDate;

public class TestCalendarService extends SimpleCalendarService {
    @Override
    protected Day<LocalDate> createDay(LocalDate day, int buffer) {
        return new TestModels.TestDay(day, buffer);
    }

    @Override
    protected LocalDate plusDays(LocalDate day, int i) {
        return day.plusDays(i);
    }
}
