package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.Status;
import it.unicam.cs.mpgc.jtime123014.model.Task;
import it.unicam.cs.mpgc.jtime123014.view.factory.UIFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.Objects;

/**
 * Visualizza una singola attività nella lista.
 * <p>
 * Mostra il nome della task, se è stata fatta o no, e i pulsanti per
 * segnarla come completata, modificarla o cancellarla.
 */
public class TaskListCell extends ListCell<Task<?>> {
    private final ProjectTasksController controller;
    private final HBox content;
    private final Label nameLabel;
    private final Label statusLabel;
    private final Button completeBtn;
    private final Button settingsBtn;
    private final Button deleteBtn;
    private final Region spacer;

    /**
     * Costruisce una cella per la lista task.
     *
     * @param controller Il controller delle task, usato per gestire le azioni.
     */
    public TaskListCell(ProjectTasksController controller) {
        super();
        this.controller = Objects.requireNonNull(controller, "Il controller non può essere null");

        nameLabel = new Label();
        statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 10px;");

        // Bottone Completa (Check)
        completeBtn = UIFactory.createSuccessButton(e -> {
            Task<?> task = getItem();
            if (task != null) {
                task.setStatus(Status.COMPLETED);
                controller.refreshTasks();
            }
        });

        // Bottone Modifica
        settingsBtn = UIFactory.createEditButton(e -> {
            Task<?> task = getItem();
            if (task != null) {
                controller.showTaskDialog(task);
            }
        });

        // Bottone Elimina
        deleteBtn = UIFactory.createDeleteButton(e -> {
            Task<?> task = getItem();
            if (task != null) {
                controller.deleteTask(task);
            }
        });

        spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        content = new HBox(10, nameLabel, statusLabel, spacer, completeBtn, settingsBtn, deleteBtn);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(5));
    }

    /**
     * Aggiorna il contenuto visuale della cella in base allo stato della task.
     * <p>
     * Se la task è completata, applica uno stile barrato (strikethrough) al testo
     * e disabilita il bottone di completamento.
     *
     * @param item  La task da mostrare.
     * @param empty True se la cella è vuota.
     */
    @Override
    protected void updateItem(Task<?> item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            nameLabel.setText(item.getName());
            statusLabel.setText(item.getStatus().toString());

            if (item.getStatus() == Status.COMPLETED) {
                nameLabel.setStyle("-fx-strikethrough: true;");
                completeBtn.setDisable(true);
            } else {
                nameLabel.setStyle("");
                completeBtn.setDisable(false);
            }

            setGraphic(content);
        }
    }
}
