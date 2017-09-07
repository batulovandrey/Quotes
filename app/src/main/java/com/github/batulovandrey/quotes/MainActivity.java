package com.github.batulovandrey.quotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.github.batulovandrey.quotes.adapter.QuotesPagerAdapter;
import com.github.batulovandrey.quotes.bean.Quote;
import com.github.batulovandrey.quotes.utils.Utils;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements
        QuotesRealmFragment.OnQuoteRealmClickListener,
        QuotesServerFragment.OnQuoteServerClickListener {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private QuotesPagerAdapter mPagerAdapter;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getInstance(this);
        mPagerAdapter = new QuotesPagerAdapter(getSupportFragmentManager());
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            showSettingsDialog();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.exit_app)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onQuoteServerClick(View view, final Quote quote) {
        Snackbar.make(view, R.string.add_quote, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addQuoteToFav(quote);
                    }
                }).show();
    }

    @Override
    public void onQuoteRealmClick(View view, final Quote quote) {
        Snackbar.make(view, R.string.remove_quote, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeQuoteFromFav(quote);
                        showMessagePullToRefresh();
                    }
                }).show();
    }

    // region private methods

    private void initUI() {
        initToolbar();
        initViewPager();
        initTabLayout();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void showSettingsDialog() {
        View view = getLayoutInflater().inflate(R.layout.settings_dialog, null);
        final TextInputEditText countEditText = (TextInputEditText) view.findViewById(R.id.category_count_edit_text);
        final RadioButton famousButton = (RadioButton) view.findViewById(R.id.famous_radio_button);
        final RadioButton moviesButton = (RadioButton) view.findViewById(R.id.movies_radio_button);

        countEditText.setText(String.valueOf(Utils.readQuotesCount(this)));
        famousButton.setChecked(Utils.readIsFamousChecked(this));
        moviesButton.setChecked(!famousButton.isChecked());

        new AlertDialog.Builder(this).setTitle(R.string.settings)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveSettings(countEditText.getText().toString(), famousButton.isChecked());
                        showMessagePullToRefresh();
                    }
                }).setView(view).show();
    }

    private void saveSettings(String count, boolean isFamousChecked) {
        Utils.writeQuotesCount(this, count);
        Utils.writeIsFamousChecked(this, isFamousChecked);
    }

    private void addQuoteToFav(Quote quote) {
        mRealm.beginTransaction();
        Quote newQuote = mRealm.createObject(Quote.class);
        newQuote.setAuthor(quote.getAuthor());
        newQuote.setQuote(quote.getQuote());
        mRealm.commitTransaction();
    }

    private void removeQuoteFromFav(final Quote quote) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Quote quote1 = realm.where(Quote.class)
                        .equalTo("quote", quote.getQuote())
                        .findFirst();
                quote1.removeFromRealm();
            }
        });
    }

    private void showMessagePullToRefresh() {
        Toast.makeText(getApplicationContext(), R.string.pull_to_refresh, Toast.LENGTH_LONG).show();
    }

    // endregion private methods
}