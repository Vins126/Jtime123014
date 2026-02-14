package it.unicam.cs.mpgc.jtime123014;
import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;
import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;

class PrioritySchedulerTest {

    private TestModels.TestCalendar calendar;
    private PriorityScheduler scheduler;
    private TestCalendarService service;
    private LocalDate today;

    @BeforeEach
    void setUp() {
        today = LocalDate.of(2024, 1, 1);
        calendar = new TestModels.TestCalendar(UUID.randomUUID());
        scheduler = new PriorityScheduler();
        service = new TestCalendarService();

        // Inizializza 31 giorni
        service.updateRollingWindow(calendar, today, 480);
        // Imposta buffer di 100 minuti per ogni giorno per semplicità di calcolo
        calendar.getDays().forEach(d -> {
            d.setBuffer(100);
            d.setFreeBuffer(100);
        });
    }

    @Test
    void testScheduleHighPriorityProject() {
        // Progetto HIGH (75%) -> Max 75 min su 100
        TestModels.TestProject project = new TestModels.TestProject(UUID.randomUUID(), "HighPrio", "Test",
                Priority.HIGH);

        // Task 1: 50 min (sta nel 75%)
        TestModels.TestTask task1 = new TestModels.TestTask(UUID.randomUUID(), "T1", "D1", 50);
        // Task 2: 30 min (50+30=80 > 75). Non dovrebbe entrare nello stesso giorno del
        // task 1
        TestModels.TestTask task2 = new TestModels.TestTask(UUID.randomUUID(), "T2", "D2", 30);

        project.getTasks().add(task1);
        project.getTasks().add(task2);
        calendar.addProject(project);

        // Esegui scheduler partendo da oggi
        scheduler.schedule(calendar, today);

        Day<?> day0 = getDay(today);
        Day<?> day1 = getDay(today.plusDays(1));

        // Verifica Task 1 nel giorno 0
        assertTrue(day0.getTasks().contains(task1), "Task 1 deve essere nel Giorno 0");
        assertEquals(Status.IN_PROGRESS, task1.getStatus());

        // Verifica Task 2 NON nel giorno 0 (sforo percentuale) ma nel Giorno 1
        assertFalse(day0.getTasks().contains(task2), "Task 2 non deve essere nel Giorno 0 (sforo %)");
        assertTrue(day1.getTasks().contains(task2), "Task 2 deve essere nel Giorno 1");
        assertEquals(Status.IN_PROGRESS, task2.getStatus());

        // Verifica consumi
        // Giorno 0: FreeBuffer = 100 - 50 = 50.
        assertEquals(50, day0.getFreeBuffer());
        // Giorno 1: FreeBuffer = 100 - 30 = 70.
        assertEquals(70, day1.getFreeBuffer());
    }

    @Test
    void testScheduleLowPriorityProject() {
        // Progetto LOW (25%) -> Max 25 min su 100
        TestModels.TestProject project = new TestModels.TestProject(UUID.randomUUID(), "LowPrio", "Test", Priority.LOW);

        // Task 1: 20 min (Ok)
        TestModels.TestTask task1 = new TestModels.TestTask(UUID.randomUUID(), "T1", "D1", 20);
        // Task 2: 10 min (20+10=30 > 25). Va al giorno dopo.
        TestModels.TestTask task2 = new TestModels.TestTask(UUID.randomUUID(), "T2", "D2", 10);

        project.getTasks().add(task1);
        project.getTasks().add(task2);
        calendar.addProject(project);

        scheduler.schedule(calendar, today);

        Day day0 = getDay(today);
        assertTrue(day0.getTasks().contains(task1));
        assertFalse(day0.getTasks().contains(task2)); // Priority constraint check
    }

    @Test
    void testMultipleProjectsInterleaving() {
        // Progetto LOW (25% -> 25 min)
        TestModels.TestProject pLow = new TestModels.TestProject(UUID.randomUUID(), "L", "L", Priority.LOW);
        TestModels.TestTask tLow = new TestModels.TestTask(UUID.randomUUID(), "TL", "TL", 25);
        pLow.getTasks().add(tLow);

        // Progetto HIGH (75% -> 75 min)
        TestModels.TestProject pHigh = new TestModels.TestProject(UUID.randomUUID(), "H", "H", Priority.HIGH);
        TestModels.TestTask tHigh = new TestModels.TestTask(UUID.randomUUID(), "TH", "TH", 50); // 50 min
        pHigh.getTasks().add(tHigh);

        calendar.addProject(pLow);
        calendar.addProject(pHigh);

        scheduler.schedule(calendar, today);

        Day day0 = getDay(today);

        // Giorno 0 deve contenere ENTRAMBI perché:
        // LOW occupa 25 min. Free: 75.
        // HIGH vede Free 75. Il suo limite è 75%. Le serve 50. 50 <= 75 e 50 <= 75. OK.
        assertTrue(day0.getTasks().contains(tLow), "Low priority task should be scheduled");
        assertTrue(day0.getTasks().contains(tHigh), "High priority task should fit in remaining buffer");

        assertEquals(25, day0.getFreeBuffer(), "Free buffer should be 100 - 25 - 50 = 25");
    }

    @Test
    void testLowPriorityTimeBoxingConstraint() {
        // Scenario: 1 Giorno con 100 minuti di buffer. 1 Progetto LOW (quota 25% -> 25
        // min).
        // Azione: Aggiungi Task A (20 min) e Task B (10 min).
        // Task A occupa 20 min. Residuo quota: 5 min.
        // Task B (10 min) > 5 min. Non schedulabile oggi.
        // Assertion: Task B spostata a Giorno 1.

        TestModels.TestProject project = new TestModels.TestProject(UUID.randomUUID(), "LowPrioCon", "Test",
                Priority.LOW);
        TestModels.TestTask taskA = new TestModels.TestTask(UUID.randomUUID(), "TA", "DA", 20);
        TestModels.TestTask taskB = new TestModels.TestTask(UUID.randomUUID(), "TB", "DB", 10);

        project.getTasks().add(taskA);
        project.getTasks().add(taskB);
        calendar.addProject(project);

        scheduler.schedule(calendar, today);

        Day<?> day0 = getDay(today);
        Day<?> day1 = getDay(today.plusDays(1));

        // Verifica Task A nel Giorno 0
        assertTrue(day0.getTasks().contains(taskA), "Task A (20m) deve essere nel giorno 0");

        // Verifica che Task B NON sia nel Giorno 0 (Max residuo 5m)
        assertFalse(day0.getTasks().contains(taskB),
                "Task B (10m) non deve essere schedulata nel giorno 0 (Residuo 5m)");

        // Verifica che SIA nel Giorno 1
        assertTrue(day1.getTasks().contains(taskB), "Task B must be moved to day 1");

        // Verifica buffer
        assertEquals(80, day0.getFreeBuffer(), "Buffer Giorno 0: 100 - 20 = 80");
        assertEquals(90, day1.getFreeBuffer(), "Buffer Giorno 1: 100 - 10 = 90");
    }

    private Day<?> getDay(LocalDate date) {
        return calendar.getDays().stream().filter(d -> d.getId().equals(date)).findFirst().orElseThrow();
    }
}
