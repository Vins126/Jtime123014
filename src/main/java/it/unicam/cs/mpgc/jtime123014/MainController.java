package it.unicam.cs.mpgc.jtime123014;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.function.Consumer;

public class MainController {

    @FXML
    private BorderPane contentArea;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnCalendar;

    @FXML
    private Button btnProjects;

    @FXML
    public void initialize() {
        // Register for navigation
        Context.getInstance().setMainController(this);
        // Load default view (Home)
        showHome();
    }

    @FXML
    private void showHome() {
        loadView("view/HomeView.fxml");
        if (btnHome != null)
            setActiveButton(btnHome);
    }

    @FXML
    private void showCalendar() {
        loadView("view/CalendarView.fxml");
        if (btnCalendar != null)
            setActiveButton(btnCalendar);
    }

    @FXML
    private void showProjects() {
        loadView("view/ProjectsView.fxml");
        if (btnProjects != null)
            setActiveButton(btnProjects);
    }

    public <T> void loadView(String fxmlFile, Consumer<T> controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();

            if (controllerSetup != null) {
                T controller = loader.getController();
                controllerSetup.accept(controller);
            }

            contentArea.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlFile) {
        loadView(fxmlFile, null);
    }

    private void setActiveButton(Button button) {
        if (btnHome != null)
            btnHome.getStyleClass().remove("selected");
        if (btnCalendar != null)
            btnCalendar.getStyleClass().remove("selected");
        if (btnProjects != null)
            btnProjects.getStyleClass().remove("selected");

        button.getStyleClass().add("selected");
    }
}
