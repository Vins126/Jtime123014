package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.view.factory.DialogFactory;
import it.unicam.cs.mpgc.jtime123014.view.factory.FormDialogFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Gestisce la schermata con l'elenco di tutti i progetti.
 * 
 * Permette di vedere i progetti esistenti, crearne di nuovi, modificarli o
 * eliminarli.
 */
public class ProjectsController {

    /**
     * Costruttore di default.
     */
    public ProjectsController() {
    }

    @FXML
    private ListView<Project<?>> projectsList;

    private ObservableList<Project<?>> projectsData = FXCollections.observableArrayList();

    private AppController controller;

    private MainController mainController;

    /**
     * Inizializza il controller.
     * Configura le dipendenze, popola la lista e imposta il factory delle celle.
     */
    @FXML
    public void initialize() {
        this.controller = Context.getInstance().getController();
        this.mainController = Context.getInstance().getMainController();

        refreshProjects();

        projectsList.setItems(projectsData);
        projectsList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Project<?>> call(ListView<Project<?>> param) {
                return new ProjectListCell(ProjectsController.this);
            }
        });
    }

    /**
     * Ricarica la lista dei progetti dal controller e aggiorna la UI.
     */
    private void refreshProjects() {
        projectsData.setAll(controller.getProjects());
    }

    /**
     * Handler per il bottone "Nuovo Progetto".
     * Mostra il dialog di creazione.
     */
    @FXML
    private void handleAddProject() {
        showProjectDialog(null);
    }

    /**
     * Mostra un dialog per creare o modificare un progetto.
     *
     * @param projectToEdit Il progetto da modificare, o null per crearne uno nuovo.
     */
    private void showProjectDialog(Project<?> projectToEdit) {
        Optional<Project<UUID>> result = FormDialogFactory.showProjectDialog(projectToEdit);

        result.ifPresent(project -> {
            if (projectToEdit == null) {
                controller.addProject(project);
            }
            // Se stiamo modificando, l'oggetto è aggiornato per riferimento,
            // ma refresh aiuta a riflettere modifiche visuali (es. nome).
            refreshProjects();
        });
    }

    /**
     * Apre la vista dettagliata con l'elenco delle task per il progetto
     * selezionato.
     *
     * @param project Il progetto di cui visualizzare le task.
     */
    void openProject(Project<?> project) {
        mainController.loadView("/it/unicam/cs/mpgc/jtime123014/view/ProjectTasksView.fxml",
                (Consumer<ProjectTasksController>) ctrl -> ctrl.setProject(project));
    }

    /**
     * Apre il dialog delle impostazioni (modifica) per un progetto.
     *
     * @param project Il progetto da modificare.
     */
    void openSettings(Project<?> project) {
        showProjectDialog(project);
    }

    /**
     * Avvia la procedura di eliminazione di un progetto, chiedendo conferma.
     *
     * @param project Il progetto da eliminare.
     */
    void deleteProject(Project<?> project) {
        boolean confirmed = DialogFactory.showConfirmation(
                "Elimina Progetto",
                "Sei sicuro di voler eliminare questo progetto?",
                "Questa azione non può essere annullata.");

        if (confirmed) {
            controller.removeProject(project);
            refreshProjects();
        }
    }
}
