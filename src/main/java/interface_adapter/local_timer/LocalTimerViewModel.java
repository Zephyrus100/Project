package interface_adapter.local_timer;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Local Timer View.
 */
public class LocalTimerViewModel extends ViewModel<LocalTimerState> {

    // Timer View Labels
    public static final String TITLE_LABEL = "Timer";
    public static final String ELAPSED_TIME_LABEL = "Current Session: ";
    public static final String TOTAL_TIME_LABEL = "Total Time: ";
    public static final String SESSION_COUNT_LABEL = "Sessions: ";

    // Timer States
    public static final String TIMER_STOPPED = "Stopped";
    public static final String TIMER_RUNNING = "Running";
    public static final String TIMER_PAUSED = "Paused";
    public static final String TIMER_SAVED = "Saved";
    public static final String TIMER_CANNOT_SAVE_RUNNING = "Cannot Save - Running";
    public static final String TIMER_CANNOT_SAVE_NO_TIME = "Cannot Save - No Time";

    // Timer Control Buttons
    public static final String START_BUTTON_LABEL = "Start";
    public static final String PAUSE_BUTTON_LABEL = "Pause";
    public static final String RESUME_BUTTON_LABEL = "Resume";
    public static final String STOP_BUTTON_LABEL = "Stop";
    public static final String RESET_BUTTON_LABEL = "Reset";

    // Add save button constant
    public static final String SAVE_BUTTON_LABEL = "Save Session";

    public LocalTimerViewModel() {
        super("local timer");
        setState(new LocalTimerState());
    }
}
