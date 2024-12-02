package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_task.TaskEnteredState;
import interface_adapter.add_task.TaskEnteredViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class TaskEnteredView extends JPanel {
    private final String viewName;
    private final TaskEnteredViewModel taskEnteredViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JLabel taskNameLabel;
    private final JLabel taskDescriptionLabel;
    private final JLabel taskStatusLabel;
    private final JLabel progressLabel;
    private final JProgressBar progressBar;
    private final JButton backButton;

    private Instant startTime;
    private Timer progressTimer;
    private double totalTaskTime;

    private static final Color BACKGROUND_COLOR = new Color(240, 249, 255);
    private static final Color ACCENT_COLOR = new Color(0, 122, 255);
    private static final Color SECONDARY_ACCENT_COLOR = new Color(0, 90, 200);
    private static final Color TEXT_COLOR = new Color(30, 41, 59);
    private static final Color LABEL_COLOR = new Color(135, 206, 250);

    public TaskEnteredView(TaskEnteredViewModel taskEnteredViewModel, ViewManagerModel viewManagerModel) {
        this.taskEnteredViewModel = taskEnteredViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewName = "Task Entered View";

        // Panel Setup
        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        // Initialize components
        taskNameLabel = createStyledLabel("", 32, ACCENT_COLOR, Font.BOLD);
        taskDescriptionLabel = createStyledLabel("", 20, TEXT_COLOR, Font.PLAIN);
        taskStatusLabel = createStyledLabel("", 16, LABEL_COLOR, Font.PLAIN);
        progressLabel = createStyledLabel("", 16, LABEL_COLOR, Font.PLAIN);
        progressBar = createStyledProgressBar();
        backButton = createStyledButton("🔙 Back", SECONDARY_ACCENT_COLOR);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.BOTH;

        contentPanel.add(taskNameLabel, constraints);
        constraints.gridy++;
        contentPanel.add(taskDescriptionLabel, constraints);
        constraints.gridy++;
        contentPanel.add(taskStatusLabel, constraints);
        constraints.gridy++;
        contentPanel.add(progressLabel, constraints);
        constraints.gridy++;
        contentPanel.add(progressBar, constraints);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(backButton);

        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setupTimerAndListeners();
        updateInitialState();
    }

    private void setupTimerAndListeners() {
        startTime = Instant.now();
        progressTimer = new Timer(1000, e -> updateProgress());

        taskEnteredViewModel.addPropertyChangeListener(evt -> {
            TaskEnteredState currentState = (TaskEnteredState) evt.getNewValue();
            updateUIWithNewState(currentState);
        });

        backButton.addActionListener(e -> {
            viewManagerModel.setState("Home View");
            viewManagerModel.firePropertyChanged();
        });
    }

    private void updateProgress() {
        Duration elapsed = Duration.between(startTime, Instant.now());
        double elapsedMinutes = elapsed.toMillis() / 60000.0;
        double progressPercent = Math.min((elapsedMinutes / totalTaskTime) * 100, 100);

        progressBar.setValue((int) progressPercent);
        progressLabel.setText(String.format("⏱️ Time Elapsed: %02d:%02d:%02d",
                elapsed.toHours(),
                elapsed.toMinutesPart(),
                elapsed.toSecondsPart()));

        if (progressPercent >= 100) {
            progressTimer.stop();
            taskStatusLabel.setText("🎉 Task Completed! 🎉");
            progressBar.setForeground(new Color(34, 197, 94));
        }
    }

    private void updateUIWithNewState(TaskEnteredState state) {
        taskNameLabel.setText(state.getTaskName());
        taskDescriptionLabel.setText(state.getTaskDescription());
        taskStatusLabel.setText("In Progress");
        progressLabel.setText(String.format("⏱️ Total Time: %.1f minutes", state.getTaskTime()));

        totalTaskTime = state.getTaskTime();
        startTime = Instant.now();

        if (totalTaskTime > 0) {
            progressTimer.start();
        }
    }

    private void updateInitialState() {
        TaskEnteredState currentState = taskEnteredViewModel.getState();
        updateUIWithNewState(currentState);
    }

    private JLabel createStyledLabel(String text, int fontSize, Color color, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", fontStyle, fontSize));
        label.setForeground(color);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JProgressBar createStyledProgressBar() {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setPreferredSize(new Dimension(400, 20));
        bar.setForeground(ACCENT_COLOR);
        bar.setBackground(new Color(200, 230, 255));
        bar.setBorderPainted(false);
        bar.setStringPainted(true);
        return bar;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        return button;
    }

    public String getViewName() {
        return viewName;
    }
}