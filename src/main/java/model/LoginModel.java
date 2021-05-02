package model;

import service.ApiService;
import stream.User;

public class LoginModel {

    private User user;

    // Performs a User login verification
    public boolean performLogin(String username, String password) {
        User loginUser = new User(username, password);
        if (ApiService.userApi().verify(loginUser)) {
            this.user = getUser(username);
            return true;
        }
        return false;
    }

    // Return a User object
    private User getUser(String username) {
        return ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    public User getUser() {
        return user;
    }
}
