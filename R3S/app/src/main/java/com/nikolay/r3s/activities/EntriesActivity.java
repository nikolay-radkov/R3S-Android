package com.nikolay.r3s.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nikolay.r3s.R;
import com.nikolay.r3s.controllers.RefreshEntriesController;
import com.nikolay.r3s.controllers.RefreshSubscriptionsController;
import com.nikolay.r3s.data.sqlite.EntriesTable;
import com.nikolay.r3s.models.Entry;
import com.nikolay.r3s.utils.EntryItemAdapter;
import com.nikolay.r3s.utils.SubscriptionItemAdapter;

import java.util.ArrayList;

public class EntriesActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private EntriesTable repository;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EntryItemAdapter itemAdapter;
    private ListView listView;
    private Integer subscriptionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                subscriptionId = null;
            } else {
                subscriptionId = extras.getInt("SUBSCRIPTION_ID");
            }
        } else {
            subscriptionId = (Integer) savedInstanceState.getSerializable("SUBSCRIPTION_ID");
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.entries_swipe_container);
        listView = (ListView) this.findViewById(R.id.entries_list_view);
        listView.setOnItemClickListener(this);

        repository = new EntriesTable(this);
        ArrayList<Entry> listData = repository.getAllBySubscriptionId(subscriptionId);
        itemAdapter = new EntryItemAdapter(this, R.layout.item_entry, listData);
        listView.setAdapter(itemAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(EntriesActivity.this, EntryInfoActivity.class);
        int entryId = (int)view.findViewById(R.id.lblEntryTitle).getTag();
        intent.putExtra("ENTRY_ID", entryId);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        RefreshEntriesController refresher = new RefreshEntriesController(EntriesActivity.this,
                itemAdapter, swipeRefreshLayout, subscriptionId);
        refresher.execute();
    }
}