package view;

import interface_adapter.local_timer.LocalTimerController;
import interface_adapter.local_timer.LocalTimerState;
import interface_adapter.local_timer.LocalTimerViewModel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.TimeUnit;

/**
 * The View for the timer functionality of the program.
 * Simplified version with just the essential timer controls.
 */
public class LocalTimerView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName;
    private final LocalTimerViewModel timerViewModel;
    private final LocalTimerController timerController;

    private final JLabel timeLabel;
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton resumeButton;
    private final JButton stopButton;
    private final JButton resetButton;
    private final Timer displayTimer;

    /**
     * Creates a new LocalTimerView with basic timer controls.
     *
     * @param timerViewModel   the view model that manages the timer's state
     * @param timerController  the controller that handles timer operations
     */
    public LocalTimerView(LocalTimerViewModel timerViewModel, LocalTimerController timerController) {
        this.viewName = "local timer";
        this.timerViewModel = timerViewModel;
        this.timerController = timerController;
        this.timerViewModel.addPropertyChangeListener(this);

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

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font(timeLabel.getFont().getName(), Font.BOLD, 24));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttonsPanel = new JPanel();
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

        add(Box.createVerticalStrut(10));
        add(timeLabel);
        add(Box.createVerticalStrut(10));
        add(buttonsPanel);

        updateButtonStates(LocalTimerViewModel.TIMER_STOPPED);
    }

    private JButton createButton(String label) {
        final JButton button = new JButton(label);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JButton) {
            final JButton button = (JButton) evt.getSource();
            final String operation = button.getText().toLowerCase();
            timerController.execute(operation);

            handleTimerDisplay(operation);
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
        final LocalTimerState state = (LocalTimerState) evt.getNewValue();
        updateView(state);
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
    }

    private void setRunningButtonStates() {
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        resumeButton.setEnabled(false);
        stopButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    private void setPausedButtonStates() {
        startButton.setEnabled(false);
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(true);
        stopButton.setEnabled(true);
        resetButton.setEnabled(true);
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
