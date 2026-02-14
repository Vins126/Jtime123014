package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;

/**
 * Una classe centrale che tiene traccia dei componenti principali.
 * 
 * Serve a far comunicare tra loro le diverse parti dell'applicazione (come le
 * varie
 * finestre e la logica di controllo) senza dover passare riferimenti ovunque.
 */
public class Context {
    private final static Context instance = new Context();

    private AppController controller;
    private MainController mainController;

    /**
     * Restituisce l'istanza unica del Context.
     *
     * @return L'istanza di Context.
     */
    public static Context getInstance() {
        return instance;
    }

    /**
     * Costruttore privato.
     */
    private Context() {
        // Inizializza il controller principale dell'applicazione (JTimeController).
        this.controller = new JTimeController();
    }

    /**
     * Imposta il riferimento al controller principale della GUI.
     *
     * @param mainController Il controller della vista principale.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Restituisce il controller della vista principale.
     *
     * @return Il MainController corrente.
     */
    public MainController getMainController() {
        return mainController;
    }

    /**
     * Restituisce il controller logico dell'applicazione.
     *
     * @return L'istanza di AppController.
     */
    public AppController getController() {
        return controller;
    }
}
