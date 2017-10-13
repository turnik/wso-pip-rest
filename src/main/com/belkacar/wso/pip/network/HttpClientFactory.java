package com.belkacar.wso.pip.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class HttpClientFactory {

    private static final int TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;
    private static final int CONNECT_TIMEOUT = 10;

    private static OkHttpClient client = buildHttpClient();

    private static final Gson GSON = new GsonBuilder().create();

    public static HttpService getHttpService(String baseUrl) {
        return buildRetrofit(baseUrl)
                .create(HttpService.class);
    }

    private static Retrofit buildRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .build();
    }

    private static HttpUrl buildBaseUrl(String scheme, String host, String apiVersion) {
        return new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .addEncodedPathSegment(apiVersion)
                .build();
    }

    private static OkHttpClient buildHttpClient() {

        /*
          You can use addInterceptor() for logging including
          addInterceptor(new HttpLoggingInterceptor()
               .setLevel({yourConfig}.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
         */
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}
