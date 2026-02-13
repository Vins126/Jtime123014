package it.unicam.cs.mpgc.jtime123014;

/**
 * Rappresenta i possibili stati di un'attività.
 */
public enum Status {
    /** L'attività è ancora da fare. */
    PENDING,
    /** L'attività è attualmente in corso. */
    IN_PROGRESS,
    /** L'attività è stata portata a termine. */
    COMPLETED
}
