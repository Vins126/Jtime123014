package it.unicam.cs.mpgc.jtime123014.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia per la gestione di un insieme di giorni.
 * 
 * Fornisce i metodi per aggiungere, rimuovere e leggere i giorni.
 * Utile quando si vuole lavorare solo con i giorni senza dover dipendere
 * dall'intero Calendario.
 */
public interface DayContainer {

    /**
     * Sostituisce l'intera lista dei giorni gestiti.
     *
     * @param days la nuova lista di giorni.
     * @throws NullPointerException se la lista è null.
     */
    void setDays(List<Day<?>> days);

    /**
     * Restituisce la lista di tutti i giorni attualmente gestiti.
     *
     * @return lista dei giorni.
     */
    List<Day<?>> getDays();

    /**
     * Aggiunge un nuovo giorno.
     *
     * @param day il giorno da aggiungere.
     * @return {@code true} se aggiunto con successo.
     * @throws NullPointerException     se il giorno è null.
     * @throws IllegalArgumentException se un giorno con lo stesso ID esiste già.
     */
    boolean addDay(Day<?> day);

    /**
     * Rimuove un giorno.
     *
     * @param day il giorno da rimuovere.
     * @return {@code true} se rimosso con successo.
     * @throws NullPointerException se il giorno è null.
     */
    boolean removeDay(Day<?> day);

    /**
     * Verifica l'esistenza di un giorno dato il suo identificativo temporale.
     *
     * @param day la data (ID) del giorno da cercare.
     * @return {@code true} se il giorno esiste.
     */
    boolean hasDay(LocalDate day);
}
