package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.Calendar;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Interfaccia per il salvataggio e il caricamento dei dati.
 * <p>
 * Permette di salvare lo stato del calendario su file e di ricaricarlo
 * al prossimo avvio dell'applicazione.
 */
public interface PersistenceService {

    /**
     * Salva l'intero oggetto Calendar su un supporto persistente.
     *
     * @param calendar il calendario da salvare.
     * @param path     il percorso di destinazione.
     * @throws IOException se si verifica un errore di I/O durante il salvataggio.
     */
    void save(Calendar<?> calendar, Path path) throws IOException;

    /**
     * Carica l'oggetto Calendar da un supporto persistente.
     *
     * @param path il percorso del file sorgente.
     * @return il calendario caricato.
     * @throws IOException            se si verifica un errore di I/O.
     * @throws ClassNotFoundException se la classe serializzata non viene trovata.
     */
    Calendar<?> load(Path path) throws IOException, ClassNotFoundException;
}
