package it.unicam.cs.mpgc.jtime123014.controller;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

import it.unicam.cs.mpgc.jtime123014.model.Calendar;
import it.unicam.cs.mpgc.jtime123014.model.SimpleCalendar;
import it.unicam.cs.mpgc.jtime123014.model.Months;
import it.unicam.cs.mpgc.jtime123014.model.Project;
import it.unicam.cs.mpgc.jtime123014.service.CalendarService;
import it.unicam.cs.mpgc.jtime123014.service.LocalDateCalendarService;
import it.unicam.cs.mpgc.jtime123014.service.PriorityScheduler;
import it.unicam.cs.mpgc.jtime123014.service.Scheduler;
import it.unicam.cs.mpgc.jtime123014.report.ReportController;

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

    /**
     * Costruttore del Controller.
     * Inizializza il sistema creando le istanze concrete di Modello e Servizi.
     * <p>
     * Al termine della costruzione, il calendario viene inizializzato con una
     * finestra temporale di default (Rolling Window).
     */
    public JTimeController() {
        // 1. Istanziamo il Servizio Calendario
        // Usiamo LocalDateCalendarService che è l'implementazione concreta per
        // LocalDate
        this.calendarService = new LocalDateCalendarService();

        // 2. Istanziamo lo Scheduler
        // Usiamo PriorityScheduler che implementa la strategia Greedy + Priority
        this.scheduler = new PriorityScheduler();

        // 3. Istanziamo il Modello (Calendario)
        // Creiamo un calendario vuoto partendo dal mese e anno correnti
        LocalDate today = LocalDate.now();
        this.calendar = new SimpleCalendar(
                UUID.randomUUID(),
                Months.values()[today.getMonthValue() - 1], // Mese corrente (0-indexed bias se necessario, verifica
                                                            // enum)
                today.getYear());

        // 4. Istanziamo il ReportController
        this.reportController = new ReportController(this.calendar);

        // Inizializzazione della finestra temporale (crea i giorni futuri)
        this.updateRollingWindow();
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
     * Avvia la procedura di scheduling automatico.
     * <p>
     * Il Controller coordina l'operazione invocando lo {@link Scheduler} sul
     * {@link Calendar} corrente,
     * a partire dalla data odierna.
     */
    public void schedule() {
        scheduler.schedule(calendar, LocalDate.now());
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
        calendarService.updateRollingWindow(calendar, LocalDate.now(), 8);
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
}
