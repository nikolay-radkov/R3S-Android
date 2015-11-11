package com.nikolay.r3s.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nikolay.r3s.constants.database.SubscriptionsTableConstants;
import com.nikolay.r3s.models.IModel;
import com.nikolay.r3s.models.Subscription;

import java.util.ArrayList;

public class SubscriptionsTable extends DbTable{

    public SubscriptionsTable(Context context) {
        super(context);
    }

    public int insert(Subscription subscription) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_NAME, subscription.getName());
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_FAVICON, subscription.getFavicon());
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_URL, subscription.getUrl());
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_UPDATED_AT, subscription.getUpdatedAt());
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_DESCRIPTION, subscription.getDescription());

        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        int id = (int)db.insert(SubscriptionsTableConstants.TABLE_NAME, null, contentValues);
        return id;
    }


    public boolean update(Subscription subscription) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_NAME, subscription.getName());
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_FAVICON, subscription.getFavicon());
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_URL, subscription.getUrl());
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_UPDATED_AT, subscription.getUpdatedAt());
        contentValues.put(SubscriptionsTableConstants.COLUMN_NAME_DESCRIPTION, subscription.getDescription());

        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        Integer id = new Integer(subscription.getId());
        db.update(SubscriptionsTableConstants.TABLE_NAME,
                contentValues,
                "id = ? ",
                new String[]{Integer.toString(id)});
        return true;
    }

    public Integer delete(Integer id) {
        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        return db.delete(SubscriptionsTableConstants.TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<Subscription> getAll() {
        ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();

        SQLiteDatabase db = this.sqLiteHelper.getReadableDatabase();
        Cursor res = db.rawQuery(SubscriptionsTableConstants.GET_DATA, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Subscription subscription = new Subscription();

            subscription.setName(
                    res.getString(
                            res.getColumnIndex(
                                    SubscriptionsTableConstants.COLUMN_NAME_NAME
                            )
                    )
            );

            subscription.setFavicon(
                    res.getString(
                            res.getColumnIndex(
                                    SubscriptionsTableConstants.COLUMN_NAME_FAVICON
                            )
                    )
            );

            subscription.setDescription(
                    res.getString(
                            res.getColumnIndex(
                                    SubscriptionsTableConstants.COLUMN_NAME_DESCRIPTION
                            )
                    )
            );

            subscription.setId(
                    res.getInt(
                            res.getColumnIndex(
                                    SubscriptionsTableConstants.COLUMN_NAME_ID
                            )
                    )
            );

            subscription.setUpdatedAt(
                    res.getString(
                            res.getColumnIndex(
                                    SubscriptionsTableConstants.COLUMN_NAME_UPDATED_AT
                            )
                    )
            );

            subscriptions.add(subscription);
            res.moveToNext();
        }

        return subscriptions;
    }
}
