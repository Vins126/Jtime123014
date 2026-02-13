package it.unicam.cs.mpgc.jtime123014.model;

import java.util.List;

/**
 * Entità aggregatrice che raggruppa un insieme di {@link Task} correlati.
 * <p>
 * Un {@code Project} definisce il contesto, la priorità generale e lo stato di
 * avanzamento
 * per un gruppo di attività. È l'unità principale gestita dal {@link Calendar}.
 * <p>
 * La priorità del progetto ({@link Priority}) influenza direttamente come le
 * sue task
 * vengono trattate dallo Scheduler (es. quanta percentuale di buffer
 * giornaliero possono occupare).
 *
 * @param <ID> Il tipo dell'identificativo univoco del progetto.
 */
public interface Project<ID> extends Identifiable<ID> {

    /**
     * Aggiorna il nome del progetto.
     * 
     * @param name il nuovo nome. Non deve essere null.
     * @throws NullPointerException se il nome è null.
     */
    void setName(String name);

    /**
     * Restituisce il nome del progetto.
     * 
     * @return il nome.
     */
    String getName();

    /**
     * Aggiorna la descrizione del progetto.
     * 
     * @param description la nuova descrizione. Non deve essere null.
     * @throws NullPointerException se la descrizione è null.
     */
    void setDescription(String description);

    /**
     * Restituisce la descrizione del progetto.
     * 
     * @return la descrizione.
     */
    String getDescription();

    /**
     * Imposta la priorità del progetto.
     * La priorità determina l'urgenza con cui le task di questo progetto devono
     * essere schedulate.
     * 
     * @param priority la nuova priorità (URGENT, HIGH, MEDIUM, LOW).
     * @throws NullPointerException se la priorità è null.
     */
    void setPriority(Priority priority);

    /**
     * Restituisce la priorità corrente del progetto.
     * 
     * @return la priorità.
     */
    Priority getPriority();

    /**
     * Restituisce lo stato globale del progetto.
     * Di solito deriva dallo stato delle sue task (es. COMPLETED se tutte le task
     * sono finite).
     * 
     * @return lo stato corrente.
     */
    Status getStatus();

    /**
     * Imposta manualmente lo stato del progetto.
     * 
     * @param status il nuovo stato.
     * @throws NullPointerException se lo stato è null.
     */
    void setStatus(Status status);

    /**
     * Tenta di marcare il progetto come {@link Status#COMPLETED}.
     * <p>
     * Questa operazione ha successo solo se **tutte** le task associate al progetto
     * sono state completate.
     * 
     * @return {@code true} se il progetto è stato chiuso con successo.
     * @throws IllegalStateException se esistono ancora task non completate (PENDING
     *                               o IN_PROGRESS).
     */
    boolean completeProject();

    /**
     * Inserisce una nuova task nel progetto.
     * 
     * @param task la task da aggiungere.
     * @return {@code true} se l'inserimento è avvenuto (la lista è cambiata),
     *         {@code false} altrimenti.
     * @throws IllegalArgumentException se la task è null o se esiste già nel
     *                                  progetto.
     */
    boolean insertTask(Task<?> task);

    /**
     * Rimuove una task dal progetto.
     * 
     * @param task la task da rimuovere.
     * @return {@code true} se la task era presente ed è stata rimossa.
     * @throws IllegalArgumentException se la task è null.
     */
    boolean deleteTask(Task<?> task);

    /**
     * Restituisce la lista completa (read-only o modificabile a seconda dell'impl)
     * delle task del progetto.
     * 
     * @return la lista di tutte le task.
     */
    List<Task<?>> getTasks();

    /**
     * Sostituisce l'intera lista delle task del progetto.
     * Utile per operazioni di inizializzazione o ripristino massivo.
     * 
     * @param newTasks la nuova lista di task.
     * @throws NullPointerException se la lista è null o contiene elementi null.
     */
    void setTasks(List<Task<?>> newTasks);

    /**
     * Restituisce solo le task che sono ancora in attesa di essere completate.
     * <p>
     * Questo metodo è fondamentale per lo {@code Scheduler}, che lo utilizza per
     * sapere
     * quali attività deve ancora pianificare nei giorni disponibili.
     * Filtra le task con stato {@link Status#PENDING}.
     * 
     * @return una lista contenente solo le task pendenti.
     */
    List<Task<?>> getPendingTasks();

}
