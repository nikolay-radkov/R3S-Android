package com.nikolay.r3s.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.nikolay.r3s.R;
import com.nikolay.r3s.controllers.RefreshSubscriptionsController;
import com.nikolay.r3s.data.sqlite.SubscriptionsTable;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.SubscriptionItemAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,
        SwipeMenuListView.OnMenuItemClickListener {
    private SubscriptionsTable repository;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuListView listView;
    private SubscriptionItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        listView = (SwipeMenuListView) this.findViewById(R.id.subscription_list_wiew);
        listView.setOnItemClickListener(this);

        repository = new SubscriptionsTable(this);
        ArrayList<Subscription> listData = repository.getAll();
        itemAdapter = new SubscriptionItemAdapter(this, R.layout.item_subscription, listData);
        listView.setAdapter(itemAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(90);
                deleteItem.setIcon(R.drawable.com_facebook_close);
                menu.addMenuItem(deleteItem);
            }
        };


        listView.setMenuCreator(creator);

        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        listView.setOnMenuItemClickListener(this);
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
    }

    @Override
    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ArrayList<Subscription> listData = repository.getAll();
//                itemAdapter = new SubscriptionItemAdapter(MainActivity.this, R.layout.item_subscription, listData);
//                listView.setAdapter(itemAdapter);
//
//                Toast.makeText(MainActivity.this, "refres", Toast.LENGTH_LONG).show();
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);
        RefreshSubscriptionsController refresher = new RefreshSubscriptionsController(MainActivity.this, itemAdapter, mSwipeRefreshLayout);
        refresher.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int subscriptionId = 0;

        Intent intent = new Intent(MainActivity.this, EntriesActivity.class);
        subscriptionId = (int) view.findViewById(R.id.postTitleLabel).getTag();
        intent.putExtra("SUBSCRIPTION_ID", subscriptionId);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

        int subscriptionId = itemAdapter.getItem(position).getId();
        repository.delete(subscriptionId);
        itemAdapter.remove(itemAdapter.getItem(position));
        itemAdapter.notifyDataSetChanged();
        return true;
    }
}
