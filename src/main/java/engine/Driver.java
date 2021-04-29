package engine;

import entity.MessagePair;
import model.CloseBidModel;
import model.OpenBidModel;
import entity.QualificationTitle;
import view.builder.CloseBidView;
import view.builder.CloseMessageView;
import view.builder.OpenBidView;
import view.form.BidInitiation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class Driver {
    public static void main( String[] args ) {
        if (System.getenv("API_KEY") == null) {
            Logger.getLogger(Driver.class.getName()).info("Please set API_KEY as environment variable");
            return;
        }

//        TimeZone tz = TimeZone.getTimeZone("UTC");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
//        df.setTimeZone(tz);
//        String nowAsISO = df.format(new Date());
//        System.out.println(nowAsISO);
        Date date = new Date();
        System.out.println(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 14);
        Date d = c.getTime();
        System.out.println(d);

        QualificationTitle q = QualificationTitle.BACHELOR;
        System.out.println(q.toString());
        System.out.println(q.name());
        String ba = "Masters";
        QualificationTitle q1 = QualificationTitle.valueOf(ba.toUpperCase());
        System.out.println(q1.toString());



        // Login model test
//        LoginModel loginModel = new LoginModel();
//        LoginView loginView = new LoginView();
//        LoginController loginController = new LoginController(loginModel, loginView);




        // reply message test

        // offer bid test

        // reply bid test

        // contract finalization test

        // Active Request Dashboard test
        // make a bid using stream.bid
        // make a bidAdditionalInfo using stream.bidadditionalInfo
        // make a bidInfo and bidPreference from model folder
//        UserApi userApi = new UserApi();
//        User testUser = userApi.getUser("bb495c2c-3e30-4e4a-80e7-3954c448d128");
//        OfferingModel testOfferingModel = new OfferingModel(testUser);

        // create new bid for testing, complete with offers
        // add open and closed bids
//        testOfferingModel.setBidsOnGoing();
//        BidApi bidApi = new BidApi();
//        List<Bid> bidList = bidApi.getAllBids();

//        testOfferingModel.refresh();
        // Passing them a list of bids to test. Once the api is complete, will not pass bids

//        // create new bid for testing, complete with offers
//        // add open and closed bids
////        testOfferingModel.setBidsOnGoing();
//        BidApi bidApi = new BidApi();
//        List<Bid> bidList = bidApi.getAllBids();
//
////        testOfferingModel.refresh();
//        // Passing them a list of bids to test. Once the api is complete, will not pass bids

//        ActiveRequestsView activeRequestViewTest = new ActiveRequestsView(testOfferingModel, bidList);
//        activeRequestViewTest.displayContracts();







        sampleUsage();





    }

    private static void sampleUsage() {
        // TODO: for Nick to run to see, this is to be displayed in the view
//        OpenBidModel openBidModel = new OpenBidModel();
//        openBidModel.setBidId("c9b04eee-6a57-4b1f-ba06-7c1f7c2e87b3");
//        openBidModel.setUserId("1ed84243-50ac-437e-872e-39dbce04c5a4");
//        openBidModel.refresh();
////        OpenBidView openBidView = new OpenBidView(openBidModel);
//
//        openBidModel.getOpenBidOffers().stream().forEach(b -> System.out.println(b));


        // TODO: For Nick to run to see, we gonna use this to manipulate the view
        CloseBidModel closeBidModel = new CloseBidModel();
        closeBidModel.setUserId("1ed84243-50ac-437e-872e-39dbce04c5a4");
        closeBidModel.setBidId("51ab43a7-25aa-4ff2-a052-418e5a46b774");
        closeBidModel.refresh();
//        CloseBidView closeBidView = new CloseBidView(closeBidModel);

        closeBidModel.getCloseBidOffers().stream().forEach(m -> System.out.println(m));
        closeBidModel.getCloseBidMessages().stream().forEach(m -> System.out.println(m));
        List<MessagePair> messagePairs= closeBidModel.getCloseBidMessages();
        MessagePair sampleMessagePair = messagePairs.get(0);

        CloseMessageView closeMessageView = new CloseMessageView(sampleMessagePair);
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