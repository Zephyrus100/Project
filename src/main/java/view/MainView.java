package view;

import use_case.enter_task.EnterTaskOutputBoundary;
import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {

    private EnterTaskView enterTaskView;
    private EnterTaskOutputBoundary enterTaskPresenter;
    private CardLayout cardLayout;
    private TaskEnteredView taskEnteredView;

    public MainView(EnterTaskView enterTaskView, TaskEnteredView taskEnteredView,
                    CardLayout cardLayout, EnterTaskOutputBoundary presenter) {
        this.enterTaskView = enterTaskView;
        this.enterTaskPresenter = presenter;
        this.cardLayout = cardLayout;
        this.taskEnteredView = taskEnteredView;
        this.setLayout(cardLayout);

        add(enterTaskView, "EnterTaskView");
        add(taskEnteredView, "TaskEnteredView");
    }

    public void chooseView() {
        if (enterTaskPresenter.taskAdded()) {
            cardLayout.show(this, "TaskEnteredView");
        }
        else {
            cardLayout.show(this, "EnterTaskView");
        }
    }
}
