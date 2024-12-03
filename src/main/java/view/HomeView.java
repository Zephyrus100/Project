package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomeViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class HomeView extends JPanel implements ActionListener {
    private final String viewName = "Home View";
    private final HomeViewModel homeViewModel;
    private HomeController homeController;
    private final ViewManagerModel viewManagerModel;

    private final RoundedButton toNewTask;
    private final RoundedButton toCurrTasks;
    private final RoundedButton goToTimer;
    private final RoundedButton toReport;
    private final RoundedButton toQuote;

    private static final Color BACKGROUND_COLOR = new Color(245, 247, 251);
    private static final Color BUTTON_COLOR = new Color(79, 70, 229);
    private static final Color HOVER_COLOR = new Color(67, 56, 202);
    private static final Color TEXT_COLOR = Color.WHITE;

    public HomeView(HomeViewModel homeViewModel, ViewManagerModel viewManagerModel) {
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;

        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setLayout(new BorderLayout(20, 20));

        // Title setup
        JLabel title = new JLabel(HomeViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(new Font("Inter", Font.BOLD, 32));
        title.setForeground(new Color(30, 41, 59));
        title.setBorder(new EmptyBorder(0, 0, 30, 0));

        // Button panel setup with 3x2 grid
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        // Initialize all buttons with icons
        toNewTask = createStyledButton("New Task", "➕");
        toCurrTasks = createStyledButton("Current Tasks", "📋");
        goToTimer = createStyledButton("Timer", "⏲️");
        toReport = createStyledButton("Report", "📊");
        toQuote = createStyledButton("Daily Quote", "💭");

        // Add all buttons to panel
        buttonPanel.add(toNewTask);
        buttonPanel.add(toCurrTasks);
        buttonPanel.add(goToTimer);
        buttonPanel.add(toReport);
        buttonPanel.add(toQuote);

        // Add components to main panel
        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setupActionListeners();
    }

    private RoundedButton createStyledButton(String text, String emoji) {
        RoundedButton button = new RoundedButton(String.format("<html><center>%s<br>%s</center></html>", emoji, text));
        button.setFont(new Font("Inter", Font.BOLD, 16));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 120));
        return button;
    }

    private void setupActionListeners() {
        toNewTask.addActionListener(evt -> homeController.switchtoNewTaskView());

        toCurrTasks.addActionListener(evt -> {
            viewManagerModel.setState("Task Entered View");
            viewManagerModel.firePropertyChanged();
        });

        goToTimer.addActionListener(evt -> {
            viewManagerModel.setState("local timer");
            viewManagerModel.firePropertyChanged();
        });

        toReport.addActionListener(evt -> {
            viewManagerModel.setState("report");
            viewManagerModel.firePropertyChanged();
        });

        toQuote.addActionListener(evt -> {
            viewManagerModel.setState("quote");
            viewManagerModel.firePropertyChanged();
        });
    }

    private static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(HOVER_COLOR);
            } else if (getModel().isRollover()) {
                g2.setColor(HOVER_COLOR);
            } else {
                g2.setColor(getBackground());
            }

            RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            g2.fill(r2d);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border painting
        }

        @Override
        public boolean contains(int x, int y) {
            return new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15)
                    .contains(x, y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Action not implemented yet.");
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }
}