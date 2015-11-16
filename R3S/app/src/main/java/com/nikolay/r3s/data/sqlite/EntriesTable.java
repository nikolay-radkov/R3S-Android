package com.nikolay.r3s.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nikolay.r3s.constants.database.EntriesTableConstants;
import com.nikolay.r3s.constants.database.SubscriptionsTableConstants;
import com.nikolay.r3s.models.Entry;

import java.util.ArrayList;
import java.util.Hashtable;

public class EntriesTable extends DbTable {

    public EntriesTable(Context context){
        super(context);
    }

    public boolean insert(Entry entry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntriesTableConstants.COLUMN_NAME_TITLE, entry.getTitle());
        contentValues.put(EntriesTableConstants.COLUMN_NAME_CREATED_AT, entry.getCreatedAt());
        contentValues.put(EntriesTableConstants.COLUMN_NAME_CONTENT, entry.getContent());
        contentValues.put(EntriesTableConstants.COLUMN_NAME_SUBSCRIPTION_ID, entry.getSubscriptionId());
        contentValues.put(EntriesTableConstants.COLUMN_NAME_LINK, entry.getLink());

        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        db.insert(EntriesTableConstants.TABLE_NAME, null, contentValues);


        return true;
    }
    public boolean update(Entry entry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntriesTableConstants.COLUMN_NAME_TITLE, entry.getTitle());
        contentValues.put(EntriesTableConstants.COLUMN_NAME_CREATED_AT, entry.getCreatedAt());
        contentValues.put(EntriesTableConstants.COLUMN_NAME_CONTENT, entry.getContent());
        contentValues.put(EntriesTableConstants.COLUMN_NAME_SUBSCRIPTION_ID, entry.getSubscriptionId());
        contentValues.put(EntriesTableConstants.COLUMN_NAME_LINK, entry.getLink());

        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        Integer id = new Integer(entry.getId());
        db.update(EntriesTableConstants.TABLE_NAME,
                contentValues,
                "id = ? ",
                new String[]{Integer.toString(id)});


        return true;
    }

    public Integer delete(Integer id) {
        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        Integer result = db.delete(EntriesTableConstants.TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});


        return result;
    }

    public ArrayList<Entry> getAllBySubscriptionId(Integer id) {
        ArrayList<Entry> entries = new ArrayList<Entry>();

        SQLiteDatabase db = this.sqLiteHelper.getReadableDatabase();
        Cursor res = db.rawQuery(EntriesTableConstants.GET_DATA + EntriesTableConstants.WHERE_CLAUSE_SUBSCRIPTION_ID + id, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Entry entry = new Entry();

            entry.setTitle(
                    res.getString(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_TITLE
                            )
                    )
            );

            entry.setContent(
                    res.getString(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_CONTENT
                            )
                    )
            );

            entry.setLink(
                    res.getString(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_LINK
                            )
                    )
            );

            entry.setId(
                    res.getInt(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_ID
                            )
                    )
            );

            entry.setCreatedAt(
                    res.getString(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_CREATED_AT
                            )
                    )
            );

            entry.setSubscriptionId(
                    res.getInt(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_SUBSCRIPTION_ID
                            )
                    )
            );

            entries.add(entry);
            res.moveToNext();
        }


        return entries;
    }

    public Entry getById(int id) {
        Entry entry = new Entry();

        SQLiteDatabase db = this.sqLiteHelper.getReadableDatabase();
        Cursor res = db.rawQuery(EntriesTableConstants.GET_DATA + EntriesTableConstants.WHERE_CLAUSE_ID + id, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            entry.setTitle(
                    res.getString(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_TITLE
                            )
                    )
            );

            entry.setContent(
                    res.getString(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_CONTENT
                            )
                    )
            );

            entry.setLink(
                    res.getString(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_LINK
                            )
                    )
            );

            entry.setId(
                    res.getInt(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_ID
                            )
                    )
            );

            entry.setCreatedAt(
                    res.getString(
                            res.getColumnIndex(
                                    EntriesTableConstants.COLUMN_NAME_CREATED_AT
                            )
                    )
            );

            res.moveToNext();
        }


        return entry;
    }


    public Integer deleteAllBySubscriptionId(int id) {
        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        Integer result = db.delete(EntriesTableConstants.TABLE_NAME,
                "subscriptionId = ? ",
                new String[]{Integer.toString(id)});



        return result;
    }
}
