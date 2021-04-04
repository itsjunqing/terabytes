package engine;

import deserialization.Bid;
import deserialization.Contract;
import deserialization.Message;
import deserialization.User;
import service.RestService;

import java.util.List;
import java.util.logging.Logger;

public class Driver {
    public static void main( String[] args ) {
        if (System.getenv("API_KEY") == null) {
            Logger.getLogger(Driver.class.getName()).info("Please set API_KEY as environment variable");
            return;
        }
        testingLol();

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
