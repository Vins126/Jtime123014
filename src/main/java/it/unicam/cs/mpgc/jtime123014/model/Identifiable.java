package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Interfaccia che rappresenta un oggetto con un identificativo univoco.
 * 
 * Questa interfaccia permette di leggere l'ID di un oggetto, ma non di
 * modificarlo. È utile per garantire che l'ID rimanga costante una volta
 * creato.
 *
 * @param <ID> Il tipo dell'identificativo.
 */
public interface Identifiable<ID> {
    /**
     * Restituisce l'identificatore dell'entità.
     *
     * @return l'identificatore come {@link ID}.
     */
    ID getId();
}
