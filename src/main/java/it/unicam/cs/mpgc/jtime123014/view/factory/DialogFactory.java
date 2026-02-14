package it.unicam.cs.mpgc.jtime123014.view.factory;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Factory per Alert generici (errore, conferma, informazione).
 * <p>
 * Responsabilità singola: costruzione di finestre di avviso standard.
 * I dialog con form domain-specific sono gestiti da {@link FormDialogFactory}.
 */
public class DialogFactory {

    /**
     * Costruttore privato per prevenire l'istanziazione di questa classe di
     * utilità.
     */
    private DialogFactory() {
        throw new UnsupportedOperationException("Questa è una classe di utilità e non può essere istanziata.");
    }

    /**
     * Mostra un messaggio di errore.
     *
     * @param title   il titolo della finestra.
     * @param header  l'intestazione del messaggio.
     * @param content il contenuto del messaggio.
     */
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Mostra una richiesta di conferma (OK/Annulla).
     *
     * @param title   il titolo della finestra.
     * @param header  l'intestazione della domanda.
     * @param content il dettaglio della domanda.
     * @return true se l'utente ha premuto OK, false altrimenti.
     */
    public static boolean showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .isPresent();
    }

    /**
     * Mostra un messaggio informativo.
     *
     * @param title   il titolo della finestra.
     * @param header  l'intestazione del messaggio.
     * @param content il contenuto del messaggio.
     */
    public static void showInformation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
