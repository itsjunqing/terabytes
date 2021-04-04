package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import deserialization.Bid;
import deserialization.Contract;
import deserialization.Message;
import deserialization.User;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RestService {
    private final String ROOT_URL = "https://fit3077.com/api/v1";
    private final String USER_URL = "/user";
    private final String SUBJECT_URL = "/subject";
    private final String QUALIFICATION_URL = "/qualification";
    private final String COMPETENCY_URL = "/competencies";
    private final String CONTRACT_URL = "/contract";
    private final String BID_URL = "/bid";
    private final String MESSAGE_URL = "/message";

    private static RestService restService;
    private OkHttpClient httpClient;
    private Gson gson;

    public static RestService getRestService() {
        if (restService == null) {
            restService = new RestService();
        }
        return restService;
    }

    private RestService() {
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .callTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("accept", "application/json") // for GET
                                .header("Content-Type", "application/json") // for POST, PATCH, PUT
                                .header("Authorization", System.getenv("API_KEY")) // for authentication
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        gson = new Gson();
    }

    private String getRequest(String url) {
        try {
            Request request = new Request.Builder()
                    .url(ROOT_URL + url)
                    .build();
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private String postRequest(String url, String json) {
        // TODO: Change to return to Boolean as POST, PUT, PATCH do not return JSON
        // TODO: Only change when all testings are completed
        try {
            RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url(ROOT_URL + url)
                    .post(body)
                    .build();
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<User> getAllUsers() {
        String url = USER_URL + "/" + "?fields=competencies.subject&fields=qualifications";
        return Arrays.asList(gson.fromJson(getRequest(url), User[].class));
    }

    public User getUser(String userId) {
        return gson.fromJson(getRequest(USER_URL + "/" + userId), User.class);
    }

    public String verifyUser(String userName, String password) {
        // TODO: Change jwt to true if necessary to perform token-based authentication
        // TODO: Change to return Boolean
        String url = USER_URL + "/login?jwt=false";
        JsonObject json = new JsonObject();
        json.addProperty("userName", userName);
        json.addProperty("password", password);
        return postRequest(url, json.toString());
    }

    public List<Contract> getAllContracts() {
        return Arrays.asList(gson.fromJson(getRequest(CONTRACT_URL), Contract[].class));
    }

    public List<Message> getAllMessages() {
        return Arrays.asList(gson.fromJson(getRequest(MESSAGE_URL), Message[].class));
    }

    public List<Bid> getAllBids() {
        return Arrays.asList(gson.fromJson(getRequest(BID_URL), Bid[].class));
    }

}
