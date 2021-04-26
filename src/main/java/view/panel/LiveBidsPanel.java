package view.panel;

import stream.Contract;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class LiveBidsPanel extends JPanel {

    private JPanel mainList;
    private JTable table1;
    private JPanel panel1;

    public LiveBidsPanel(List<Contract> contractList) {
        setLayout(new BorderLayout());

        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 100;
        gbc.weighty = 100;
        mainList.add(new JPanel(), gbc);
        add(new JScrollPane(mainList));

        for (Contract c: contractList) {
            JPanel panel = new JPanel();
            JTable table = getTable(c);
            resizeColumnWidth(table);
            panel.add(table);
            panel.add(new JButton("Select Offer"));
            panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
            GridBagConstraints gbc1 = new GridBagConstraints();
            gbc1.gridwidth = GridBagConstraints.REMAINDER;
            gbc1.gridheight = 2;
            gbc1.weightx = 1;
            gbc1.fill = GridBagConstraints.HORIZONTAL;
            mainList.add(panel, gbc1, 0);
        }
    }

    private JTable getTable(Contract contractObject) {
        String[][] rec = {
                {"Contract End Date", ""},
                {"Tutor Name", contractObject.getSecondParty().getGivenName()},
                {"Subject", contractObject.getSubject().getName()},
                {"Number Of Sessions (per week)", "Kane"},
                {"Day & Time", "David"},
                {"Duration (hours)", ""},
                {"Rate (per hour)", ""},
        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);
        return contractTable;
    }

    // TODO: this is from https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths, rewrite
    public void resizeColumnWidth(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}


