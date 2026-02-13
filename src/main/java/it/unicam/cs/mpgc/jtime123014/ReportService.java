package it.unicam.cs.mpgc.jtime123014;

import java.time.LocalDate;

public interface ReportService {
    ProjectReportDTO generateProjectReport(Project<?> project);

    IntervalReportDTO generateIntervalReport(LocalDate start, LocalDate end);
}
