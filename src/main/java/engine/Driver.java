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

        /*
         Generate 3 expired contracts between renewalstudent and dummytutor1
         uncomment one of the options below to choose either 3 or 4 for renewal testing
          */

        // if 3 is provided, all dummy tutors (0-3) can be used in renewal
        TestScript.generateExpiredContracts("renewalstudent", "dummytutor1", 3);
        // if 4 is provided, then only dummytutor and dummytutor1 can be used in renewal
//        TestScript.generateExpiredContracts("renewalstudent", "dummytutor1", 4);

        TestScript.generateAlmostExpiredContract("almostexpiredstudent", "dummytutor");
    }

}
