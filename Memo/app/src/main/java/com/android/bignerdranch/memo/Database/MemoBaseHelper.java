package com.android.bignerdranch.memo.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by KeanuZhao on 2017/4/25.
 */

public class MemoBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "memoBase.db";


    public MemoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MemoDbSchema.MemoTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                MemoDbSchema.MemoTable.Mols.UUID + ", " +
                MemoDbSchema.MemoTable.Mols.USID + ", " +
                MemoDbSchema.MemoTable.Mols.TITLE + ", " +
                MemoDbSchema.MemoTable.Mols.CONTENT + ", " +
                MemoDbSchema.MemoTable.Mols.LOCATION + ", " +
                MemoDbSchema.MemoTable.Mols.PHOTOPATH + ", " +
                MemoDbSchema.MemoTable.Mols.PAINTPATH + ", " +
                MemoDbSchema.MemoTable.Mols.DATE  +

                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
