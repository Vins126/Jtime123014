package it.unicam.cs.mpgc.jtime123014.model;

/**
 * Rappresenta un'attività (Task) all'interno del sistema.
 * 
 * Una task è un lavoro da svolgere, che fa parte di un progetto e può essere
 * pianificato in un giorno specifico.
 * Contiene informazioni come il nome, lo stato (da fare, in corso, completata)
 * e quanto tempo si stima che richieda.
 * 
 * Questa interfaccia raggruppa diverse funzionalità:
 * 
 * Informazioni base (nome, descrizione).
 * Gestione del completamento (segnala come fatto).
 * Dati per la pianificazione (quanto tempo serve).
 *
 * @param <ID> Il tipo dell'identificativo della task.
 */
public interface Task<ID> extends Identifiable<ID>, TaskInfo, CompletableTask, SchedulableTask {

    // Tutti i metodi sono ereditati dalle interfacce.
    // Task funge da interfaccia unica

}
