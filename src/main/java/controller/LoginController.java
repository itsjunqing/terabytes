package controller;

import controller.dashboard.StudentController;
import controller.dashboard.TutorController;
import model.LoginModel;
import stream.User;
import view.LoginView;

import javax.swing.*;

public class LoginController {

    private LoginModel loginModel;
    private LoginView loginView;

    public LoginController(LoginModel loginModel, LoginView loginView) {
        this.loginModel = loginModel;
        SwingUtilities.invokeLater(() -> {
            this.loginView = loginView;
            listenLogin();
        });
    }

    private void listenLogin() {
        loginView.getLoginButton().addActionListener(e -> {
            String username = loginView.getUserField().getText();
            String password = String.valueOf(loginView.getPasswordField().getPassword());
            if (loginModel.performLogin(username, password)) {
                loginView.getErrorLabel().setText("Success! Launching Dashboard...");
                loginSuccess(loginModel.getUser());
            } else {
                loginView.getErrorLabel().setText("Error: Wrong credentials!");
            }
        });
    }

    private void loginSuccess(User user) {
        if (user.getIsStudent()) {
            new StudentController(user.getId());
        } else {
            new TutorController(user.getId());
        }
        loginView.dispose(); // destroy login view
    }
}
