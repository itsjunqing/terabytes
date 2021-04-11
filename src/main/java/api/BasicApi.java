package api;

import com.google.gson.Gson;
import okhttp3.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicApi<T> {
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
        this.gson = new Gson(); // need to be replaced


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

    private String getRequest(String url) {
        Request request = buildRequest(url, null, ApiType.GET);
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            Logger.getLogger(BasicApi.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private boolean updateRequest(String url, String json, ApiType type) {
        Request request = buildRequest(url, json, type);
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return true;
            }
        } catch (IOException e) {
            Logger.getLogger(BasicApi.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    protected List<T> getAllObjects(String endpoint, Class<T[]> clazz) {
        String json = getRequest(endpoint);
//        below method doesn't work
//        Type arrType = new TypeToken<ArrayList<T>>(){}.getType();
//        System.out.println(arrType);
//        System.out.println(clazz);
        return Arrays.asList(gson.fromJson(json, clazz));
    }

    protected T getObject(String endpoint, Class<T> clazz) {
        return gson.fromJson(getRequest(endpoint), clazz);
    }

    protected boolean postObject(String endpoint, T object) {
        return updateRequest(endpoint, gson.toJson(object), ApiType.POST);
    }

    protected boolean deleteObject(String url) {
        return updateRequest(url, null, ApiType.DELETE);
    }

    protected boolean patchObject(String url, T object) {
        return updateRequest(url, gson.toJson(object), ApiType.PATCH);
    }




}
