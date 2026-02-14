package it.unicam.cs.mpgc.jtime123014.model;

import java.util.List;

/**
 * Interfaccia per la gestione di un insieme di progetti.
 * 
 * Fornisce i metodi per aggiungere, rimuovere e leggere i progetti.
 * Utile quando si vuole lavorare solo con i progetti senza dover dipendere
 * dall'intero Calendario.
 */
public interface ProjectContainer {

    /**
     * Aggiunge un nuovo progetto.
     *
     * @param project il progetto da aggiungere.
     * @return {@code true} se aggiunto con successo.
     * @throws NullPointerException     se il progetto è null.
     * @throws IllegalArgumentException se il progetto esiste già.
     */
    boolean addProject(Project<?> project);

    /**
     * Rimuove un progetto.
     *
     * @param project il progetto da rimuovere.
     * @return {@code true} se rimosso.
     * @throws NullPointerException se il progetto è null.
     */
    boolean removeProject(Project<?> project);

    /**
     * Restituisce la lista di tutti i progetti gestiti.
     *
     * @return lista dei progetti.
     */
    List<Project<?>> getProjects();
}
