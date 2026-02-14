package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.Report;
import it.unicam.cs.mpgc.jtime123014.service.ReportSection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Gestisce la finestra che mostra il contenuto di un report a video.
 * <p>
 * Prende i dati del report e li trasforma in elementi grafici (titoli e testo)
 * da mostrare all'utente.
 */
public class ReportViewController {

    /**
     * Costruttore di default.
     */
    public ReportViewController() {
    }

    @FXML
    private Label lblTitle;

    @FXML
    private VBox reportContainer;

    /**
     * Imposta il report da visualizzare e avvia il rendering delle sezioni.
     *
     * @param report Il report da mostrare.
     */
    public void setReport(Report report) {
        if (report == null)
            return;

        lblTitle.setText(report.getTitle());

        clearReportContainer();

        for (ReportSection section : report.getDocument().getSections()) {
            reportContainer.getChildren().add(renderSection(section));
        }
    }

    /**
     * Pulisce il contenitore del report, rimuovendo le sezioni precedenti.
     * Mantiene intatta l'intestazione se presente (indice 0).
     */
    private void clearReportContainer() {
        // Clear existing content (except title if present at index 0)
        // We probably want to keep the title label if it's inside the VBox,
        // but here lblTitle is injected separately.
        // Assuming we kept index 0 logic from original code.
        if (reportContainer.getChildren().size() > 1) {
            reportContainer.getChildren().remove(1, reportContainer.getChildren().size());
        }
    }

    /**
     * Crea un nodo grafico (VBox) per rappresentare una sezione del report.
     *
     * @param section La sezione da renderizzare.
     * @return Il VBox contenente titolo e testo della sezione.
     */
    private VBox renderSection(ReportSection section) {
        VBox sectionBox = new VBox(5);
        sectionBox.getStyleClass().add("report-section");

        Label sectionTitle = new Label(section.getSectionTitle());
        sectionTitle.getStyleClass().add("subheader-label");
        sectionBox.getChildren().add(sectionTitle);

        for (String line : section.getContent()) {
            Text text = new Text(line + "\n");
            TextFlow flow = new TextFlow(text);
            sectionBox.getChildren().add(flow);
        }
        return sectionBox;
    }
}
