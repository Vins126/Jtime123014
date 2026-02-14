package it.unicam.cs.mpgc.jtime123014;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;

import java.time.LocalDate;

public class TestCalendarService extends SimpleCalendarService {

    public TestCalendarService() {
        super(TestModels.TestDay::new);
    }

    @Override
    protected LocalDate plusDays(LocalDate day, int i) {
        return day.plusDays(i);
    }
}
