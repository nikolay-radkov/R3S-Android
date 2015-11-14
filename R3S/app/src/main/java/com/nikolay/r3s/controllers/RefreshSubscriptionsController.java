package com.nikolay.r3s.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.nikolay.r3s.data.sqlite.SubscriptionsTable;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.RssHelper;
import com.nikolay.r3s.utils.SubscriptionItemAdapter;

import java.util.ArrayList;

/**
 * Created by Nikolay on 13-Nov-15.
 */
public class RefreshSubscriptionsController extends AsyncTask<Boolean, Void, Boolean> {
    private Context context;
    private SubscriptionItemAdapter itemAdapter;
    private SubscriptionsTable table;
    private SwipeRefreshLayout swipeRefreshLayout;

    public RefreshSubscriptionsController(Context context, SubscriptionItemAdapter itemAdapter,
                                          SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.itemAdapter = itemAdapter;
        this.table = new SubscriptionsTable(this.context);
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        boolean isRefreshSuccessful = RssHelper.refreshAll(this.context);

        return isRefreshSuccessful;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        itemAdapter.notifyDataSetChanged();
        Message message = new Message(this.context);
        if (result) {
            message.print("Refreshed");
        } else {
            message.print("Not all RSS have been refreshed");
        }

        swipeRefreshLayout.setRefreshing(false);
    }
}
