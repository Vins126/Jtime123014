package it.unicam.cs.mpgc.jtime123014;

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
 * Controller principale dell'applicazione JTime (Pattern MVC).
 * <p>
 * Agisce come Coordinator/Facade tra:
 * <ul>
 * <li><b>Model</b>: {@link Calendar} e le sue entità.</li>
 * <li><b>Service</b>: {@link Scheduler} per la pianificazione e
 * {@link CalendarService} per la gestione temporale.</li>
 * <li><b>View</b>: Interfaccia utente (che chiama i metodi di questo
 * controller).</li>
 * </ul>
 * <p>
 * Responsabilità:
 * <ol>
 * <li><b>Composition Root</b>: Istanzia e configura le implementazioni concrete
 * delle componenti (Dependency Injection manuale).</li>
 * <li><b>Orchestrazione</b>: Espone operazioni di alto livello (es. "schedula
 * tutto") delegando l'esecuzione ai servizi competenti.</li>
 * </ol>
 */
public class JTimeController {

    /** Il Modello dati (Calendar) */
    private Calendar<UUID> calendar;

    /** Servizio per la gestione della finestra temporale */
    private CalendarService calendarService;

    /** Servizio per l'algoritmo di scheduling */
    private Scheduler scheduler;

    /** Controller per la generazione dei report */
    private ReportController reportController;

    /** Gestore della persistenza */
    private PersistenceManager persistenceManager;

    private final ExecutorService executor;

    /** Percorso del file di salvataggio dati */
    private static final Path DATA_FILE_PATH = Paths.get("src", "main", "resources", "it", "unicam", "cs", "mpgc",
            "jtime123014", "save", "jtime_data.ser");

    /**
     * Costruttore del Controller.
     * Inizializza il sistema creando le istanze concrete di Modello e Servizi.
     * <p>
     * Al termine della costruzione, il calendario viene inizializzato con una
     * finestra temporale di default (Rolling Window).
     */
    public JTimeController() {
        // 1. Istanziamo il Servizio Calendario
        this.calendarService = new LocalDateCalendarService();

        // 2. Istanziamo lo Scheduler
        this.scheduler = new PriorityScheduler();

        // 3. Istanziamo il PersistenceManager
        this.persistenceManager = new PersistenceManager();

        // 4. Inizializziamo l'ExecutorService
        this.executor = Executors.newCachedThreadPool();

        // 5. Carichiamo i dati o inizializziamo un nuovo calendario
        loadData();

        // 6. Istanziamo il ReportController
        this.reportController = new ReportController(this.calendar);

        // Inizializzazione della finestra temporale (crea i giorni futuri)
        this.updateRollingWindow();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try {
            this.calendar = (Calendar<UUID>) persistenceManager.load(DATA_FILE_PATH);
            System.out.println("Dati caricati correttamente da: " + DATA_FILE_PATH);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Nessun dato salvato trovato o errore nel caricamento. Inizializzazione nuovo calendario.");
            LocalDate today = LocalDate.now();
            this.calendar = new SimpleCalendar(
                    UUID.randomUUID(),
                    Months.values()[today.getMonthValue() - 1],
                    today.getYear());
        }
    }

    public void saveData() {
        try {
            persistenceManager.save(this.calendar, DATA_FILE_PATH);
            System.out.println("Dati salvati correttamente in: " + DATA_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei dati: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- METODI ESPOSTI ALLA UI (DELEGATION PATTERN) ---

    /**
     * Aggiunge un nuovo progetto al sistema.
     * 
     * @param project il progetto da aggiungere.
     * @throws NullPointerException se il progetto è null.
     */
    public void addProject(Project<?> project) {
        calendar.addProject(project);
    }

    /**
     * Restituisce la lista di tutti i progetti attivi nel sistema.
     * 
     * @return lista dei progetti.
     */
    public List<Project<?>> getProjects() {
        return calendar.getProjects();
    }

    /**
     * Rimuove un progetto dal sistema.
     * 
     * @param project il progetto da rimuovere.
     */
    public void removeProject(Project<?> project) {
        calendar.removeProject(project);
    }

    /**
     * Avvia la procedura di scheduling automatico.
     * <p>
     * Il Controller coordina l'operazione:
     * 1. Resetta lo scheduling futuro.
     * 2. Imposta il buffer giornaliero specificato per i giorni futuri.
     * 3. Invoca lo {@link Scheduler} per ripianificare.
     * 
     * @param dailyBufferMinutes il buffer giornaliero da utilizzare (minuti).
     */
    public void schedule(int dailyBufferMinutes) {
        LocalDate today = LocalDate.now();

        // 1. Reset dello scheduling esistente
        resetSchedule(today);

        // 2. Aggiornamento buffer giorni futuri
        for (it.unicam.cs.mpgc.jtime123014.Day<?> day : calendar.getDays()) {
            if (((LocalDate) day.getId()).compareTo(today) >= 0) {
                day.setBuffer(dailyBufferMinutes);
                day.setFreeBuffer(dailyBufferMinutes);
            }
        }

        // 3. Esecuzione algoritmo
        scheduler.schedule(calendar, today);
    }

    /**
     * Esegue lo scheduling in modo asincrono per non bloccare la UI.
     * 
     * @param dailyBufferMinutes buffer giornaliero
     * @param onComplete         callback da eseguire al termine (es. aggiornamento
     *                           UI)
     */
    public void scheduleAsync(int dailyBufferMinutes, Runnable onComplete) {
        CompletableFuture.runAsync(() -> {
            System.out.println("Avvio scheduling asincrono su thread: " + Thread.currentThread().getName());
            schedule(dailyBufferMinutes);
        }, executor).thenRun(onComplete);
    }

    private void resetSchedule(LocalDate fromDate) {
        for (it.unicam.cs.mpgc.jtime123014.Day<?> day : calendar.getDays()) {
            if (((LocalDate) day.getId()).compareTo(fromDate) >= 0) {
                // Recupera le task del giorno
                List<it.unicam.cs.mpgc.jtime123014.Task<?>> tasks = day.getTasks();

                // Resetta lo stato delle task a PENDING (se non completate)
                // Nota: bisognerebbe controllare se erano COMPLETED, ma lo scheduler
                // mette le task IN_PROGRESS quando le assegna.
                // Se una task è COMPLETED non dovrebbe essere toccata o rimossa?
                // Assumiamo che se è nel giorno "futuro" ed è stata schedulata,
                // vogliamo rischedularla.
                for (it.unicam.cs.mpgc.jtime123014.Task<?> task : tasks) {
                    if (task.getStatus() != it.unicam.cs.mpgc.jtime123014.Status.COMPLETED) {
                        task.setStatus(it.unicam.cs.mpgc.jtime123014.Status.PENDING);
                    }
                }

                // Rimuove tutte le task dal giorno
                day.getTasks().clear();

                // Resetta il buffer del giorno al suo massimo
                day.setFreeBuffer(day.getBuffer());
            }
        }
    }

    /**
     * Salva i dati in modo asincrono.
     */
    public void saveDataAsync() {
        CompletableFuture.runAsync(this::saveData, executor)
                .thenRun(() -> System.out.println("Salvataggio asincrono completato."));
    }

    public void shutdownExecutor() {
        executor.shutdown();
        System.out.println("ExecutorService spento.");
    }

    /**
     * Aggiorna la finestra temporale (Rolling Window) assicurando che ci siano
     * giorni disponibili nel futuro.
     * <p>
     * Delega al {@link CalendarService} l'aggiornamento.
     * Il buffer di default per i nuovi giorni creati è impostato a 8 ore (valore
     * arbitrario di default).
     */
    public void updateRollingWindow() {
        // Default buffer: 8 ore (può essere parametrizzato in futuro)
        calendarService.updateRollingWindow(calendar, LocalDate.now(), 480);
    }

    /**
     * Restituisce il riferimento al Calendario (Modello).
     * Utile per la View per leggere lo stato (es. lista giorni).
     * 
     * @return il calendario.
     */
    public Calendar<UUID> getCalendar() {
        return calendar;
    }

    // --- METODI REPORTING ---

    /**
     * Genera un report XML per un progetto specifico.
     * 
     * @param projectId ID del progetto.
     */
    public void createProjectReport(String projectId) {
        reportController.createProjectReport(projectId);
    }

    /**
     * Genera un report XML per un intervallo di date.
     * 
     * @param start Data inizio.
     * @param end   Data fine.
     */
    public void createIntervalReport(LocalDate start, LocalDate end) {
        reportController.createIntervalReport(start, end);
    }

    /**
     * Configura la directory di output per i report.
     * Utile per i test.
     * 
     * @param path il percorso della directory.
     */
    public void setReportOutputDirectory(String path) {
        reportController.setOutputDirectory(path);
    }
}
