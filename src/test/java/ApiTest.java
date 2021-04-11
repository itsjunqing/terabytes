import api.UserApi;
import org.junit.Test;
import stream.User;

public class ApiTest {

    private UserApi userApi = new UserApi();

    @Test
    public void userTest() {
        // Tests getUser
        assert (userApi.getUser("70e136be-40ec-4e4b-8682-ac457f43a3cf")
                .equals(new User("70e136be-40ec-4e4b-8682-ac457f43a3cf", "Irene", "Young", "iamyoung99", true, false)));
        assert (!userApi.getUser("70e136be-40ec-4e4b-8682-ac457f43a3cf")
                .equals(new User("2919e042-d7ec-468a-9f58-30c5250d411b", "Kevin", "Kim", "kevink", false, true)));

        // Tests verifyUser
        assert (userApi.verifyUser(new User("mbrown123", "mbrown123")));
        assert (!userApi.verifyUser(new User("mbrown123", "wrongpassword")));
        assert (!userApi.verifyUser(new User("wrongUsername", "wrongpassword")));

    }

}