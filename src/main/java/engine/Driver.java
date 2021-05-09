package engine;

import api.BidApi;
import api.ContractApi;
import api.MessageApi;
import controller.LoginController;
import model.LoginModel;
import stream.Bid;
import stream.Contract;
import stream.Message;
import view.LoginView;

import java.util.List;


import controller.LoginController;
import model.LoginModel;
import view.LoginView;

import java.util.Calendar;
import java.util.Date;

import java.util.Calendar;
import java.util.Date;

public class Driver {
    public static void main( String[] args ) {
        // Login Model
        LoginModel loginModel = new LoginModel();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginModel, loginView);

       // clearData();
    }

    private static void clearData() {
        ContractApi contractApi = new ContractApi();
        List<Contract> contracts = contractApi.getAll();
        contracts.stream().forEach(b -> contractApi.remove(b.getId()));

        MessageApi messageApi = new MessageApi();
        List<Message> messages = messageApi.getAll();
        messages.stream().forEach(b -> messageApi.remove(b.getId()));

        BidApi bidApi = new BidApi();
        List<Bid> bids = bidApi.getAll();
        bids.stream().forEach(b -> bidApi.remove(b.getId()));
    }
    public static void testExpiry() {
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, -2); // minus 2 weeks
        Date before = c.getTime();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, 2); // add 2 weeks
        Date after = c.getTime();
        c.setTime(date);
        c.add(Calendar.SECOND, 10); // add 10 seconds
        Date now = c.getTime();

        System.out.println(before);
        System.out.println(now);
        System.out.println(after);

        Date current = new Date();
        System.out.println(current);
        System.out.println(before.before(current));
        System.out.println(now.before(current));
        System.out.println(after.before(current));
        //same logic as below
        System.out.println(current.after(before));
        System.out.println(current.after(now));
        System.out.println(current.after(after));

    }


}


}