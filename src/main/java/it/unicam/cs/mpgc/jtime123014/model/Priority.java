package it.unicam.cs.mpgc.jtime123014.model;

import lombok.Getter;

/**
 * Rappresenta l'importanza di un'attività (Priorità).
 * <p>
 * Ogni livello di priorità è associato a un valore numerico ("maxPercentage")
 * che indica quanto buffer giornaliero può occupare al massimo un'attività di
 * quella priorità.
 * Più alta è la priorità, più spazio può occupare nel giorno.
 */
public enum Priority {

    /** Priorità bassa (può occupare poco spazio). */
    LOW(25),
    /** Priorità media. */
    MEDIUM(50),
    /** Priorità alta. */
    HIGH(75),
    /** Priorità massima (può occupare tutto lo spazio disponibile). */
    URGENT(100);

    @Getter
    private final int maxPercentage;

    Priority(int maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

}
