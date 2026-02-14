package it.unicam.cs.mpgc.jtime123014.controller;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.service.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Si occupa di creare e gestire i report.
 * <p>
 * Coordina tutto il processo: recupera i dati dal calendario, prepara il
 * documento
 * leggibile e lo salva su file.
 */
public class ReportController {

    private final ReportService reportService;
    private final DocumentGenerator documentGenerator;
    private final ReportExporter reportExporter;
    private final XMLReportParser reportParser;
    private final Calendar<?> calendar;
    private String outputDirectory = "src/main/resources/it/unicam/cs/mpgc/jtime123014/reports";

    /**
     * Costruttore predefinito.
     * 
     * Usa le implementazioni standard per tutti i servizi necessari.
     *
     * @param calendar il calendario da cui estrarre i dati per i report.
     */
    public ReportController(Calendar<?> calendar) {
        this(calendar,
                new SimpleReportService(calendar),
                new StandardDocumentGenerator(),
                new XMLReportExporter(),
                new XMLReportParser());
    }

    /**
     * Costruttore principale.
     * 
     * Permette di specificare le implementazioni dei vari servizi utilizzati,
     * rendendo la classe flessibile.
     *
     * @param calendar          il calendario sorgente dei dati.
     * @param reportService     il servizio per la generazione dei dati.
     * @param documentGenerator il generatore di documenti.
     * @param reportExporter    l'esportatore di documenti su file.
     * @param reportParser      il parser per leggere i file dei report.
     */
    public ReportController(Calendar<?> calendar, ReportService reportService,
            DocumentGenerator documentGenerator, ReportExporter reportExporter,
            XMLReportParser reportParser) {
        this.calendar = calendar;
        this.reportService = reportService;
        this.documentGenerator = documentGenerator;
        this.reportExporter = reportExporter;
        this.reportParser = reportParser;
    }

    /**
     * Imposta la directory di output per i file dei report.
     *
     * @param outputDirectory Il percorso della directory.
     */
    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * Restituisce la directory di output configurata.
     *
     * @return Il percorso della directory.
     */
    public String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Crea un report completo per un progetto specifico.
     * 
     * Il processo include:
     * 1. Ricerca del progetto per ID.
     * 2. Generazione del DTO.
     * 3. Creazione del Documento.
     * 4. Esportazione su file XML.
     *
     * @param projectId L'ID del progetto.
     * @return L'oggetto {@link Report} generato, o null se il progetto non esiste.
     */
    public Report createProjectReport(String projectId) {
        // Cerca il progetto
        Project<?> targetProject = null;
        for (Project<?> p : calendar.getProjects()) {
            if (p.getId().toString().equals(projectId)) {
                targetProject = p;
                break;
            }
        }

        if (targetProject == null) {
            System.err.println("Project not found: " + projectId);
            return null;
        }

        ProjectReportDTO dto = reportService.generateProjectReport(targetProject);

        Document doc = documentGenerator.transform(dto);

        String title = targetProject.getName() + " " + LocalDate.now();
        String fileName = "Report_" + targetProject.getName().replace(" ", "_") + "_" + LocalDate.now() + ".xml";
        String filePath = outputDirectory + "/" + fileName;

        new File(outputDirectory).mkdirs();
        reportExporter.export(doc, filePath);

        return new Report(title, doc, new File(filePath));
    }

    /**
     * Crea un report per un intervallo di date.
     *
     * @param start La data di inizio.
     * @param end   La data di fine.
     * @return L'oggetto {@link Report} generato.
     */
    public Report createIntervalReport(LocalDate start, LocalDate end) {
        IntervalReportDTO dto = reportService.generateIntervalReport(start, end);

        Document doc = documentGenerator.transform(dto);
        String title = start + "-" + end;
        String fileName = "IntervalReport_" + start + "_to_" + end + ".xml";
        String filePath = outputDirectory + "/" + fileName;

        new File(outputDirectory).mkdirs();
        reportExporter.export(doc, filePath);

        return new Report(title, doc, new File(filePath));
    }

    /**
     * Scansiona la directory dei report configurata e restituisce la lista dei
     * report disponibili.
     * 
     * I file XML trovati vengono parsati tramite {@link XMLReportParser} per
     * ricostruire
     * gli oggetti {@link Document}.
     *
     * @return Una lista di oggetti {@link Report} caricati da file.
     */
    public List<Report> loadReportsFromDirectory() {
        List<Report> reports = new ArrayList<>();
        File dir = new File(outputDirectory);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".xml"));
            if (files != null) {
                for (File file : files) {
                    String name = file.getName().replace(".xml", "").replace("_", " ");
                    Document doc = reportParser.parse(file);
                    reports.add(new Report(name, doc, file));
                }
            }
        }
        return reports;
    }
}
