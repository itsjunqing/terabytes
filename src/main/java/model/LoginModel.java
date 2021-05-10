package model;

import entity.Constants;
import entity.Utility;
import service.ApiService;
import stream.Contract;
import stream.User;

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
     * Returns the list of contracts that are expiring within 1 month
     * @return a list of Contract objects
     */
    public List<Contract> getExpiringContracts() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        return ApiService.contractApi().getAll().stream()
                .filter(c -> c.getFirstParty().getId().equals(getUserId())
                            || c.getSecondParty().getId().equals(getUserId())) // filter for either student / tutor
                .filter(c -> now.before(c.getExpiryDate())) // filter today's date < expiry date
                .filter(c -> {
                    calendar.setTime(c.getExpiryDate());
                    calendar.add(Calendar.MONTH, -Constants.LOGIN_EXPIRY_MONTHS);
                    Date oneMonthBeforeExpiry = calendar.getTime();
                    return now.after(oneMonthBeforeExpiry); // filter today's date > one month before expiry date
                })
                .collect(Collectors.toList());
    }
}
