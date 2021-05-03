package model;

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
            this.user = getUser(username);
            return true;
        }
        return false;
    }

    /**
     * Returns a User object of the LoginModel
     * @param username username of the user
     * @return User object
     */
    private User getUser(String username) {
        return ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the User object
     * @return user object
     */
    public User getUser() {
        return user;
    }
}
