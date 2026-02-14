package it.unicam.cs.mpgc.jtime123014;
import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;
import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SimpleProjectTest {

    private SimpleProject project;
    private Task<?> task1;
    private Task<?> task2;

    @BeforeEach
    void setUp() {
        project = new SimpleProject(UUID.randomUUID(), "Test Project", "Description", Priority.MEDIUM);
        task1 = new SimpleTask(UUID.randomUUID(), "T1", "D1", 60);
        task2 = new SimpleTask(UUID.randomUUID(), "T2", "D2", 60);
        project.insertTask(task1);
        project.insertTask(task2);
    }

    @Test
    void shouldThrowExceptionIfCompletingWithPendingTasks() {
        // Both tasks are PENDING by default

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            project.completeProject();
        });

        assertEquals("Non tutte le task del progetto sono completate", exception.getMessage());
    }

    @Test
    void shouldReturnOnlyPendingTasks() {
        // Complete one task
        task1.completeTaskAndSetDuration(60);

        List<Task<?>> pendingTasks = project.getPendingTasks();

        assertEquals(1, pendingTasks.size(), "Should return only 1 pending task");
        assertTrue(pendingTasks.contains(task2), "Should contain task2 (which is pending)");
        assertFalse(pendingTasks.contains(task1), "Should not contain task1 (which is completed)");
    }

    @Test
    void shouldCompleteProjectWhenAllTasksAreCompleted() {
        task1.completeTaskAndSetDuration(60);
        task2.completeTaskAndSetDuration(60);

        boolean result = project.completeProject();

        assertTrue(result, "Should return true when completing successfully");
        assertEquals(Status.COMPLETED, project.getStatus(), "Project status should be COMPLETED");
    }
}
