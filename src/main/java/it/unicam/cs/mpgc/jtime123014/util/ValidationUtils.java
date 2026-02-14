package it.unicam.cs.mpgc.jtime123014.util;

/**
 * Classe per la validazione di valori.
 */
public class ValidationUtils {

    /**
     * Costruttore privato per prevenire l'istanziazione.
     */
    private ValidationUtils() {
        throw new UnsupportedOperationException("La classe utility non può essere istanziata.");
    }

    /**
     * Verifica se un valore è non negativo.
     *
     * @param value     il valore da controllare
     * @param fieldName il nome del campo per i messaggi di errore
     * @throws IllegalArgumentException se il valore è negativo
     */
    public static void checkNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " non può essere negativo/a");
        }
    }

    /**
     * Verifica se un valore è positivo.
     * 
     * @param value     il valore da controllare
     * @param fieldName il nome del campo per i messaggi di errore
     * @throws IllegalArgumentException se il valore non è positivo
     */
    public static void checkPositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " deve essere positivo/a");
        }
    }
}
