package view.dashboard;

import lombok.Getter;
import lombok.Setter;
import model.dashboard.DashboardModel;
import stream.Contract;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public class TutorView extends DashboardView {

    public TutorView(DashboardModel dashboardModel) {
        super(dashboardModel);
        initView();
    }

    private void initView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("Tutor Dashboard");
        updateContracts();
        addButtons();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(830, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateContent(){
        updateContracts();
        addButtons();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
    }


    public void updateContracts() {
        // if contractPanel already constructed, just remove the contents (only one item inside - mainList)
        if (contractPanel != null) {
            contractPanel.removeAll();
        } else {
            contractPanel = new JPanel();
            contractPanel.setLayout(new BorderLayout());
            mainPanel.add(contractPanel);
        }

        JPanel mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 100;
        gbc.weighty = 100;
        mainList.add(new JPanel(), gbc);

        // add component into contractPanel. Note that contractPanel by default is in mainPanel, so just modify contractPanel will do
        contractPanel.add(new JScrollPane(mainList));

        // get the list of contracts and update accordingly
        List<Contract> contractList = getDashboardModel().getContractsList();
        Collections.reverse(contractList);
        int contractIndex = contractList.size();
        for (Contract c: contractList) {
            JPanel panel = new JPanel();
            JTable table = getTable(c, contractIndex);
            contractIndex -= 1;
            resizeColumnWidth(table);
            table.setBounds(10, 10, 500, 100);
            panel.add(table);

            panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
            GridBagConstraints gbc1 = new GridBagConstraints();
            gbc1.gridwidth = GridBagConstraints.REMAINDER;
            gbc1.gridheight = 2;
            gbc1.weightx = 1;
            gbc1.fill = GridBagConstraints.HORIZONTAL;
            mainList.add(panel, gbc1, 0);
        }
    }

    private JTable getTable(Contract contractObject, int contractNo) {
        String[][] rec = {
                {"Contract Number", Integer.toString(contractNo)},
                {"Contract End Date", contractObject.getExpiryDate().toString()},
                {"Tutor Name", contractObject.getSecondParty().getGivenName()},
                {"Subject", contractObject.getSubject().getName()},
                {"Number Of Sessions",  contractObject.getLessonInfo().getNumberOfSessions().toString()},
                {"Day & Time", contractObject.getLessonInfo().getTime() + " " + contractObject.getLessonInfo().getDay()},
                {"Duration", contractObject.getLessonInfo().getDuration().toString() + " hour(s)"},
                {"Rate (per hour)", "$ " + Integer.toString( contractObject.getPaymentInfo().getTotalPrice()/contractObject.getLessonInfo().getNumberOfSessions())},
        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);

        return contractTable;
    }

    // TODO: this is from https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths, rewrite
    private void resizeColumnWidth(JTable table) {
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

    private void addButtons() {
        // constructs buttonPanel and add into the mainPanel of the view
        if (buttonPanel != null) {
            buttonPanel.removeAll();
        } else {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            mainPanel.add(buttonPanel);}

        JPanel mainList = new JPanel(new GridBagLayout());
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;

        refreshButton = new JButton("Refresh");
        panel.add(refreshButton, gbc2);

        initiateButton = new JButton("Initiate Offer");
        panel.add(initiateButton, gbc2);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 100;
        gbc1.weightx = 100;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc1, 0);
        buttonPanel.add(mainList, BorderLayout.CENTER);
    }



    @Override
    public void update() {
        updateContent();
    }
}
