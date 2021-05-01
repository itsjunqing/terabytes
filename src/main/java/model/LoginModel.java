package model;

import service.ApiService;
import stream.User;

import java.util.List;

/**
 * @author Jun Qing LIM
 */
public class LoginModel {

    private User user;
    private ApiService apiService;

    public LoginModel() {
        this.apiService = new ApiService();
    }

    // Return a User object
    public User getUser(String username) {
        List<User> users = apiService.getUserApi().getAll();
        return users.stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Performs a User login verification
    public boolean performLogin(String username, String password) {
        User loginUser = new User(username, password);
        if (apiService.getUserApi().verify(loginUser)) {
            this.user = getUser(username);
            return true;
        }
        return false;
    }

    public User getUser() {
        return user;
    }
}
