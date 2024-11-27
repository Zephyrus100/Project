package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.add_task.EnterTaskController;
import interface_adapter.add_task.EnterTaskState;
import interface_adapter.add_task.EnterTaskViewModel;

public class EnterTaskView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Enter Task";
    private final EnterTaskViewModel enterTaskViewModel;

    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(255, 255, 255, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2.setColor(new Color(100, 150, 250));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        return textField;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(30, 30, 70));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(100, 150, 250),
                        0, getHeight(), new Color(80, 130, 230)
                );
                g2.setPaint(gradient);

                RoundRectangle2D roundedRect = new RoundRectangle2D.Double(
                        0, 0, getWidth()-1, getHeight()-1, 20, 20
                );
                g2.fill(roundedRect);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 50));

        return button;
    }

    private final JTextField taskNameInputField = createStyledTextField(10);
    private final JTextField taskDescriptionInputField = createStyledTextField(10);
    private final JTextField hoursInputField = createStyledTextField(3);
    private final JTextField minutesInputField = createStyledTextField(3);

    private final JLabel taskErrorField = new JLabel();

    private EnterTaskController enterTaskController;

    public EnterTaskView(EnterTaskViewModel enterTaskViewModel) {
        this.enterTaskViewModel = enterTaskViewModel;
        this.setEnterTaskController(enterTaskController);
        enterTaskViewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 255));

        JPanel containerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(200, 200, 220, 100));
                g2.fillRoundRect(5, 5, getWidth()-10, getHeight()-10, 25, 25);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth()-10, getHeight()-10, 25, 25);

                super.paintComponent(g);
            }
        };
        containerPanel.setOpaque(false);
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Create New Task");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 30, 70));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JPanel taskNamePanel = createInputPanel("Task Name", taskNameInputField);
        JPanel taskDescriptionPanel = createInputPanel("Task Description", taskDescriptionInputField);

        JPanel timeInputPanel = new JPanel();
        timeInputPanel.setLayout(new BoxLayout(timeInputPanel, BoxLayout.X_AXIS));
        timeInputPanel.setOpaque(false);

        JLabel hoursLabel = createStyledLabel("Hours:");
        JLabel minutesLabel = createStyledLabel("Minutes:");

        timeInputPanel.add(Box.createHorizontalGlue());
        timeInputPanel.add(hoursLabel);
        timeInputPanel.add(Box.createHorizontalStrut(5));
        timeInputPanel.add(hoursInputField);
        timeInputPanel.add(Box.createHorizontalStrut(10));
        timeInputPanel.add(minutesLabel);
        timeInputPanel.add(Box.createHorizontalStrut(5));
        timeInputPanel.add(minutesInputField);
        timeInputPanel.add(Box.createHorizontalGlue());

        final JButton submitButton = createStyledButton("Submit");
        submitButton.setBackground(new Color(200, 200, 255));
        submitButton.setForeground(new Color(240, 110, 60));
        submitButton.addActionListener(
                evt -> {
                    if (evt.getSource() == submitButton) {
                        if (enterTaskController != null) {
                            final EnterTaskState currentState = enterTaskViewModel.getState();
                            enterTaskController.addTask(currentState.getTaskName(), currentState.getTaskDescription()
                                    , currentState.getTaskTime());
                        }
                        else {
                            System.out.println("EnterTaskController is null");
                        }
                    }
                }
        );

        taskErrorField.setForeground(Color.RED);
        taskErrorField.setFont(new Font("Arial", Font.BOLD, 12));
        taskErrorField.setAlignmentX(Component.CENTER_ALIGNMENT);

        addDocumentListeners();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(taskNamePanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(taskDescriptionPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(timeInputPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(taskErrorField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(submitButton);
        mainPanel.add(Box.createVerticalGlue());

        containerPanel.add(Box.createHorizontalGlue());
        containerPanel.add(mainPanel);
        containerPanel.add(Box.createHorizontalGlue());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(50, 50, 50, 50);
        add(containerPanel, gbc);
    }

    private JPanel createInputPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = createStyledLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);

        return panel;
    }

    private void addDocumentListeners() {
        taskNameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final EnterTaskState currentState = enterTaskViewModel.getState();
                currentState.setTaskName(taskNameInputField.getText());
                enterTaskViewModel.setState(currentState);
            }

            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });

        taskDescriptionInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final EnterTaskState currentState = enterTaskViewModel.getState();
                currentState.setTaskDescription(taskDescriptionInputField.getText());
                enterTaskViewModel.setState(currentState);
            }

            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });

        hoursInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateTime() {
                try {
                    final EnterTaskState currentState = enterTaskViewModel.getState();
                    String hoursText = hoursInputField.getText().trim();
                    String minutesText = minutesInputField.getText().trim();

                    double hours = hoursText.isEmpty() ? 0 : Double.parseDouble(hoursText);
                    double minutes = minutesText.isEmpty() ? 0 : Double.parseDouble(minutesText);

                    double totalMinutes = (hours * 60) + minutes;

                    if (totalMinutes > 0) {
                        currentState.setTaskTime(totalMinutes);
                        enterTaskViewModel.setState(currentState);
                        taskErrorField.setText("");
                    } else {
                        taskErrorField.setText("Time must be positive");
                    }
                } catch (NumberFormatException e) {
                    taskErrorField.setText("Please enter valid numbers");
                }
            }

            public void insertUpdate(DocumentEvent e) { updateTime(); }
            public void removeUpdate(DocumentEvent e) { updateTime(); }
            public void changedUpdate(DocumentEvent e) { updateTime(); }
        });

        minutesInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateTime() {
                try {
                    final EnterTaskState currentState = enterTaskViewModel.getState();
                    String hoursText = hoursInputField.getText().trim();
                    String minutesText = minutesInputField.getText().trim();

                    double hours = hoursText.isEmpty() ? 0 : Double.parseDouble(hoursText);
                    double minutes = minutesText.isEmpty() ? 0 : Double.parseDouble(minutesText);

                    double totalMinutes = (hours * 60) + minutes;

                    if (totalMinutes > 0) {
                        currentState.setTaskTime(totalMinutes);
                        enterTaskViewModel.setState(currentState);
                        taskErrorField.setText("");
                    } else {
                        taskErrorField.setText("Time must be positive");
                    }
                } catch (NumberFormatException e) {
                    taskErrorField.setText("Please enter valid numbers");
                }
            }

            public void insertUpdate(DocumentEvent e) { updateTime(); }
            public void removeUpdate(DocumentEvent e) { updateTime(); }
            public void changedUpdate(DocumentEvent e) { updateTime(); }
        });
    }

    public String getViewName() {
        return viewName;
    }

    public void setEnterTaskController(EnterTaskController enterTaskController) {
        this.enterTaskController = enterTaskController;
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final EnterTaskState state = (EnterTaskState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(EnterTaskState state) {
        taskNameInputField.setText(state.getTaskName());
        taskDescriptionInputField.setText(state.getTaskDescription());

        double totalMinutes = state.getTaskTime();
        int hours = (int) (totalMinutes / 60);
        int minutes = (int) (totalMinutes % 60);

        hoursInputField.setText(String.valueOf(hours));
        minutesInputField.setText(String.valueOf(minutes));
    }
}