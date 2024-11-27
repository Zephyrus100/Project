package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomeViewModel;

/**
 * The Homeview that the user first sees.
 */
public class HomeView extends JPanel {
    private final String viewName = "HomeView";

    private final HomeViewModel homeViewModel;
    private HomeController homeController;
    private final ViewManagerModel viewManagerModel;

    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(
                        0, 0, getWidth()-1, getHeight()-1, 20, 20);

                g2.setColor(bgColor);
                g2.fill(roundedRectangle);

                g2.setColor(bgColor.darker());
                g2.draw(roundedRectangle);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(textColor);
        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    public HomeView(HomeViewModel homeViewModel, ViewManagerModel viewManagerModel) {
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 255));

        JPanel containerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(200, 200, 220, 100));
                g2.fillRoundRect(5, 5, getWidth()-10, getHeight()-10, 25, 25);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth()-10, getHeight()-10, 25, 25);

                super.paintComponent(g);
            }
        };
        containerPanel.setOpaque(false);
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(HomeViewModel.TITLE_LABEL);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(30, 30, 70));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JButton toNewTask = createStyledButton(
                HomeViewModel.TO_NEW_TASK_BUTTON_LABEL,
                new Color(173, 216, 230),
                new Color(20, 20, 70)
        );
        JButton toCurrTasks = createStyledButton(
                HomeViewModel.TO_CURR_TASKS_LABEL,
                new Color(152, 251, 152),
                new Color(20, 70, 20)
        );
        JButton goToTimer = createStyledButton(
                "Go To Timer",
                new Color(255, 160, 122),
                new Color(70, 20, 20)
        );

        toNewTask.addActionListener(evt -> homeController.switchtoNewTaskView());
        toCurrTasks.addActionListener(evt -> homeController.switchtoCurrTasksView());
        goToTimer.addActionListener(evt -> {
            viewManagerModel.setState("local timer");
            viewManagerModel.firePropertyChanged();
        });

        Box buttonBox = Box.createVerticalBox();
        buttonBox.add(Box.createVerticalStrut(10));
        buttonBox.add(toNewTask);
        buttonBox.add(Box.createVerticalStrut(15));
        buttonBox.add(toCurrTasks);
        buttonBox.add(Box.createVerticalStrut(15));
        buttonBox.add(goToTimer);
        buttonBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        containerPanel.add(title);
        containerPanel.add(buttonBox);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 50);
        add(containerPanel, gbc);
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }
}