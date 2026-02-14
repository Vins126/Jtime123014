package it.unicam.cs.mpgc.jtime123014;

import it.unicam.cs.mpgc.jtime123014.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test per le operazioni primitive di SimpleDay (addTask, updateBuffer).
 * La logica di scheduling è ora responsabilità del PriorityScheduler (SRP),
 * testata in TestSystemScenario / TestRealisticScenario.
 */
class SimpleDayTest {

    private SimpleDay day;
    private Task<?> task;

    @BeforeEach
    void setUp() {
        day = new SimpleDay(LocalDate.now(), 100);
        task = new SimpleTask(UUID.randomUUID(), "Test Task", "Description", 50);
        task.setTimeConsuming(50);
    }

    @Test
    void shouldNotAddTaskIfBufferExceeded() {
        // Simula scheduling: task richiede 150, buffer è 100
        task.setTimeConsuming(150);

        // Il buffer non è sufficiente
        assertFalse(day.updateBuffer(150), "updateBuffer should return false if exceeds free buffer");
        assertEquals(100, day.getFreeBuffer(), "Free buffer should remain unchanged");
    }

    @Test
    void shouldAddTaskAndUpdateBuffer() {
        // Simula scheduling: task 50, buffer 100
        day.addTask(task);
        task.setStatus(Status.IN_PROGRESS);
        day.updateBuffer(50);

        assertTrue(day.getTasks().contains(task), "Task should be in the list");
        assertEquals(50, day.getFreeBuffer(), "Free buffer should be reduced by task duration");
        assertEquals(Status.IN_PROGRESS, task.getStatus(), "Task status should be IN_PROGRESS");
    }

    @Test
    void shouldRejectDuplicateTask() {
        day.addTask(task);

        assertThrows(IllegalArgumentException.class, () -> day.addTask(task),
                "Should reject duplicate task");
    }

    @Test
    void shouldRejectNullTask() {
        assertThrows(NullPointerException.class, () -> day.addTask(null),
                "Should reject null task");
    }
}
