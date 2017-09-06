package com.github.batulovandrey.quotes;

import android.content.DialogInterface;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements
        QuotesRealmFragment.OnQuoteRealmClickListener,
        QuotesServerFragment.OnQuoteServerClickListener {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private QuotesPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void onQuoteServerClick(Quote quote) {
        Toast.makeText(getApplicationContext(), "quote is " + quote.getQuote(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onQuoteRealmClick(String quote) {
        // TODO: 04.09.2017 delete from realm
    }

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
                    }
                }).setView(view).show();
    }

    private void saveSettings(String count, boolean isFamousChecked) {
        Utils.writeQuotesCount(this, count);
        Utils.writeIsFamousChecked(this, isFamousChecked);
    }
}