package com.github.batulovandrey.quotes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.batulovandrey.quotes.adapter.QuoteAdapter;
import com.github.batulovandrey.quotes.adapter.QuotesClickListener;
import com.github.batulovandrey.quotes.bean.Quote;
import com.github.batulovandrey.quotes.dagger.QuoteApplication;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * @author Andrey Batulov on 04/09/2017
 */

public class QuotesRealmFragment extends BasicFragment implements QuotesClickListener {

    @Inject
    Realm mRealm;

    private OnQuoteRealmClickListener mClickListener;

    public static QuotesRealmFragment newInstance() {
        return new QuotesRealmFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((QuoteApplication) getActivity().getApplication())
                .getNetComponent()
                .inject(this);
        mClickListener = (OnQuoteRealmClickListener) getActivity();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            fillAdapter();
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

    public void fillAdapter() {
        mQuotes = getQuoteList();
        mAdapter = new QuoteAdapter(mQuotes, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Quote> getQuoteList() {
        return mRealm.allObjects(Quote.class);
    }

    // endregion private methods

    public interface OnQuoteRealmClickListener {
        void onQuoteRealmClick(View view, Quote quote);
    }
}