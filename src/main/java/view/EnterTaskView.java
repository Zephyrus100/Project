package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_task.EnterTaskController;
import interface_adapter.add_task.EnterTaskState;
import interface_adapter.add_task.EnterTaskViewModel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class EnterTaskView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Enter Task";
    private final EnterTaskViewModel enterTaskViewModel;
    private final ViewManagerModel viewManagerModel;
    private EnterTaskController enterTaskController;

    // UI Components
    private final JTextField taskNameField;
    private final JTextField taskDescriptionField;
    private final JTextField hoursField;
    private final JTextField minutesField;
    private final JLabel taskNameErrorLabel;
    private final JLabel taskDescriptionErrorLabel;
    private final JButton submitButton;
    private final JButton homeButton;

    // Simple Color Scheme
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private static final Color ACCENT_COLOR = new Color(59, 130, 246);
    private static final Color ERROR_COLOR = new Color(239, 68, 68);
    private static final Color TEXT_COLOR = new Color(15, 23, 42);

    public EnterTaskView(EnterTaskViewModel enterTaskViewModel, ViewManagerModel viewManagerModel) {
        this.enterTaskViewModel = enterTaskViewModel;
        this.viewManagerModel = viewManagerModel;

        // Initialize components
        taskNameField = createStyledTextField();
        taskDescriptionField = createStyledTextField();
        hoursField = createStyledTextField();
        minutesField = createStyledTextField();
        taskNameErrorLabel = createStyledLabel();
        taskDescriptionErrorLabel = createStyledLabel();
        submitButton = createStyledButton("Create Task");
        homeButton = createStyledButton("Back");

        // Setup panel
        setupPanel();
        setupListeners();
        enterTaskViewModel.addPropertyChangeListener(this);
    }

    private void setupPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Add title
        JLabel titleLabel = new JLabel("New Task");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(25));

        // Add form fields
        mainPanel.add(createFormPanel());

        add(mainPanel, BorderLayout.CENTER);
    }

    private void addFormField(JPanel panel, String labelText, JTextField field, JLabel errorLabel) {
        JLabel label = createStyledLabel();
        label.setText(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(400, 35));

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(5));
        panel.add(errorLabel);
    }

    private JPanel createTimeInputPanel() {
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        timePanel.setBackground(BACKGROUND_COLOR);

        JLabel timeLabel = createStyledLabel();
        timeLabel.setText("Duration:");
        timePanel.add(timeLabel);

        JPanel hoursPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        hoursPanel.setBackground(BACKGROUND_COLOR);
        JLabel hoursLabel = createStyledLabel();
        hoursLabel.setText("Hours");
        hoursField.setPreferredSize(new Dimension(80, 35));
        hoursPanel.add(hoursField);
        hoursPanel.add(hoursLabel);

        JPanel minutesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        minutesPanel.setBackground(BACKGROUND_COLOR);
        JLabel minutesLabel = createStyledLabel();
        minutesLabel.setText("Minutes");
        minutesField.setPreferredSize(new Dimension(80, 35));
        minutesPanel.add(minutesField);
        minutesPanel.add(minutesLabel);

        timePanel.add(hoursPanel);
        timePanel.add(minutesPanel);

        return timePanel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Inter", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JLabel createStyledLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Inter", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BACKGROUND_COLOR);

        // Add all the existing fields...
        addFormField(formPanel, "Task Name", taskNameField, taskNameErrorLabel);
        formPanel.add(Box.createVerticalStrut(20));

        addFormField(formPanel, "Description", taskDescriptionField, taskDescriptionErrorLabel);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(createTimeInputPanel());
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(Box.createVerticalStrut(25));

        // Fixed button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));  // 1 row, 2 columns, 10px gap
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setMaximumSize(new Dimension(300, 40));  // Fixed width for button panel
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add buttons with equal size
        submitButton.setPreferredSize(new Dimension(140, 35));
        homeButton.setPreferredSize(new Dimension(140, 35));

        buttonPanel.add(submitButton);
        buttonPanel.add(homeButton);

        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalStrut(20));

        return formPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(ACCENT_COLOR);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ACCENT_COLOR.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }
        });

        return button;
    }

    private void setupListeners() {
        setupTextFieldListener(taskNameField, state -> state.setTaskName(taskNameField.getText()), taskNameErrorLabel);
        setupTextFieldListener(taskDescriptionField, state -> state.setTaskDescription(taskDescriptionField.getText()), taskDescriptionErrorLabel);
        setupTimeFieldListeners();

        submitButton.addActionListener(e -> {
            if (enterTaskController != null) {
                EnterTaskState currentState = enterTaskViewModel.getState();
                if (currentState.getTaskName().trim().isEmpty()) {
                    taskNameErrorLabel.setText("Task name cannot be empty");
                    taskNameErrorLabel.setForeground(ERROR_COLOR);
                } else if (currentState.getTaskDescription().trim().isEmpty()) {
                    taskDescriptionErrorLabel.setText("Task description cannot be empty");
                    taskDescriptionErrorLabel.setForeground(ERROR_COLOR);
                } else {
                    enterTaskController.addTask(
                            currentState.getTaskName(),
                            currentState.getTaskDescription(),
                            currentState.getTaskTime()
                    );
                }
            }
        });

        homeButton.addActionListener(e -> {
            viewManagerModel.setState("Home View");
            viewManagerModel.firePropertyChanged();
        });
    }

    private void setupTimeFieldListeners() {
        DocumentListener timeListener = new DocumentListener() {
            private void updateTime() {
                try {
                    String hoursText = hoursField.getText().trim();
                    String minutesText = minutesField.getText().trim();

                    double hours = hoursText.isEmpty() ? 0 : Double.parseDouble(hoursText);
                    double minutes = minutesText.isEmpty() ? 0 : Double.parseDouble(minutesText);
                    double totalMinutes = (hours * 60) + minutes;

                    EnterTaskState currentState = enterTaskViewModel.getState();
                    if (totalMinutes > 0) {
                        currentState.setTaskTime(totalMinutes);
                        enterTaskViewModel.setState(currentState);
                        taskNameErrorLabel.setText("");
                        taskDescriptionErrorLabel.setText("");
                    } else {
                        taskNameErrorLabel.setForeground(ERROR_COLOR);
                        taskNameErrorLabel.setText("Field cannot be empty");
                        taskDescriptionErrorLabel.setForeground(ERROR_COLOR);
                        taskDescriptionErrorLabel.setText("Field cannot be empty");
                    }
                } catch (NumberFormatException e) {
                    taskNameErrorLabel.setForeground(ERROR_COLOR);
                    taskNameErrorLabel.setText("Please enter valid numbers");
                    taskDescriptionErrorLabel.setForeground(ERROR_COLOR);
                    taskDescriptionErrorLabel.setText("Please enter valid numbers");
                }
            }

            public void insertUpdate(DocumentEvent e) { updateTime(); }
            public void removeUpdate(DocumentEvent e) { updateTime(); }
            public void changedUpdate(DocumentEvent e) { updateTime(); }
        };

        hoursField.getDocument().addDocumentListener(timeListener);
        minutesField.getDocument().addDocumentListener(timeListener);
    }

    private void setupTextFieldListener(JTextField field, StateUpdater updater, JLabel errorLabel) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                EnterTaskState currentState = enterTaskViewModel.getState();
                updater.update(currentState);
                enterTaskViewModel.setState(currentState);
                errorLabel.setText("");
            }

            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    @FunctionalInterface
    private interface StateUpdater {
        void update(EnterTaskState state);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Handle any additional action events if needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EnterTaskState state = (EnterTaskState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(EnterTaskState state) {
        taskNameField.setText(state.getTaskName());
        taskDescriptionField.setText(state.getTaskDescription());

        double totalMinutes = state.getTaskTime();
        int hours = (int) (totalMinutes / 60);
        int minutes = (int) (totalMinutes % 60);

        hoursField.setText(String.valueOf(hours));
        minutesField.setText(String.valueOf(minutes));
    }

    public void setEnterTaskController(EnterTaskController controller) {
        this.enterTaskController = controller;
    }

    public String getViewName() {
        return viewName;
    }
}