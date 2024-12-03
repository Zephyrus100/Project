package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_quote.QuoteController;
import interface_adapter.get_quote.QuoteState;
import interface_adapter.get_quote.QuoteViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class QuoteView extends JPanel implements PropertyChangeListener {
    public final String viewName = "quote";
    private final QuoteViewModel quoteViewModel;
    private QuoteController quoteController;
    private final ViewManagerModel viewManagerModel;

    // UI Components
    private final JLabel titleLabel;
    private final JTextArea quoteTextArea;
    private final JLabel authorLabel;
    private final RoundedButton getQuoteButton;
    private final RoundedButton backButton;
    private final JPanel mainPanel;
    private final JPanel quotePanel;

    // Colors
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 251);
    private static final Color BUTTON_COLOR = new Color(79, 70, 229);
    private static final Color HOVER_COLOR = new Color(67, 56, 202);
    private static final Color TEXT_COLOR = new Color(30, 41, 59);
    private static final Color QUOTE_BACKGROUND = new Color(255, 255, 255);

    public QuoteView(QuoteViewModel quoteViewModel, QuoteController quoteController, ViewManagerModel viewManagerModel) {
        this.quoteViewModel = quoteViewModel;
        this.quoteController = quoteController;
        this.viewManagerModel = viewManagerModel;
        this.quoteViewModel.addPropertyChangeListener(this);

        titleLabel = new JLabel("Daily Inspiration");
        quoteTextArea = new JTextArea(3, 20);
        authorLabel = new JLabel("");
        getQuoteButton = new RoundedButton("Get Today's Quote 💭");
        backButton = new RoundedButton("← Back");
        mainPanel = new JPanel();
        quotePanel = new JPanel();

        setupPanels();

        styleComponents();

        layoutComponents();

        setupActionListeners();
    }

    private void setupPanels() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        quotePanel.setLayout(new BoxLayout(quotePanel, BoxLayout.Y_AXIS));
        quotePanel.setBackground(QUOTE_BACKGROUND);
        quotePanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(15),
                new EmptyBorder(20, 30, 20, 30)
        ));
    }

    private void styleComponents() {
        // Title styling
        titleLabel.setFont(new Font("Inter", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Quote text area styling
        quoteTextArea.setFont(new Font("Inter", Font.ITALIC, 16));
        quoteTextArea.setForeground(TEXT_COLOR);
        quoteTextArea.setWrapStyleWord(true);
        quoteTextArea.setLineWrap(true);
        quoteTextArea.setOpaque(false);
        quoteTextArea.setEditable(false);
        quoteTextArea.setFocusable(false);
        quoteTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Author styling
        authorLabel.setFont(new Font("Inter", Font.BOLD, 14));
        authorLabel.setForeground(TEXT_COLOR);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button styling
        getQuoteButton.setFont(new Font("Inter", Font.BOLD, 14));
        getQuoteButton.setBackground(BUTTON_COLOR);
        getQuoteButton.setForeground(Color.WHITE);
        getQuoteButton.setMaximumSize(new Dimension(200, 35));

        backButton.setFont(new Font("Inter", Font.BOLD, 12));
        backButton.setBackground(BUTTON_COLOR);
        backButton.setForeground(Color.WHITE);
        backButton.setMaximumSize(new Dimension(80, 30));
    }

    private void layoutComponents() {
        quotePanel.add(quoteTextArea);
        quotePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        quotePanel.add(authorLabel);

        JLabel attributionLabel = new JLabel("<html>Inspirational quotes provided by " +
                "<a href='https://zenquotes.io/'>ZenQuotes API</a></html>");
        attributionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attributionLabel.setFont(new Font("Inter", Font.PLAIN, 11));

        mainPanel.add(backButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(quotePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(getQuoteButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(attributionLabel);

        add(mainPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            QuoteState state = quoteViewModel.getState();
            if (state != null) {
                quoteTextArea.setText("\"" + state.getContent() + "\"");
                authorLabel.setText("— " + state.getAuthor());
                getQuoteButton.setEnabled(true);
                revalidate();
                repaint();
            }
        }
    }

    private void setupActionListeners() {
        getQuoteButton.addActionListener(e -> {
            getQuoteButton.setEnabled(false);
            quoteController.execute();
        });

        backButton.addActionListener(e -> {
            viewManagerModel.setState("Home View");
            viewManagerModel.firePropertyChanged();
        });
    }

    private static class RoundedBorder extends EmptyBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            super(0, 0, 0, 0);
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(QUOTE_BACKGROUND);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }
    }

    private static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
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

            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public String getViewName() {
        return this.viewName;
    }

    public void setQuoteController(QuoteController quoteController) {
        this.quoteController = quoteController;
    }
}
