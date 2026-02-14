package it.unicam.cs.mpgc.jtime123014;
import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;
import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CompletionLogicTest {

    @Test
    void testTaskCompletionWithDelta() {
        // Task stimato: 60 min.
        TestModels.TestTask task = new TestModels.TestTask(UUID.randomUUID(), "Task1", "Desc", 60);

        // Scenario 1: Messo 50 min (Delta -10, ci ho messo meno)
        int delta = task.completeTaskAndSetDuration(50);

        assertEquals(Status.COMPLETED, task.getStatus());
        assertEquals(50, task.getDurationActual());
        assertEquals(-10, delta);
    }

    @Test
    void testTaskCompletionOvertime() {
        // Task stimato: 60 min.
        TestModels.TestTask task = new TestModels.TestTask(UUID.randomUUID(), "Task2", "Desc", 60);

        // Scenario 2: Messo 90 min (Delta +30, ci ho messo di più)
        task.setDurationActual(90);
        int delta = task.completeTask();

        assertEquals(Status.COMPLETED, task.getStatus());
        assertEquals(30, delta);
    }

    @Test
    void testTaskCompletionWithoutDurationFails() {
        TestModels.TestTask task = new TestModels.TestTask(UUID.randomUUID(), "Task3", "Desc", 60);

        // Provo a completare senza dire quanto ci ho messo
        assertThrows(IllegalStateException.class, () -> task.completeTask(),
                "Deve fallire se actualDuration è null");
    }

    @Test
    void testProjectCompletionSuccess() {
        TestModels.TestProject project = new TestModels.TestProject(UUID.randomUUID(), "Proj1", "Desc",
                Priority.MEDIUM);
        TestModels.TestTask t1 = new TestModels.TestTask(UUID.randomUUID(), "T1", "D", 10);
        TestModels.TestTask t2 = new TestModels.TestTask(UUID.randomUUID(), "T2", "D", 10);

        project.insertTask(t1);
        project.insertTask(t2);

        // Completo tutti i task
        t1.completeTaskAndSetDuration(10);
        t2.completeTaskAndSetDuration(15);

        // Ora posso completare il progetto
        boolean result = project.completeProject();

        assertTrue(result);
        assertEquals(Status.COMPLETED, project.getStatus());
    }

    @Test
    void testProjectCompletionFailure() {
        TestModels.TestProject project = new TestModels.TestProject(UUID.randomUUID(), "Proj2", "Desc",
                Priority.MEDIUM);
        TestModels.TestTask t1 = new TestModels.TestTask(UUID.randomUUID(), "T1", "D", 10);
        project.insertTask(t1);

        // Task ancora PENDING
        assertThrows(IllegalStateException.class, () -> project.completeProject(),
                "Non puoi chiudere un progetto se ha task pendenti");
    }
}
