package it.unicam.cs.mpgc.jtime123014.model;

import java.util.List;

/**
 * Implementazione concreta di base di {@link AbstractDay}.
 */
import java.time.LocalDate;

public class SimpleDay extends AbstractDay {

    public SimpleDay(LocalDate id, int buffer) {
        super(id, buffer);
    }

    public SimpleDay(LocalDate id, int buffer, List<Task<?>> tasks) {
        super(id, buffer, tasks);
    }
}
