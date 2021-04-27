package view.panel;

import stream.Bid;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class OpenOffersPanel extends JPanel {

    private JPanel mainList;
    private JTable table1;
    private JPanel panel1;

    public OpenOffersPanel(List<Bid> bidList) {
        setLayout(new BorderLayout());

        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 100;
        gbc.weighty = 100;
        mainList.add(new JPanel(), gbc);
        add(new JScrollPane(mainList));

        int bidIndex = bidList.size();
        System.out.println(bidIndex);
        for (Bid b: bidList) {
            // Code to add open bid panel
            if (1 == 1) {
                JPanel panel = new JPanel();
                JTable table = getTable(b, bidIndex);
                bidIndex -= 1;
                resizeColumnWidth(table);
                table.setBounds(10, 10, 500, 100);
                panel.add(table);
//            panel.setViewportView(table);

//            panel.add(new JButton("Select Offer"));
                TitledBorder title;
                title = BorderFactory.createTitledBorder("Student Request");
                panel.setBorder(title);
                GridBagConstraints gbc1 = new GridBagConstraints();
                gbc1.gridwidth = GridBagConstraints.REMAINDER;
                gbc1.gridheight = 2;
                gbc1.weightx = 1;
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                mainList.add(panel, gbc1, 0);
            }
            // Code to display offerings from other tutors
            else if (1 == 1) {

                JPanel panel = new JPanel();
                JTable table = getOfferingTable(b, bidIndex);
                bidIndex -= 1;
                resizeColumnWidth(table);
                table.setBounds(10, 10, 500, 100);
                panel.add(table);
//            panel.setViewportView(table);

//            panel.add(new JButton("Select Offer"));
                panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                GridBagConstraints gbc1 = new GridBagConstraints();
                gbc1.gridwidth = GridBagConstraints.REMAINDER;
                gbc1.gridheight = 2;
                gbc1.weightx = 1;
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                mainList.add(panel, gbc1, 0);

            }
        }
    }

    private JTable getTable(Bid bidObject, int bidNo) {
        String[][] rec = {
                {"Student Name:", ""},
                {"Subject:", ""},
                {"Number of Sessions:", ""},
                {"Day & Time:", ""},
                {"Duration (hours):", ""},
                {"Rate (per hour):", ""},
        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);
        return contractTable;
    }


    private JTable getOfferingTable(Bid bidObject, int bidNo) {
        String[][] rec = {
                {"Proposed Contract End Date", ""},
                {"Tutor Name:", ""},
                {"Subject:", ""},
                {"Number of Sessions:", ""},
                {"Day & Time:", ""},
                {"Duration (hours):", ""},
                {"Rate (per hour):", ""},
                {"Free Lesson?", ""},

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
            System.out.println(width);
            if(width > 300)
                width=300;
            if(width < 200)
                width=200;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}


