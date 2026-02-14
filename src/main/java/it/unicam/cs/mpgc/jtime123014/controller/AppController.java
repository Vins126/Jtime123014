package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * L'interfaccia principale che controlla l'intera applicazione.
 * <p>
 * Mette a disposizione le funzioni più importanti (come aggiungere progetti
 * o avviare la pianificazione) in modo che la grafica possa usarle facilmente.
 */
public interface AppController {

    // --- Gestione Progetti ---

    /**
     * Aggiunge un nuovo progetto al sistema.
     *
     * @param project Il progetto da aggiungere.
     */
    void addProject(Project<?> project);

    /**
     * Restituisce la lista di tutti i progetti attualmente gestiti.
     *
     * @return Una lista di progetti.
     */
    List<Project<?>> getProjects();

    /**
     * Rimuove un progetto dal sistema.
     *
     * @param project Il progetto da rimuovere.
     */
    void removeProject(Project<?> project);

    // --- Calendario e Scheduling ---

    /**
     * Restituisce il calendario principale contenente i giorni e le task
     * pianificate.
     *
     * @return Il calendario dell'applicazione.
     */
    Calendar<UUID> getCalendar();

    /**
     * Avvia lo scheduling delle task in modo sincrono (bloccante).
     *
     * @param dailyBufferMinutes I minuti di lavoro disponibili per ogni giorno.
     */
    void schedule(int dailyBufferMinutes);

    /**
     * Avvia lo scheduling delle task in background (non bloccante).
     *
     * @param dailyBufferMinutes I minuti di lavoro disponibili per ogni giorno.
     * @param onComplete         Callback da eseguire al termine dello scheduling.
     */
    void scheduleAsync(int dailyBufferMinutes, Runnable onComplete);

    /**
     * Aggiorna la finestra temporale del calendario in base alla data odierna,
     * assicurando che i giorni passati vengano archiviati o gestiti correttamente.
     */
    void updateRollingWindow();

    // --- Persistenza ---

    /**
     * Salva i dati dell'applicazione su disco (operazione bloccante).
     */
    void saveData();

    /**
     * Salva i dati dell'applicazione su disco in background.
     */
    void saveDataAsync();

    // --- Reporting ---

    /**
     * Crea un report dettagliato per un singolo progetto.
     *
     * @param projectId L'ID del progetto di cui generare il report.
     * @return Il report generato.
     */
    Report createProjectReport(String projectId);

    /**
     * Crea un report aggregato per un intervallo di date specifico.
     *
     * @param start Data di inizio intervallo.
     * @param end   Data di fine intervallo.
     * @return Il report generato.
     */
    Report createIntervalReport(LocalDate start, LocalDate end);

    /**
     * Carica i report salvati in precedenza dalla directory di output standard.
     *
     * @return Una lista di report caricati.
     */
    List<Report> loadReportsFromDirectory();

    /**
     * Ottiene il percorso della directory dove vengono salvati i report xml.
     *
     * @return Il percorso assoluto o relativo della directory.
     */
    String getReportOutputDirectory();

    /**
     * Imposta il percorso della directory di output per i report.
     *
     * @param path Il nuovo percorso della directory.
     */
    void setReportOutputDirectory(String path);

    // --- Lifecycle ---

    /**
     * Chiude ordinatamente l'executor service utilizzato per le operazioni
     * asincrone.
     * Da chiamare alla chiusura dell'applicazione per liberare le risorse.
     */
    void shutdownExecutor();
}
