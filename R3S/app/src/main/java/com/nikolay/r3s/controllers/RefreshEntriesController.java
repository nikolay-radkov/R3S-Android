package com.nikolay.r3s.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.nikolay.r3s.data.sqlite.EntriesTable;
import com.nikolay.r3s.data.sqlite.SubscriptionsTable;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.EntryItemAdapter;
import com.nikolay.r3s.utils.RssHelper;
import com.nikolay.r3s.utils.SubscriptionItemAdapter;

public class RefreshEntriesController extends AsyncTask<Boolean, Integer, Boolean> {
    private Context context;
    private EntryItemAdapter itemAdapter;
    private EntriesTable table;
    private SubscriptionsTable subscriptionsTable;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int subscriptionId;

    public RefreshEntriesController(Context context, EntryItemAdapter itemAdapter,
                                          SwipeRefreshLayout swipeRefreshLayout,
                                    int subscriptionId) {
        this.context = context;
        this.itemAdapter = itemAdapter;
        this.table = new EntriesTable(this.context);
        this.subscriptionsTable = new SubscriptionsTable(this.context);
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.subscriptionId = subscriptionId;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
       Subscription subscription = subscriptionsTable.getById(this.subscriptionId);

        boolean isRefreshSuccessful = RssHelper.refresh(this.context, subscription.getRss(), this.subscriptionId);

        return isRefreshSuccessful;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        itemAdapter.notifyDataSetChanged();
        Message message = new Message(this.context);
        if (result) {
            message.print("RSS successfully refreshed");
        } else {
            message.print("RSS not refreshed");
        }

        swipeRefreshLayout.setRefreshing(false);
    }
}
