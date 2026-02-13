package it.unicam.cs.mpgc.jtime123014;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

/**
 * Implementazione concreta di base di {@link AbstractProject}.
 */
public class SimpleProject extends AbstractProject {

    public SimpleProject(UUID id, String name, String description, Priority priority) {
        super(id, name, description);
        this.setPriority(priority);
        this.setTasks(new ArrayList<Task<?>>());
    }

    public SimpleProject(UUID id, String name, String description, Priority priority, List<Task<?>> tasks) {
        super(id, name, description);
        this.setPriority(priority);
        this.setTasks(tasks);
    }
}
