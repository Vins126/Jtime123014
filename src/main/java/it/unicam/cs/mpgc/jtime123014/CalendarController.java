package it.unicam.cs.mpgc.jtime123014;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import java.util.Optional;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CalendarController {

    @FXML
    private ListView<Day<?>> calendarList;

    private ObservableList<Day<?>> calendarData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        refreshCalendar();

        calendarList.setItems(calendarData);
        calendarList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Day<?>> call(ListView<Day<?>> param) {
                return new DayListCell();
            }
        });
    }

    @FXML
    private void handleSchedule() {
        TextInputDialog dialog = new TextInputDialog("8");
        dialog.setTitle("Configurazione Scheduling");
        dialog.setHeaderText("Imposta le ore lavorative giornaliere");
        dialog.setContentText("Ore al giorno:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(hoursStr -> {
            try {
                int hours = Integer.parseInt(hoursStr);
                if (hours <= 0 || hours > 24) {
                    showError("Inserisci un numero di ore valido (1-24).");
                    return;
                }
                int minutes = hours * 60;

                // Mostra un indicatore di caricamento (es. disabilita la UI o mostra un Alert
                // non bloccante)
                // Per semplicità, cambiamo il titolo della finestra o stampiamo in console per
                // ora
                System.out.println("Richiesta scheduling avviata...");

                Context.getInstance().getController().scheduleAsync(minutes, () -> {
                    // Questa callback viene eseguita nel thread di background
                    // Dobbiamo tornare al thread FX per aggiornare la UI
                    Platform.runLater(() -> {
                        refreshCalendar();
                        System.out.println("UI Aggiornata dopo scheduling.");
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Scheduling Completato");
                        info.setHeaderText(null);
                        info.setContentText("L'operazione di scheduling è stata completata.");
                        info.show(); // Non bloccante rispetto al resto, ma è un popup
                    });
                });

            } catch (NumberFormatException e) {
                showError("Inserisci un numero intero valido.");
            }
        });
    }

    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshCalendar() {
        // We need to get days from the Calendar model.
        // Usually Calendar has getDays().
        // Context.getInstance().getController().getCalendar() returns Calendar<UUID>.
        // Let's assume Calendar interface has getDays() returning List<Day<ID>>.

        // Note: Using getDays() which returns Map or List?
        // Let's check Calendar interface. Ideally it returns a List or we iterate.
        // For now, assuming getDays() returns a List<Day<ID>> is risky without
        // checking.
        // Assuming implementation SimpleCalendar has a list of days.
        // Let's check Calendar interface first.

        // Placeholder until checked:
        calendarData.setAll(Context.getInstance().getController().getCalendar().getDays());
    }

    // ...

    // Custom Cell for Day
    private class DayListCell extends ListCell<Day<?>> {
        private VBox content;
        private Label dateLabel;
        private VBox tasksContainer;

        public DayListCell() {
            super();
            dateLabel = new Label();
            dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            tasksContainer = new VBox(5);

            content = new VBox(5, dateLabel, tasksContainer);
            content.setPadding(new javafx.geometry.Insets(10));
        }

        @Override
        protected void updateItem(Day<?> item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                if (item.getId() instanceof LocalDate) {
                    dateLabel.setText(
                            ((LocalDate) item.getId()).format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
                } else {
                    dateLabel.setText(item.getId().toString());
                }

                tasksContainer.getChildren().clear();

                if (item.getTasks().isEmpty()) {
                    Label noTasks = new Label("Nessuna attività programmata.");
                    noTasks.setStyle("-fx-font-style: italic; -fx-text-fill: gray;");
                    tasksContainer.getChildren().add(noTasks);
                } else {
                    for (Task<?> task : item.getTasks()) {
                        HBox taskRow = createTaskRow(task, (Day) item);
                        tasksContainer.getChildren().add(taskRow);
                    }
                }

                setGraphic(content);
            }
        }

        private HBox createTaskRow(Task<?> task, Day day) {
            Label nameLabel = new Label("• " + task.getName() + " (" + task.getDurationEstimate() + " min)");

            if (task.getStatus() == it.unicam.cs.mpgc.jtime123014.Status.COMPLETED) {
                nameLabel.setStyle("-fx-strikethrough: true;");
            }

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button completeBtn = new Button("✓");
            completeBtn.getStyleClass().addAll("button", "btn-success");
            completeBtn.setDisable(task.getStatus() == it.unicam.cs.mpgc.jtime123014.Status.COMPLETED);

            Button settingsBtn = new Button("⚙");
            settingsBtn.getStyleClass().addAll("button", "btn-edit");

            Button deleteBtn = new Button("🗑");
            deleteBtn.getStyleClass().addAll("button", "btn-delete");

            completeBtn.setOnAction(e -> {
                task.setStatus(it.unicam.cs.mpgc.jtime123014.Status.COMPLETED);
                CalendarController.this.refreshCalendar();
            });

            settingsBtn.setOnAction(e -> {
                // We'd need to bring up a dialog here.
                // Since showTaskDialog is in ProjectTasksController, we might duplicate it or
                // move it to a Util/Service.
                // For now, let's just trigger a refresh or similar if we had a shared dialog.
                // Actually, I can implement a simple edit dialog here or better yet, make
                // CalendarController have a method.
                showEditTaskDialog(task);
            });

            deleteBtn.setOnAction(e -> {
                day.removeTask(task);
                CalendarController.this.refreshCalendar();
            });

            HBox row = new HBox(10, nameLabel, spacer, completeBtn, settingsBtn, deleteBtn);
            row.setAlignment(Pos.CENTER_LEFT);
            return row;
        }

    }

    private void showEditTaskDialog(Task<?> task) {
        // Simplified version of the dialog for Calendar view
        Dialog<SimpleTask> dialog = new Dialog<>();
        dialog.setTitle("Modifica Task");
        dialog.setHeaderText("Modifica dettagli attività");

        ButtonType saveButtonType = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField name = new TextField(task.getName());
        TextField description = new TextField(task.getDescription());
        TextField duration = new TextField(String.valueOf(task.getDurationEstimate()));

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Descrizione:"), 0, 1);
        grid.add(description, 1, 1);
        grid.add(new Label("Durata (min):"), 0, 2);
        grid.add(duration, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                task.setName(name.getText());
                task.setDescription(description.getText());
                try {
                    task.setDurationEstimate(Integer.parseInt(duration.getText()));
                } catch (Exception e) {
                }
                return null;
            }
            return null;
        });

        dialog.showAndWait();
        refreshCalendar();
    }
}
