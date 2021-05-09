package model;

import entity.Utility;
import service.ApiService;
import stream.User;

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
}
