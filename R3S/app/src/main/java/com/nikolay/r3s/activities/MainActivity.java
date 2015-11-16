package com.nikolay.r3s.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.nikolay.r3s.R;
import com.nikolay.r3s.controllers.Message;
import com.nikolay.r3s.controllers.NetworkManager;
import com.nikolay.r3s.controllers.RefreshSubscriptionsController;
import com.nikolay.r3s.data.sqlite.SubscriptionsTable;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.SubscriptionItemAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,
        SwipeMenuListView.OnMenuItemClickListener,
        AbsListView.OnScrollListener{
    private SubscriptionsTable repository;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuListView listView;
    private SubscriptionItemAdapter itemAdapter;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Subscriptions");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setImageResource(R.drawable.ic_subscribe);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.subscriptions_swipe_container);
        listView = (SwipeMenuListView) this.findViewById(R.id.subscription_list_view);
        listView.setOnItemClickListener(this);

        repository = new SubscriptionsTable(this);
        ArrayList<Subscription> listData = repository.getAll();
        itemAdapter = new SubscriptionItemAdapter(this, R.layout.item_subscription, listData);
        listView.setAdapter(itemAdapter);
        listView.setOnScrollListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(120);
                deleteItem.setIcon(R.mipmap.ic_trash);
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        listView.setOnMenuItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
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
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                ArrayList<Subscription> listData = repository.getAll();
                itemAdapter = new SubscriptionItemAdapter(this, R.layout.item_subscription, listData);
                itemAdapter.clear();
                itemAdapter.addAll(listData);
                itemAdapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public void onRefresh() {
        boolean isConnectedToNetwork = NetworkManager.checkNetworkConnection(this.getApplication());
        Message message = new Message(MainActivity.this);

        if (isConnectedToNetwork) {
            RefreshSubscriptionsController refresher = new RefreshSubscriptionsController(MainActivity.this, itemAdapter, mSwipeRefreshLayout);
            refresher.execute();
        } else {
            message.print("No internet connection");
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, EntriesActivity.class);
        int subscriptionId = itemAdapter.getItem(position).getId();
        String subscriptionName = itemAdapter.getItem(position).getName();

        intent.putExtra("SUBSCRIPTION_ID", subscriptionId);
        intent.putExtra("SUBSCRIPTION_NAME", subscriptionName);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        final int itemPosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Deleting item");
        alert.setMessage("Are you sure you want to delete this RSS");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int subscriptionId = itemAdapter.getItem(itemPosition).getId();
                int result = repository.delete(subscriptionId);
                Message message = new Message(MainActivity.this);

                if (result == 0) {
                    message.print("Cannot delete the RSS");
                    return;
                }

                itemAdapter.remove(itemAdapter.getItem(itemPosition));
                itemAdapter.notifyDataSetChanged();
                message.print("RSS deleted successfully");

                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int topRowVerticalPosition =
                (listView == null || listView.getChildCount() == 0) ?
                        0 : listView.getChildAt(0).getTop();
        mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
    }
}
