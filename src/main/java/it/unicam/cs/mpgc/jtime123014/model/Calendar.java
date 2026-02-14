package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Rappresenta il calendario principale dell'applicazione.
 * <p>
 * Il Calendario contiene tutti i progetti e tutti i giorni pianificati.
 * È il punto centrale da cui è possibile accedere a tutti i dati:
 * <ul>
 * <li>I Progetti (attività da svolgere).</li>
 * <li>I Giorni (tempo a disposizione).</li>
 * <li>La vista temporale (mese e anno corrente).</li>
 * </ul>
 * <p>
 * Questa interfaccia unisce le funzionalità di gestione giorni e progetti in un
 * unico oggetto.
 *
 * @param <ID> Il tipo dell'identificativo del calendario.
 */
public interface Calendar<ID> extends Identifiable<ID>, DayContainer, ProjectContainer, TemporalView {

    // Tutti i metodi sono ereditati dalle sotto-interfacce.
    // Calendar funge da interfaccia aggregante per i client che necessitano
    // dell'accesso completo al modello.

}
