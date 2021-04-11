package engine;

import api.RestService;
import controller.LoginController;
import model.LoginModel;
import stream.Bid;
import stream.Contract;
import stream.Message;
import stream.User;
import view.LoginView;

import java.util.List;
import java.util.logging.Logger;

public class Driver {
    public static void main( String[] args ) {
        if (System.getenv("API_KEY") == null) {
            Logger.getLogger(Driver.class.getName()).info("Please set API_KEY as environment variable");
            return;
        }

        LoginModel loginModel = new LoginModel();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginModel, loginView);

        loginController.test();


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
}
