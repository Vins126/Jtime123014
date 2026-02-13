package it.unicam.cs.mpgc.jtime123014.model;

import java.util.UUID;

/**
 * Implementazione concreta di base di {@link AbstractTask}.
 */
public class SimpleTask extends AbstractTask {

    public SimpleTask(UUID id, String name, String description, int durationEstimate) {
        super(id, name, description, durationEstimate);
    }

    public SimpleTask(UUID id, String name, String description, int durationEstimate, Status status) {
        super(id, name, description, durationEstimate);
        this.setStatus(status);
    }
}
