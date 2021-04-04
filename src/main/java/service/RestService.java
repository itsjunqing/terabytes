package service;

import com.google.gson.Gson;
import deserialization.User;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                                .header("accept", "application/json")
                                .header("Authorization", System.getenv("API_KEY"))
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

    public User getUser(String userId) {
        return gson.fromJson(getRequest(USER_URL + "/" + userId), User.class);
    }

    public List<User> getUserList() {
        String url = USER_URL + "/" + "?fields=competencies.subject&fields=qualifications";
        return Arrays.asList(gson.fromJson(getRequest(url), User[].class));
    }

}
