package it.unicam.cs.mpgc.jtime123014;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JTimeApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainLayout.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("JTime 123014");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Chiusura applicazione. Salvataggio dati...");
        if (Context.getInstance().getController() != null) {
            Context.getInstance().getController().saveData();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
