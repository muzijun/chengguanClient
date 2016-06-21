package com.pactera.chengguan.municipal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.chengguan.dao.DaoMaster;
import com.chengguan.dao.DaoSession;
import com.pactera.chengguan.app.config.Contants;


/**
 * 数据库操作
 * Created by lijun on 2015/12/23.
 */
public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static DBHelper instance;
    private static Context appContext;
    private DaoSession daoSession;
    private ChengguanOpenHelper helper;
    private DaoMaster daoMaster;
    private SQLiteDatabase db;

    private DBHelper() {
    }

    //单例模式，DBHelper只初始化一次
    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper();
            if (appContext == null) {
                appContext = context.getApplicationContext();
            }
        }
        return instance;
    }

    private DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            helper = new ChengguanOpenHelper(context, Contants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


    private SQLiteDatabase getSQLDatebase(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            db = daoMaster.getDatabase();

        }
        return db;
    }

}
