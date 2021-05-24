package view.template;

import observer.Observer;

import javax.swing.*;
import java.awt.*;

public abstract class viewTemplate implements Observer{
    public JFrame frame;
    public JPanel mainPanel;

    public viewTemplate() {
    }

    public void makeMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
    };
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

//    abstract void updateContent();
//    abstract void refreshContent();
//    abstract void refreshButtons();
//    abstract void updateButtons();
//    abstract void updateView();

    abstract public void update();
}
