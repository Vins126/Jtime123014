package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Interfaccia per la gestione della vista temporale (Mese e Anno).
 * <p>
 * Permette di sapere o modificare quale mese e anno l'applicazione sta
 * mostrando,
 * senza dover accedere all'intero calendario.
 */
public interface TemporalView {

    /**
     * Imposta il mese di riferimento.
     *
     * @param month il mese.
     * @throws NullPointerException se il mese è null.
     */
    void setMonth(Months month);

    /**
     * Restituisce il mese corrente.
     *
     * @return il mese.
     */
    Months getMonth();

    /**
     * Imposta l'anno di riferimento.
     *
     * @param year l'anno.
     * @throws IllegalArgumentException se l'anno è negativo.
     */
    void setYear(int year);

    /**
     * Restituisce l'anno corrente.
     *
     * @return l'anno.
     */
    int getYear();
}
