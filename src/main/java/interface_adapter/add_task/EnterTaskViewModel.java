package interface_adapter.add_task;

import interface_adapter.ViewModel;

/**
 * The view model for the EnterTask view.
 */
public class EnterTaskViewModel extends ViewModel<EnterTaskState> {
    public EnterTaskViewModel() {
        super("enter task");
        super.setState(new EnterTaskState());
    }
}
