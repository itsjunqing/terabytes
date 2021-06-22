package entity;

import service.ApiService;
import stream.Subject;
import stream.User;

import java.util.Calendar;
import java.util.Date;

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

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

}
