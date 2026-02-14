package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Interfaccia che definisce le proprietà base di una task.
 * 
 * Permette di leggere e modificare informazioni come nome, descrizione, stato e
 * stime di tempo.
 * Serve per limitare l'accesso ai soli dati della task, nascondendo altre
 * funzionalità complesse.
 */
public interface TaskInfo {

    /**
     * Imposta il nome della task.
     * 
     * @param name il nuovo nome. Non deve essere null.
     */
    void setName(String name);

    /**
     * Restituisce il nome della task.
     * 
     * @return il nome della task.
     */
    String getName();

    /**
     * Imposta la descrizione della task.
     * 
     * @param description la nuova descrizione. Non deve essere null.
     */
    void setDescription(String description);

    /**
     * Restituisce la descrizione della task.
     * 
     * @return la descrizione della task.
     */
    String getDescription();

    /**
     * Restituisce lo stato corrente.
     * 
     * @return lo stato corrente ({@link Status}).
     */
    Status getStatus();

    /**
     * Imposta lo stato della task.
     * 
     * @param status il nuovo stato. Non deve essere null.
     */
    void setStatus(Status status);

    /**
     * Restituisce la durata stimata.
     * 
     * @return la durata stimata (minuti).
     */
    int getDurationEstimate();

    /**
     * Imposta la durata stimata.
     * 
     * @param durationEstimate la nuova stima.
     */
    void setDurationEstimate(int durationEstimate);

    /**
     * Restituisce la durata effettiva.
     * 
     * @return la durata effettiva, o {@code null} se non ancora misurata.
     */
    Integer getDurationActual();

    /**
     * Imposta la durata effettiva.
     * 
     * @param durationActual la durata effettiva.
     */
    void setDurationActual(Integer durationActual);
}
