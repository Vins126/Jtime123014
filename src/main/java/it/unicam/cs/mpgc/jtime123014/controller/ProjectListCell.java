package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.Project;
import it.unicam.cs.mpgc.jtime123014.util.UIConstants;
import it.unicam.cs.mpgc.jtime123014.view.factory.UIFactory;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.Objects;

/**
 * Si occupa di visualizzare un singolo progetto nella lista.
 * 
 * Mostra il nome del progetto, quante cose ci sono da fare e i pulsanti per
 * gestirlo (apri, modifica, elimina).
 */
public class ProjectListCell extends ListCell<Project<?>> {
    private final ProjectsController controller;
    private final HBox content;
    private final Label nameLabel;
    private final Label pendingTasksLabel;
    private final Button btnOpen;
    private final Button btnSettings;
    private final Button btnDelete;

    /**
     * Costruisce una cella per la lista progetti.
     *
     * @param controller Il controller dei progetti, usato per gestire gli eventi
     *                   dei bottoni.
     */
    public ProjectListCell(ProjectsController controller) {
        super();
        this.controller = Objects.requireNonNull(controller, "Il controller non può essere null");

        nameLabel = new Label();
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        pendingTasksLabel = new Label();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Bottone Apri
        btnOpen = UIFactory.createOpenButton(UIConstants.BTN_OPEN, e -> {
            Project<?> item = getItem();
            if (item != null) {
                controller.openProject(item);
            }
        });

        // Bottone Impostazioni (Edit)
        btnSettings = UIFactory.createEditLabelButton(UIConstants.BTN_SETTINGS, e -> {
            Project<?> item = getItem();
            if (item != null) {
                controller.openSettings(item);
            }
        });

        // Bottone Elimina
        btnDelete = UIFactory.createDeleteLabelButton(UIConstants.BTN_DELETE, e -> {
            Project<?> item = getItem();
            if (item != null) {
                controller.deleteProject(item);
            }
        });

        content = new HBox(10, nameLabel, pendingTasksLabel, spacer, btnOpen, btnSettings, btnDelete);
        content.setPadding(new javafx.geometry.Insets(5));
        content.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    }

    /**
     * Aggiorna il contenuto della cella con i dati del Progetto.
     *
     * @param item  Il progetto da mostrare.
     * @param empty True se la cella è vuota, False altrimenti.
     */
    @Override
    protected void updateItem(Project<?> item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            nameLabel.setText(item.getName());
            pendingTasksLabel.setText("Task pendenti: " + item.getPendingTasks().size());
            setGraphic(content);
        }
    }
}
