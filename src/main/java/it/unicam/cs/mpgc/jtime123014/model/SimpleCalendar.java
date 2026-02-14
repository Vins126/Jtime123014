package it.unicam.cs.mpgc.jtime123014.model;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class SimpleCalendar extends AbstractCalendar {

    public SimpleCalendar(UUID id, Months month, int year) {
        super(id, month, year, new ArrayList<Day<?>>(), new ArrayList<Project<?>>());
    }

    public SimpleCalendar(UUID id, Months month, int year, List<Day<?>> days, List<Project<?>> projects) {
        super(id, month, year, days, projects);
    }
}
