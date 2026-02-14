package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.view.factory.FormDialogFactory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controlla la schermata principale (Dashboard).
 * <p>
 * Mostra un riepilogo veloce: quanti progetti ci sono, quante cose restano da
 * fare
 * e qual è la prossima scadenza. Permette anche di aprire rapidamente i report.
 */
public class HomeController {

    /**
     * Costruttore di default.
     */
    public HomeController() {
    }

    @FXML
    private Label totalProjectsLabel;

    @FXML
    private Label pendingTasksLabel;

    @FXML
    private Label nextDeadlineLabel;

    @FXML
    private ListView<Report> reportsListView;

    /** Riferimento al controller applicativo. */
    private AppController controller;

    /** Riferimento al controller della vista principale per la gestione dei tab. */
    private MainController mainController;

    /**
     * Inizializza la dashboard.
     * Configura le dipendenze, aggiorna i contatori, carica i report e imposta i
     * listener.
     */
    @FXML
    public void initialize() {
        resolveDependencies();
        updateProjectCounters();
        updateNextDeadline();
        loadReportsAsync();
        setupListeners();
    }

    /**
     * Recupera i riferimenti ai controller necessari tramite il Context.
     */
    private void resolveDependencies() {
        this.controller = Context.getInstance().getController();
        this.mainController = Context.getInstance().getMainController();
    }

    /**
     * Aggiorna le etichette con il conteggio dei progetti totali e delle task
     * ancora da completare.
     */
    private void updateProjectCounters() {
        List<Project<?>> projects = controller.getProjects();
        totalProjectsLabel.setText(String.valueOf(projects.size()));

        // Conta task PENDING e IN_PROGRESS
        long pendingTasks = projects.stream()
                .flatMap(p -> p.getTasks().stream())
                .filter(t -> t.getStatus() == Status.PENDING || t.getStatus() == Status.IN_PROGRESS)
                .count();
        pendingTasksLabel.setText(String.valueOf(pendingTasks));
    }

    /**
     * Calcola e visualizza la prossima scadenza (task non completata in una data
     * futura più prossima).
     */
    private void updateNextDeadline() {
        Calendar<?> cal = controller.getCalendar();
        LocalDate today = LocalDate.now();
        LocalDate nextDeadline = cal.getDays().stream()
                .filter(d -> d.getId() instanceof LocalDate date && !date.isBefore(today))
                .filter(d -> d.getTasks().stream()
                        .anyMatch(t -> t.getStatus() != Status.COMPLETED))
                .map(d -> (LocalDate) d.getId())
                .sorted()
                .findFirst()
                .orElse(null);

        if (nextDeadline != null) {
            nextDeadlineLabel.setText(nextDeadline.format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            nextDeadlineLabel.setText("Nessuna");
        }
    }

    /**
     * Configura i listener per le interazioni dell'utente (es. doppio click sui
     * report).
     */
    private void setupListeners() {
        reportsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && reportsListView.getSelectionModel().getSelectedItem() != null) {
                openReport(reportsListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Carica la lista dei report in modo asincrono per non bloccare l'interfaccia.
     */
    private void loadReportsAsync() {
        CompletableFuture.supplyAsync(() -> controller.loadReportsFromDirectory())
                .thenAccept(reports -> Platform.runLater(
                        () -> reportsListView.getItems().setAll(reports)));
    }

    /**
     * Handler per la creazione di un nuovo report di progetto.
     * Mostra un dialog di selezione e, se confermato, genera e apre il report.
     */
    @FXML
    private void onNewProjectReport() {
        List<Project<?>> projects = controller.getProjects();
        FormDialogFactory.showProjectSelectionDialog(projects)
                .ifPresent(project -> {
                    Report report = controller.createProjectReport(project.getId().toString());
                    if (report != null) {
                        reportsListView.getItems().add(report);
                        openReport(report);
                    }
                });
    }

    /**
     * Handler per la creazione di un nuovo report su intervallo di date.
     * Mostra un dialog di selezione date e, se confermato, genera e apre il report.
     */
    @FXML
    private void onNewIntervalReport() {
        FormDialogFactory.showDateRangeSelectionDialog().ifPresent(pair -> {
            Report report = controller.createIntervalReport(pair.getKey(), pair.getValue());
            if (report != null) {
                reportsListView.getItems().add(report);
                openReport(report);
            }
        });
    }

    /**
     * Apre un report in una nuova scheda tramite il MainController.
     *
     * @param report Il report da visualizzare.
     */
    private void openReport(Report report) {
        if (mainController != null) {
            mainController.openReportTab(report);
        }
    }
}
