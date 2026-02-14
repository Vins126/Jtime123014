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
import java.util.List;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TestRealisticScenario {

    private JTimeController controller;

    @BeforeEach
    void setUp() {
        controller = new JTimeController();
        // Setup directory report per i test
        // Uso src/test/resources/reports come richiesto
        controller.setReportOutputDirectory("src/test/resources/reports");

        // Pulisci la directory se esiste per un test pulito (opzionale ma consigliato)
        File reportDir = new File("src/test/resources/reports");
        if (reportDir.exists()) {
            for (File file : reportDir.listFiles())
                if (!file.isDirectory())
                    file.delete();
        }
    }

    @Test
    void testFullWorkflow() {
        JTimeController controller = new JTimeController();

        // CLEANUP: Remove existing projects to ensure test isolation
        java.util.List<Project<?>> currentProjects = new java.util.ArrayList<>(controller.getProjects());
        for (Project<?> project : currentProjects) {
            controller.removeProject(project);
        }

        // 1. Setup: Crea 3 Progetti

        Project<?> pCore = new SimpleProject(UUID.randomUUID(), "Sviluppo Core", "Core dev", Priority.HIGH);
        for (int i = 0; i < 5; i++) {
            pCore.insertTask(new SimpleTask(UUID.randomUUID(), "Core Task " + i, "Desc", 60));
        }

        Project<?> pUI = new SimpleProject(UUID.randomUUID(), "Refactoring UI", "UI dev", Priority.MEDIUM);
        for (int i = 0; i < 5; i++) {
            pUI.insertTask(new SimpleTask(UUID.randomUUID(), "UI Task " + i, "Desc", 120));
        }

        // "Documentazione" (LOW)
        Project<?> pDocs = new SimpleProject(UUID.randomUUID(), "Documentazione", "Docs", Priority.LOW);
        for (int i = 0; i < 3; i++) {
            pDocs.insertTask(new SimpleTask(UUID.randomUUID(), "Doc Task " + i, "Desc", 45));
        }

        // Aggiungi progetti
        controller.addProject(pCore);
        controller.addProject(pUI);
        controller.addProject(pDocs);

        // 2. Esecuzione: Schedule
        controller.schedule(480);

        // 3. Simulazione Lavoro
        // Recupera primo giorno
        @SuppressWarnings("unchecked")
        List<Day<?>> days = (List<Day<?>>) (List<?>) controller.getCalendar().getDays();
        // Calendar è Calendar<UUID>. getDays returns List<Day<UUID>>?
        // Nel codice: List<Day<?>> dates = calendar.getDays();

        Day<?> day0 = days.stream()
                .filter(d -> d.getId().equals(LocalDate.now()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Giorno corrente non trovato"));

        assertFalse(day0.getTasks().isEmpty(), "Il giorno 0 deve avere task assegnate");

        Task<?> taskToComplete = day0.getTasks().get(0);
        // Simula completamento
        System.out.println("Completing task: " + taskToComplete.getName());

        // Esegue il cast a AbstractTask o usa i metodi dell'interfaccia se presenti
        // Task interface ha completeTaskAndSetDuration? Sì, in AbstractTask visto
        // prima.
        // In Task.java (interfaccia) devo controllare ma AbstractTask lo implementa.
        taskToComplete.completeTaskAndSetDuration(taskToComplete.getDurationEstimate() + 10); // +10 min ritardo

        assertEquals(Status.COMPLETED, taskToComplete.getStatus());
        assertNotNull(taskToComplete.getDurationActual());

        // 4. Reporting
        controller.setReportOutputDirectory("src/test/resources/reports");
        controller.createProjectReport(pCore.getId().toString());
        controller.createIntervalReport(LocalDate.now(), LocalDate.now().plusDays(7));

        System.out.println("Calendar days: " + days.size());
        for (Day<?> d : days) {
            System.out.println(
                    "Day " + d.getId() + " - Tasks: " + d.getTasks().size() + " - Free Buffer: " + d.getFreeBuffer());
            for (Task<?> t : d.getTasks()) {
                System.out.println("  - " + t.getName() + " (" + t.getTimeConsuming() + "m)");
            }
        }

        // 5. Asserzioni Finali

        // Verifica distribuzione su più giorni
        Day<?> day1 = days.stream()
                .filter(d -> d.getId().equals(LocalDate.now().plusDays(1)))
                .findFirst().orElse(null);

        assertNotNull(day1, "Giorno 1 deve esistere");
        // Verifica che ci sia almeno una task schedulata globalmente oltre il giorno 0
        // o che day1 abbia task se il carico è sufficiente.
        // Core: 5x60 = 300 min. UI: 5x120 = 600 min. Docs: 3x45 = 135 min.
        // Totale: 1035 min.
        // Default buffer in rolling window controller update: 8 ore = 480 min.
        // 1035 > 480. Quindi DEVE occupare più di un giorno.

        boolean tasksInDay1 = !day1.getTasks().isEmpty();
        assertTrue(tasksInDay1, "Le task devono essere distribuite su più giorni (almeno Giorno 1 popolato)");

        // Verifica buffer diminuito
        assertTrue(day0.getFreeBuffer() < day0.getBuffer(), "Il freeBuffer del giorno 0 deve essere diminuito");

        // Verifica Report Files (reports sono file XML, non serializzati)
        java.io.File reportDir = new java.io.File(controller.getReportOutputDirectory());
        assertTrue(reportDir.exists() && reportDir.isDirectory(),
                "La directory dei report deve esistere");

        java.io.File[] reportFiles = reportDir.listFiles((dir, name) -> name.endsWith(".xml"));
        assertNotNull(reportFiles);
        assertTrue(reportFiles.length >= 2,
                "Dovrebbero esserci almeno 2 report XML (1 progetto, 1 intervallo)");

        // Verifica che il caricamento dalla directory funzioni
        java.util.List<Report> loadedReports = controller.loadReportsFromDirectory();
        assertNotNull(loadedReports);
        assertTrue(loadedReports.size() >= 2,
                "loadReportsFromDirectory deve trovare almeno 2 report");

        // Verifica reflection sul progetto
        // La task taskToComplete appartiene a uno dei progetti.
        // Verifichiamo che nel progetto risulti COMPLETED.

        // Cerchiamo il progetto proprietario della task
        Project<?> ownerProject = null;
        if (pCore.getTasks().contains(taskToComplete))
            ownerProject = pCore;
        else if (pUI.getTasks().contains(taskToComplete))
            ownerProject = pUI;
        else if (pDocs.getTasks().contains(taskToComplete))
            ownerProject = pDocs;

        assertNotNull(ownerProject);

        // Ricarichiamo la task dalla lista del progetto per sicurezza (anche se è by
        // reference)
        Task<?> taskInProject = ownerProject.getTasks().stream()
                .filter(t -> t.getId().equals(taskToComplete.getId()))
                .findFirst().orElseThrow();

        assertEquals(Status.COMPLETED, taskInProject.getStatus(),
                "Lo stato della task nel progetto deve essere COMPLETED");
    }
}
