package it.unicam.cs.mpgc.jtime123014.report;

import it.unicam.cs.mpgc.jtime123014.model.Project;
import it.unicam.cs.mpgc.jtime123014.report.dto.IntervalReportDTO;
import it.unicam.cs.mpgc.jtime123014.report.dto.ProjectReportDTO;
import java.time.LocalDate;

public interface ReportService {
    ProjectReportDTO generateProjectReport(Project<?> project);

    IntervalReportDTO generateIntervalReport(LocalDate start, LocalDate end);
}
