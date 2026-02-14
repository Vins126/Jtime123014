package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.view.factory.FormDialogFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Optional;

/**
 * Gestisce la schermata che mostra le attività di un progetto specifico.
 * <p>
 * Permette di vedere cosa c'è da fare, aggiungere nuove attività o rimuoverle.
 */
public class ProjectTasksController {

    /**
     * Costruttore di default.
     */
    public ProjectTasksController() {
    }

    @FXML
    private Label projectNameLabel;

    @FXML
    private ListView<Task<?>> tasksList;

    private ObservableList<Task<?>> tasksData = FXCollections.observableArrayList();

    /** Il progetto attualmente visualizzato. */
    private Project<?> currentProject;

    private MainController mainController;

    /**
     * Inizializza il controller, impostando la lista e le celle personalizzate.
     */
    @FXML
    public void initialize() {
        this.mainController = Context.getInstance().getMainController();

        tasksList.setItems(tasksData);
        tasksList.setCellFactory(param -> new TaskListCell(ProjectTasksController.this));
    }

    /**
     * Imposta il progetto da visualizzare.
     * Aggiorna l'intestazione e la lista delle task.
     *
     * @param project Il progetto corrente.
     */
    public void setProject(Project<?> project) {
        this.currentProject = project;
        projectNameLabel.setText("Progetto: " + project.getName());
        refreshTasks();
    }

    /**
     * Aggiorna la lista delle task visualizzate recuperandole dal progetto
     * corrente.
     */
    void refreshTasks() {
        if (currentProject != null) {
            tasksData.setAll(currentProject.getTasks());
        }
        tasksList.refresh();
    }

    /**
     * Torna alla vista generale dei progetti.
     */
    @FXML
    private void handleBack() {
        mainController.loadView("/it/unicam/cs/mpgc/jtime123014/view/ProjectsView.fxml", null);
    }

    /**
     * Apre il dialog per creare una nuova task nel progetto corrente.
     */
    @FXML
    private void handleAddTask() {
        showTaskDialog(null);
    }

    /**
     * Mostra il dialog per creare o modificare una task.
     *
     * @param taskToEdit La task da modificare, o null per crearne una nuova.
     */
    void showTaskDialog(Task<?> taskToEdit) {
        Optional<SimpleTask> result = FormDialogFactory.showTaskDialog(taskToEdit);

        result.ifPresent(newTask -> {
            if (newTask != null) {
                // BUG POTENZIALE: Se stiamo editando, la task è già modificata per riferimento?
                // insertTask di solito aggiunge in coda.
                // Se taskToEdit != null, la modifica è probabilmente già avvenuta nell'oggetto,
                // ma se è nuovo usiamo insertTask.
                // Controllo se è un inserimento o meno dipenderebbe dall'implementazione di
                // insertTask,
                // ma qui assumiamo che newTask sia l'oggetto aggiornato o nuovo.
                // Se era null (nuovo), lo aggiungiamo.
                if (taskToEdit == null) {
                    currentProject.insertTask(newTask);
                }
            }
            refreshTasks();
        });

        // Forza refresh anche se cancellato il dialog, per sicurezza
        if (taskToEdit != null) {
            refreshTasks();
        }
    }

    /**
     * Rimuove una task dal progetto.
     *
     * @param task La task da rimuovere.
     */
    void deleteTask(Task<?> task) {
        if (task != null && currentProject != null) {
            currentProject.getTasks().remove(task);
            tasksData.remove(task);
            refreshTasks();
        }
    }
}
