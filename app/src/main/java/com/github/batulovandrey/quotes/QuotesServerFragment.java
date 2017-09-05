package com.github.batulovandrey.quotes;

import android.content.Context;

/**
 * @author Andrey Batulov on 04/09/2017
 */

public class QuotesServerFragment extends BasicFragment {

    private OnQuoteServerClickListener mClickListener;

    public static QuotesServerFragment newInstance() {
        return new QuotesServerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuoteServerClickListener) {
            mClickListener = (OnQuoteServerClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuoteServerClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }

    @Override
    protected void onSwipeRefresh() {
        // TODO: 04.09.2017 setadapter from list of objects from server
        onStopRefreshing();
    }

    private void onStopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public interface OnQuoteServerClickListener {
        void onQuoteServerClick(int position);
    }
}