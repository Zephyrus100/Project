package view;

import use_case.enter_task.EnterTaskOutputBoundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterTaskView extends JPanel {
    private EnterTaskOutputBoundary taskPresenter;
    private MainView mainView;  // Reference to MainView

    public EnterTaskView(EnterTaskOutputBoundary taskPresenter, MainView mainView) {
        this.taskPresenter = taskPresenter;
        this.mainView = mainView;  // Store the reference

        // Other initialization code, such as button listeners
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // After adding the task, check if it was successful and switch the view
                if (taskPresenter.taskAdded()) {
                    mainView.chooseView();  // Call chooseView() on the MainView directly
                }
            }
        });

        this.add(addButton);  // Add the button to the view
    }
}
