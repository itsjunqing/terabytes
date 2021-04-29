package api;

import stream.User;

import java.util.List;

public class UserApi extends BasicApi<User> {

    private final String USER_ENDPOINT = "/user";

    public List<User> getAllUsers() {
        String url = USER_ENDPOINT + "?fields=competencies&fields=competencies.subject&" +
                "fields=qualifications&fields=initiatedBids";
        return getAllObjects(url, User[].class);
    }

    public User getUser(String id) {
        String endpoint = USER_ENDPOINT + "/" + id +
                "?fields=competencies&fields=competencies.subject&" +
                "fields=qualifications&fields=initiatedBids";
        return getObject(endpoint, User.class);
    }

    public boolean addUser(User user) {
        return postObject(USER_ENDPOINT, user);
    }

    public boolean patchUser(User user) {
        return patchObject(USER_ENDPOINT, user);
    }

    public boolean removeUser(String id) {
        return deleteObject(USER_ENDPOINT + "/" + id);
    }

    public boolean verifyUser(User user) {
        String endpoint = USER_ENDPOINT + "/login?jwt=false";
        return postObject(endpoint, user);
    }
}
