package com.github.batulovandrey.quotes.dagger;

import android.app.Application;

/**
 * @author Andrey Batulov on 10/09/2017
 */

public class QuoteApplication extends Application {

    private static final String BASE_URL = "https://andruxnet-random-famous-quotes.p.mashape.com";

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}