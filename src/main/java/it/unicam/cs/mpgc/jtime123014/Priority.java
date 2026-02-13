package it.unicam.cs.mpgc.jtime123014;

import lombok.Getter;

/**
 * Rappresenta i livelli di priorità assegnabili a un'attività.
 */
public enum Priority {

    /** Priorità bassa. */
    LOW(25),
    /** Priorità media. */
    MEDIUM(50),
    /** Priorità alta. */
    HIGH(75),
    /** Priorità massima. */
    URGENT(100);

    @Getter
    private final int maxPercentage;

    Priority(int maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

}
