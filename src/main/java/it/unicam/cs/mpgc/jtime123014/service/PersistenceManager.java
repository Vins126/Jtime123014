package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.io.*;
import java.nio.file.Path;

/**
 * Gestisce il salvataggio e il caricamento dei dati su file.
 */
public class PersistenceManager implements PersistenceService {

    /**
     * Crea un nuovo gestore della persistenza.
     */
    public PersistenceManager() {
    }

    /**
     * Salva l'intero oggetto Calendar su file.
     *
     * @param calendar il calendario da salvare.
     * @param path     il percorso del file di destinazione.
     * @throws IOException se si verifica un errore di I/O durante il salvataggio.
     */
    public void save(Calendar<?> calendar, Path path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(calendar);
        }
    }

    /**
     * Carica l'oggetto Calendar da file.
     *
     * @param path il percorso del file sorgente.
     * @return il calendario caricato.
     * @throws IOException            se si verifica un errore di I/O.
     * @throws ClassNotFoundException se la classe serializzata non viene trovata.
     */
    public Calendar<?> load(Path path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (Calendar<?>) ois.readObject();
        }
    }
}
