package com.nikolay.r3s.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.nikolay.r3s.R;
import com.nikolay.r3s.data.sqlite.EntriesTable;
import com.nikolay.r3s.models.Entry;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private String toolbarTitle = "Entry info";
    private static ArrayList<Entry> entries;
    private static int currentIndex = 0;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int subscriptionId = 0;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                subscriptionId = 0;
            } else {
                currentIndex = extras.getInt("POSITION");
                subscriptionId = extras.getInt("SUBSCRIPTION_ID");
            }
        } else {
            currentIndex = (int) savedInstanceState.getSerializable("POSITION");
            subscriptionId = (int) savedInstanceState.getSerializable("SUBSCRIPTION_ID");
        }

        EntriesTable repository = new EntriesTable(this);
        entries = repository.getAllBySubscriptionId(subscriptionId);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(currentIndex);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("LOG_TAG", "success");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LOG_TAG", "error");
            }

            @Override
            public void onCancel() {
                Log.d("LOG_TAG", "cancel");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(entries.get(currentIndex).getTitle())
                        .setContentUrl(Uri.parse(entries.get(currentIndex).getLink()))
                        .build();

                shareDialog.show(linkContent);

                return true;
            }
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
        getSupportActionBar().setTitle(entries.get(position).getTitle());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return entries.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return entries.get(position).getTitle();
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_content, container, false);

            WebView webView = (WebView) rootView.findViewById(R.id.webViewEntry);
            webView.getSettings().setBuiltInZoomControls(true);

            int index = getArguments().getInt(ARG_SECTION_NUMBER);
            webView.loadData(entries.get(index).getContent(), "text/html; charset=utf-8", "utf-8");

            return rootView;
        }
    }
}
