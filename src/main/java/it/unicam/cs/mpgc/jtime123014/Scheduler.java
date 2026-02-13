package it.unicam.cs.mpgc.jtime123014;


import java.time.LocalDate;

/**
 * Interfaccia Strategy per l'algoritmo di pianificazione delle attività.
 * <p>
 * Un {@code Scheduler} ha la responsabilità di prendere le task pendenti dai
 * progetti
 * contenuti nel {@link Calendar} e assegnarle ai
 * {@link it.unicam.cs.mpgc.jtime123014.Day} disponibili.
 * <p>
 * Implementazioni diverse possono adottare strategie diverse (es. Greedy, Round
 * Robin, Machine Learning, ecc.).
 */
public interface Scheduler {

    /**
     * Esegue la pianificazione delle attività sul calendario.
     * 
     * @param calendar  il calendario contenente i progetti con task da pianificare
     *                  e i giorni disponibili.
     * @param startDate la data da cui iniziare a cercare slot disponibili.
     * @throws NullPointerException se il calendario o la data di inizio sono null.
     */
    void schedule(Calendar<?> calendar, LocalDate startDate);
}
