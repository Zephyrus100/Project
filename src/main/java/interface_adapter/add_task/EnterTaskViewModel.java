package interface_adapter.add_task;

import interface_adapter.ViewModel;

public class EnterTaskViewModel extends ViewModel<EnterTaskState> {

    public EnterTaskViewModel() {
        super("enter_task");
        super.setState(new EnterTaskState());
    }
}
