package com.github.batulovandrey.quotes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

import com.github.batulovandrey.quotes.adapter.QuoteAdapter;
import com.github.batulovandrey.quotes.adapter.QuotesClickListener;
import com.github.batulovandrey.quotes.bean.Quote;
import com.github.batulovandrey.quotes.dagger.QuoteApplication;
import com.github.batulovandrey.quotes.net.QuoteService;
import com.github.batulovandrey.quotes.utils.SharedPrefManager;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.batulovandrey.quotes.R.string;
import static com.github.batulovandrey.quotes.net.Categories.FAMOUS;
import static com.github.batulovandrey.quotes.net.Categories.MOVIES;

/**
 * @author Andrey Batulov on 04/09/2017
 */

public class QuotesServerFragment extends BasicFragment implements QuotesClickListener {

    @Inject
    QuoteService mService;

    @Inject
    SharedPrefManager mPrefManager;

    private OnQuoteServerClickListener mClickListener;

    public static QuotesServerFragment newInstance() {
        return new QuotesServerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((QuoteApplication) getActivity().getApplication())
                .getNetComponent()
                .inject(this);
        mClickListener = (OnQuoteServerClickListener) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromServer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }

    @Override
    protected void onSwipeRefresh() {
        getDataFromServer();
        onStopRefreshing();
    }

    @Override
    public void onQuoteClick(View view, int position) {
        if (mClickListener != null) {
            mClickListener.onQuoteServerClick(view, mQuotes.get(position));
        }
    }

    // region private methods

    private void getDataFromServer() {
        String category = mPrefManager.readIsFamousChecked() ? FAMOUS : MOVIES;
        int count = mPrefManager.readQuotesCount();
        if (mPrefManager.hasConnection(getContext())) {
            if (count == 1) {
                getOneQuote(category);
            } else {
                getQuotes(category, count);
            }
        } else {
            handleFailure(string.no_connection);
        }
    }

    private void getOneQuote(String category) {
        Call<Quote> call = mService.getQuote(category, 1);
        call.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(@NonNull Call<Quote> call, @NonNull Response<Quote> response) {
                Quote quote = response.body();
                if (quote != null) {
                    mQuotes = Collections.singletonList(quote);
                    showData();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Quote> call, @NonNull Throwable t) {
                handleFailure(string.error_data);
            }
        });
    }

    private void getQuotes(String category, int count) {
        Call<List<Quote>> listCall = mService.getQuotesList(category, count);
        listCall.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(@NonNull Call<List<Quote>> call, @NonNull Response<List<Quote>> response) {
                List<Quote> responseList = response.body();
                if (responseList != null && !responseList.isEmpty()) {
                    mQuotes = responseList;
                    showData();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Quote>> call, @NonNull Throwable t) {
                handleFailure(string.error_data);
            }
        });
    }

    private void handleFailure(@StringRes int message) {
        onStopRefreshing();
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showData() {
        fillAdapter();
    }

    private void fillAdapter() {
        mAdapter = new QuoteAdapter(mQuotes, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onStopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    // endregion private methods

    public interface OnQuoteServerClickListener {
        void onQuoteServerClick(View view, Quote quote);
    }
}