package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.Day;
import it.unicam.cs.mpgc.jtime123014.model.Status;
import it.unicam.cs.mpgc.jtime123014.model.Task;
import it.unicam.cs.mpgc.jtime123014.view.factory.UIFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Si occupa di visualizzare un singolo giorno nella lista del calendario.
 * <p>
 * Mostra la data e l'elenco delle attività previste per quel giorno,
 * distinguendo
 * quelle completate da quelle ancora da fare.
 */
public class DayListCell extends ListCell<Day<?>> {
    private final CalendarController controller;
    private final VBox content;
    private final Label dateLabel;
    private final VBox tasksContainer;

    /**
     * Costruisce una nuova cella per la lista dei giorni.
     *
     * @param controller Il controller del calendario, necessario per le callback
     *                   delle azioni (es. elimina, completa).
     */
    public DayListCell(CalendarController controller) {
        super();
        this.controller = Objects.requireNonNull(controller, "Il controller non può essere null");
        dateLabel = new Label();
        dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        tasksContainer = new VBox(5);

        content = new VBox(5, dateLabel, tasksContainer);
        content.setPadding(new javafx.geometry.Insets(10));
    }

    /**
     * Metodo chiamato dal framework JavaFX per aggiornare il contenuto della cella.
     *
     * @param item  L'oggetto Day da visualizzare.
     * @param empty True se la cella è vuota, False altrimenti.
     */
    @Override
    protected void updateItem(Day<?> item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            // Formatta la data in modo leggibile (es. "Lunedì, 14 Febbraio 2026")
            if (item.getId() instanceof LocalDate) {
                dateLabel.setText(
                        ((LocalDate) item.getId()).format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
            } else {
                dateLabel.setText(item.getId().toString());
            }

            // Ricostruisce la lista delle task
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

    /**
     * Crea una riga grafica per una singola task.
     * <p>
     * La riga contiene: nome della task, durata stimata e bottoni per le azioni
     * (completa, modifica, elimina).
     *
     * @param task La task da visualizzare.
     * @param day  Il giorno a cui la task appartiene.
     * @return Un contenitore HBox con gli elementi della riga.
     */
    private HBox createTaskRow(Task<?> task, Day day) {
        Label nameLabel = new Label("• " + task.getName() + " (" + task.getDurationEstimate() + " min)");

        if (task.getStatus() == Status.COMPLETED) {
            nameLabel.setStyle("-fx-strikethrough: true;");
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Bottone per completare la task
        Button completeBtn = UIFactory.createSuccessButton(e -> {
            task.setStatus(Status.COMPLETED);
            controller.refreshCalendar();
        });
        completeBtn.setDisable(task.getStatus() == Status.COMPLETED);

        // Bottone per modificare la task
        Button settingsBtn = UIFactory.createEditButton(e -> {
            controller.showEditTaskDialog(task);
        });

        // Bottone per eliminare la task
        Button deleteBtn = UIFactory.createDeleteButton(e -> {
            day.removeTask(task);
            controller.refreshCalendar();
        });

        HBox row = new HBox(10, nameLabel, spacer, completeBtn, settingsBtn, deleteBtn);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
}
