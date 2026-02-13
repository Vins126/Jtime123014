package it.unicam.cs.mpgc.jtime123014.report;

import it.unicam.cs.mpgc.jtime123014.report.dto.IntervalReportDTO;
import it.unicam.cs.mpgc.jtime123014.report.dto.ProjectReportDTO;

public interface DocumentGenerator {
    Document transform(ProjectReportDTO report);

    Document transform(IntervalReportDTO report);
}
