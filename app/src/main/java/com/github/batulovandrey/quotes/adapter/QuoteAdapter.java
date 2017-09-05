package com.github.batulovandrey.quotes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.batulovandrey.quotes.R;
import com.github.batulovandrey.quotes.bean.Quote;

import java.util.List;

/**
 * @author Andrey Batulov on 05/09/2017
 */

public class QuoteAdapter extends RecyclerView.Adapter<QuoteViewHolder> {

    private List<Quote> mQuotes;
    private QuotesClickListener mListener;

    public QuoteAdapter(List<Quote> quotes, QuotesClickListener listener) {
        mQuotes = quotes;
        mListener = listener;
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, null);
        return new QuoteViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(QuoteViewHolder holder, int position) {
        Quote item = mQuotes.get(position);
        holder.setQuoteTextViewValue(item.getQuote());
        holder.setAuthorTextViewValue(item.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }
}