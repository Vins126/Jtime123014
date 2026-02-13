package it.unicam.cs.mpgc.jtime123014.model;

public enum Months {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

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
