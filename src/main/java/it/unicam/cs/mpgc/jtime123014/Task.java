package it.unicam.cs.mpgc.jtime123014;

/**
 * Entità fondamentale che rappresenta un'unità di lavoro atomica all'interno
 * del sistema JTime.
 * <p>
 * Una {@code Task} appartiene a un {@link Project} e può essere schedulata in
 * un {@link Day}.
 * Mantiene informazioni sullo stato di avanzamento, la stima temporale e il
 * tempo effettivamente impiegato.
 * <p>
 * Le task attraversano un ciclo di vita definito dall'enum {@link Status}:
 * <ul>
 * <li>{@code PENDING}: Creata ma non ancora iniziata/pianificata.</li>
 * <li>{@code IN_PROGRESS}: Attualmente in lavorazione o pianificata in un
 * Giorno.</li>
 * <li>{@code COMPLETED}: Terminata.</li>
 * </ul>
 *
 * @param <ID> Il tipo dell'identificativo univoco della task (es. String, UUID,
 *             Long).
 */
public interface Task<ID> extends Identifiable<ID> {

    /**
     * Aggiorna il nome identificativo della task.
     * 
     * @param name il nuovo nome da impostare. Non deve essere null.
     * @throws NullPointerException se il nome è null.
     */
    void setName(String name);

    /**
     * Restituisce il nome identificativo della task.
     * 
     * @return il nome della task.
     */
    String getName();

    /**
     * Aggiorna la descrizione dettagliata dell'attività da svolgere.
     * 
     * @param description la nuova descrizione. Non deve essere null.
     * @throws NullPointerException se la descrizione è null.
     */
    void setDescription(String description);

    /**
     * Restituisce la descrizione dettagliata della task.
     * 
     * @return la descrizione.
     */
    String getDescription();

    /**
     * Restituisce lo stato corrente dell'attività nel suo ciclo di vita.
     *
     * @return lo stato come valore dell'enumerazione {@link Status}.
     */
    Status getStatus();

    /**
     * Aggiorna lo stato dell'attività.
     * <p>
     * Questo metodo è utilizzato principalmente dallo Scheduler o dall'utente per
     * avanzare
     * il progresso della task (es. da PENDING a IN_PROGRESS).
     *
     * @param status il nuovo stato da impostare.
     * @throws NullPointerException se lo status è null.
     */
    void setStatus(Status status);

    /**
     * Restituisce la stima iniziale della durata della task (in minuti/ore a
     * seconda dell'implementazione).
     * 
     * @return la durata stimata.
     */
    int getDurationEstimate();

    /**
     * Aggiorna la stima della durata della task.
     * Può essere modificata se la valutazione iniziale si rivela inesatta prima del
     * completamento.
     * 
     * @param durationEstimate la nuova stima della durata.
     * @throws IllegalArgumentException se il valore è negativo.
     */
    void setDurationEstimate(int durationEstimate);

    /**
     * Restituisce la durata effettiva impiegata per completare la task.
     * 
     * @return la durata effettiva, o {@code null} se la task non è ancora
     *         completata/misurata.
     */
    Integer getDurationActual();

    /**
     * Imposta la durata effettiva impiegata.
     * Solitamente impostato al momento del completamento della task.
     * 
     * @param durationActual la durata effettiva.
     * @throws IllegalArgumentException se il valore è negativo.
     */
    void setDurationActual(Integer durationActual);

    /**
     * Calcola lo scostamento (delta) tra la durata effettiva e la durata stimata.
     * <p>
     * Un valore positivo indica che la task ha richiesto più tempo del previsto
     * (ritardo).
     * Un valore negativo indica che la task ha richiesto meno tempo (anticipo).
     * 
     * @return la differenza {@code durationActual - durationEstimate}.
     * @throws IllegalStateException se {@code durationActual} non è stato ancora
     *                               impostato (è null).
     */
    int calculateDelta();

    /**
     * Completa la task atomicamente: imposta lo stato a {@link Status#COMPLETED},
     * registra la durata effettiva e restituisce il delta rispetto alla stima.
     * 
     * @param durationActual la durata effettiva finale.
     * @return il delta calcolato ({@code durationActual - durationEstimate}).
     * @throws IllegalArgumentException se {@code durationActual} è negativo.
     */
    int completeTaskAndSetDuration(int durationActual);

    /**
     * Completa la task impostando lo stato a {@link Status#COMPLETED}.
     * Presuppone che {@code durationActual} sia già stato impostato
     * precedentemente.
     * 
     * @return il delta calcolato.
     * @throws IllegalStateException se {@code durationActual} è null.
     */
    int completeTask();

    /**
     * Verifica helper per controllare rapidamente se la task è conclusa.
     * 
     * @return {@code true} se lo status è {@link Status#COMPLETED}, {@code false}
     *         altrimenti.
     */
    boolean isCompleted();

    /**
     * Imposta il tempo ancora necessario per completare la task (residuo).
     * <p>
     * Questo valore è critico per l'algoritmo di scheduling, che usa questo dato
     * per capire quanto "spazio" occupare nel buffer di un {@link Day}.
     * 
     * @param timeConsuming il tempo residuo necessario.
     * @throws IllegalArgumentException se il tempo è negativo.
     */
    void setTimeConsuming(int timeConsuming);

    /**
     * Restituisce il tempo residuo necessario per completare la task.
     * All'inizio corrisponde a {@code durationEstimate}, ma può diminuire man mano
     * che la task viene lavorata parzialmente.
     * 
     * @return il tempo necessario residuo.
     */
    int getTimeConsuming();
}
