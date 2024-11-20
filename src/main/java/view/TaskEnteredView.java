package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_task.TaskEnteredState;
import interface_adapter.add_task.TaskEnteredViewModel;

import javax.swing.*;
import java.awt.*;

public class TaskEnteredView extends JPanel {

    private final String viewName;
    private TaskEnteredViewModel taskEnteredViewModel;

    private JLabel taskName = new JLabel();
    private JLabel taskDescription = new JLabel();
    private JLabel taskStatus = new JLabel();
    private final ViewManagerModel viewManagerModel;

    public TaskEnteredView(TaskEnteredViewModel taskEnteredViewModel, ViewManagerModel viewManagerModel) {
        this.taskEnteredViewModel = taskEnteredViewModel;
        this.viewManagerModel = viewManagerModel;

        viewName = "Task Entered View";

        this.setLayout(new GridBagLayout()); // GridBagLayout centers components
        this.setBackground(new Color(240, 248, 255)); // Light blue background

        setupLabel(taskName);
        setupLabel(taskDescription);
        setupLabel(taskStatus);

        taskEnteredViewModel.addPropertyChangeListener(evt -> {
            TaskEnteredState currentState = (TaskEnteredState) evt.getNewValue();

            taskName.setText(formatHtml("Task:", currentState.getTaskName()));
            taskDescription.setText(formatHtml("Description:", currentState.getTaskDescription()));
            taskStatus.setText(formatHtml("Status:", "In Progress"));
        });

        TaskEnteredState currentState = taskEnteredViewModel.getState();
        taskName.setText(formatHtml("Task:", currentState.getTaskName()));
        taskDescription.setText(formatHtml("Description:", currentState.getTaskDescription()));
        taskStatus.setText(formatHtml("Status:", "In Progress"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(taskName, gbc);

        gbc.gridy = 1;
        this.add(taskDescription, gbc);

        gbc.gridy = 2;
        this.add(taskStatus, gbc);
    }

    private void setupLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(30, 30, 30)); // Dark text color
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private String formatHtml(String label, String value) {
        return String.format(
                "<html><div style='text-align: center;'><span style='color: navy;'>%s</span><br><b>%s</b></div></html>",
                label, value
        );
    }
}
