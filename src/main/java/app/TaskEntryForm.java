package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import use_case.enter_task.EnterTaskDataInterface;
import use_case.enter_task.EnterTaskOutputData;
import interface_adapter.add_task.EnterTaskPresenter;
import interface_adapter.ViewManagerModel;
import use_case.enter_task.InMemoryTaskData;
import view.TaskEnteredView;
import interface_adapter.add_task.EnterTaskState;
import interface_adapter.add_task.EnterTaskViewModel;



public class TaskEntryForm extends JFrame {

    private final JTextField taskNameField;
    private final JTextField taskDescriptionField;
    private final JTextField taskTimeField;
    private final JTextField taskStatusField;
    private final EnterTaskPresenter presenter;
    private final EnterTaskDataInterface taskDataInterface;

    public TaskEntryForm(EnterTaskPresenter presenter, EnterTaskDataInterface taskDataInterface) {
        this.presenter = presenter;
        this.taskDataInterface = taskDataInterface;

        setTitle("Task Entry");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Task Name:"));
        taskNameField = new JTextField();
        add(taskNameField);

        add(new JLabel("Task Description:"));
        taskDescriptionField = new JTextField();
        add(taskDescriptionField);

        add(new JLabel("Task Time (hours):"));
        taskTimeField = new JTextField();
        add(taskTimeField);

        add(new JLabel("Task Status:"));
        taskStatusField = new JTextField();
        add(taskStatusField);

        JButton submitButton = new JButton("Add Task");
        add(submitButton);
        submitButton.addActionListener(new SubmitButtonListener());

        setVisible(true);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String taskName = taskNameField.getText();
            String taskDescription = taskDescriptionField.getText();
            double taskTime;
            try {
                taskTime = Double.parseDouble(taskTimeField.getText());
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TaskEntryForm.this, "Invalid time entered.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String taskStatus = taskStatusField.getText();

            taskDataInterface.addTask(taskName, taskDescription, taskTime, taskStatus);

            EnterTaskOutputData taskData = new EnterTaskOutputData(taskName, taskDescription, taskTime, taskStatus);
            boolean success = presenter.prepareTaskAddedView(taskData);

            if (success) {
                JOptionPane.showMessageDialog(TaskEntryForm.this, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                presenter.prepareTaskNotAddedView();
                JOptionPane.showMessageDialog(TaskEntryForm.this, "Failed to add task.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        EnterTaskDataInterface taskDataInterface = new InMemoryTaskData();
        EnterTaskState enterTaskState = new EnterTaskState();
        EnterTaskViewModel enterTaskViewModel = new EnterTaskViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        TaskEnteredView taskEnteredView = new TaskEnteredView();

        EnterTaskPresenter presenter = new EnterTaskPresenter(
                enterTaskState, enterTaskViewModel, viewManagerModel, taskEnteredView);

        new TaskEntryForm(presenter, taskDataInterface);
    }
}
