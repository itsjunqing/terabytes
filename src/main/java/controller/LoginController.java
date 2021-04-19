package controller;

import model.DashboardModel;
import model.LoginModel;
import stream.User;
import view.DashboardView;
import view.LoginView;
import view.StudentDashboard;
import view.TutorDashboard;

public class LoginController {

//    private LoginModel loginModel;
//    private LoginView loginView;
//
//    public LoginController(LoginModel loginModel, LoginView loginView) {
//        this.loginModel = loginModel;
//        this.loginView = loginView;
//    }
//
//    public void listenLogin() {
//        // extract username and password from text view
//        String username = "username";
//        String password = "password";
//        if (loginModel.performLogin(username, password)) {
//            loginSuccess(loginModel.getUser(username));
//        }
//    }
//
//    private void loginSuccess(User user) {
//        DashboardModel dashboardModel = new DashboardModel(user);
//        DashboardView dashboardView;
//        if (user.getIsStudent()) {
//            dashboardView = new StudentDashboard(dashboardModel);
//        } else {
//            dashboardView = new TutorDashboard(dashboardModel);
//        }
//        DashboardController dashboardController = new DashboardController(dashboardModel, dashboardView);
////        loginView.dispose(); // destroy login view
//    }



}
