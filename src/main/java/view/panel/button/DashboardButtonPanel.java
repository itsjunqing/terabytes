package view.panel.button;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public abstract class DashboardButtonPanel extends JPanel {

    protected JPanel mainList;
    protected JPanel panel;
    protected JButton button1;
    protected JButton button2;



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


