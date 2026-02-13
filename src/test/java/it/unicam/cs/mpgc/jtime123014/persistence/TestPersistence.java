package it.unicam.cs.mpgc.jtime123014.persistence;

import it.unicam.cs.mpgc.jtime123014.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TestPersistence {

    @Test
    void testSaveAndLoad() throws IOException, ClassNotFoundException {
        // Setup
        Path tempFile = Files.createTempFile("jtime_test", ".ser");
        PersistenceManager pm = new PersistenceManager();

        LocalDate today = LocalDate.now();
        SimpleCalendar calendar = new SimpleCalendar(UUID.randomUUID(), Months.JANUARY, 2024);

        // Add some data
        SimpleProject project = new SimpleProject(UUID.randomUUID(), "Test Project", "Desc", Priority.HIGH);
        calendar.addProject(project);

        SimpleTask task = new SimpleTask(UUID.randomUUID(), "Task 1", "Desc", 60);
        project.insertTask(task);

        // Add a day
        SimpleDay day = new SimpleDay(today, 480);
        calendar.addDay(day);

        // Save
        pm.save(calendar, tempFile);

        // Load
        Calendar<?> loadedCalendar = pm.load(tempFile);

        // Verify
        assertNotNull(loadedCalendar);
        assertEquals(calendar.getId(), loadedCalendar.getId());
        assertEquals(1, loadedCalendar.getProjects().size());
        assertEquals("Test Project", loadedCalendar.getProjects().get(0).getName());
        assertEquals(1, loadedCalendar.getProjects().get(0).getTasks().size());
        assertEquals("Task 1", loadedCalendar.getProjects().get(0).getTasks().get(0).getName());

        // Cleanup
        Files.deleteIfExists(tempFile);
    }
}
