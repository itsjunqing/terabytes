package view.template;

import entity.BidInfo;
import lombok.Data;
import observer.Observer;
import stream.Bid;

import javax.swing.*;
import java.awt.*;
import java.util.List;
@Data
/**
 * Template class for a number of views. The basic template
 * of the view is a frame, containing a main panel.
 *
 * The main panel contants one content panel and one button
 * panel
 *
 * Basic methods used by the views are listed below
 */
public abstract class viewTemplate implements Observer{
    public JFrame frame;
    public JPanel mainPanel;
    public JPanel contentPanel;
    public JPanel buttonPanel;
    public JLabel errorLabel;

    public viewTemplate() {
    }

    /**
     * Method to create the main Panel
     */
    public void makeMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
    };

    /**
     * Method to create a frame and to have the mainPanel
     * contained inside the frame
     * @param frameTitle the title of the frame
     * @param closeAction the action to perform when the frame
     *                    is closed
     */
    public void makeFrame(String frameTitle, int closeAction){
        frame = new JFrame(frameTitle);
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(closeAction);
        frame.setMinimumSize(new Dimension(860, 400));
        frame.setMaximumSize(new Dimension(860, 1000));
        frame.setPreferredSize(new Dimension(860, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    };

    /**
     * Method to refresh the view when this
     * function is called by the subject,
     * that is the model
     */
    public void update() {
        refreshContent();
    }

    /**
     * method to create the button panel
     */
    abstract protected void createButtons();

    /**
     * Method to both create the contents of the content
     * panel and to update the content of the content panel
     */
    abstract protected void updateView();

    /**
     * Method to refresh the contents of the view
     * without unnecessarily re-creating ui components
     */
    abstract protected void refreshContent();

    /**
     * Method to refresh the contents of the button panel
     * without unnecessarily re-creating ui components
     */
    abstract protected void refreshButtons();



}
