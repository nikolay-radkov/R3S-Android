package com.nikolay.r3s.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nikolay.r3s.R;
import com.nikolay.r3s.controllers.Message;
import com.nikolay.r3s.controllers.NetworkManager;
import com.nikolay.r3s.controllers.RefreshEntriesController;
import com.nikolay.r3s.controllers.RefreshSubscriptionsController;
import com.nikolay.r3s.controllers.ShakeManager;
import com.nikolay.r3s.data.sqlite.EntriesTable;
import com.nikolay.r3s.models.Entry;
import com.nikolay.r3s.utils.EntryItemAdapter;
import com.nikolay.r3s.utils.SubscriptionItemAdapter;

import java.util.ArrayList;

public class EntriesActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener{
    private EntriesTable repository;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EntryItemAdapter itemAdapter;
    private ListView listView;
    private Integer subscriptionId = 0;
    private String toolbarTitle = "Entries";
    private ShakeManager shakeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                subscriptionId = null;
            } else {
                subscriptionId = extras.getInt("SUBSCRIPTION_ID");
                toolbarTitle = extras.getString("SUBSCRIPTION_NAME");
            }
        } else {
            subscriptionId = (Integer) savedInstanceState.getSerializable("SUBSCRIPTION_ID");
            toolbarTitle = (String) savedInstanceState.getSerializable("SUBSCRIPTION_NAME");
        }

        getSupportActionBar().setTitle(toolbarTitle);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.entries_swipe_container);
        listView = (ListView) this.findViewById(R.id.entries_list_view);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);

        repository = new EntriesTable(this);
        ArrayList<Entry> listData = repository.getAllBySubscriptionId(subscriptionId);
        itemAdapter = new EntryItemAdapter(this, R.layout.item_entry, listData);
        listView.setAdapter(itemAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        shakeManager = new ShakeManager(EntriesActivity.this, swipeRefreshLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(EntriesActivity.this, ContentActivity.class);

        intent.putExtra("POSITION", position);
        intent.putExtra("SUBSCRIPTION_ID", subscriptionId);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        boolean isConnectedToNetwork = NetworkManager.checkNetworkConnection(this.getApplication());
        Message message = new Message(EntriesActivity.this);

        if (isConnectedToNetwork) {
            RefreshEntriesController refresher = new RefreshEntriesController(EntriesActivity.this,
                    itemAdapter, swipeRefreshLayout, subscriptionId);
            refresher.execute();
        } else {
            message.print("No internet connection");
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int topRowVerticalPosition =
                (listView == null || listView.getChildCount() == 0) ?
                        0 : listView.getChildAt(0).getTop();
        swipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shakeManager.registerListener();
    }

    @Override
    protected void onPause() {
        shakeManager.removeListener();
        super.onPause();
    }
}