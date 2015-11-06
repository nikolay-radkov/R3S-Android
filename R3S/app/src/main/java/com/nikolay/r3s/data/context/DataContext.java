package com.nikolay.r3s.data.context;

import com.nikolay.r3s.constants.RepositoryTypes;
import com.nikolay.r3s.models.Entry;
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



    public Hashtable getSet(RepositoryTypes type) {
        switch (type) {
            case SUBSCRIPTION:
                return  subscriptions;
            case ENTRY:
                return entries;
            default:
                return null;
        }
    }
}
