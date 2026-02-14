package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.util.UIConstants;
import it.unicam.cs.mpgc.jtime123014.view.factory.DialogFactory;
import it.unicam.cs.mpgc.jtime123014.view.factory.FormDialogFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.util.Callback;
import javafx.application.Platform;
import java.util.Optional;

public class CalendarController {

    /**
     * Costruttore di default.
     */
    public CalendarController() {
    }

    @FXML
    private ListView<Day<?>> calendarList;

    private ObservableList<Day<?>> calendarData = FXCollections.observableArrayList();

    /** Riferimento al controller applicativo, ottenuto tramite Context. */
    private AppController controller;

    /**
     * Prepara la schermata del calendario all'avvio.
     * Recupera i dati necessari e imposta come devono apparire i giorni nella
     * lista.
     */
    @FXML
    public void initialize() {
        this.controller = Context.getInstance().getController();
        refreshCalendar();

        calendarList.setItems(calendarData);
        calendarList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Day<?>> call(ListView<Day<?>> param) {
                return new DayListCell(CalendarController.this);
            }
        });
    }

    /**
     * Gestisce l'azione di scheduling (click sul bottone "Pianifica").
     * Richiede all'utente le ore di lavoro giornaliere tramite un dialog e avvia lo
     * scheduling asincrono.
     */
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
                    DialogFactory.showError(UIConstants.TITLE_ERROR, null, "Inserisci un numero di ore valido (1-24).");
                    return;
                }
                int minutes = hours * 60;

                System.out.println("Richiesta scheduling avviata...");

                controller.scheduleAsync(minutes, () -> {
                    Platform.runLater(() -> {
                        refreshCalendar();
                        System.out.println("UI Aggiornata dopo scheduling.");
                        DialogFactory.showInformation("Scheduling Completato", null,
                                "L'operazione di scheduling è stata completata.");
                    });
                });

            } catch (NumberFormatException e) {
                DialogFactory.showError(UIConstants.TITLE_ERROR, null, "Inserisci un numero intero valido.");
            }
        });
    }

    /**
     * Aggiorna la lista dei giorni visualizzati nel calendario recuperando i dati
     * aggiornati dal controller.
     */
    void refreshCalendar() {
        calendarData.setAll(controller.getCalendar().getDays());
    }

    /**
     * Apre il dialog per la modifica di una task esistente.
     *
     * @param task La task da modificare.
     */
    void showEditTaskDialog(Task<?> task) {
        FormDialogFactory.showEditTaskDialog(task);
        refreshCalendar();
    }
}
