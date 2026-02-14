package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Interfaccia per la gestione del completamento di una task.
 * <p>
 * Contiene i metodi necessari per segnare una task come completata e calcolare
 * la differenza tra il tempo stimato e quello effettivamente impiegato.
 */
public interface CompletableTask {

    /**
     * Completa la task atomicamente: imposta lo stato a {@link Status#COMPLETED},
     * registra la durata effettiva e restituisce il delta.
     *
     * @param durationActual la durata effettiva finale.
     * @return il delta ({@code durationActual - durationEstimate}).
     */
    int completeTaskAndSetDuration(int durationActual);

    /**
     * Completa la task (presuppone che {@code durationActual} sia già impostato).
     *
     * @return il delta calcolato.
     * @throws IllegalStateException se {@code durationActual} è null.
     */
    int completeTask();

    /**
     * Calcola lo scostamento tra durata effettiva e stimata.
     *
     * @return {@code durationActual - durationEstimate}.
     * @throws IllegalStateException se {@code durationActual} non è impostato.
     */
    int calculateDelta();

    /**
     * Verifica se la task è completata.
     *
     * @return {@code true} se lo status è {@link Status#COMPLETED}.
     */
    boolean isCompleted();
}
