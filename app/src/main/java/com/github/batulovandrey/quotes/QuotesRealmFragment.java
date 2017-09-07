package com.github.batulovandrey.quotes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.batulovandrey.quotes.adapter.QuoteAdapter;
import com.github.batulovandrey.quotes.adapter.QuotesClickListener;
import com.github.batulovandrey.quotes.bean.Quote;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * @author Andrey Batulov on 04/09/2017
 */

public class QuotesRealmFragment extends BasicFragment implements QuotesClickListener {

    private OnQuoteRealmClickListener mClickListener;
    private Realm mRealm;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getInstance(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillAdapter();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }

    @Override
    public void onQuoteClick(View view, int position) {
        if (mClickListener != null) {
            mClickListener.onQuoteRealmClick(view, mQuotes.get(position));
        }
    }

    @Override
    protected void onSwipeRefresh() {
        fillAdapter();
        onStopRefreshing();
    }

    // region private methods

    private void onStopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void fillAdapter() {
        mQuotes = getQuoteList();
        mAdapter = new QuoteAdapter(mQuotes, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Quote> getQuoteList() {
        List<Quote> list = new ArrayList<>();
        list.addAll(mRealm.allObjects(Quote.class));
        return list;
    }

    // endregion private methods

    public interface OnQuoteRealmClickListener {
        void onQuoteRealmClick(View view, Quote quote);
    }
}