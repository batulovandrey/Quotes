package com.github.batulovandrey.quotes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.batulovandrey.quotes.R;

/**
 * @author Andrey Batulov on 05/09/2017
 */

public class QuoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private QuotesClickListener mListener;
    private TextView mQuoteTextView;
    private TextView mAuthorTextView;

    public QuoteViewHolder(View itemView, QuotesClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        mListener = listener;
        mQuoteTextView = (TextView) itemView.findViewById(R.id.quote_text_view);
        mAuthorTextView = (TextView) itemView.findViewById(R.id.author_text_view);
    }

    @Override
    public void onClick(View v) {
        mListener.onQuoteClick(getLayoutPosition());
    }

    public void setQuoteTextViewValue(String text) {
        mQuoteTextView.setText(text);
    }

    public void setAuthorTextViewValue(String text) {
        mAuthorTextView.setText(text);
    }
}