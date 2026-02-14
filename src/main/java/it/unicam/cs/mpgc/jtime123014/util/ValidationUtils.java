package it.unicam.cs.mpgc.jtime123014.util;

/**
 * Utility class for common validation logic.
 */
public class ValidationUtils {

    /**
     * Costruttore privato per prevenire l'istanziazione.
     */
    private ValidationUtils() {
        throw new UnsupportedOperationException("La classe utility non può essere istanziata.");
    }

    /**
     * Checks if a value is non-negative.
     *
     * @param value     the value to check
     * @param fieldName the name of the field mostly for error messages
     * @throws IllegalArgumentException if the value is negative
     */
    public static void checkNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " non può essere negativo/a");
        }
    }

    /**
     * Checks if a value is positive.
     * 
     * @param value     the value to check
     * @param fieldName the name of the field mostly for error messages
     * @throws IllegalArgumentException if the value is not positive
     */
    public static void checkPositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " deve essere positivo/a");
        }
    }
}
