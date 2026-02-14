package it.unicam.cs.mpgc.jtime123014.model;

import java.util.List;

/**
 * Rappresenta un giorno nel calendario.
 * <p>
 * Un giorno ha una certa capacità lavorativa (buffer), che diminuisce man mano
 * che vengono aggiunte delle attività.
 * Serve a gestire le task assegnate a una data specifica e a controllare
 * quanto tempo libero rimane.
 *
 * @param <ID> Il tipo dell'identificativo del giorno (di solito una data).
 */
public interface Day<ID> extends Identifiable<ID> {

    /**
     * Imposta la capacità totale (buffer) di lavoro per questo giorno.
     * 
     * @param buffer il valore del buffer in unità di tempo (es. minuti).
     * @throws IllegalArgumentException se il buffer è negativo.
     */
    void setBuffer(int buffer);

    /**
     * Restituisce la capacità totale (buffer) di questo giorno.
     * 
     * @return il buffer totale.
     */
    int getBuffer();

    /**
     * Sottrae una quantità di tempo dal buffer disponibile.
     * Viene chiamato quando una task viene pianificata o si consuma tempo.
     * <p>
     * Se il buffer utilizzato supera quello disponibile, il comportamento dipende
     * dall'implementazione
     * (potrebbe lanciare eccezione o ritornare false).
     * 
     * @param usedBuffer la quantità di tempo consumata.
     * @return {@code true} se l'operazione ha successo.
     */
    boolean updateBuffer(int usedBuffer);

    /**
     * Restituisce la lista di tutte le task assegnate (schedulate) per questo
     * giorno.
     * 
     * @return la lista delle task.
     */
    List<Task<?>> getTasks();

    /**
     * Sostituisce la lista delle task per questo giorno.
     * Attenzione: potrebbe richiedere il ricalcolo del buffer residuo.
     * 
     * @param task la nuova lista di task.
     * @throws NullPointerException se la lista è null.
     */
    void setTasks(List<Task<?>> task);

    /**
     * Aggiunge manualmente una task a questo giorno.
     *
     * @param task la task da aggiungere.
     * @return {@code true} se aggiunta con successo.
     * @exception NullPointerException     se la task è null.
     * @exception IllegalArgumentException se la task è già presente.
     */
    boolean addTask(Task<?> task);

    /**
     * Rimuove una task da questo giorno.
     * Dovrebbe anche liberare il buffer corrispondente.
     * 
     * @param task la task da rimuovere.
     * @return {@code true} se rimossa con successo.
     * @exception NullPointerException se la task è null.
     */
    boolean removeTask(Task<?> task);

    /**
     * Imposta direttamente il valore del buffer residuo (libero).
     * 
     * @param freeBuffer il nuovo buffer libero.
     */
    void setFreeBuffer(int freeBuffer);

    /**
     * Restituisce la quantità di tempo ancora disponibile per nuove attività in
     * questo giorno.
     * 
     * @return il buffer libero.
     */
    int getFreeBuffer();

    /**
     * Controlla se nel giorno sono presenti task che non sono ancora state
     * completate.
     * Utile per service che devono fare pulizia o controlli di fine giornata.
     * 
     * @return {@code true} se esiste almeno una task con status {@code PENDING} o
     *         {@code IN_PROGRESS}.
     */
    boolean hasPendingTasks();

    /**
     * Resetta lo stato del giorno eliminando le task assegnate (riportandole a
     * PENDING se necessario)
     * e ripristinando il buffer.
     */
    void reset();
}
