package it.unicam.cs.mpgc.jtime123014.view.factory;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.util.UIConstants;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Factory per Dialog con form domain-specific (progetti, task, selezione date).
 * <p>
 * Responsabilità singola: costruzione di finestre di dialogo con form di input
 * legati alle entità del dominio JTime.
 * Per gli alert generici (errore, conferma, info) usare {@link DialogFactory}.
 */
public class FormDialogFactory {

    /**
     * Costruttore privato per prevenire l'istanziazione di questa classe di
     * utilità.
     */
    private FormDialogFactory() {
        throw new UnsupportedOperationException("Questa è una classe di utilità e non può essere istanziata.");
    }

    // ── Selezione progetto ───────────────────────────────────────────────

    public static Optional<Project<?>> showProjectSelectionDialog(List<Project<?>> projects) {
        ChoiceDialog<Project<?>> dialog = new ChoiceDialog<>(null, projects);
        dialog.setTitle("Selezione Progetto");
        dialog.setHeaderText("Seleziona un progetto per il report");
        dialog.setContentText("Progetto:");
        return dialog.showAndWait();
    }

    // ── Selezione intervallo date ────────────────────────────────────────

    /**
     * Mostra un dialog per selezionare un intervallo di date.
     *
     * @return un Optional contenente la coppia di date (inizio, fine) se
     *         confermato.
     */
    public static Optional<Pair<LocalDate, LocalDate>> showDateRangeSelectionDialog() {
        Dialog<Pair<LocalDate, LocalDate>> dialog = new Dialog<>();
        dialog.setTitle("Selezione Periodo");
        dialog.setHeaderText("Seleziona l'intervallo di date per il report");

        ButtonType genButtonType = new ButtonType("Genera", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(genButtonType, ButtonType.CANCEL);

        GridPane grid = UIFactory.createFormGrid();

        DatePicker startDate = new DatePicker();
        startDate.setPromptText("Data Inizio");
        DatePicker endDate = new DatePicker();
        endDate.setPromptText("Data Fine");

        grid.add(new Label("Da:"), 0, 0);
        grid.add(startDate, 1, 0);
        grid.add(new Label("A:"), 0, 1);
        grid.add(endDate, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == genButtonType) {
                return new Pair<>(startDate.getValue(), endDate.getValue());
            }
            return null;
        });

        return dialog.showAndWait();
    }

    // ── Modifica task (inline edit) ──────────────────────────────────────

    /**
     * Mostra un dialog per modificare le proprietà di una task esistente.
     * Aggiorna direttamente l'oggetto task per riferimento.
     */
    public static void showEditTaskDialog(Task<?> task) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(UIConstants.TITLE_EDIT_TASK);
        dialog.setHeaderText(UIConstants.HEADER_EDIT_TASK);

        ButtonType saveButtonType = new ButtonType(UIConstants.BTN_SAVE, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = UIFactory.createFormGrid();

        TextField name = new TextField(task.getName());
        TextField description = new TextField(task.getDescription());
        TextField duration = new TextField(String.valueOf(task.getDurationEstimate()));

        grid.add(new Label(UIConstants.LBL_NAME), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label(UIConstants.LBL_DESCRIPTION), 0, 1);
        grid.add(description, 1, 1);
        grid.add(new Label(UIConstants.LBL_DURATION_MIN), 0, 2);
        grid.add(duration, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                task.setName(name.getText());
                task.setDescription(description.getText());
                try {
                    task.setDurationEstimate(Integer.parseInt(duration.getText()));
                } catch (NumberFormatException ignored) {
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    // ── Dialog progetto (crea/modifica) ──────────────────────────────────

    /**
     * Mostra un dialog per creare o modificare un progetto.
     *
     * @param projectToEdit il progetto da modificare, o {@code null} per crearne
     *                      uno nuovo.
     * @return un Optional contenente il progetto creato/modificato.
     */
    public static Optional<Project<UUID>> showProjectDialog(Project<?> projectToEdit) {
        Dialog<Project<UUID>> dialog = new Dialog<>();
        dialog.setTitle(projectToEdit == null ? UIConstants.TITLE_NEW_PROJECT : UIConstants.TITLE_EDIT_PROJECT);
        dialog.setHeaderText(projectToEdit == null ? "Crea un nuovo progetto" : "Modifica dettagli progetto");

        ButtonType saveButtonType = new ButtonType(UIConstants.BTN_SAVE, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = UIFactory.createFormGrid();

        TextField name = new TextField();
        name.setPromptText(UIConstants.PROMPT_NAME);
        TextField description = new TextField();
        description.setPromptText(UIConstants.PROMPT_DESCRIPTION);
        ComboBox<Priority> priority = new ComboBox<>();
        priority.getItems().setAll(Priority.values());
        priority.setValue(Priority.MEDIUM);

        if (projectToEdit != null) {
            name.setText(projectToEdit.getName());
            description.setText(projectToEdit.getDescription());
            priority.setValue(projectToEdit.getPriority());
        }

        grid.add(new Label(UIConstants.LBL_NAME), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label(UIConstants.LBL_DESCRIPTION), 0, 1);
        grid.add(description, 1, 1);
        grid.add(new Label(UIConstants.LBL_PRIORITY), 0, 2);
        grid.add(priority, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (projectToEdit == null) {
                    return new SimpleProject(UUID.randomUUID(), name.getText(),
                            description.getText(), priority.getValue());
                } else {
                    projectToEdit.setName(name.getText());
                    projectToEdit.setDescription(description.getText());
                    projectToEdit.setPriority(priority.getValue());
                    @SuppressWarnings("unchecked")
                    Project<UUID> casted = (Project<UUID>) projectToEdit;
                    return casted;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }

    // ── Dialog task (crea/modifica) ──────────────────────────────────────

    /**
     * Mostra un dialog per creare o modificare una task.
     *
     * @param taskToEdit la task da modificare, o {@code null} per crearne una
     *                   nuova.
     * @return un Optional contenente la nuova task, o empty se annullato/modificato
     *         in-place.
     */
    public static Optional<SimpleTask> showTaskDialog(Task<?> taskToEdit) {
        Dialog<SimpleTask> dialog = new Dialog<>();
        dialog.setTitle(taskToEdit == null ? UIConstants.TITLE_NEW_TASK : UIConstants.TITLE_EDIT_TASK);
        dialog.setHeaderText(taskToEdit == null ? UIConstants.HEADER_NEW_TASK : UIConstants.HEADER_EDIT_TASK);

        ButtonType saveButtonType = new ButtonType(UIConstants.BTN_SAVE, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = UIFactory.createFormGrid();

        TextField name = new TextField();
        name.setPromptText(UIConstants.PROMPT_NAME);
        TextField description = new TextField();
        description.setPromptText(UIConstants.PROMPT_DESCRIPTION);
        TextField duration = new TextField();
        duration.setPromptText(UIConstants.PROMPT_DURATION);

        if (taskToEdit != null) {
            name.setText(taskToEdit.getName());
            description.setText(taskToEdit.getDescription());
            duration.setText(String.valueOf(taskToEdit.getDurationEstimate()));
        }

        grid.add(new Label(UIConstants.LBL_NAME), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label(UIConstants.LBL_DESCRIPTION), 0, 1);
        grid.add(description, 1, 1);
        grid.add(new Label(UIConstants.LBL_DURATION_MIN), 0, 2);
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
                        return null;
                    }
                } catch (NumberFormatException e) {
                    DialogFactory.showError(UIConstants.ERR_INPUT_TITLE, UIConstants.ERR_DURATION_INVALID,
                            UIConstants.ERR_DURATION_MSG);
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
