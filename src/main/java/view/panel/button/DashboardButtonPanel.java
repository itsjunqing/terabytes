package view.panel.button;

import javax.swing.*;
import java.awt.*;

public abstract class DashboardButtonPanel extends JPanel {

    protected JPanel mainList;
    private JPanel panel;


    public DashboardButtonPanel(int contractSize) {
        setLayout(new BorderLayout());
        mainList = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.weightx = 100;
//        gbc.weighty = 100;
//        mainList.add(new JPanel(), gbc);
    }
    protected abstract void addButtons(int contractSize);

}


