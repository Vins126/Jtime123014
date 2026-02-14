package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Classe principale che fa da collante tra tutto.
 * Gestisce il calendario, lo scheduler e la UI.
 * Praticamente è il punto di ingresso per la logica.
 */
public class JTimeController implements AppController {

    private Calendar<UUID> calendar;

    private final CalendarService calendarService;
    private final Scheduler scheduler;

    private ReportController reportController;

    private final PersistenceService persistenceService;

    private final ExecutorService executor;

    /** Percorso del file */
    private static final Path DATA_FILE_PATH = Paths.get("src", "main", "resources", "it", "unicam", "cs", "mpgc",
            "jtime123014", "save", "jtime_data.ser");

    /**
     * Costruttore predefinito.
     * Inizializza il controller con i servizi standard.
     */
    public JTimeController() {
        this(new LocalDateCalendarService(), new PriorityScheduler(), new PersistenceManager());
    }

    /**
     * Costruttore principale.
     * Permette di passare versioni specifiche dei servizi (utile per i test).
     *
     * @param calendarService    Il servizio di gestione calendario.
     * @param scheduler          L'algoritmo di scheduling da utilizzare.
     * @param persistenceService Il servizio di persistenza.
     */
    public JTimeController(CalendarService calendarService, Scheduler scheduler,
            PersistenceService persistenceService) {
        this.calendarService = calendarService;
        this.scheduler = scheduler;
        this.persistenceService = persistenceService;
        this.executor = Executors.newCachedThreadPool();

        // Caricamento dei dati all'avvio
        loadData();

        // Inizializzazione del sistema di reporting
        this.reportController = new ReportController(this.calendar);

        // Assicura che la finestra temporale sia valida (crea giorni futuri se
        // necessario)
        this.updateRollingWindow();
    }

    /**
     * Tenta di caricare i dati dal file di salvataggio.
     * Se il file non esiste o è corrotto, crea un nuovo calendario vuoto.
     */
    @SuppressWarnings("unchecked")
    private void loadData() {
        try {
            this.calendar = (Calendar<UUID>) persistenceService.load(DATA_FILE_PATH);
            System.out.println("Dati caricati correttamente da: " + DATA_FILE_PATH);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Nessun dato salvato trovato o errore nel caricamento. Inizializzazione nuovo calendario.");
            this.calendar = createDefaultCalendar();
        }
    }

    /**
     * Crea un calendario di default valido.
     *
     * @return Una nuova istanza di SimpleCalendar.
     */
    private Calendar<UUID> createDefaultCalendar() {
        LocalDate today = LocalDate.now();
        return new SimpleCalendar(
                UUID.randomUUID(),
                Months.values()[today.getMonthValue() - 1],
                today.getYear());
    }

    /**
     * Salva lo stato attuale dell'applicazione su disco.
     */
    public void saveData() {
        try {
            persistenceService.save(this.calendar, DATA_FILE_PATH);
            System.out.println("Dati salvati correttamente in: " + DATA_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei dati: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- METODI ESPOSTI ALLA UI ---

    /**
     * {@inheritDoc}
     */
    public void addProject(Project<?> project) {
        calendar.addProject(project);
    }

    /**
     * {@inheritDoc}
     */
    public List<Project<?>> getProjects() {
        return calendar.getProjects();
    }

    /**
     * {@inheritDoc}
     */
    public void removeProject(Project<?> project) {
        calendar.removeProject(project);
    }

    /**
     * {@inheritDoc}
     */
    public java.util.List<Report> loadReportsFromDirectory() {
        return reportController.loadReportsFromDirectory();
    }

    /**
     * {@inheritDoc}
     */
    public String getReportOutputDirectory() {
        return reportController.getOutputDirectory();
    }

    /**
     * {@inheritDoc}
     */
    public void schedule(int dailyBufferMinutes) {
        LocalDate today = LocalDate.now();

        // 1. Reset dello scheduling esistente dalle data odierna in poi
        resetSchedule(today);

        // 2. Prepara i giorni futuri con il buffer specificato
        calendarService.prepareDaysForScheduling(calendar, today, dailyBufferMinutes);

        // 3. Esegue l'algoritmo di pianificazione
        scheduler.schedule(calendar, today);
    }

    /**
     * {@inheritDoc}
     */
    public void scheduleAsync(int dailyBufferMinutes, Runnable onComplete) {
        CompletableFuture.runAsync(() -> {
            System.out.println("Avvio scheduling asincrono su thread: " + Thread.currentThread().getName());
            schedule(dailyBufferMinutes);
        }, executor).thenRun(onComplete);
    }

    /**
     * Helper per resettare la pianificazione futura prima di una nuova esecuzione.
     *
     * @param fromDate La data da cui partire per il reset.
     */
    private void resetSchedule(LocalDate fromDate) {
        calendarService.resetSchedule(calendar, fromDate);
    }

    /**
     * {@inheritDoc}
     */
    public void saveDataAsync() {
        CompletableFuture.runAsync(this::saveData, executor)
                .thenRun(() -> System.out.println("Salvataggio asincrono completato."));
    }

    /**
     * {@inheritDoc}
     */
    public void shutdownExecutor() {
        executor.shutdown();
        System.out.println("ExecutorService spento.");
    }

    /**
     * {@inheritDoc}
     */
    public void updateRollingWindow() {
        // Default buffer impostato a 8 ore (480 minuti) per i nuovi giorni creati
        // automaticamente
        calendarService.updateRollingWindow(calendar, LocalDate.now(), 480);
    }

    /**
     * {@inheritDoc}
     */
    public Calendar<UUID> getCalendar() {
        return calendar;
    }

    // --- METODI REPORTING ---

    /**
     * {@inheritDoc}
     */
    public Report createProjectReport(String projectId) {
        return reportController.createProjectReport(projectId);
    }

    /**
     * {@inheritDoc}
     */
    public Report createIntervalReport(LocalDate start, LocalDate end) {
        return reportController.createIntervalReport(start, end);
    }

    /**
     * {@inheritDoc}
     */
    public void setReportOutputDirectory(String path) {
        reportController.setOutputDirectory(path);
    }
}
