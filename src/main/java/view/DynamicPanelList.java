package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

public class DynamicPanelList {

    public DynamicPanelList() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private JPanel mainList;
        private JTable table1;
        private JPanel panel1;

        public TestPane() {
            setLayout(new BorderLayout());

            mainList = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 100;
            gbc.weighty = 100;
            mainList.add(new JPanel(), gbc);

            add(new JScrollPane(mainList));

            JButton add = new JButton("Add");
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel panel = new JPanel();
                    panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                    String[][] rec = {
                            { "1", "Steve" },
                            { "2", "Virat"},
                            { "3", "Kane"},
                            { "4", "David"},
                            { "5", "Ben"},
                            { "6", "Eion"},
                    };
                    String[] col = {"field", "value"};
                    JTable table = new JTable(rec, col);
                    panel.add(table);
                    panel.add(new JButton("Select Offer"));
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                    gbc.gridheight = 2;
                    gbc.weightx = 1;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    mainList.add(panel, gbc, 0);

                    validate();
                    repaint();
                }
            });

            add(add, BorderLayout.SOUTH);

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    }

//    private JTable makeTable(){
//        JTable table = new JTable();
//        String data1 ="Hi";
//        String data2 = "here is some data";
//        String data3 = "here is more data";
//        String data4 = "here is even more data";
//
//        Object[] row = { data1, data2, data3, data4 };
//
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//
//        model.addRow(row);
//
//        return table;
//    }


}
