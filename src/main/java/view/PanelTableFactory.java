package view;

import stream.Contract;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

public class PanelTableFactory {
    private JPanel contractPanel;
    private String id;

    public JPanel getPanel() {
        return contractPanel;
    }

    private void makePanel(List<Contract> contractList) {

        for (Contract h: contractList) {
            System.out.println(h);
        }
    }



    private JTable getPanel(Contract contractObject) {
        JTable contractTable = new JTable();
        contractTable.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        String[][] rec = {
                {"Tutor Name", contractObject.getSecondParty().getGivenName()},
                {"Subject", contractObject.getSubject().getName()},
                {"Number Of Sessions (per week)", "Kane"},
                {"Day & Time", "David"},
                {"Duration (hours)", ""},
                {"Rate (per hour)", ""},
        };
        String[] col = {"Contract End Date", "31 Apr 2021"};
        JTable table = new JTable(rec, col);
        contractTable.add(table);
        contractTable.add(new JButton("Select Offer"));
        return contractTable;
    }
}