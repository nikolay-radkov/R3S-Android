package com.nikolay.r3s.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nikolay.r3s.R;
import com.nikolay.r3s.data.sqlite.SubscriptionsTable;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.SubscriptionItemAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private SubscriptionsTable repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        repository = new SubscriptionsTable(this);
        ArrayList<Subscription> listData = repository.getAll();

        ListView listView = (ListView) this.findViewById(R.id.subscriptionsListView);
        listView.setOnItemClickListener(this);
        SubscriptionItemAdapter itemAdapter = new SubscriptionItemAdapter(this, R.layout.item_subscription, listData);
        listView.setAdapter(itemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SubscribeActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, EntriesActivity.class);
        int subscriptionId = (int) view.findViewById(R.id.postTitleLabel).getTag();
        intent.putExtra("SUBSCRIPTION_ID", subscriptionId);
        startActivity(intent);
    }
}
