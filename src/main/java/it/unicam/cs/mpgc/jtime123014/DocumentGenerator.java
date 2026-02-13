package it.unicam.cs.mpgc.jtime123014;


public interface DocumentGenerator {
    Document transform(ProjectReportDTO report);

    Document transform(IntervalReportDTO report);
}
