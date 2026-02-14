package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.Calendar;
import it.unicam.cs.mpgc.jtime123014.model.Day;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe astratta che fornisce funzionalità comuni per le implementazioni di
 * {@link Scheduler}.
 * 
 * Questa classe gestisce la logica di filtraggio dei giorni e fornisce metodi
 * per semplificare la creazione di nuovi algoritmi di scheduling.
 */
public abstract class AbstractScheduler implements Scheduler {

    /**
     * Costruttore base per gli scheduler.
     */
    public AbstractScheduler() {
    }

    /**
     * Restituisce la lista dei giorni del calendario successivi o uguali alla data
     * specificata.
     * 
     * Seleziona solo i giorni validi (quelli che hanno una data associata) ed
     * esclude quelli passati o con formati non supportati.
     *
     * @param calendar il calendario da cui estrarre i giorni.
     * @param today    la data di partenza (inclusa).
     * @return una lista immutabile o modificabile di giorni validi per lo
     *         scheduling.
     */
    protected List<Day<?>> getDaysFromToday(Calendar<?> calendar, LocalDate today) {
        return calendar.getDays().stream()
                .filter(d -> d.getId() instanceof LocalDate date && !date.isBefore(today))
                .collect(Collectors.toList());
    }
}
