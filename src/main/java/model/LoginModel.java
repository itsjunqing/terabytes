package model;

import api.UserApi;
import stream.User;

import java.util.List;

/**
 * @author Jun Qing LIM
 */
public class LoginModel {

    private User user;
    private UserApi userApi;

    public LoginModel() {
        this.userApi = new UserApi();
    }

    // Return a User object
    public User getUser(String username) {
        List<User> users = userApi.getAllUsers();
        return users.stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Performs a User login verification
    public boolean performLogin(String username, String password) {
        User loginUser = new User(username, password);
        if (userApi.verifyUser(loginUser)) {
            this.user = getUser(username);
            return true;
        }
        return false;
    }

}
