package com.github.batulovandrey.quotes.net;

import com.github.batulovandrey.quotes.bean.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author Andrey Batulov on 04/09/2017
 */

public interface QuoteService {

    @Headers("X-Mashape-Key: 3eH3ER7RlbmshgOtLgeZ2Jsg0Wm8p1iIxaIjsngr7Gf4Ge0d3h")
    @GET("/")
    Call<List<Quote>> getQuotesList(@Query("cat") @Categories.Category String cat,
                                    @Query("count") int count);

    @Headers("X-Mashape-Key: 3eH3ER7RlbmshgOtLgeZ2Jsg0Wm8p1iIxaIjsngr7Gf4Ge0d3h")
    @GET("/")
    Call<Quote> getQuote(@Query("cat") @Categories.Category String cat,
                         @Query("count") int count);
}