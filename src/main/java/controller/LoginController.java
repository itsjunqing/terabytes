package controller;

import controller.dashboard.StudentController;
import controller.dashboard.TutorController;
import model.LoginModel;
import stream.Contract;
import stream.User;
import view.LoginView;
import view.ViewUtility;
import view.dashboard.ExpiryNotification;

import javax.swing.*;
import java.util.List;

/**
 * A Class to control Login's dashboard movements
 */
public class LoginController {

    private LoginModel loginModel;
    private LoginView loginView;

    /**
     * Constructs LoginController object
     * @param loginModel a LoginModel object
     * @param loginView a LoginView object
     */
    public LoginController(LoginModel loginModel, LoginView loginView) {
        this.loginModel = loginModel;
        SwingUtilities.invokeLater(() -> {
            this.loginView = loginView;
            listenLogin();
        });
    }

    /**
     * Listens to login action
     */
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

    /**
     * Performs the login of the user and prompt ExpiryNotification view for contracts that are
     * about to expire within 1 month if necessary.
     * @param user the User object to login
     */
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
