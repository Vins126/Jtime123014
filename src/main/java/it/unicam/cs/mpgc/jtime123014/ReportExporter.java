package it.unicam.cs.mpgc.jtime123014;

public interface ReportExporter {
    /**
     * Esporta il documento astratto su file fisico.
     * 
     * @param doc      il modello del documento.
     * @param filePath percorso di destinazione.
     * @return true se successo.
     */
    boolean export(Document doc, String filePath);
}
