package it.unicam.cs.mpgc.jtime123014.model;

import java.util.ArrayList;

/**
 * Classi concrete di supporto per i test JUnit.
 * Usano Integer come ID per semplicità.
 */
import java.time.LocalDate;
import java.util.UUID;

public class TestModels {

    public static class TestDay extends AbstractDay {
        public TestDay(LocalDate id, int buffer) {
            super(id, buffer);
        }

        public TestDay(LocalDate id) {
            super(id);
        }
    }

    public static class TestTask extends AbstractTask {
        public TestTask(UUID id, String name, String description, int durationEstimate) {
            super(id, name, description, durationEstimate);
            this.setTimeConsuming(durationEstimate); // Assuming timeConsuming is initialized with estimate
        }
    }

    public static class TestProject extends AbstractProject {
        public TestProject(UUID id, String name, String description, Priority priority) {
            super(id, name, description);
            this.setPriority(priority);
            this.setTasks(new ArrayList<Task<?>>());
        }
    }

    public static class TestCalendar extends AbstractCalendar {
        public TestCalendar(UUID id) {
            super(id, Months.JANUARY, 2024, new ArrayList<Day<?>>(), new ArrayList<Project<?>>());
        }
    }
}
