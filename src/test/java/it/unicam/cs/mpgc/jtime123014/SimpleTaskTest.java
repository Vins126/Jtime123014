package it.unicam.cs.mpgc.jtime123014;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTaskTest {

    private SimpleTask task;

    @BeforeEach
    void setUp() {
        task = new SimpleTask(UUID.randomUUID(), "Task", "Desc", 60); // Estimate: 60 min
    }

    @Test
    void shouldCalculateDeltaCorrectly() {
        // Actual: 80 min. Delta = 80 - 60 = 20
        task.setDurationActual(80);

        int delta = task.calculateDelta();

        assertEquals(20, delta, "Delta should be Actual (80) - Estimate (60) = 20");
    }

    @Test
    void shouldCalculateNegativeDeltaCorrectly() {
        // Actual: 40 min. Delta = 40 - 60 = -20
        task.setDurationActual(40);

        int delta = task.calculateDelta();

        assertEquals(-20, delta, "Delta should be Actual (40) - Estimate (60) = -20");
    }

    @Test
    void shouldThrowExceptionIfDeltaWithoutActual() {
        // durationActual is null by default

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            task.calculateDelta();
        });

        assertEquals("La durata effettiva non è stata impostata", exception.getMessage());
    }

    @Test
    void shouldSetDurationAndCompleteTask() {
        int delta = task.completeTaskAndSetDuration(90);

        assertEquals(Status.COMPLETED, task.getStatus());
        assertEquals(90, task.getDurationActual());
        assertEquals(30, delta);
    }
}
