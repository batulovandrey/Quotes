package com.github.batulovandrey.quotes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.batulovandrey.quotes.adapter.QuoteAdapter;
import com.github.batulovandrey.quotes.adapter.QuotesClickListener;
import com.github.batulovandrey.quotes.bean.Quote;

import java.util.ArrayList;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(new QuoteAdapter(new ArrayList<Quote>(), new QuotesClickListener() {
            @Override
            public void onQuoteClick(int position) {

            }
        }));
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