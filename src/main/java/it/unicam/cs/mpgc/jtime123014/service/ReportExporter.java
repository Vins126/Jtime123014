package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

/**
 * Interfaccia per l'esportazione dei report su file.
 * <p>
 * Implementata dalle classi che gestiscono formati specifici (es. XML, JSON).
 */
public interface ReportExporter {
    /**
     * Scrive il documento su un file nel percorso specificato.
     * 
     * @param doc      il documento da esportare.
     * @param filePath dove salvare il file.
     * @return true se il salvataggio è andato a buon fine.
     */
    boolean export(Document doc, String filePath);
}
