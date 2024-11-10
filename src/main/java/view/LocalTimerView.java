package view;

import interface_adapter.local_timer.LocalTimerController;
import interface_adapter.local_timer.LocalTimerState;
import interface_adapter.local_timer.LocalTimerViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.TimeUnit;

/**
 * The View for the timer functionality of the program.
 * This view displays the current timer state, elapsed time, and control buttons.
 * It follows the Clean Architecture pattern by communicating with the timer
 * through a Controller and ViewModel.
 */
public class LocalTimerView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "local timer";
    private final LocalTimerViewModel timerViewModel;
    private final LocalTimerController timerController;

    // UI Components
    private final JLabel titleLabel;
    private final JLabel elapsedTimeLabel;
    private final JLabel totalTimeLabel;
    private final JLabel sessionCountLabel;
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton resumeButton;
    private final JButton stopButton;
    private final JButton resetButton;

    /**
     * Creates a new LocalTimerView.
     *
     * @param timerViewModel the view model that manages the timer's state
     * @param timerController the controller that handles timer operations
     */
    public LocalTimerView(LocalTimerViewModel timerViewModel, LocalTimerController timerController) {
        this.timerViewModel = timerViewModel;
        this.timerController = timerController;
        this.timerViewModel.addPropertyChangeListener(this);

        // Set up the main panel
        setLayout(new BorderLayout());

        // Create title panel
        JPanel titlePanel = new JPanel();
        titleLabel = new JLabel(LocalTimerViewModel.TITLE_LABEL);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Create info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        elapsedTimeLabel = new JLabel(LocalTimerViewModel.ELAPSED_TIME_LABEL + "00:00:00");
        totalTimeLabel = new JLabel(LocalTimerViewModel.TOTAL_TIME_LABEL + "00:00:00");
        sessionCountLabel = new JLabel(LocalTimerViewModel.SESSION_COUNT_LABEL + "0");
        infoPanel.add(elapsedTimeLabel);
        infoPanel.add(totalTimeLabel);
        infoPanel.add(sessionCountLabel);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        startButton = createButton(LocalTimerViewModel.START_BUTTON_LABEL);
        pauseButton = createButton(LocalTimerViewModel.PAUSE_BUTTON_LABEL);
        resumeButton = createButton(LocalTimerViewModel.RESUME_BUTTON_LABEL);
        stopButton = createButton(LocalTimerViewModel.STOP_BUTTON_LABEL);
        resetButton = createButton(LocalTimerViewModel.RESET_BUTTON_LABEL);

        buttonsPanel.add(startButton);
        buttonsPanel.add(pauseButton);
        buttonsPanel.add(resumeButton);
        buttonsPanel.add(stopButton);
        buttonsPanel.add(resetButton);

        // Add all panels to the main view
        add(titlePanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Initial button states
        updateButtonStates(LocalTimerViewModel.TIMER_STOPPED);
    }

    /**
     * Creates a button with the given label and adds this view as its ActionListener.
     *
     * @param label the text to display on the button
     * @return the created button
     */
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        return button;
    }

    /**
     * React to a button click that results in evt.
     *
     * @param evt the ActionEvent to react to
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JButton) {
            JButton button = (JButton) evt.getSource();
            String operation = button.getText().toLowerCase();
            timerController.execute(operation);
        }
    }

    /**
     * Handles property change events from the ViewModel.
     * Updates the view to reflect the new state.
     *
     * @param evt the PropertyChangeEvent describing the change
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LocalTimerState state = (LocalTimerState) evt.getNewValue();
        updateView(state);
    }

    /**
     * Updates the view components based on the current timer state.
     *
     * @param state the current state of the timer
     */
    private void updateView(LocalTimerState state) {
        elapsedTimeLabel.setText(LocalTimerViewModel.ELAPSED_TIME_LABEL +
                formatTime(state.getTotalTime()));
        sessionCountLabel.setText(LocalTimerViewModel.SESSION_COUNT_LABEL +
                state.getSessionCount());
        updateButtonStates(state.getTimerState());
    }

    /**
     * Updates the enabled/disabled state of buttons based on timer state.
     *
     * @param timerState the current state of the timer
     */
    private void updateButtonStates(String timerState) {
        switch (timerState) {
            case LocalTimerViewModel.TIMER_STOPPED:
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(false);
                stopButton.setEnabled(false);
                resetButton.setEnabled(false);
                break;
            case LocalTimerViewModel.TIMER_RUNNING:
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);
                resumeButton.setEnabled(false);
                stopButton.setEnabled(true);
                resetButton.setEnabled(true);
                break;
            case LocalTimerViewModel.TIMER_PAUSED:
                startButton.setEnabled(false);
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(true);
                stopButton.setEnabled(true);
                resetButton.setEnabled(true);
                break;
        }
    }

    /**
     * Formats nanoseconds into a human-readable time string (HH:MM:SS).
     *
     * @param nanoSeconds the time in nanoseconds to format
     * @return formatted time string
     */
    private String formatTime(long nanoSeconds) {
        long hours = TimeUnit.NANOSECONDS.toHours(nanoSeconds);
        long minutes = TimeUnit.NANOSECONDS.toMinutes(nanoSeconds) % 60;
        long seconds = TimeUnit.NANOSECONDS.toSeconds(nanoSeconds) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Gets the name of this view.
     *
     * @return the view name
     */
    public String getViewName() {
        return viewName;
    }
}