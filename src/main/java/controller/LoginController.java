package controller;

import controller.dashboard.StudentController;
import controller.dashboard.TutorController;
import model.LoginModel;
import stream.Contract;
import stream.User;
import view.LoginView;
import view.ViewUtility;
import view.form.ExpiryNotification;

import javax.swing.*;
import java.util.List;

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
        List<Contract> expiringContracts = loginModel.getExpiringContracts();
        if (user.getIsStudent()) {
            new StudentController(user.getId());
            if (!expiringContracts.isEmpty()) {
                ExpiryNotification en = new ExpiryNotification(expiringContracts, ViewUtility.STUDENT_CODE);
                en.getNotedButton().addActionListener(e -> en.dispose());
            }
        } else {
            new TutorController(user.getId());
            if (!expiringContracts.isEmpty()) {
                ExpiryNotification en = new ExpiryNotification(expiringContracts, ViewUtility.TUTOR_CODE);
                en.getNotedButton().addActionListener(e -> en.dispose());
            }
        }
        loginView.dispose(); // destroy login view
    }
}
