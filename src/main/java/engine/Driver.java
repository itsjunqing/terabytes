package engine;

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
        RestService restService = RestService.getRestService();
        List<User> userList =  restService.getUserList();
        for (User u: userList) {
            System.out.println(u);
        }
    }
}
