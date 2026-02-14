package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.Report;
import it.unicam.cs.mpgc.jtime123014.view.factory.DialogFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Gestisce la finestra principale dell'applicazione.
 * <p>
 * Si occupa di far funzionare la navigazione tra le varie schermate (Home,
 * Calendario, Progetti) e di aprire le schede per visualizzare i report.
 */
public class MainController {

    /**
     * Costruttore di default.
     */
    public MainController() {
    }

    @FXML
    private javafx.scene.control.TabPane mainTabPane;

    @FXML
    private javafx.scene.control.Tab dashboardTab;

    @FXML
    private BorderPane contentArea;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnCalendar;

    @FXML
    private Button btnProjects;

    /** Riferimento cached al Context. */
    private final Context context = Context.getInstance();

    /**
     * Inizializza il controller principale.
     * Registra se stesso nel Context per essere accessibile da altri controller
     * e carica la vista predefinita (Home).
     */
    @FXML
    public void initialize() {
        // Registrazione per la navigazione inversa
        context.setMainController(this);
        // Caricamento vista di default
        showHome();
    }

    /**
     * Mostra la vista "Home" nell'area centrale e aggiorna lo stato dei bottoni
     * laterali.
     */
    @FXML
    private void showHome() {
        loadView("/it/unicam/cs/mpgc/jtime123014/view/HomeView.fxml");
        if (btnHome != null)
            setActiveButton(btnHome);
    }

    /**
     * Mostra la vista "Calendario" nell'area centrale.
     */
    @FXML
    private void showCalendar() {
        loadView("/it/unicam/cs/mpgc/jtime123014/view/CalendarView.fxml");
        if (btnCalendar != null)
            setActiveButton(btnCalendar);
    }

    /**
     * Mostra la vista "Progetti" nell'area centrale.
     */
    @FXML
    private void showProjects() {
        loadView("/it/unicam/cs/mpgc/jtime123014/view/ProjectsView.fxml");
        if (btnProjects != null)
            setActiveButton(btnProjects);
    }

    /**
     * Carica una vista FXML nell'area centrale della finestra.
     * Permette di configurare il controller della vista caricata tramite una
     * callback.
     *
     * @param fxmlFile        Il percorso del file FXML da caricare.
     * @param controllerSetup Un consumatore per configurare il controller (o null).
     * @param <T>             Il tipo del controller atteso.
     */
    public <T> void loadView(String fxmlFile, Consumer<T> controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();

            if (controllerSetup != null) {
                T controller = loader.getController();
                controllerSetup.accept(controller);
            }

            contentArea.setCenter(view);

            // Assicura che il focus sia sul tab principale (Dashboard)
            mainTabPane.getSelectionModel().select(dashboardTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overload di {@link #loadView(String, Consumer)} per caricamenti semplici
     * senza configurazione.
     *
     * @param fxmlFile Il percorso del file FXML.
     */
    private void loadView(String fxmlFile) {
        loadView(fxmlFile, null);
    }

    /**
     * Aggiorna lo stile dei bottoni di navigazione per evidenziare quello attivo.
     *
     * @param button Il bottone da attivare.
     */
    private void setActiveButton(Button button) {
        if (btnHome != null)
            btnHome.getStyleClass().remove("selected");
        if (btnCalendar != null)
            btnCalendar.getStyleClass().remove("selected");
        if (btnProjects != null)
            btnProjects.getStyleClass().remove("selected");

        button.getStyleClass().add("selected");
    }

    /**
     * Apre una nuova scheda (Tab) per visualizzare un report specifico.
     *
     * @param report Il report da visualizzare.
     */
    public void openReportTab(Report report) {
        javafx.scene.control.Tab tab = new javafx.scene.control.Tab(report.getTitle());
        // Carica la vista del Report
        try {
            Parent view = loadReportView(report);
            tab.setContent(view);
            mainTabPane.getTabs().add(tab);
            mainTabPane.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
            DialogFactory.showError("Errore", "Impossibile aprire il report",
                    e.getMessage());
        }
    }

    /**
     * Helper privato per caricare l'FXML della vista report e iniettare i dati.
     *
     * @param report Il report da mostrare.
     * @return Il nodo radice della vista caricata.
     * @throws IOException Se il caricamento dell'FXML fallisce.
     */
    private Parent loadReportView(Report report) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/it/unicam/cs/mpgc/jtime123014/view/ReportView.fxml"));
        Parent view = loader.load();

        ReportViewController controller = loader.getController();
        controller.setReport(report);
        return view;
    }
}
