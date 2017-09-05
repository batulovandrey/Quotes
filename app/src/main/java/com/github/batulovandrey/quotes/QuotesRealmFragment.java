package com.github.batulovandrey.quotes;

import android.content.Context;

/**
 * @author Andrey Batulov on 04/09/2017
 */

public class QuotesRealmFragment extends BasicFragment {

    private OnQuoteRealmClickListener mClickListener;

    public static QuotesRealmFragment newInstance() {
        return new QuotesRealmFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuoteRealmClickListener) {
            mClickListener = (OnQuoteRealmClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuoteRealmClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }

    @Override
    protected void onSwipeRefresh() {
        // TODO: 04.09.2017 setadapter from realm
        onStopRefreshing();
    }

    private void onStopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public interface OnQuoteRealmClickListener {
        void onQuoteRealmClick(String quote);
    }
}