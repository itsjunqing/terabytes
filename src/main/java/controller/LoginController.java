package controller;

import model.LoginModel;
import view.LoginView;

public class LoginController {

    private LoginModel loginModel;
    private LoginView loginView;

    public LoginController(LoginModel loginModel, LoginView loginView) {
        this.loginModel = loginModel;
        this.loginView = loginView;
    }

    public void test() {
        System.out.println(loginModel.getUser("mbrown123"));
        System.out.println(loginModel.getUser("jamesli42"));
        assert loginModel.performLogin("jamesli42", "jamesli42");
        assert !loginModel.performLogin("baddie", "jamesli42");
    }


}
