package com.github.batulovandrey.quotes.dagger;

import com.github.batulovandrey.quotes.MainActivity;
import com.github.batulovandrey.quotes.QuotesRealmFragment;
import com.github.batulovandrey.quotes.QuotesServerFragment;
import com.github.batulovandrey.quotes.widget.QuoteWidgetProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Andrey Batulov on 10/09/2017
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(MainActivity activity);

    void inject(QuotesRealmFragment fragment);

    void inject(QuotesServerFragment fragment);

    void inject(QuoteWidgetProvider provider);
}