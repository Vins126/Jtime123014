package it.unicam.cs.mpgc.jtime123014;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;
import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestReportGeneration {

    private SimpleCalendar calendar;
    private Project<UUID> project;

    @BeforeEach
    void setUp() {
        calendar = new SimpleCalendar(UUID.randomUUID(), Months.JANUARY, 2026);
        project = new SimpleProject(UUID.randomUUID(), "Test Project", "A sample project for reporting", Priority.HIGH);
        calendar.addProject(project);

        Task<UUID> t1 = new SimpleTask(UUID.randomUUID(), "Analysis", "Analyze requirements", 120);
        t1.setStatus(Status.COMPLETED);
        t1.setDurationActual(130);

        Task<UUID> t2 = new SimpleTask(UUID.randomUUID(), "Design", "Architecture design", 240);
        t2.setStatus(Status.PENDING);

        project.insertTask(t1);
        project.insertTask(t2);

        // Assign completed task to Today so it appears in Interval Report
        // SimpleDay constructor: (LocalDate id, int buffer)
        SimpleDay today = new SimpleDay(LocalDate.now(), 480);
        today.addTask(t1);
        calendar.addDay(today);
    }

    @Test
    void testFullReportFlow() {
        ReportController controller = new ReportController(calendar);
        controller.setOutputDirectory("reports");

        File reportDir = new File("reports");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        System.out.println("Generating Project Report...");
        controller.createProjectReport(project.getId().toString());

        String expectedFilenameStart = "Report_Test_Project_";
        boolean found = false;

        if (reportDir.exists()) {
            File[] files = reportDir
                    .listFiles((d, name) -> name.startsWith(expectedFilenameStart) && name.endsWith(".xml"));
            if (files != null && files.length > 0) {
                found = true;
                System.out.println("Found report file: " + files[0].getAbsolutePath());
            }
        }

        assertTrue(found, "Project Report XML file should be created in reports/ folder");

        // --- Interval Report Test ---
        System.out.println("Generating Interval Report...");
        LocalDate start = LocalDate.now().minusDays(5);
        LocalDate end = LocalDate.now().plusDays(5);
        controller.createIntervalReport(start, end);

        String expectedIntervalFilenameStart = "IntervalReport_";
        boolean foundInterval = false;
        if (reportDir.exists()) {
            File[] files = reportDir
                    .listFiles((d, name) -> name.startsWith(expectedIntervalFilenameStart) && name.endsWith(".xml"));
            if (files != null && files.length > 0) {
                foundInterval = true;
                System.out.println("Found interval report file: " + files[0].getAbsolutePath());
            }
        }
        assertTrue(foundInterval, "Interval Report XML file should be created");
    }
}
