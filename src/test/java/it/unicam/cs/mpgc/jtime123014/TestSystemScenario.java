package it.unicam.cs.mpgc.jtime123014;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;
import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;
import it.unicam.cs.mpgc.jtime123014.controller.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TestSystemScenario {

        @Test
        void testRealisticScenario() {
                System.out.println("\n=== STARTING REALISTIC SYSTEM TEST ===");

                // 1. Initialize Controller
                JTimeController controller = new JTimeController();
                System.out.println("Controller initialized. Rolling window updated.");

                // CLEANUP: Remove existing projects to ensure test isolation
                java.util.List<Project<?>> projects = new java.util.ArrayList<>(controller.getProjects());
                for (Project<?> project : projects) {
                        controller.removeProject(project);
                }
                System.out.println("Cleared existing projects for test isolation.");

                // 2. Create Projects
                // P1: Urgent - Website Redesign
                Project<UUID> p1 = new SimpleProject(UUID.randomUUID(), "Website Redesign",
                                "Critical overhaul of main site", Priority.URGENT);
                // P2: Low - Internal Wiki
                Project<UUID> p2 = new SimpleProject(UUID.randomUUID(), "Internal Wiki", "Update docs", Priority.LOW);

                controller.addProject(p1);
                controller.addProject(p2);
                System.out.println("Projects added: " + p1.getName() + " (" + p1.getPriority() + "), " + p2.getName()
                                + " (" + p2.getPriority() + ")");

                // 3. Add Tasks
                // Day capacity is default 480 mins (8 hours) in Controller

                // P1 Tasks (Urgent)
                // T1.1: 4 hours (240 min)
                Task<UUID> t1_1 = new SimpleTask(UUID.randomUUID(), "Infra Setup", "Server config", 240);
                // T1.2: 3 hours (180 min)
                Task<UUID> t1_2 = new SimpleTask(UUID.randomUUID(), "Home Layout", "CSS/HTML", 180);
                // T1.3: 2 hours (120 min) - Should NOT fit in Day 1 if T1.1 + T1.2 = 420 min
                // (Free 60)
                Task<UUID> t1_3 = new SimpleTask(UUID.randomUUID(), "Deployment", "Go live", 120);

                p1.insertTask(t1_1);
                p1.insertTask(t1_2);
                p1.insertTask(t1_3);

                // P2 Tasks (Low)
                // T2.1: 2 hours (120 min)
                Task<UUID> t2_1 = new SimpleTask(UUID.randomUUID(), "Docs Update", "Write pages", 120);
                p2.insertTask(t2_1);

                System.out.println("Tasks created.");

                // 4. Schedule
                System.out.println("Running Schedule...");
                controller.schedule(480);

                // 5. Verify Scheduling (Day 1 vs Day 2)
                Calendar<UUID> cal = controller.getCalendar();
                LocalDate todayDate = LocalDate.now();

                // Find Day 1
                // We need to cast generic Calendar to access days if necessary, or assuming
                // Calendar interface exposes getDays() which returns List<Day<ID>> or similar.
                // The implementation SimpleCalendar uses UUID as ID for itself, but Days have
                // LocalDate ID.
                // Let's iterate and check ID.

                Day<?> day1 = null;
                for (Day<?> d : cal.getDays()) {
                        if (d.getId().equals(todayDate)) {
                                day1 = d;
                                break;
                        }
                }
                assertNotNull(day1, "Day 1 should exist");

                System.out.println("\n--- Day 1 Analysis (" + todayDate + ") ---");
                System.out.println("Buffer: " + day1.getBuffer());
                System.out.println("Free Buffer: " + day1.getFreeBuffer());
                System.out.println("Scheduled Tasks:");
                day1.getTasks().forEach(t -> System.out.println(
                                " - " + t.getName() + " (" + t.getTimeConsuming() + "m) [" + t.getStatus() + "]"));

                // Let's check Day 2
                LocalDate tomorrowDate = todayDate.plusDays(1);
                Day<?> day2 = null;
                for (Day<?> d : cal.getDays()) {
                        if (d.getId().equals(tomorrowDate)) {
                                day2 = d;
                                break;
                        }
                }
                assertNotNull(day2, "Day 2 should exist");

                System.out.println("\n--- Day 2 Analysis (" + tomorrowDate + ") ---");
                System.out.println("Scheduled Tasks:");
                day2.getTasks().forEach(t -> System.out.println(
                                " - " + t.getName() + " (" + t.getTimeConsuming() + "m) [" + t.getStatus() + "]"));

                // Assertions for Scheduling correctness
                assertTrue(day1.getTasks().contains(t1_1), "Infra Setup should be on Day 1");
                assertTrue(day1.getTasks().contains(t1_2), "Home Layout should be on Day 1");
                // T1.3 (120) > 60 remains?
                // Urgent Max % = 75% of 480 = 360 min.
                // T1.1(240) + T1.2(180) = 420.
                // 420 > 360.
                // So actually, PriorityScheduler might stop even EARLIER if maxPercentage logic
                // works!
                // If Logic is: fill until maxPercentage...
                // T1.1 (240) -> Fits (240 < 360). Remaining quota: 120.
                // T1.2 (180) -> 180 > 120. Should NOT be scheduled on Day 1 if strict priority
                // quota!

                // Let's see what happens. This test is also an exploration of the Scheduler
                // logic.

                // 6. Simulate Work
                System.out.println("\n--- Simulating Work on Day 1 ---");
                // Complete T1.1
                // We must update the Task object that is IN THE DAY. (They are shared
                // references, so updating variable t1_1 is fine/enough)
                System.out.println("Completing 'Infra Setup' (Est: 240, Act: 250)");
                t1_1.setDurationActual(250);
                t1_1.setStatus(Status.COMPLETED);

                // 7. Generate Reports
                System.out.println("\n--- Generating Reports ---");

                // Manually instantiate ReportController as it is NOT in JTimeController anymore
                ReportController reportController = new ReportController(cal);
                reportController.setOutputDirectory("reports");

                // Project Report for P1
                reportController.createProjectReport(p1.getId().toString());

                // Interval Report (Today + Tomorrow)
                reportController.createIntervalReport(todayDate, tomorrowDate);

                // 8. Verify Reports Exist
                java.io.File reportDir = new java.io.File("reports");
                assertTrue(reportDir.exists());

                long reportCount = reportDir.listFiles((d, n) -> n.endsWith(".xml")).length;
                System.out.println("Total XML reports found: " + reportCount);
                assertTrue(reportCount >= 2, "Should have at least 2 reports generated");

                System.out.println("=== SYSTEM TEST COMPLETED SUCCESSFULLY ===");
        }
}
