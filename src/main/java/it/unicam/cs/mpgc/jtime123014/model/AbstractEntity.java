package it.unicam.cs.mpgc.jtime123014.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe astratta che implementa l'interfaccia {@link Identifiable}.
 * Fornisce il supporto di base per la gestione dell'identificatore univoco
 * (UUID),
 * inclusa l'implementazione corretta dei metodi {@code equals} e
 * {@code hashCode}.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractEntity<ID> implements Identifiable<ID> {

    @EqualsAndHashCode.Include
    private ID id;

    /*
     * Il costruttore di default è rimosso perché non possiamo generare un ID
     * generico dal nulla.
     * Le classi concrete dovranno passare l'ID al costruttore.
     */
    // public AbstractEntity() { ... }

    public AbstractEntity(ID id) {
        this.id = id;
    }
}
