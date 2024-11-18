package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The View for creating a new task.
 */
public class NewTaskView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "new task";

    private final NewTaskViewModel newTaskViewModel;
    private final JTextField taskNameField = new JTextField();
    private final JTextField taskDescriptionField = new JTextField();
    private final JTextField taskTimeField = new JTextField();
    private final JCheckBox taskStatusField = new JCheckBox();
    private NewTaskController newTaskController;

    private final JButton addTask;
    private final JButton cancel;

    public NewTaskView(NewTaskViewModel newTaskViewModel) {
        this.newTaskViewModel = newTaskViewModel;
        newTaskViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(newTaskViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel taskNameInfo = new LabelTextPanel(
                new JLabel(NewTaskViewModel.TASK_NAME_LABEL), taskNameField);
        final LabelTextPanel taskDescriptionInfo = new LabelTextPanel(
                new JLabel(NewTaskViewModel.TASK_DESCRIPTION_LABEL), taskDescriptionField);
        final LabelTextPanel taskTimeInfo = new LabelTextPanel(
                new JLabel(NewTaskViewModel.TASK_TIME_LABEL), taskTimeField);
        final LabelTextPanel taskStatusInfo = new LabelTextPanel(
                new JLabel(NewTaskViewModel.TASK_STATUS_LABEL), taskStatusField);

        final JPanel buttons = new JPanel();
        addTask = new JButton(NewTaskViewModel.ADD_TASK_BUTTON_LABEL);
        buttons.add(addTask);
        cancel = new JButton(NewTaskViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        addTask.addActionListener(this);
        cancel.addActionListener(this);

        addAddTaskListener();
        addCancelListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(taskNameInfo);
        this.add(taskDescriptionInfo);
        this.add(taskTimeInfo);
        this.add(taskStatusInfo);
        this.add(buttons);
    }

    private void addAddTaskListener() {

    }
    private void addCancelListener() {

    }

    public String getViewName() {
        return viewName;
    }

    public void setAddTaskController(AddTaskController controller) {
        this.addTaskController = controller
    }

}
