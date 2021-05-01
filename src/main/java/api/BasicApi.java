package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BasicApi<T> implements ApiInterface<T> {

    private final String ROOT_URL = "https://fit3077.com/api/v1";
    private OkHttpClient httpClient;
    private Gson gson;

    public BasicApi() {
        this.httpClient = new OkHttpClient.Builder()
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

                // Fix SSL issue
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();


    }

    private Request buildRequest(String url, String json, ApiType type) {
        RequestBody body = null;
        if (json != null) {
            body = RequestBody.create(json, MediaType.parse("application/json"));
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(ROOT_URL + url);
        switch (type) {
            case GET:
                requestBuilder = requestBuilder.get();
                break;
            case POST:
                requestBuilder = requestBuilder.post(body);
                break;
            case PATCH:
                requestBuilder = requestBuilder.patch(body);
                break;
            case DELETE:
                requestBuilder = requestBuilder.delete();
                break;
        }
        return requestBuilder.build();
    }

    private Response getRequest(String url) throws IOException {
        Request request = buildRequest(url, null, ApiType.GET);
        return httpClient.newCall(request).execute();
    }

    private Response updateRequest(String url, String json, ApiType type) throws IOException {
        Request request = buildRequest(url, json, type);
        return httpClient.newCall(request).execute();
    }

    protected List<T> getAllObjects(String endpoint, Class<T[]> clazz) {
        try {
            Response response = getRequest(endpoint);
            if (response.isSuccessful()) {
                String json = response.body().string();
                System.out.println("From BasicApi: Successfully GETALL for " + clazz.getName());
                return Arrays.asList(gson.fromJson(json, clazz));
            }
        } catch (IOException e) {
            Logger.getLogger(BasicApi.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("From BasicApi: Failed GETALL for " + clazz.getName());
        return null;
    }

    protected T getObject(String endpoint, Class<T> clazz) {
        try {
            Response response = getRequest(endpoint);
            String json = response.body().string();
            System.out.println(json);
            if (response.isSuccessful()) {
                System.out.println("From BasicApi: Successfully GET for " + clazz.getName());
                return gson.fromJson(json, clazz);
            }
        } catch (IOException e) {
            Logger.getLogger(BasicApi.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("From BasicApi: Failed GET for " + clazz.getName());
        return null;
    }

    protected T postObject(String endpoint, T object, Class<T> clazz) {
        try {
            Response response = updateRequest(endpoint, gson.toJson(object), ApiType.POST);
            String json = response.body().string();
            System.out.println(json);
            if (response.isSuccessful()) {
                System.out.println("From BasicApi: Successfully POST");
                return gson.fromJson(json, clazz);
            }
        } catch (IOException e) {
            Logger.getLogger(BasicApi.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("From BasicApi: Failed POST");
        return null;
    }

    protected boolean postObject(String endpoint, T object) {
        try {
            Response response = updateRequest(endpoint, gson.toJson(object), ApiType.POST);
            System.out.println(gson.toJson(object));
            System.out.println(response.body().string());
            if (response.isSuccessful()) {
                System.out.println("From BasicApi: Successfully POST");
                return true;
            }
        } catch (IOException e) {
            Logger.getLogger(BasicApi.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("From BasicApi: Failed POST");
        return false;
    }

    protected boolean deleteObject(String url) {
        try {
            Response response = updateRequest(url, null, ApiType.DELETE);
            if (response.isSuccessful()) {
                return true;
            }
        } catch (IOException e) {
            Logger.getLogger(BasicApi.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    protected boolean patchObject(String url, T object) {
        try {
            Response response = updateRequest(url, gson.toJson(object), ApiType.PATCH);
            System.out.println(response.body().string());
            if (response.isSuccessful()) {
                System.out.println("From BasicApi: Successfully PATCH");
                return true;
            }
        } catch (IOException e) {
            Logger.getLogger(BasicApi.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("From BasicApi: Failed PATCH");
        return false;
    }
}
