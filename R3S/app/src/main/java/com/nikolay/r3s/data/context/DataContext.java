package com.nikolay.r3s.data.context;

import com.nikolay.r3s.activities.SubscribeActivity;
import com.nikolay.r3s.constants.RepositoryTypes;
import com.nikolay.r3s.models.Entry;
import com.nikolay.r3s.models.IModel;
import com.nikolay.r3s.models.Subscription;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class DataContext {
    private static DataContext context;

    public static DataContext getInstance() {
        if (context == null) {
            context = new DataContext();
        }

        return context;
    }

    private Hashtable subscriptions;
    private Hashtable entries;

    private DataContext() {
        this.subscriptions = new Hashtable();
        this.entries = new Hashtable();

//        subscriptions.put("1", new Subscription("IronMan", null, null, new Date().toString()));
//        subscriptions.put("2", new Subscription("Fast and Furious", null, null, new Date().toString()));
//        subscriptions.put("3", new Subscription("Avengers", null, null, new Date().toString()));
    }

    public <T extends IModel> Hashtable getSet(Class<T> type) {
        if (type == Entry.class) {
            return entries;
        } else if (type == Subscription.class) {
            return  subscriptions;
        }

        return null;
    }
}
