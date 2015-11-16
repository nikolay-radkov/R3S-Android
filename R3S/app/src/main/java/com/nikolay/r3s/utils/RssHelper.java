package com.nikolay.r3s.utils;

import android.content.Context;

import com.nikolay.r3s.data.sqlite.EntriesTable;
import com.nikolay.r3s.data.sqlite.SubscriptionsTable;
import com.nikolay.r3s.models.Entry;
import com.nikolay.r3s.models.Subscription;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class RssHelper {
    public static boolean load(Context context, String url) {
        InputStream is = HttpHelper.getRequestStream(url);

        if (is != null) {
            Subscription subscription = XmlParserHelper.parse(is);

            if (subscription != null) {
                SubscriptionsTable subscriptions = new SubscriptionsTable(context);
                EntriesTable entries = new EntriesTable(context);

                subscription.setRss(url);
                subscription.setUpdatedAt(new Date().toString());
                int subscriptionId = subscriptions.insert(subscription);

                for (Entry entry : subscription.getEntries()) {
                    entry.setSubscriptionId(subscriptionId);
                    entries.insert(entry);
                }

                return true;
            }
        }

        return false;
    }


    public static boolean refresh(Context context, String url, int id) {
        InputStream is = HttpHelper.getRequestStream(url);

        if (is != null) {
            Subscription subscription = XmlParserHelper.parse(is);

            if (subscription != null) {
                SubscriptionsTable subscriptions = new SubscriptionsTable(context);
                EntriesTable entries = new EntriesTable(context);

                subscription.setRss(url);
                subscription.setId(id);
                subscription.setUpdatedAt(new Date().toString());
                subscriptions.update(subscription);

                entries.deleteAllBySubscriptionId(id);

                for (Entry entry : subscription.getEntries()) {
                    entry.setSubscriptionId(id);
                    entries.insert(entry);
                }

                return true;
            }
        }

        return false;
    }


    public static boolean refreshAll(Context context) {
        boolean isRefreshSuccessful = true;
        SubscriptionsTable table = new SubscriptionsTable(context);
        ArrayList<Subscription> subscriptions = table.getAll();

        for (Subscription s: subscriptions) {
            String url = s.getRss();
            int id = s.getId();
            boolean isSuccessful = RssHelper.refresh(context, url, id);

            if (!isSuccessful) {
                isRefreshSuccessful = false;
            }
        }

        return isRefreshSuccessful;
    }
}
