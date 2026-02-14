package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Interfaccia che espone i dati necessari per la pianificazione (scheduling).
 * 
 * Permette di sapere quanto tempo manca per completare una task,
 * informazione essenziale per decidere in quale giorno inserirla.
 */
public interface SchedulableTask {

    /**
     * Imposta il tempo residuo necessario per completare la task.
     *
     * @param timeConsuming il tempo residuo.
     * @throws IllegalArgumentException se il tempo è negativo.
     */
    void setTimeConsuming(int timeConsuming);

    /**
     * Restituisce il tempo residuo necessario.
     *
     * @return il tempo residuo.
     */
    int getTimeConsuming();
}
