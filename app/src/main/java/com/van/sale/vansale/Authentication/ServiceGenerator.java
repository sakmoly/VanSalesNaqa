package com.van.sale.vansale.Authentication;

import com.van.sale.vansale.Retrofit_Interface.UserInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ajmal Bin Khalid on 24-01-2018.
 */

public class ServiceGenerator {



    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static OkHttpClient.Builder setClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(httpLoggingInterceptor);

        return client;
    }


    public static Retrofit.Builder builder = new Retrofit.Builder()
            .client(setClient()
                    .addInterceptor(new BasicAuthInterceptor(UserInterface.AUTHENTICATION_USERNAME, UserInterface.AUTHENTICATION_PASSWORD))
                    .build())
            .baseUrl(UserInterface.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


}