package it.unicam.cs.mpgc.jtime123014;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.geometry.Insets;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class ProjectsController {

    @FXML
    private ListView<Project<?>> projectsList;

    private ObservableList<Project<?>> projectsData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Load projects from Controller
        refreshProjects();

        projectsList.setItems(projectsData);
        projectsList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Project<?>> call(ListView<Project<?>> param) {
                return new ProjectListCell();
            }
        });
    }

    private void refreshProjects() {
        projectsData.setAll(Context.getInstance().getController().getProjects());
    }

    @FXML
    private void handleAddProject() {
        showProjectDialog(null);
    }

    private void showProjectDialog(Project<?> projectToEdit) {
        Dialog<Project<UUID>> dialog = new Dialog<>();
        dialog.setTitle(projectToEdit == null ? "Nuovo Progetto" : "Modifica Progetto");
        dialog.setHeaderText(projectToEdit == null ? "Crea un nuovo progetto" : "Modifica dettagli progetto");

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
        ComboBox<it.unicam.cs.mpgc.jtime123014.Priority> priority = new ComboBox<>();
        priority.getItems().setAll(it.unicam.cs.mpgc.jtime123014.Priority.values());
        priority.setValue(it.unicam.cs.mpgc.jtime123014.Priority.MEDIUM);

        if (projectToEdit != null) {
            name.setText(projectToEdit.getName());
            description.setText(projectToEdit.getDescription());
            priority.setValue(projectToEdit.getPriority());
        }

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Descrizione:"), 0, 1);
        grid.add(description, 1, 1);
        grid.add(new Label("Priorità:"), 0, 2);
        grid.add(priority, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (projectToEdit == null) {
                    return new SimpleProject(UUID.randomUUID(), name.getText(), description.getText(),
                            priority.getValue());
                } else {
                    projectToEdit.setName(name.getText());
                    projectToEdit.setDescription(description.getText());
                    projectToEdit.setPriority(priority.getValue());
                    @SuppressWarnings("unchecked")
                    Project<UUID> castedProject = (Project<UUID>) projectToEdit;
                    return castedProject;
                }
            }
            return null;
        });

        Optional<Project<UUID>> result = dialog.showAndWait();

        result.ifPresent(project -> {
            if (projectToEdit == null) {
                Context.getInstance().getController().addProject(project);
            }
            // If editing, the object is already updated by reference, but we refresh the
            // list to show changes
            refreshProjects();
        });
    }

    // Custom Cell
    private class ProjectListCell extends ListCell<Project<?>> {
        private HBox content;
        private Label nameLabel;
        private Label pendingTasksLabel;
        private Button btnOpen;
        private Button btnSettings;

        public ProjectListCell() {
            super();
            nameLabel = new Label();
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            pendingTasksLabel = new Label();

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            btnOpen = new Button("Apri");
            btnOpen.getStyleClass().addAll("button", "btn-open");
            btnOpen.setOnAction(e -> openProject(getItem()));

            btnSettings = new Button("Impostazioni");
            btnSettings.getStyleClass().addAll("button", "btn-edit");
            btnSettings.setOnAction(e -> openSettings(getItem()));

            Button btnDelete = new Button("Elimina");
            btnDelete.getStyleClass().addAll("button", "btn-delete");
            btnDelete.setOnAction(e -> deleteProject(getItem()));

            content = new HBox(10, nameLabel, pendingTasksLabel, spacer, btnOpen, btnSettings, btnDelete);
            content.setPadding(new javafx.geometry.Insets(5));
            content.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        }

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

    private void openProject(Project<?> project) {
        Context.getInstance().getMainController().loadView("view/ProjectTasksView.fxml",
                (Consumer<ProjectTasksController>) controller -> {
                    controller.setProject(project);
                });
    }

    private void openSettings(Project<?> project) {
        showProjectDialog(project);
    }

    private void deleteProject(Project<?> project) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Elimina Progetto");
        alert.setHeaderText("Sei sicuro di voler eliminare questo progetto?");
        alert.setContentText("Questa azione non può essere annullata.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Context.getInstance().getController().removeProject(project);
            refreshProjects();
        }
    }
}
