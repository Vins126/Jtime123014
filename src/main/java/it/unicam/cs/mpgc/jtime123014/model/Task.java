package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Rappresenta un'attività (Task) all'interno del sistema.
 * <p>
 * Una task è un lavoro da svolgere, che fa parte di un progetto e può essere
 * pianificato in un giorno specifico.
 * Contiene informazioni come il nome, lo stato (da fare, in corso, completata)
 * e quanto tempo si stima che richieda.
 * <p>
 * Questa interfaccia raggruppa diverse funzionalità:
 * <ul>
 * <li>Informazioni base (nome, descrizione).</li>
 * <li>Gestione del completamento (segnala come fatto).</li>
 * <li>Dati per la pianificazione (quanto tempo serve).</li>
 * </ul>
 *
 * @param <ID> Il tipo dell'identificativo della task.
 */
public interface Task<ID> extends Identifiable<ID>, TaskInfo, CompletableTask, SchedulableTask {

    // Tutti i metodi sono ereditati dalle sotto-interfacce.
    // Task funge da interfaccia aggregante per i client che necessitano
    // dell'accesso completo alla task.

}
