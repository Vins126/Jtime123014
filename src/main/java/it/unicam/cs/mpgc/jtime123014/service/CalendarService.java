package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.time.LocalDate;

/**
 * Interfaccia che definisce le operazioni principali da fare sul calendario.
 * <p>
 * Si occupa di aggiornare i giorni quando passa il tempo (rolling window) e
 * di preparare i giorni futuri per accogliere nuove task.
 */
public interface CalendarService {
    /**
     * Aggiorna la finestra temporale del calendario (rolling window).
     *
     * @param calendar il calendario da aggiornare.
     * @param today    la data odierna.
     * @param buffer   il buffer giornaliero predefinito.
     */
    void updateRollingWindow(Calendar<?> calendar, LocalDate today, int buffer);

    /**
     * Rimuove i giorni passati che non servono più.
     *
     * @param calendar il calendario da pulire.
     * @param today    la data odierna.
     * @return true se sono stati rimossi dei giorni, false altrimenti.
     */
    boolean cleanupPastDays(Calendar<?> calendar, LocalDate today);

    /**
     * Resetta la pianificazione futura a partire da una certa data.
     *
     * @param calendar il calendario da resettare.
     * @param fromDate la data da cui iniziare il reset.
     */
    void resetSchedule(Calendar<?> calendar, LocalDate fromDate);

    /**
     * Prepara i giorni del calendario per lo scheduling.
     * <p>
     * Imposta il buffer giornaliero e resetta il buffer libero per i giorni futuri
     * a partire dalla data specificata.
     *
     * @param calendar           il calendario su cui operare.
     * @param fromDate           la data di inizio (inclusa).
     * @param dailyBufferMinutes il buffer giornaliero in minuti.
     */
    void prepareDaysForScheduling(Calendar<?> calendar, LocalDate fromDate, int dailyBufferMinutes);
}
