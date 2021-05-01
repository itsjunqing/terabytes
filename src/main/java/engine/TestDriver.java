package engine;

import view.form.BidInitiation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class TestDriver {
    public static void main( String[] args ) {
        if (System.getenv("API_KEY") == null) {
            Logger.getLogger(Driver.class.getName()).info("Please set API_KEY as environment variable");
            return;
        }


        //Login model test
//        LoginModel loginModel = new LoginModel();
//        LoginView loginView = new LoginView();
//        LoginController loginController = new LoginController(loginModel, loginView);
    }



    // bid listeners test
    private static void listenBid(BidInitiation bidInitiation){
        bidInitiation.getOpenBidButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(bidInitiation.getSubject());
                System.out.println(bidInitiation.getSubject());
                System.out.println(bidInitiation.getRate());
            }
        });
    }



}