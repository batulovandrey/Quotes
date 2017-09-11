package com.github.batulovandrey.quotes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Andrey Batulov on 04/09/2017
 */

public class SharedPrefManager {

    private static final String QUOTES_COUNT = "quotes_count";
    private static final String IS_FAMOUS_CHECKED = "is_famous_checked";

    private SharedPreferences mSharedPreferences;

    public SharedPrefManager(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    /**
     * Method to check network accessibility
     *
     * @param context Context
     * @return true if there is connection, false otherwise
     */
    public boolean hasConnection(Context context) {
        boolean isConnected = false;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED) {
            isConnected = true;
        }
        return isConnected;
    }

    public int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }

    public void writeQuotesCount(String quotesCount) {
        int count;
        if (quotesCount == null || quotesCount.equals("0"))
            count = 1;
        else
            count = Integer.parseInt(quotesCount);

        if (count < 0)
            count = 1;
        else if (count > 10)
            count = 10;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(QUOTES_COUNT, count);
        editor.apply();
    }

    public int readQuotesCount() {
        int defaultValue = 10;
        return mSharedPreferences.getInt(QUOTES_COUNT, defaultValue);
    }

    public void writeIsFamousChecked(boolean isChecked) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(IS_FAMOUS_CHECKED, isChecked);
        editor.apply();
    }

    public boolean readIsFamousChecked() {
        boolean defaultValue = true;
        return mSharedPreferences.getBoolean(IS_FAMOUS_CHECKED, defaultValue);
    }

    private SharedPreferences.Editor getEditor(Activity activity) {
        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        return preferences.edit();
    }
}