package engine;

import controller.LoginController;
import model.LoginModel;
import model.offering.OfferingModel;
import stream.Bid;
import view.LoginView;
import view.form.BidInitiation;
import view.offering.CloseOfferView;
import view.offering.OfferingView;
import view.offering.OpenOffersView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

public class TestDriver {
    public static void main( String[] args ) {
        if (System.getenv("API_KEY") == null) {
            Logger.getLogger(Driver.class.getName()).info("Please set API_KEY as environment variable");
            return;
        }

        //Login model test
        LoginModel loginModel = new LoginModel();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginModel, loginView);


//        TimeZone tz = TimeZone.getTimeZone("UTC");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
//        df.setTimeZone(tz);
//        String nowAsISO = df.format(new Date());
//        System.out.println(nowAsISO);
//        Date date = new Date();
//        System.out.println(date);
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.DATE, 14);
//        Date d = c.getTime();
//        System.out.println(d);


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