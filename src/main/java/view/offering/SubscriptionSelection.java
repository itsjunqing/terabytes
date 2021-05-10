package view.offering;

import test.Bid;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;


public class SubscriptionSelection extends JFrame {
    private JPanel contentPane;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SubscriptionSelection frame = new SubscriptionSelection();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SubscriptionSelection() {

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(860, 400));
        this.setMaximumSize(new Dimension(1000, 1000));
        this.setPreferredSize(new Dimension(860, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        contentPane = new JPanel();
        this.add(contentPane);
        contentPane.setLayout(new GridBagLayout());

        DefaultListModel<Bid> listModel = new DefaultListModel<>();

        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));
        listModel.addElement(new Bid("Open", "James", "Maths"));

        JList<Bid> list = new JList<Bid>(listModel);
//        {
//            @Override
//            public Dimension getPreferredScrollableViewportSize() {
//                Dimension size = super.getPreferredScrollableViewportSize();
//                size.width = 300;
//                size.height = 800;
//                return size;
//            }
//        };

        JPanel panel = new JPanel();
        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.BOTH;
        panelConstraints.weightx = 0.4;
        panelConstraints.weighty = 1;
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 0;
        panel.setLayout(new BorderLayout(0, 0));
        JScrollPane bidScrollpane = new JScrollPane();
        panel.add(bidScrollpane, BorderLayout.CENTER);
        bidScrollpane.setViewportView(list);
        contentPane.add(panel, panelConstraints);


        JPanel panel_1 = new JPanel();
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.weightx = 0.6;
        buttonConstraints.weighty = 1;
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 0;
        contentPane.add(panel_1, buttonConstraints);

        this.pack();

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                final List<Bid> selected = list.getSelectedValuesList();
                if (selected.size() > 0){
                    System.out.println(selected.get(0).getClass());
                }

                System.out.println(selected);
            }
        });

    }

}
