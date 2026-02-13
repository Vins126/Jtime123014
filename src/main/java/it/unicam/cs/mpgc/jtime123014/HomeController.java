package it.unicam.cs.mpgc.jtime123014;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.List;

public class HomeController {

    @FXML
    private Label totalProjectsLabel;

    @FXML
    private Label pendingTasksLabel;

    @FXML
    private Label nextDeadlineLabel;

    @FXML
    public void initialize() {
        JTimeController controller = Context.getInstance().getController();
        List<Project<?>> projects = controller.getProjects();

        totalProjectsLabel.setText(String.valueOf(projects.size()));

        long pendingTasks = projects.stream()
                .mapToLong(p -> p.getPendingTasks().size())
                .sum();

        pendingTasksLabel.setText(String.valueOf(pendingTasks));

        // Next Deadline logic placeholder
        nextDeadlineLabel.setText("-");
    }
}
