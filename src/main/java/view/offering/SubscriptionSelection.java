package view.offering;

import lombok.Getter;
import test.Bid;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SubscriptionSelection extends JFrame {
    private JPanel contentPane;
    private JLabel errorLabel;
    private JButton confirmSelection;
    private JList<Bid> bidJList;


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
        super("Subscription Selection");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(860, 400));
        this.setMaximumSize(new Dimension(1000, 1000));
        this.setPreferredSize(new Dimension(860, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        contentPane = new JPanel();
        this.add(contentPane);
        contentPane.setLayout(new GridBagLayout());

        List<Bid> bidList = new ArrayList<Bid>();

        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));
        bidList.add(new Bid("Open", "James", "Maths"));

        DefaultListModel<Bid> listModel = new DefaultListModel<>();
        for (Bid b: bidList) {
            listModel.addElement(b);
        }
        bidJList = new JList<Bid>(listModel);
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
        panelConstraints.weightx = 0.3;
        panelConstraints.weighty = 1;
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 0;
        panel.setLayout(new BorderLayout(0, 0));
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                loweredbevel, "Please Select Bids to Subscribe to");
        titledBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
        panel.setBorder(titledBorder);
        JScrollPane bidScrollpane = new JScrollPane();
        panel.add(bidScrollpane, BorderLayout.CENTER);
        bidScrollpane.setViewportView(bidJList);
        contentPane.add(panel, panelConstraints);


        JPanel panel_1 = updateButtons();
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.weightx = 0.7;
        buttonConstraints.weighty = 1;
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 0;
        contentPane.add(panel_1, buttonConstraints);

        this.pack();

        bidJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                final List<Bid> selected = bidJList.getSelectedValuesList();
                if (selected.size() > 0){
                    System.out.println(selected.get(0).getClass());
                }

                System.out.println(selected);
            }
        });


    }

    private JPanel updateButtons() {


        JPanel mainList = new JPanel(new GridBagLayout());
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;


        // add select offer button
        confirmSelection  = new JButton("Confirm Selection");
        panel.add(confirmSelection, gbc2);
        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(-4521974));
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setHorizontalTextPosition(0);
        //TODO: get error text from the model instead
        errorLabel.setText("");
        panel.add(errorLabel);

        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 100;
        gbc1.weightx = 100;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
//        mainList.add(panel, gbc1, 0);
        return panel;
    }

}
