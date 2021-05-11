package model;

import entity.Constants;
import entity.Utility;
import service.ApiService;
import stream.Contract;
import stream.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A Class of LoginModel to store data relevant to the aspect of user login.
 */
public class LoginModel {

    private User user;

    /**
     * Performs a User login verification
     * @param username a String username
     * @param password a String password
     * @return True if verification is passed or False
     */
    public boolean performLogin(String username, String password) {
        User loginUser = new User(username, password);
        if (ApiService.userApi().verify(loginUser)) {
            this.user = Utility.getUser(username);
            return true;
        }
        return false;
    }

    /**
     * Returns the User object
     * @return user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the user of the current User
     * @return a string of user id
     */
    public String getUserId() {
        return user.getId();
    }

    /**
     * Returns the list of contracts that are expiring within (or exactly) 1 month
     * @return a list of expiring Contract objects
     */
    public List<Contract> getExpiringContracts() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();

        List<Contract> contracts = ApiService.contractApi().getAll().stream()
                .filter(c -> c.getFirstParty().getId().equals(getUserId())
                        || c.getSecondParty().getId().equals(getUserId()))
                .filter(c -> c.getExpiryDate().after(today))
                .collect(Collectors.toList());
        System.out.println("All contracts are: ");
        contracts.stream().forEach(c -> System.out.println(c));
        List<Contract> expiring = new ArrayList<>();

        for (Contract c: contracts) {
            Date expiryDate = c.getExpiryDate();
            calendar.setTime(expiryDate);
            calendar.add(Calendar.MONTH,  -Constants.LOGIN_EXPIRY_MONTHS);
            Date oneMonthBeforeExpiry = calendar.getTime();
            if (oneMonthBeforeExpiry.before(today) || Utility.isSameDay(oneMonthBeforeExpiry, today)) {
                expiring.add(c);
            }
        }
        System.out.println("Expiring contracts are: ");
        expiring.stream().forEach(e -> System.out.println(e));

        return expiring;


            // don't delete this, it's the same as above, just that above style has printing
//        return ApiService.contractApi().getAll().stream()
//
//                // filter for either student / tutor
//                .filter(c -> c.getFirstParty().getId().equals(getUserId())
//                            || c.getSecondParty().getId().equals(getUserId()))
//
//                // filter expiry date > today's date (filter non expired contracts)
//                .filter(c -> c.getExpiryDate().after(today))
//
//                // filter date of (one month before expiry) <= (is before or equals) today's date
//                .filter(c -> {
//                    calendar.setTime(c.getExpiryDate());
//                    calendar.add(Calendar.MONTH, -Constants.LOGIN_EXPIRY_MONTHS);
//                    Date oneMonthBeforeExpiry = calendar.getTime();
//                    return oneMonthBeforeExpiry.before(today) || Utility.isSameDay(today, oneMonthBeforeExpiry);
//                })
//                .collect(Collectors.toList());




    }
}
