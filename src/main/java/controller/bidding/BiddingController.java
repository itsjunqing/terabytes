package controller.bidding;

import controller.EventListener;
import service.ApiService;
import stream.Contract;
import view.ViewUtility;
import view.form.Confirmation;

import java.util.Calendar;
import java.util.Date;


public abstract class BiddingController implements EventListener {

    protected boolean handleContract(Contract contract) {
        Object lock = new Object();
        Boolean[] confirmed = {false}; // array is used to resolve lambda expression local variable issue

        System.out.println("From BiddingController: Contract is being confirmed now");
        Confirmation confirmation = new Confirmation(contract, ViewUtility.STUDENT_CODE);

        confirmation.getConfirmSignButton().addActionListener(e1 -> {
            System.out.println("From BiddingController: Confirm/Sign Button is pressed");
            // get the contract duration
            int months = confirmation.getContractDuration();

            // calculate expiry date based on date creation
            Calendar c = Calendar.getInstance();
            c.setTime(contract.getDateCreated());
            c.add(Calendar.MONTH, months);
            Date expiryDate = c.getTime();

            // update expiry date
            contract.setExpiryDate(expiryDate);

            // push to API and sign
            Contract contractAdded = ApiService.contractApi().add(contract);
            String contractId = contractAdded.getId();
            ApiService.contractApi().sign(contractId, new Contract(new Date()));

            // dispose view
            confirmation.dispose();

            confirmed[0] = true;

            synchronized (lock) {
                lock.notify();
            }
        });

        confirmation.getCancelButton().addActionListener(e1 -> {
            System.out.println("From BiddingController: Cancel Button is pressed");
            // do nothing and dispose when cancelled
            confirmation.dispose();

            synchronized (lock) {
                lock.notify();
            }
        });

        // wait until one of the buttons is clicked
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
            }
        }
        return confirmed[0];
    }

    public abstract void listenViewActions();

}
