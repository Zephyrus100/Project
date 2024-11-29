package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;

/**
 * The View for the Home Use Case.
 */
public class HomeView extends JPanel implements ActionListener {
    private final String viewName = "Home View";

    private final HomeViewModel homeViewModel;
    private HomeController homeController;

    private final JButton toNewTask;
    private final JButton toCurrTasks;

    private final JButton goToTimer;
    private final JButton toReport;

    private final ViewManagerModel viewManagerModel;

    public HomeView(HomeViewModel homeViewModel, ViewManagerModel viewManagerModel) {
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;

        final JLabel title = new JLabel(HomeViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        toNewTask = new JButton(HomeViewModel.TO_NEW_TASK_BUTTON_LABEL);
        buttons.add(toNewTask);
        toCurrTasks = new JButton(HomeViewModel.TO_CURR_TASKS_LABEL);
        buttons.add(toCurrTasks);
        goToTimer = new JButton("Go To Timer");
        buttons.add(goToTimer);
        toReport = new JButton("Report");
        buttons.add(toReport);
        // TODO: add the report listener later.

//        toCurrTasks.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        homeController.switchtoCurrTasksView();
//                    }
//                }
//        );

        toCurrTasks.addActionListener(evt -> {
            if (evt.getSource().equals(toCurrTasks)) {
                viewManagerModel.setState("Task Entered View");
                viewManagerModel.firePropertyChanged();
            }
        });


        toNewTask.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        homeController.switchtoNewTaskView();
                    }
                }
        );


        goToTimer.addActionListener(evt -> {
            if (evt.getSource().equals(goToTimer)) {
                viewManagerModel.setState("local timer");
                viewManagerModel.firePropertyChanged();
            }
        });

        toReport.addActionListener(evt -> {
            if (evt.getSource().equals(toReport)) {
                viewManagerModel.setState("report");
                viewManagerModel.firePropertyChanged();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(buttons);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController controller) {

        this.homeController = controller;
    }
}
