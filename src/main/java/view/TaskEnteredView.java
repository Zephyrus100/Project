package view;

import javax.swing.*;

public class TaskEnteredView extends JPanel {

    public TaskEnteredView() {
        // Display a simple message after the task has been added
        this.add(new JLabel("Task was successfully added!"));
    }
}
