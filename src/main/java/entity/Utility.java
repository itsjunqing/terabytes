package entity;

import service.ApiService;
import stream.User;

/**
 * A Utility static helper class.
 */
public class Utility {

    public static String getFullName(User user) {
        return user.getGivenName() + " " + user.getFamilyName();
    }

    public static String getFullName(String userId) {
        User user = ApiService.userApi().get(userId);
        return getFullName(user);
    }
}
