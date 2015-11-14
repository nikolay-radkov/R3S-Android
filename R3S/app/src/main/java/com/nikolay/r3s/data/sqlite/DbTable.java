package com.nikolay.r3s.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nikolay.r3s.constants.database.EntriesTableConstants;
import com.nikolay.r3s.constants.database.SubscriptionsTableConstants;

public abstract class DbTable {
    protected SQLiteHelper sqLiteHelper;

    public DbTable(Context context) {
        this.sqLiteHelper = new SQLiteHelper(context);
//        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
////        db.execSQL(EntriesTableConstants.DROP_TABLE);
////        db.execSQL(SubscriptionsTableConstants.DROP_TABLE);
//        db.execSQL(EntriesTableConstants.CREATE_TABLE);
//        db.execSQL(SubscriptionsTableConstants.CREATE_TABLE);
    }

    public void close() {
        sqLiteHelper.close();
    }
}
