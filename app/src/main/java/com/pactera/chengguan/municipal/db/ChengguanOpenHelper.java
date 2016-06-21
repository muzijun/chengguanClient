package com.pactera.chengguan.municipal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chengguan.dao.DaoMaster;

/**
 * Created by lijun on 2015/12/18.
 */
public class ChengguanOpenHelper extends DaoMaster.DevOpenHelper {
    public ChengguanOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
