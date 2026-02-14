package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Estende {@link Identifiable} aggiungendo la possibilità di modificare l'ID.
 * <p>
 * Questa interfaccia viene usata quando è necessario cambiare l'ID di un
 * oggetto
 * dopo la sua creazione, ad esempio durante il caricamento dei dati.
 *
 * @param <ID> Il tipo dell'identificativo.
 */
public interface MutableIdentifiable<ID> extends Identifiable<ID> {

    /**
     * Imposta l'identificatore univoco dell'entità.
     *
     * @param id l'identificatore da impostare.
     */
    void setId(ID id);
}
