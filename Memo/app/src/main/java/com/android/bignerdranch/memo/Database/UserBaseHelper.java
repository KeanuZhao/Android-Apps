package com.android.bignerdranch.memo.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KeanuZhao on 2017/4/23.
 */

public class UserBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "accountBase.db";
    //public static int Count=0;

    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + UserDbSchema.UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserDbSchema.UserTable.Cols.UUID + ", " +
                UserDbSchema.UserTable.Cols.NAME + ", " +
                UserDbSchema.UserTable.Cols.PASS + ", " +
                UserDbSchema.UserTable.Cols.PHONE + ", " +
                UserDbSchema.UserTable.Cols.DATE  +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);

    }

}
