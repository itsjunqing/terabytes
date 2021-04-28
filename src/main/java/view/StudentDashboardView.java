//import model.DashboardModel;
//import view.DashboardView;
//
//import javax.swing.*;
//import java.awt.*;
//
//<<<<<<<HEAD
////package view;
//=======
//        package view;
//
//
//public class StudentDashboardView extends DashboardView {
//
//    public StudentDashboardView(DashboardModel dashboardModel) {
//        super(dashboardModel);
//        initDisplay();
//        updateContracts();
//    }
//
//    private void initDisplay() {
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new GridLayout(1,2));
//    }
//
//    @Override
//    public void updateContracts() {
////        EventQueue.invokeLater(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////                } catch (Exception ex) {
////                }
////                JPanel mainPanel = new JPanel();
////                mainPanel.setLayout(new GridLayout(1,2));
//>>>>>>> d018c779cd3e24da86fc80a04a8d9ea5183760e2
////
////import model.DashboardModel;
////import stream.Contract;
////import view.panel.ContractPanel;
////import view.panel.button.StudentDashboardButtonPanel;
////
////import javax.swing.*;
////import java.awt.*;
////import java.util.List;
////
////public class StudentDashboardView extends DashboardView {
////
////    private JPanel mainPanel;
////    private ContractPanel contractPanel;
////    private StudentDashboardButtonPanel buttonPanel;
////
////    public StudentDashboardView(DashboardModel dashboardModel) {
////        super(dashboardModel);
////        initDisplay();
////        updateContracts();
////    }
////
////    private void initDisplay() {
////        mainPanel = new JPanel();
////        mainPanel.setLayout(new GridLayout(1,2));
////    }
////
////    @Override
////    public void updateContracts() {
//////        EventQueue.invokeLater(new Runnable() {
//////            @Override
//////            public void run() {
//////                try {
//////                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//////                } catch (Exception ex) {
//////                }
//////                JPanel mainPanel = new JPanel();
//////                mainPanel.setLayout(new GridLayout(1,2));
//////
//////                List<Contract> contractList = getDashboardModel().getContractsList();
//////                ContractPanel contractPanel = new ContractPanel(contractList);
//////                StudentDashboardButtonPanel buttonPanel = new StudentDashboardButtonPanel(contractList.size());
//////
//////                mainPanel.add(contractPanel);
//////                mainPanel.add(buttonPanel);
//////
//////                JFrame frame = new JFrame("Student Dashboard");
//////                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//////                frame.add(mainPanel);
//////                frame.pack();
//////                frame.setLocationRelativeTo(null);
//////                frame.setVisible(true);
//////            }
//////        });
////        mainPanel.removeAll();
////
////        List<Contract> contractList = getDashboardModel().getContractsList();
////        contractPanel = new ContractPanel(contractList);
////        buttonPanel = new StudentDashboardButtonPanel(contractList.size());
////
////        mainPanel.add(contractPanel);
////        mainPanel.add(buttonPanel);
////
////        JFrame frame = new JFrame("Student Dashboard");
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.add(mainPanel);
////        frame.pack();
////        frame.setLocationRelativeTo(null);
////        frame.setVisible(true);
////    }
////}
