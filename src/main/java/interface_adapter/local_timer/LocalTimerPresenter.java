package interface_adapter.local_timer;

import use_case.local_timer.LocalTimerOutputBoundary;
import use_case.local_timer.LocalTimerOutputData;

/**
 * The Presenter for the Local Timer Use Case.
 */
public class LocalTimerPresenter implements LocalTimerOutputBoundary {

    private final LocalTimerViewModel timerViewModel;

    public LocalTimerPresenter(LocalTimerViewModel timerViewModel) {
        this.timerViewModel = timerViewModel;
    }

    @Override
    public void prepareSuccessView(LocalTimerOutputData response) {
        LocalTimerState timerState = timerViewModel.getState();
        timerState.setTimerState(response.getTimerState());
        timerState.setTotalTime(response.getElapsedTime());

        timerViewModel.setState(timerState);
        timerViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LocalTimerState timerState = timerViewModel.getState();
        timerState.setTimerState("Error: " + error);
        
        timerViewModel.setState(timerState);
        timerViewModel.firePropertyChanged();
        
        // Don't reset to stopped state immediately
        // Let the view handle the error state first
    }

    // TODO: Switch to what window next?
}
