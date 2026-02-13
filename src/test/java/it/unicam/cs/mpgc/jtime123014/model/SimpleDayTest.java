package it.unicam.cs.mpgc.jtime123014.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDayTest {

    private SimpleDay day;
    private Task<?> task;

    @BeforeEach
    void setUp() {
        day = new SimpleDay(LocalDate.now(), 100);
        task = new SimpleTask(UUID.randomUUID(), "Test Task", "Description", 50);
        task.setTimeConsuming(50); // Important: setTimeConsuming is used by scheduleTask
    }

    @Test
    void shouldNotScheduleIfFreeBufferExceeded() {
        // Task requires 150, buffer is 100
        task.setTimeConsuming(150);

        boolean scheduled = day.scheduleTask(task, 100);

        assertFalse(scheduled, "Should not schedule if task duration exceeds free buffer");
        assertEquals(100, day.getFreeBuffer(), "Free buffer should remain unchanged");
        assertFalse(day.getTasks().contains(task), "Task should not be in the list");
    }

    @Test
    void shouldNotScheduleIfMaxPercentageExceeded() {
        // Task requires 60, maxPercentage is 50
        // Free buffer is 100 (so it fits in buffer, but not in percentage)
        task.setTimeConsuming(60);

        boolean scheduled = day.scheduleTask(task, 50);

        assertFalse(scheduled, "Should not schedule if task duration exceeds max percentage");
        assertEquals(100, day.getFreeBuffer(), "Free buffer should remain unchanged");
    }

    @Test
    void shouldUpdateBufferAfterTaskAdded() {
        // Task 50, Buffer 100, Max% 100
        boolean scheduled = day.scheduleTask(task, 100);

        assertTrue(scheduled, "Should schedule task successfully");
        assertEquals(50, day.getFreeBuffer(), "Free buffer should be reduced by task duration"); // 100 - 50 = 50
        assertTrue(day.getTasks().contains(task), "Task should be added to the list");
        assertEquals(Status.IN_PROGRESS, task.getStatus(), "Task status should be changed to IN_PROGRESS");
    }
}
