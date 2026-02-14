package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Enum che rappresenta i mesi dell'anno.
 * 
 * Utile per gestire la visualizzazione del calendario e per convertire
 * i nomi dei mesi in italiano.
 */
public enum Months {
    /** Gennaio */
    JANUARY,
    /** Febbraio */
    FEBRUARY,
    /** Marzo */
    MARCH,
    /** Aprile */
    APRIL,
    /** Maggio */
    MAY,
    /** Giugno */
    JUNE,
    /** Luglio */
    JULY,
    /** Agosto */
    AUGUST,
    /** Settembre */
    SEPTEMBER,
    /** Ottobre */
    OCTOBER,
    /** Novembre */
    NOVEMBER,
    /** Dicembre */
    DECEMBER;

    /**
     * Restituisce il nome del mese in italiano.
     *
     * @param month il mese da convertire.
     * @return il nome in italiano (es. "Gennaio").
     */
    public static String toString(Months month) {
        return switch (month) {
            case JANUARY -> "Gennaio";
            case FEBRUARY -> "Febbraio";
            case MARCH -> "Marzo";
            case APRIL -> "Aprile";
            case MAY -> "Maggio";
            case JUNE -> "Giugno";
            case JULY -> "Luglio";
            case AUGUST -> "Agosto";
            case SEPTEMBER -> "Settembre";
            case OCTOBER -> "Ottobre";
            case NOVEMBER -> "Novembre";
            case DECEMBER -> "Dicembre";
        };
    }
}
