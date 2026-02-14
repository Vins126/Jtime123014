package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.time.LocalDate;

/**
 * Interfaccia per l'algoritmo di pianificazione (Scheduling).
 * <p>
 * Questo componente ha il compito di prendere le attività da fare e
 * decidere in quali giorni posizionarle, in base al tempo disponibile.
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
