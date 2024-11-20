package view;


import interface_adapter.add_task.EnterTaskController;
import interface_adapter.add_task.EnterTaskState;
import interface_adapter.add_task.EnterTaskViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EnterTaskView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Enter Task";
    private final EnterTaskViewModel enterTaskViewModel;

    private final JTextField taskNameInputField = new JTextField(15);

    private final JTextField taskDescriptionInputField = new JTextField(15);

    private final JTextField taskTimeInputField = new JTextField(15);

    private final JLabel taskErrorField = new JLabel();

    private EnterTaskController enterTaskController;

    public EnterTaskView(EnterTaskViewModel enterTaskViewModel) {
        this.enterTaskViewModel = enterTaskViewModel;
        this.setEnterTaskController(enterTaskController);
        enterTaskViewModel.addPropertyChangeListener(this);

        final LabelTextPanel taskNameInfo = new LabelTextPanel(
                new JLabel("Task Name"), taskNameInputField);

        final LabelTextPanel taskDescriptionInfo = new LabelTextPanel(
                new JLabel("Task Description"), taskDescriptionInputField);

        final LabelTextPanel taskTimeInfo = new LabelTextPanel(
                new JLabel("Task Time"), taskTimeInputField);

        final JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(
                evt -> {
                    if (evt.getSource() == submitButton) {
                        if (enterTaskController != null) {
                            final EnterTaskState currentState = enterTaskViewModel.getState();
                            enterTaskController.addTask(currentState.getTaskName(), currentState.getTaskDescription()
                                    , currentState.getTaskTime());
                        }
                        else {
                            System.out.println("EnterTaskController is null");
                        }
                    }
                }
        );


        taskNameInputField.getDocument(

        ).addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final EnterTaskState currentState = enterTaskViewModel.getState();
                currentState.setTaskName(taskNameInputField.getText());
                enterTaskViewModel.setState(currentState);

            }

            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();}

            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        taskDescriptionInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final EnterTaskState currentState = enterTaskViewModel.getState();
                currentState.setTaskDescription(taskDescriptionInputField.getText());
                enterTaskViewModel.setState(currentState);
            }

            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();}

            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

    taskTimeInputField.getDocument().addDocumentListener(new DocumentListener() {

        private void documentListenerHelper() {
            final EnterTaskState currentState = enterTaskViewModel.getState();
            currentState.setTaskTime(Float.parseFloat(taskTimeInputField.getText()));
            enterTaskViewModel.setState(currentState);
        }

        public void insertUpdate(DocumentEvent e) {
            documentListenerHelper();}

        public void removeUpdate(DocumentEvent e) {
            documentListenerHelper();
        }
        public void changedUpdate(DocumentEvent e) {
            documentListenerHelper();
        }
    });

    this.add(taskNameInfo);
    this.add(taskDescriptionInfo);
    this.add(taskTimeInfo);
    this.add(taskErrorField);
    this.add(submitButton);
}

    public String getViewName() {
        return viewName;
    }

    public void setEnterTaskController(EnterTaskController enterTaskController) {
        this.enterTaskController = enterTaskController;
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final EnterTaskState state = (EnterTaskState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(EnterTaskState state) {
        taskNameInputField.setText(state.getTaskName());
        taskDescriptionInputField.setText(state.getTaskDescription());
        taskTimeInputField.setText(String.valueOf(state.getTaskTime()));

    }

}
