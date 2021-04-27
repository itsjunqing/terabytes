package engine;

import api.BidApi;
import api.RestService;
import api.UserApi;
import controller.LoginController;
import model.BiddingModel;
import model.LoginModel;
import model.OfferingModel;
import model.QualificationTitle;
import stream.Bid;
import stream.Contract;
import stream.Message;
import stream.User;
import view.ActiveRequestsView;
import view.LoginView;
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
        LoginModel loginModel = new LoginModel();
        LoginView loginView = new LoginView();
//        loginView.display();
        LoginController loginController = new LoginController(loginModel, loginView);

        // Bid Initiation test
//        BidInitiation bidInitiation = new BidInitiation();
//        listenBid(bidInitiation);



        // open bidding dashboard test

        // close bidding dashboard test

        // reply message test

        // offer bid test

        // reply bid test

        // contract finalization test

        // Active Request Dashboard test
        // make a bid using stream.bid
        // make a bidAdditionalInfo using stream.bidadditionalInfo
        // make a bidInfo and bidPreference from model folder
        UserApi userApi = new UserApi();
        User testUser = userApi.getUser("bb495c2c-3e30-4e4a-80e7-3954c448d128");
        OfferingModel testOfferingModel = new OfferingModel(testUser);
        // create new bid for testing, complete with offers
        // add open and closed bids
//        testOfferingModel.setBidsOnGoing();
        BidApi bidApi = new BidApi();
        List<Bid> bidList = bidApi.getAllBids();

//        testOfferingModel.refresh();
        // Passing them a list of bids to test. Once the api is complete, will not pass bids
        ActiveRequestsView activeRequestViewTest = new ActiveRequestsView(testOfferingModel, bidList);
        activeRequestViewTest.displayContracts();













    }

    private static void testingLol() {
        RestService restService = RestService.getRestService();
        // Get all users
        List<User> userList =  restService.getAllUsers();
        for (User u: userList) {
            System.out.println(u);
        }
        // Verify user
        System.out.println(restService.verifyUser("mbrown123", "mbrown123"));
        // Get all contracts
        List<Contract> contractList = restService.getAllContracts();
        for (Contract c: contractList) {
            System.out.println(c);
        }
        // Get all messages
        List<Message> messageList = restService.getAllMessages();
        for (Message m: messageList) {
            System.out.println(m);
        }
        // Get all bids
        List<Bid> bidList = restService.getAllBids();
        for (Bid b: bidList) {
            System.out.println(b);
        }
    }



    // bid listeners test
    private static void listenBid(BidInitiation bidInitiation){
        bidInitiation.getOpenBidButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(bidInitiation.getSubject());
                System.out.println(bidInitiation.getSubject());
                System.out.println(bidInitiation.getRate());
                System.out.println(bidInitiation.getMessageNote());
            }
        });
    }

}

//        testingLol();
//        Date date = new Date();
//        System.out.println(date.toString());
//        BidApi bidApi = new BidApi();
//        List<Bid> bids = bidApi.getAll();
////
//        for (Bid b: bids) {
////            System.out.println(b.getDateCreated());
//            System.out.println(b);
//        }
//        Gson gson = new Gson();
//        Bid bid = new Bid("open", "123", new Date(), "32132", null);
//        String x = gson.toJson(bid);
//        System.out.println(x);
//        bidApi.postNew(bid);

//        OpenBiddingModel openBiddingModel = new OpenBiddingModel(1, null);