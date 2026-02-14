package it.unicam.cs.mpgc.jtime123014.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Classe astratta che implementa l'interfaccia {@link Identifiable}.
 * Fornisce il supporto di base per la gestione dell'identificatore univoco
 * (UUID),
 * inclusa l'implementazione corretta dei metodi {@code equals} e
 * {@code hashCode}.
 *
 * @param <ID> il tipo dell'identificatore univoco.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractEntity<ID> implements MutableIdentifiable<ID>, Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private ID id;

    /**
     * Costruttore base.
     *
     * @param id l'identificatore univoco dell'entità.
     */
    public AbstractEntity(ID id) {
        this.id = id;
    }
}
