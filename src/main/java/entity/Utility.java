package entity;

import service.ApiService;
import stream.Subject;
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

    public static User getUser(String username) {
        return ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    public static String getSubjectName(String subjectId) {
        Subject subject = ApiService.subjectApi().get(subjectId);
        return subject.getName();
    }

}
