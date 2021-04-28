//<<<<<<< HEAD
////package view;
////
////import model.DashboardModel;
////import stream.Contract;
////import view.panel.ContractPanel;
////import view.panel.button.TutorDashboardButtonPanel;
////
////import javax.swing.*;
////import java.awt.*;
////import java.util.List;
////
////public class TutorDashboardView extends DashboardView {
////
////    public TutorDashboardView(DashboardModel dashboardModel) {
////        super(dashboardModel);
////        updateContracts();
////    }
////
////    @Override
////    public void updateContracts() {
//=======
//package view;
//
//import lombok.Getter;
//import model.DashboardModel;
//import stream.Contract;
//import view.panel.ContractPanel;
//import view.panel.button.TutorDashboardButtonPanel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.List;
//
//public class TutorDashboardView extends DashboardView {
//
//    public TutorDashboardView(DashboardModel dashboardModel) {
//        super(dashboardModel);
//        initDisplay();
//        updateContracts();
//    }
//    private void initDisplay(){
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new GridLayout(1,2));
//    }
//
//    @Override
//    public void updateContracts() {
//
////        EventQueue.invokeLater(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////                } catch (Exception ex) {
////                }
//
////                mainPanel = new JPanel();
////                mainPanel.setLayout(new GridLayout(1,2));
//
////
//
////
////                List<Contract> contractList = getDashboardModel().getContractsList();
////                contractPanel = new ContractPanel(contractList);
////                buttonPanel = new TutorDashboardButtonPanel(contractList.size());
////                mainPanel.add(contractPanel);
////                mainPanel.add(buttonPanel);
////
////                JFrame frame = new JFrame("Tutor Dashboard");
////                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////                frame.add(mainPanel);
////                frame.pack();
////                frame.setLocationRelativeTo(null);
////                frame.setVisible(true);
////            }
////        });
//
////    }
////}
//
//
//        List<Contract> contractList = getDashboardModel().getContractsList();
//        contractPanel = new ContractPanel(contractList);
//        buttonPanel = new TutorDashboardButtonPanel(contractList.size());
//        mainPanel.add(contractPanel);
//        mainPanel.add(buttonPanel);
//
//        JFrame frame = new JFrame("Tutor Dashboard");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(mainPanel);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//}
//>>>>>>> d018c779cd3e24da86fc80a04a8d9ea5183760e2
