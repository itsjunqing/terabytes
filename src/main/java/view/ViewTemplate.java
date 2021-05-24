package view;

import lombok.Data;
import observer.Observer;

import javax.swing.*;
import java.awt.*;


/**
 * A Class of ViewTemplate that contains the basic content and button panel
 * used throughout for all views.
 */
@Data
public abstract class ViewTemplate implements Observer {

    protected JFrame frame;
    protected JPanel mainPanel;
    protected JPanel contentPanel;
    protected JPanel buttonPanel;
    protected JLabel errorLabel;

    /**
     * Initializes the ViewTemplate
     * @param frameTitle the title of the frame
     * @param closeAction the action to be performed upon closing
     */
    protected void initViewTemplate(String frameTitle, int closeAction) {
        makeMainPanel();
        updateView();
        createButtons();
        makeFrame(frameTitle, closeAction);
    }

    /**
     * Creates a main panel
     */
    private void makeMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
    }

    /**
     * Creates a frame to be contained in the mainPanel
     * @param frameTitle the title of the frame
     * @param closeAction the action to be performed upon closing
     */
    private void makeFrame(String frameTitle, int closeAction) {
        frame = new JFrame(frameTitle);
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(closeAction);
        frame.setMinimumSize(new Dimension(860, 400));
        frame.setMaximumSize(new Dimension(860, 1000));
        frame.setPreferredSize(new Dimension(860, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Perform a refresh on the view
     */
    public void update() {
        refreshContent();
    }

    /**
     * Disposes the frame
     */
    public void dispose() {
        frame.dispose();
    }

    /**
     * Refreshes the content of the view
     */
    private void refreshContent() {
        updateView();
        refreshButtons();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    /**
     * Updates the content of the content panel
     */
    abstract protected void updateView();

    /**
     * Creates the button panel
     */
    abstract protected void createButtons();

    /**
     * Refreshes the content of the buttonPanel without unnecessarily re-creating UI components
     */
    abstract protected void refreshButtons();

}
