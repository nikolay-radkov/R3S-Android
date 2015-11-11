package com.nikolay.r3s.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nikolay.r3s.constants.database.DbConstants;
import com.nikolay.r3s.constants.database.EntriesTableConstants;
import com.nikolay.r3s.constants.database.SubscriptionsTableConstants;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EntriesTableConstants.CREATE_TABLE);
        db.execSQL(SubscriptionsTableConstants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}