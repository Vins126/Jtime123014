package it.unicam.cs.mpgc.jtime123014;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import java.util.Optional;
import java.util.UUID;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;

public class ProjectTasksController {

    @FXML
    private Label projectNameLabel;

    @FXML
    private ListView<Task<?>> tasksList;

    private ObservableList<Task<?>> tasksData = FXCollections.observableArrayList();

    private Project<?> currentProject;

    @FXML
    public void initialize() {
        tasksList.setItems(tasksData);
        tasksList.setCellFactory(param -> new TaskListCell());
    }

    public void setProject(Project<?> project) {
        this.currentProject = project;
        projectNameLabel.setText("Progetto: " + project.getName());
        refreshTasks();
    }

    private void refreshTasks() {
        if (currentProject != null) {
            tasksData.setAll(currentProject.getTasks());
        }
    }

    @FXML
    private void handleBack() {
        Context.getInstance().getMainController().loadView("view/ProjectsView.fxml", null);
    }

    @FXML
    private void handleAddTask() {
        showTaskDialog(null);
    }

    private void showTaskDialog(Task<?> taskToEdit) {
        Dialog<SimpleTask> dialog = new Dialog<>();
        dialog.setTitle(taskToEdit == null ? "Nuova Task" : "Modifica Task");
        dialog.setHeaderText(taskToEdit == null ? "Aggiungi una nuova attività" : "Modifica dettagli attività");

        ButtonType saveButtonType = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Nome");
        TextField description = new TextField();
        description.setPromptText("Descrizione");
        TextField duration = new TextField();
        duration.setPromptText("Durata (minuti)");

        if (taskToEdit != null) {
            name.setText(taskToEdit.getName());
            description.setText(taskToEdit.getDescription());
            duration.setText(String.valueOf(taskToEdit.getDurationEstimate()));
        }

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Descrizione:"), 0, 1);
        grid.add(description, 1, 1);
        grid.add(new Label("Durata (min):"), 0, 2);
        grid.add(duration, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String tName = name.getText();
                    String tDesc = description.getText();
                    int tDur = Integer.parseInt(duration.getText());

                    if (taskToEdit == null) {
                        return new SimpleTask(UUID.randomUUID(), tName, tDesc, tDur);
                    } else {
                        taskToEdit.setName(tName);
                        taskToEdit.setDescription(tDesc);
                        taskToEdit.setDurationEstimate(tDur);
                        return null; // return null because we modified in place
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore di input");
                    alert.setHeaderText("Durata non valida");
                    alert.setContentText("Per favore, inserisci un numero intero per la durata.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        Optional<SimpleTask> result = dialog.showAndWait();

        result.ifPresent(newTask -> {
            if (newTask != null) {
                ((it.unicam.cs.mpgc.jtime123014.SimpleProject) currentProject).insertTask(newTask);
            }
            refreshTasks();
        });

        if (taskToEdit != null) {
            refreshTasks();
        }
    }

    // Inner class for custom cell
    private class TaskListCell extends ListCell<Task<?>> {
        private HBox content;
        private Label nameLabel;
        private Label statusLabel;
        private Button completeBtn;
        private Button settingsBtn;
        private Button deleteBtn;
        private Region spacer;

        public TaskListCell() {
            super();
            nameLabel = new Label();
            statusLabel = new Label();
            statusLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 10px;");

            completeBtn = new Button("✓");
            completeBtn.getStyleClass().addAll("button", "btn-success");

            settingsBtn = new Button("⚙");
            settingsBtn.getStyleClass().addAll("button", "btn-edit");

            deleteBtn = new Button("🗑");
            deleteBtn.getStyleClass().addAll("button", "btn-delete");

            spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            content = new HBox(10, nameLabel, statusLabel, spacer, completeBtn, settingsBtn, deleteBtn);
            content.setAlignment(Pos.CENTER_LEFT);
            content.setPadding(new Insets(5));

            completeBtn.setOnAction(e -> {
                Task<?> task = getItem();
                if (task != null) {
                    task.setStatus(it.unicam.cs.mpgc.jtime123014.Status.COMPLETED);
                    tasksList.refresh();
                }
            });

            settingsBtn.setOnAction(e -> {
                Task<?> task = getItem();
                if (task != null) {
                    showTaskDialog(task);
                }
            });

            deleteBtn.setOnAction(e -> {
                Task<?> task = getItem();
                if (task != null) {
                    currentProject.getTasks().remove(task);
                    tasksData.remove(task);
                }
            });
        }

        @Override
        protected void updateItem(Task<?> item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                nameLabel.setText(item.getName());
                statusLabel.setText(item.getStatus().toString());

                if (item.getStatus() == it.unicam.cs.mpgc.jtime123014.Status.COMPLETED) {
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
}
