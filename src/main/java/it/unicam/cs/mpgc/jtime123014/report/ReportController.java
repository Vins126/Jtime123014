package it.unicam.cs.mpgc.jtime123014.report;

import it.unicam.cs.mpgc.jtime123014.model.Calendar;
import it.unicam.cs.mpgc.jtime123014.model.Project;
import it.unicam.cs.mpgc.jtime123014.report.dto.IntervalReportDTO;
import it.unicam.cs.mpgc.jtime123014.report.dto.ProjectReportDTO;
import java.io.File;
import java.time.LocalDate;

public class ReportController {

    private final ReportService reportService;
    private final DocumentGenerator documentGenerator;
    private final ReportExporter reportExporter;
    private final Calendar<?> calendar; // Needed to look up projects if ID is passed
    private String outputDirectory = "reports"; // Default directory

    public ReportController(Calendar<?> calendar) {
        this.calendar = calendar;
        this.reportService = new SimpleReportService(calendar);
        this.documentGenerator = new StandardDocumentGenerator();
        this.reportExporter = new XMLReportExporter();
    }

    // Constructor injection for testing or alternative implementations
    public ReportController(Calendar<?> calendar, ReportService service, DocumentGenerator generator,
            ReportExporter exporter) {
        this.calendar = calendar;
        this.reportService = service;
        this.documentGenerator = generator;
        this.reportExporter = exporter;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void createProjectReport(String projectId) {
        // Look up project
        // Assuming ID is UUID string, but Model might use object.
        // We iterate to find matching ID string.
        Project<?> targetProject = null;
        for (Project<?> p : calendar.getProjects()) {
            if (p.getId().toString().equals(projectId)) {
                targetProject = p;
                break;
            }
        }

        if (targetProject == null) {
            System.err.println("Project not found: " + projectId);
            return;
        }

        // 1. Service -> DTO
        ProjectReportDTO dto = reportService.generateProjectReport(targetProject);

        // 2. Generator -> Document
        Document doc = documentGenerator.transform(dto);

        // 3. Exporter -> File
        File outputDir = new File(outputDirectory);
        if (!outputDir.exists())
            outputDir.mkdirs();

        String filename = outputDirectory + "/Report_" + targetProject.getName().replaceAll("\\s+", "_") + "_"
                + LocalDate.now()
                + ".xml";

        boolean success = reportExporter.export(doc, filename);

        if (success) {
            System.out.println("Report generated successfully: " + filename);
        } else {
            System.err.println("Failed to generate report: " + filename);
        }
    }

    public void createIntervalReport(LocalDate start, LocalDate end) {
        // 1. Service -> DTO
        IntervalReportDTO dto = reportService.generateIntervalReport(start, end);

        // 2. Generator -> Document
        Document doc = documentGenerator.transform(dto);

        // 3. Exporter -> File
        File outputDir = new File(outputDirectory);
        if (!outputDir.exists())
            outputDir.mkdirs();

        String filename = outputDirectory + "/IntervalReport_" + start + "_to_" + end + ".xml";

        boolean success = reportExporter.export(doc, filename);

        if (success) {
            System.out.println("Report generated successfully: " + filename);
        } else {
            System.err.println("Failed to generate report: " + filename);
        }
    }
}
