package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_task.TaskEnteredState;
import interface_adapter.add_task.TaskEnteredViewModel;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import javax.swing.Timer;

public class TaskEnteredView extends JPanel {

    private final String viewName;
    private TaskEnteredViewModel taskEnteredViewModel;

    private JLabel taskName = new JLabel();
    private JLabel taskDescription = new JLabel();
    private JLabel taskStatus = new JLabel();
    private final ViewManagerModel viewManagerModel;
    private JProgressBar progressBar;
    private JLabel timeLabel;
    private Instant startTime;
    private Timer progressTimer;
    private double totalTaskTime;

    private JButton homePage = new JButton();

    public TaskEnteredView(TaskEnteredViewModel taskEnteredViewModel, ViewManagerModel viewManagerModel) {
        this.taskEnteredViewModel = taskEnteredViewModel;
        this.viewManagerModel = viewManagerModel;

        viewName = "Task Entered View";

        this.setLayout(new GridBagLayout()); // GridBagLayout centers components
        this.setBackground(new Color(240, 248, 255)); // Light blue background

        setupLabel(taskName);
        setupLabel(taskDescription);
        setupLabel(taskStatus);

        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(300, 40));
        progressBar.setStringPainted(true);
        timeLabel = new JLabel();

        startTime = Instant.now();
        progressTimer = new Timer(1000, e -> updateProgress()); // Update every second

        taskEnteredViewModel.addPropertyChangeListener(evt -> {
            TaskEnteredState currentState = (TaskEnteredState) evt.getNewValue();

            taskName.setText(formatHtml("Task:", currentState.getTaskName()));
            taskDescription.setText(formatHtml("Description:", currentState.getTaskDescription()));
            taskStatus.setText(formatHtml("Status:", "In Progress"));
            timeLabel.setText(String.format("Time: %.1f minutes", currentState.getTaskTime()));

            totalTaskTime = currentState.getTaskTime();

            startTime = Instant.now();
            if (totalTaskTime > 0) {
                progressTimer.start();
            }
        });

        final JPanel buttons = new JPanel();

        homePage = new JButton("Home Page");
        buttons.add(homePage);


        TaskEnteredState currentState = taskEnteredViewModel.getState();
        taskName.setText(formatHtml("Task:", currentState.getTaskName()));
        taskDescription.setText(formatHtml("Description:", currentState.getTaskDescription()));
        taskStatus.setText(formatHtml("Status:", "In Progress"));
        timeLabel.setText(String.format("Time: %.1f minutes", currentState.getTaskTime()));
        totalTaskTime = currentState.getTaskTime();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(taskName, gbc);

        gbc.gridy = 1;
        this.add(taskDescription, gbc);

        gbc.gridy = 2;
        this.add(taskStatus, gbc);

        gbc.gridy = 3;
        this.add(timeLabel, gbc);

        gbc.gridy = 4;
        this.add(progressBar, gbc);

        gbc.gridy = 5;
        this.add(homePage, gbc);

        homePage.addActionListener(evt -> {
            if (evt.getSource().equals(homePage)) {
                viewManagerModel.setState("Home View");
                viewManagerModel.firePropertyChanged();
            }
        });
        this.add(buttons);
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
    private void updateProgress() {
        double elapsedMinutes = (System.currentTimeMillis() - startTime.toEpochMilli()) / 60000.0;
        double progressPercent = Math.min((elapsedMinutes / totalTaskTime) * 100, 100);

        progressBar.setValue((int) progressPercent);
        timeLabel.setText(String.format("Time: %.1f / %.1f minutes", elapsedMinutes, totalTaskTime));

        if (progressPercent >= 100) {
            progressTimer.stop();
            taskStatus.setText(formatHtml("Status:", "Completed!"));
        }
    }

}
