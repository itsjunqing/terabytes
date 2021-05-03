package engine;

import controller.LoginController;
import model.LoginModel;
import view.LoginView;

public class Driver {
    public static void main( String[] args ) {
        // Login Model
        LoginModel loginModel = new LoginModel();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginModel, loginView);
    }


}