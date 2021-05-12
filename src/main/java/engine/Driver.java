package engine;

import controller.LoginController;
import model.LoginModel;
import view.LoginView;

public class Driver {
    public static void main(String[] args) {
        LoginModel loginModel = new LoginModel();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginModel, loginView);

//        mainScript();



    }


    public static void mainScript() {
        TestScript.clearAllData();
        // Generate about to expire for testing expiry notification upon login
        TestScript.generateAboutToExpireContracts("expirystudent", "dummytutor3");

        // Generate 5 active contracts between activestudent and dummytutor3 (possible because dummytutor2 has at least level 3+2)
        TestScript.generateActiveContracts("activestudent", "dummytutor2");

        // Generate 3 expired contracts between activestudent and dummytutor3 (possible because dummytutor2 has at least level 3+2)
        TestScript.generateExpiredContracts("activestudent", "dummytutor3", 3);

        // Generate 3 expired contracts between renewalstudent and dummytutor1
        TestScript.generateExpiredContracts("renewalstudent", "dummytutor1", 4);
    }

}
