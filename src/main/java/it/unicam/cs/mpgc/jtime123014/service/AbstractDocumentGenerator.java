package it.unicam.cs.mpgc.jtime123014.service;

import java.util.Locale;

/**
 * Classe base per i generatori di documenti.
 * 
 * Offre funzioni utili per formattare i numeri (es. percentuali e decimali)
 * e per organizzare i dati in sezioni, evitando di riscrivere lo stesso codice
 * in ogni generatore specifico.
 */
public abstract class AbstractDocumentGenerator implements DocumentGenerator {

    /**
     * Costruttore base per i generatori.
     */
    public AbstractDocumentGenerator() {
    }

    /**
     * Formatta un valore double come stringa percentuale con due cifre decimali.
     *
     * @param value il valore da formattare.
     * @return la stringa formattata (es. "75.50%").
     */
    protected String formatPercentage(double value) {
        return String.format(Locale.US, "%.2f%%", value);
    }

    /**
     * Formatta un valore double con due cifre decimali.
     *
     * @param value il valore da formattare.
     * @return la stringa formattata (es. "123.45").
     */
    protected String formatDecimal(double value) {
        return String.format(Locale.US, "%.2f", value);
    }

    /**
     * Crea una nuova sezione del report con il titolo specificato.
     *
     * @param title il titolo della sezione.
     * @return una nuova istanza di {@link ReportSection}.
     */
    protected ReportSection createSection(String title) {
        return new ReportSection(title);
    }

    /**
     * Aggiunge una riga di contenuto chiave-valore a una sezione.
     *
     * @param section la sezione a cui aggiungere il contenuto.
     * @param key     l'etichetta del dato.
     * @param value   il valore del dato.
     */
    protected void addKeyValue(ReportSection section, String key, Object value) {
        section.addContent(key + ": " + value);
    }
}
