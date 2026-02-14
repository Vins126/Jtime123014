package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.Day;

import java.time.LocalDate;

/**
 * Factory per la creazione di istanze di {@link Day} (OCP).
 * <p>
 * Permette a {@link SimpleCalendarService} di creare giorni
 * senza essere accoppiato a un'implementazione concreta.
 * Per sostituire il tipo di Day basta iniettare una factory diversa.
 */
@FunctionalInterface
public interface DayFactory {

    /**
     * Crea un nuovo giorno con l'identificativo e il buffer specificati.
     *
     * @param date   la data identificativa del giorno.
     * @param buffer il buffer di tempo disponibile (minuti).
     * @return una nuova istanza di {@link Day}.
     */
    Day<LocalDate> create(LocalDate date, int buffer);
}
