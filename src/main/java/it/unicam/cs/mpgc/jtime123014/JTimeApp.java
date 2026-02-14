package it.unicam.cs.mpgc.jtime123014;

import it.unicam.cs.mpgc.jtime123014.controller.AppController;
import it.unicam.cs.mpgc.jtime123014.controller.Context;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JTimeApp extends Application {

    /**
     * Costruttore di default per l'applicazione JavaFX.
     */
    public JTimeApp() {
    }

    /**
     * Avvia l'interfaccia grafica principale caricando il layout da FXML.
     * Imposta anche le dimensioni minime della finestra e i fogli di stile.
     *
     * @param stage lo stage principale dell'applicazione.
     * @throws Exception se ci sono errori nel caricamento dell'interfaccia.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainLayout.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("JTime 123014");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    /**
     * Metodo chiamato alla chiusura dell'applicazione.
     * Si assicura di salvare i dati tramite il controller prima di uscire.
     *
     * @throws Exception se ci sono problemi durante la chiusura.
     */
    @Override
    public void stop() throws Exception {
        System.out.println("Chiusura applicazione. Salvataggio dati...");
        AppController ctrl = Context.getInstance().getController();
        if (ctrl != null) {
            ctrl.saveData();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
