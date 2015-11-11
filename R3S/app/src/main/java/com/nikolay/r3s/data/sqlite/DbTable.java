package com.nikolay.r3s.data.sqlite;

import android.content.Context;

public abstract class DbTable {
    protected SQLiteHelper sqLiteHelper;

    public DbTable(Context context) {
        this.sqLiteHelper = new SQLiteHelper(context);
    }
}
