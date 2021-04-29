package view.builder;

//public class CloseBidView extends BiddingView {

    // similar to OpenBidView

//    private JPanel mainPanel;
//
//    public CloseBidView(BiddingModel biddingModel) {
//        super(biddingModel);
//        initView();
//    }
//
//    private void initView() {
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new GridLayout(1,2));
//
////                List <Bid> bidList = offeringModel.getBidsOnGoing();
//        CloseBiddingPanel closeBiddingPanel = new CloseBiddingPanel(bidList);
//        CloseBiddingButtonPanel buttonPanel = new CloseBiddingButtonPanel(bidList.size());
//
//        mainPanel.add(closeBiddingPanel);
//        mainPanel.add(buttonPanel);
//
//        JFrame frame = new JFrame("Open Offers");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(mainPanel);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//
//        updateContent();
//    }
//
//    @Override
//    public void updateContent() {
//        List<BidInfo> bidInfoList = getBiddingModel().getBidOffers();
//        int bidIndex = bidInfoList.size();
//        updateView(bidInfoList);
//        updateButtons();
//    }
//
//    private void updateView(List<BidInfo> bidInfoList) {
//        setLayout(new BorderLayout());
//
//        mainList = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.weightx = 100;
//        gbc.weighty = 100;
//        mainList.add(new JPanel(), gbc);
//        add(new JScrollPane(mainList));
//
//        int bidIndex = bidList.size();
//        System.out.println(bidIndex);
//        for (Bid b: bidList) {
//            // Code to generate an open contract panel
//            JPanel panel = new JPanel();
//            JTable table = getTable(b, bidIndex);
//            bidIndex -= 1;
//            resizeColumnWidth(table);
//            table.setBounds(10, 10, 500, 100);
//            panel.add(table);
////            panel.setViewportView(table);
//
////            panel.add(new JButton("Select Offer"));
//            panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
//            GridBagConstraints gbc1 = new GridBagConstraints();
//            gbc1.gridwidth = GridBagConstraints.REMAINDER;
//            gbc1.gridheight = 2;
//            gbc1.weightx = 1;
//            gbc1.fill = GridBagConstraints.HORIZONTAL;
//            mainList.add(panel, gbc1, 0);
//
//            // Code to generate a closed contract panel
//
//        }
//    }
//



//}
