package it.unicam.cs.mpgc.jtime123014;


public class Context {
    private final static Context instance = new Context();

    private JTimeController controller;
    private MainController mainController;

    public static Context getInstance() {
        return instance;
    }

    private Context() {
        this.controller = new JTimeController();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public JTimeController getController() {
        return controller;
    }
}
