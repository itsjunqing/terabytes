package view.panel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public abstract class DashboardButtonPanel extends JPanel {

    private JPanel mainList;
    private JPanel panel;
    private JComboBox contractChoice;

    public DashboardButtonPanel(int contractSize) {
        setLayout(new BorderLayout());

        mainList = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.weightx = 100;
//        gbc.weighty = 100;
//        mainList.add(new JPanel(), gbc);

        panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();

//        gbc2.fill = GridBagConstraints.HORIZONTAL;
        panel.setLayout(layout);
    }
    private void addButtons(int contractSize) {}

}


