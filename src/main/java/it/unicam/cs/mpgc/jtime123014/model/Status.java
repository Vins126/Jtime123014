package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Rappresenta lo stato di avanzamento di un'attività.
 */
public enum Status {
    /** L'attività è stata creata ma non ancora iniziata. */
    PENDING,
    /** L'attività è in corso di svolgimento. */
    IN_PROGRESS,
    /** L'attività è conclusa. */
    COMPLETED
}
