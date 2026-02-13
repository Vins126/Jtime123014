package it.unicam.cs.mpgc.jtime123014.report;

import it.unicam.cs.mpgc.jtime123014.model.Calendar;
import it.unicam.cs.mpgc.jtime123014.model.Day;
import it.unicam.cs.mpgc.jtime123014.model.Priority;
import it.unicam.cs.mpgc.jtime123014.model.Project;
import it.unicam.cs.mpgc.jtime123014.model.Status;
import it.unicam.cs.mpgc.jtime123014.model.Task;
import it.unicam.cs.mpgc.jtime123014.report.dto.IntervalReportDTO;
import it.unicam.cs.mpgc.jtime123014.report.dto.ProjectReportDTO;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleReportService implements ReportService {

    private final Calendar<?> calendar;

    public SimpleReportService(Calendar<?> calendar) {
        this.calendar = calendar;
    }

    @Override
    public ProjectReportDTO generateProjectReport(Project<?> project) {
        long totalEst = 0;
        long totalAct = 0;
        long totalDelta = 0;
        Map<String, Integer> statusCounts = new HashMap<>();
        Map<String, java.util.List<String>> taskListsByStatus = new HashMap<>(); // New map
        Map<Priority, Integer> prioDist = new EnumMap<>(Priority.class);

        // Stats
        List<Task<?>> tasks = project.getTasks();
        for (Task<?> t : tasks) {
            totalEst += t.getDurationEstimate();
            statusCounts.put(t.getStatus().name(), statusCounts.getOrDefault(t.getStatus().name(), 0) + 1);

            // Populate task lists
            taskListsByStatus.putIfAbsent(t.getStatus().name(), new java.util.ArrayList<>());
            String taskDetail = t.getName();
            if (t.getStatus() == Status.COMPLETED) {
                taskDetail += " [Est: " + t.getDurationEstimate() + "m, Act: "
                        + (t.getDurationActual() != null ? t.getDurationActual() : "N/A") + "m]";
            } else {
                taskDetail += " [Est: " + t.getDurationEstimate() + "m]";
            }
            taskListsByStatus.get(t.getStatus().name()).add(taskDetail);

            if (t.getStatus() == Status.COMPLETED) {
                if (t.getDurationActual() != null)
                    totalAct += t.getDurationActual();
                try {
                    totalDelta += t.calculateDelta();
                } catch (Exception e) {
                }
            }
        }
        prioDist.put(project.getPriority(), tasks.size()); // Simplifying project-level priority

        // Completion
        long completedEst = tasks.stream()
                .filter(t -> t.getStatus() == Status.COMPLETED)
                .mapToLong(Task::getDurationEstimate)
                .sum();
        double completion = totalEst == 0 ? 0 : ((double) completedEst / totalEst) * 100.0;

        // Forecast
        double avgDelta = tasks.stream()
                .filter(t -> t.getStatus() == Status.COMPLETED)
                .mapToLong(t -> {
                    try {
                        return t.calculateDelta();
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .average().orElse(0);

        long pendingEst = tasks.stream().filter(t -> t.getStatus() != Status.COMPLETED)
                .mapToLong(Task::getTimeConsuming).sum();
        long predictedExtra = (long) (tasks.stream().filter(t -> t.getStatus() != Status.COMPLETED).count() * avgDelta);
        long daysRemaining = (pendingEst + predictedExtra) / 300; // 5 hours/day
        LocalDate estimatedEnd = LocalDate.now().plusDays(Math.max(0, daysRemaining));

        return new ProjectReportDTO(
                project.getId().toString(),
                project.getName(),
                completion,
                totalEst,
                totalAct,
                totalDelta,
                statusCounts,
                taskListsByStatus, // Pass new map
                prioDist,
                estimatedEnd);
    }

    @Override
    public IntervalReportDTO generateIntervalReport(LocalDate start, LocalDate end) {
        long totalMinutes = 0;
        long completedTaskCount = 0;
        Map<Priority, Long> prioMap = new HashMap<>();

        // Find Tasks completed in range (approximation by Project scan as Task has no
        // completion date in model yet)
        // Assuming we scan all projects and check "if completed, count it".
        // Accurate implementation would require Task::getCompletionDate.
        // For now, we sum up Work Done by scanning Days in range.

        // Use generic Day traversal
        List<? extends Day<?>> days = calendar.getDays().stream()
                .filter(d -> {
                    if (d.getId() instanceof LocalDate) {
                        LocalDate dd = (LocalDate) d.getId();
                        return !dd.isBefore(start) && !dd.isAfter(end);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        double totalSaturation = 0;
        int dayBufferCount = 0;

        for (Day<?> d : days) {
            if (d.getBuffer() > 0) {
                totalSaturation += (double) (d.getBuffer() - d.getFreeBuffer()) / d.getBuffer();
                dayBufferCount++;
            }

            // Tasks in Day
            for (Task<?> t : d.getTasks()) {
                if (t.getStatus() == Status.COMPLETED && t.getDurationActual() != null) {
                    totalMinutes += t.getDurationActual();
                    completedTaskCount++; // Note: double counting if same task on multiple days?
                    // But Task model is simple. Assume 1-to-1 for now or accept approximation.
                }
            }
        }

        double velocity = days.isEmpty() ? 0 : (double) completedTaskCount / days.size();
        double avgSat = dayBufferCount == 0 ? 0 : (totalSaturation / dayBufferCount) * 100.0;

        return new IntervalReportDTO(
                start, end, totalMinutes, velocity, avgSat, prioMap);
    }
}
