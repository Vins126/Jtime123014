package it.unicam.cs.mpgc.jtime123014;

import java.time.LocalDate;
import java.util.Map;

public class ProjectReportDTO {
    private final String projectId;
    private final String projectName;
    private final double completionPercentage;
    private final long totalEstimatedTime;
    private final long totalActualTime;
    private final long totalDelta; // Actual - Estimated
    private final Map<String, Integer> taskStatusCounts; // e.g., "COMPLETED" -> 5
    private final Map<String, java.util.List<String>> taskListsByStatus; // e.g., "COMPLETED" -> ["Task A", "Task B"]
    private final Map<Priority, Integer> priorityDistribution;
    private final LocalDate estimatedEndDate;

    public ProjectReportDTO(String projectId, String projectName, double completionPercentage,
            long totalEstimatedTime, long totalActualTime, long totalDelta,
            Map<String, Integer> taskStatusCounts,
            Map<String, java.util.List<String>> taskListsByStatus,
            Map<Priority, Integer> priorityDistribution,
            LocalDate estimatedEndDate) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.completionPercentage = completionPercentage;
        this.totalEstimatedTime = totalEstimatedTime;
        this.totalActualTime = totalActualTime;
        this.totalDelta = totalDelta;
        this.taskStatusCounts = taskStatusCounts;
        this.taskListsByStatus = taskListsByStatus;
        this.priorityDistribution = priorityDistribution;
        this.estimatedEndDate = estimatedEndDate;
    }

    // Getters
    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public long getTotalEstimatedTime() {
        return totalEstimatedTime;
    }

    public long getTotalActualTime() {
        return totalActualTime;
    }

    public long getTotalDelta() {
        return totalDelta;
    }

    public Map<String, Integer> getTaskStatusCounts() {
        return taskStatusCounts;
    }

    public Map<String, java.util.List<String>> getTaskListsByStatus() {
        return taskListsByStatus;
    }

    public Map<Priority, Integer> getPriorityDistribution() {
        return priorityDistribution;
    }

    public LocalDate getEstimatedEndDate() {
        return estimatedEndDate;
    }
}
