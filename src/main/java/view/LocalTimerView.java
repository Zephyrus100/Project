package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.local_timer.LocalTimerController;
import interface_adapter.local_timer.LocalTimerState;
import interface_adapter.local_timer.LocalTimerViewModel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


import java.awt.Component;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingConstants;

/**
 * The View for the timer functionality of the program.
 * Simplified version with just the essential timer controls.
 */
public class LocalTimerView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName;
    private final LocalTimerViewModel timerViewModel;
    private final LocalTimerController timerController;

    private final JLabel timeLabel;
    private final RoundedButton startButton;
    private final RoundedButton pauseButton;
    private final RoundedButton resumeButton;
    private final RoundedButton stopButton;
    private final RoundedButton resetButton;
    private final Timer displayTimer;

    private final JButton enterTask;
    private final RoundedButton saveTimeButton;
    private final JButton homePage;

    private final ViewManagerModel viewManagerModel;

    // Add color constants
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 251);
    private static final Color BUTTON_COLOR = new Color(79, 70, 229);
    private static final Color HOVER_COLOR = new Color(67, 56, 202);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color TIMER_TEXT_COLOR = new Color(30, 41, 59);

    /**
     * Creates a new LocalTimerView with basic timer controls.
     *
     * @param timerViewModel   the view model that manages the timer's state
     * @param timerController  the controller that handles timer operations
     */
    public LocalTimerView(LocalTimerViewModel timerViewModel, LocalTimerController timerController,
                          ViewManagerModel viewManagerModel) {
        this.timerViewModel = timerViewModel;
        this.timerController = timerController;
        this.viewManagerModel = viewManagerModel;
        this.viewName = timerViewModel.getViewName();
        
        timerViewModel.addPropertyChangeListener(this);
        
        // Update panel properties
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setLayout(new BorderLayout(20, 20));

        // Update title styling
        final JLabel title = new JLabel(LocalTimerViewModel.TITLE_LABEL);
        title.setFont(new Font("Inter", Font.BOLD, 32));
        title.setForeground(TIMER_TEXT_COLOR);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(0, 0, 30, 0));

        displayTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerController.execute("update");
                final LocalTimerState state = timerViewModel.getState();
                if (state.getTimerState().equals(LocalTimerViewModel.TIMER_RUNNING)) {
                    updateView(state);
                }
            }
        });
        displayTimer.start();

        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font("Inter", Font.BOLD, 48));
        timeLabel.setForeground(TIMER_TEXT_COLOR);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create styled button panels
        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        controlPanel.setBackground(BACKGROUND_COLOR);
        
        // Update existing buttons to RoundedButton
        startButton = createStyledButton(LocalTimerViewModel.START_BUTTON_LABEL, "▶");
        pauseButton = createStyledButton(LocalTimerViewModel.PAUSE_BUTTON_LABEL, "⏸");
        resumeButton = createStyledButton(LocalTimerViewModel.RESUME_BUTTON_LABEL, "⏵");
        stopButton = createStyledButton(LocalTimerViewModel.STOP_BUTTON_LABEL, "⏹");
        resetButton = createStyledButton(LocalTimerViewModel.RESET_BUTTON_LABEL, "↺");
        saveTimeButton = createStyledButton(LocalTimerViewModel.SAVE_BUTTON_LABEL, "💾");

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        controlPanel.add(stopButton);
        controlPanel.add(resetButton);
        controlPanel.add(saveTimeButton);

        add(Box.createVerticalStrut(10));
        add(timeLabel);
        add(Box.createVerticalStrut(10));
        add(controlPanel);

        updateButtonStates(LocalTimerViewModel.TIMER_STOPPED);

        final JPanel buttons = new JPanel();
        enterTask = new JButton("Enter Task");
        buttons.add(enterTask);


        homePage = new JButton("Home Page");
        buttons.add(homePage);

        // Create a main panel to hold all components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Add title
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        
        // Add timer label
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(timeLabel);
        
        // Add control panel
        controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(controlPanel);
        
        // Add navigation buttons panel
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.setBackground(BACKGROUND_COLOR);
        mainPanel.add(buttons);

        // Add the main panel to the view
        add(mainPanel, BorderLayout.CENTER);

        enterTask.addActionListener(evt -> {
            if (evt.getSource().equals(enterTask)) {
                viewManagerModel.setState("Enter Task");
                viewManagerModel.firePropertyChanged();
            }
        });

        homePage.addActionListener(evt -> {
            if (evt.getSource().equals(homePage)) {
                viewManagerModel.setState("Home View");
                viewManagerModel.firePropertyChanged();
            }
        });
    }

    private RoundedButton createStyledButton(String text, String emoji) {
        RoundedButton button = new RoundedButton(
            String.format("<html><center>%s<br>%s</center></html>", emoji, text)
        );
        button.setFont(new Font("Inter", Font.BOLD, 14));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.addActionListener(this);
        return button;
    }

    // Add RoundedButton inner class
    private static class RoundedButton extends JButton {
        // Update color constants
        private static final Color DISABLED_COLOR = new Color(226, 232, 240);  // Lighter gray
        private static final Color ENABLED_COLOR = new Color(219, 234, 254);   // Light blue
        private static final Color HOVER_COLOR = new Color(191, 219, 254);     // Slightly darker blue
        private static final Color ENABLED_TEXT = new Color(30, 64, 175);      // Dark blue text
        private static final Color DISABLED_TEXT = new Color(148, 163, 184);   // Gray text

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                              RenderingHints.VALUE_ANTIALIAS_ON);

            if (!isEnabled()) {
                g2.setColor(DISABLED_COLOR);
                setForeground(DISABLED_TEXT);
            } else if (getModel().isPressed()) {
                g2.setColor(HOVER_COLOR);
                setForeground(ENABLED_TEXT);
            } else if (getModel().isRollover()) {
                g2.setColor(HOVER_COLOR);
                setForeground(ENABLED_TEXT);
            } else {
                g2.setColor(ENABLED_COLOR);
                setForeground(ENABLED_TEXT);
            }

            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15));
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border
        }

        @Override
        public boolean contains(int x, int y) {
            return new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15)
                    .contains(x, y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JButton) {
            JButton button = (JButton) evt.getSource();
            String buttonText = button.getText()
                .replaceAll("<[^>]*>", "") // Remove HTML tags
                .replaceAll("[^a-zA-Z ]", "") // Remove non-letter characters
                .trim()
                .toLowerCase();
            
            try {
                System.out.println("Button pressed: " + buttonText); // Debug line
                if (buttonText.equals("save session")) {
                    timerController.saveTimerSession(); // Use the specific save method
                } else {
                    timerController.execute(buttonText);
                }
                handleTimerDisplay(buttonText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleTimerDisplay(String operation) {
        if ("start".equals(operation) || "resume".equals(operation)) {
            displayTimer.start();
        }
        else if ("stop".equals(operation) || "pause".equals(operation)) {
            displayTimer.stop();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("View: Property change event received"); // Debug line
        if (evt.getPropertyName().equals("state")) {
            LocalTimerState state = (LocalTimerState) evt.getNewValue();
            String timerState = state.getTimerState();
            System.out.println("View: New timer state is: " + timerState); // Debug line
            
            if (timerState.equals(LocalTimerViewModel.TIMER_SAVED)) {
                long elapsedTime = state.getTotalTime();
                double minutes = elapsedTime / (60.0 * 1_000_000_000.0);  // Convert nanoseconds to minutes
                JOptionPane.showMessageDialog(
                    this,
                    String.format("Your focus time (%.1f minutes) has been saved.", minutes),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                state.setTimerState(LocalTimerViewModel.TIMER_STOPPED);
            } else if (timerState.startsWith("Error:")) {
                JOptionPane.showMessageDialog(
                    this,
                    timerState.substring(7),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                state.setTimerState(LocalTimerViewModel.TIMER_STOPPED);
            }
            
            updateView(state);
        }
    }

    private void updateView(LocalTimerState state) {
        timeLabel.setText(formatTime(state.getTotalTime()));
        updateButtonStates(state.getTimerState());
    }

    private void updateButtonStates(String timerState) {
        switch (timerState) {
            case LocalTimerViewModel.TIMER_STOPPED:
                setStoppedButtonStates();
                break;
            case LocalTimerViewModel.TIMER_RUNNING:
                setRunningButtonStates();
                break;
            case LocalTimerViewModel.TIMER_PAUSED:
                setPausedButtonStates();
                break;
            default:
                break;
        }
    }

    private void setStoppedButtonStates() {
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(false);
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
        saveTimeButton.setEnabled(false);
    }

    private void setRunningButtonStates() {
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        resumeButton.setEnabled(false);
        stopButton.setEnabled(true);
        resetButton.setEnabled(true);
        saveTimeButton.setEnabled(false);
    }

    private void setPausedButtonStates() {
        startButton.setEnabled(false);
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(true);
        stopButton.setEnabled(true);
        resetButton.setEnabled(true);
        saveTimeButton.setEnabled(true);
    }

    private String formatTime(long nanoSeconds) {
        final long hours = TimeUnit.NANOSECONDS.toHours(nanoSeconds);
        final long minutes = TimeUnit.NANOSECONDS.toMinutes(nanoSeconds) % 60;
        final long seconds = TimeUnit.NANOSECONDS.toSeconds(nanoSeconds) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getViewName() {
        return viewName;
    }
}
