package com.github.batulovandrey.quotes.net;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Andrey Batulov on 04/09/2017
 */

public class ApiClient {

    private static final String BASE_URL = "https://andruxnet-random-famous-quotes.p.mashape.com";
    private static Retrofit sRetrofit = null;

    private ApiClient() {
        throw new IllegalStateException("can't create object");
    }

    public static Retrofit getClient() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}