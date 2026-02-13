package it.unicam.cs.mpgc.jtime123014.report;

import it.unicam.cs.mpgc.jtime123014.report.dto.IntervalReportDTO;
import it.unicam.cs.mpgc.jtime123014.report.dto.ProjectReportDTO;
import java.util.Map;

public class StandardDocumentGenerator implements DocumentGenerator {

    @Override
    public Document transform(ProjectReportDTO report) {
        Document doc = new Document("Project Report: " + report.getProjectName());

        // Section 1: Overview
        ReportSection overview = new ReportSection("Overview");
        overview.addContent("Project ID: " + report.getProjectId());
        overview.addContent("Completion: " + String.format("%.2f", report.getCompletionPercentage()) + "%");
        overview.addContent("Estimated End Date: " + report.getEstimatedEndDate());
        doc.addSection(overview);

        // Section 2: Time Analysis
        ReportSection time = new ReportSection("Time Analysis");
        time.addContent("Total Estimated: " + report.getTotalEstimatedTime());
        time.addContent("Total Actual: " + report.getTotalActualTime());
        time.addContent("Total Delta: " + report.getTotalDelta());
        doc.addSection(time);

        // Section 3: Status Breakdown
        ReportSection status = new ReportSection("Task Status Details");

        // Summary Counts
        status.addContent("--- Summary ---");
        for (Map.Entry<String, Integer> entry : report.getTaskStatusCounts().entrySet()) {
            status.addContent(entry.getKey() + ": " + entry.getValue());
        }

        // Detailed Lists
        status.addContent("--- Detailed Lists ---");
        for (Map.Entry<String, java.util.List<String>> entry : report.getTaskListsByStatus().entrySet()) {
            status.addContent("Status: " + entry.getKey());
            for (String taskStr : entry.getValue()) {
                status.addContent(" - " + taskStr);
            }
        }

        doc.addSection(status);

        return doc;
    }

    @Override
    public Document transform(IntervalReportDTO report) {
        Document doc = new Document("Interval Report (" + report.getStartDate() + " - " + report.getEndDate() + ")");

        ReportSection stats = new ReportSection("Statistics");
        stats.addContent("Total Minutes Worked: " + report.getTotalMinutesWorked());
        stats.addContent("Average Velocity (Tasks/Day): " + String.format("%.2f", report.getAverageVelocity()));
        stats.addContent("Buffer Saturation: " + String.format("%.2f", report.getAverageBufferSaturation()) + "%");
        doc.addSection(stats);

        return doc;
    }
}
