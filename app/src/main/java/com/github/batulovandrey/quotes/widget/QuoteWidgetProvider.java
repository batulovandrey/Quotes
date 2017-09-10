package com.github.batulovandrey.quotes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.github.batulovandrey.quotes.R;
import com.github.batulovandrey.quotes.bean.Quote;
import com.github.batulovandrey.quotes.net.ApiClient;
import com.github.batulovandrey.quotes.net.Categories;
import com.github.batulovandrey.quotes.net.QuoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS;

/**
 * @author Andrey Batulov on 10/09/2017
 */

public class QuoteWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, QuoteWidgetProvider.class);
        intent.setAction(ACTION_APPWIDGET_UPDATE);
        intent.putExtra(EXTRA_APPWIDGET_IDS, appWidgetIds);
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quote_appwidget);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.refresh_image_button, pendingIntent);
        context.startService(intent);

        final AppWidgetManager manager = AppWidgetManager.getInstance(context);
        final ComponentName componentName = new ComponentName(context, QuoteWidgetProvider.class);
        manager.updateAppWidget(componentName, views);

        QuoteService service = ApiClient.getClient().create(QuoteService.class);
        Call<Quote> call = service.getQuote(Categories.FAMOUS, 1);
        call.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                Quote quote = response.body();
                if (quote != null) {
                    views.setTextViewText(R.id.quote_text_view, quote.getQuote());
                    views.setTextViewText(R.id.author_text_view, quote.getAuthor());
                    manager.updateAppWidget(componentName, views);
                }
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                views.setTextViewText(R.id.quote_text_view, "Error");
            }
        });
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}