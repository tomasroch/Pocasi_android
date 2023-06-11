package com.pocasi_android.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestHttpClient {


    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient
            = new OkHttpClient.Builder();

    /**
     * For basic use
     * @param serviceClass
     * @return
     * @param <S>
     */
    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) { // tady je check aby tam nebyl interceptor 2x
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    /**
     * logging
     */
    private static HttpLoggingInterceptor logging
            = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * for use with authentication (jwt etc.)
     * @param serviceClass
     * @param token
     * @return
     * @param <S>
     */
    public static <S> S createService(Class<S> serviceClass, final String token) {
        if (token != null) {
            httpClient.interceptors().clear();
            httpClient.addInterceptor( chain -> {
                Request original = chain.request();
                Request.Builder builder1 = original.newBuilder()
                                                   .header("Authorization", token);
                Request request = builder1.build();
                return chain.proceed(request);
            });
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

}
