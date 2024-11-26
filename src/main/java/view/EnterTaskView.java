package view;


import interface_adapter.ViewManagerModel;
import interface_adapter.add_task.EnterTaskController;
import interface_adapter.add_task.EnterTaskState;
import interface_adapter.add_task.EnterTaskViewModel;
// import interface_adapter.login.LoginController;
// import interface_adapter.login.LoginState;

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

    private final JTextField hoursInputField = new JTextField(5);
    private final JTextField minutesInputField = new JTextField(5);

    private final JLabel taskErrorField = new JLabel();
    private final ViewManagerModel viewManagerModel;

    private EnterTaskController enterTaskController;

    private final JButton goToHomeView;

    public EnterTaskView(EnterTaskViewModel enterTaskViewModel, ViewManagerModel viewManagerModel) {
        this.enterTaskViewModel = enterTaskViewModel;
        this.viewManagerModel = viewManagerModel;
        this.setEnterTaskController(enterTaskController);
        enterTaskViewModel.addPropertyChangeListener(this);

        final LabelTextPanel taskNameInfo = new LabelTextPanel(
                new JLabel("Task Name"), taskNameInputField);

        final LabelTextPanel taskDescriptionInfo = new LabelTextPanel(
                new JLabel("Task Description"), taskDescriptionInputField);

//        final LabelTextPanel taskTimeInfo = new LabelTextPanel(
//                new JLabel("Hours: "), taskHourInputField);
//        final LabelTextPanel Task
        JPanel timeInputPanel = new JPanel();
        timeInputPanel.add(new JLabel("Hours: "));
        timeInputPanel.add(hoursInputField);
        timeInputPanel.add(new JLabel("Minutes: "));
        timeInputPanel.add(minutesInputField);

        final JPanel buttons = new JPanel();
        goToHomeView = new JButton("Home");
        buttons.add(goToHomeView);
        this.add(buttons);

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

        goToHomeView.addActionListener(evt -> {
            if (evt.getSource().equals(goToHomeView)) {
                viewManagerModel.setState("Home View");
                viewManagerModel.firePropertyChanged();
            }
        });

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

        hoursInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateTime() {
                try {
                    final EnterTaskState currentState = enterTaskViewModel.getState();
                    String hoursText = hoursInputField.getText().trim();
                    String minutesText = minutesInputField.getText().trim();

                    double hours = hoursText.isEmpty() ? 0 : Double.parseDouble(hoursText);
                    double minutes = minutesText.isEmpty() ? 0 : Double.parseDouble(minutesText);

                    double totalMinutes = (hours * 60) + minutes;

                    if (totalMinutes > 0) {
                        currentState.setTaskTime(totalMinutes);
                        enterTaskViewModel.setState(currentState);
                        taskErrorField.setText("");
                    } else {
                        taskErrorField.setText("Time must be positive");
                    }
                } catch (NumberFormatException e) {
                    taskErrorField.setText("Please enter valid numbers");
                }
            }

            public void insertUpdate(DocumentEvent e) { updateTime(); }
            public void removeUpdate(DocumentEvent e) { updateTime(); }
            public void changedUpdate(DocumentEvent e) { updateTime(); }
        });

        minutesInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateTime() {
                try {
                    final EnterTaskState currentState = enterTaskViewModel.getState();
                    String hoursText = hoursInputField.getText().trim();
                    String minutesText = minutesInputField.getText().trim();

                    double hours = hoursText.isEmpty() ? 0 : Double.parseDouble(hoursText);
                    double minutes = minutesText.isEmpty() ? 0 : Double.parseDouble(minutesText);

                    double totalMinutes = (hours * 60) + minutes;

                    if (totalMinutes > 0) {
                        currentState.setTaskTime(totalMinutes);
                        enterTaskViewModel.setState(currentState);
                        taskErrorField.setText("");
                    } else {
                        taskErrorField.setText("Time must be positive");
                    }
                } catch (NumberFormatException e) {
                    taskErrorField.setText("Please enter valid numbers");
                }
            }

            public void insertUpdate(DocumentEvent e) { updateTime(); }
            public void removeUpdate(DocumentEvent e) { updateTime(); }
            public void changedUpdate(DocumentEvent e) { updateTime(); }
        });

        this.add(taskNameInfo);
        this.add(taskDescriptionInfo);
        this.add(timeInputPanel);
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

        double totalMinutes = state.getTaskTime();
        int hours = (int) (totalMinutes / 60);
        int minutes = (int) (totalMinutes % 60);

        hoursInputField.setText(String.valueOf(hours));
        minutesInputField.setText(String.valueOf(minutes));
    }

}
