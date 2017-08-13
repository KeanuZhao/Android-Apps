package com.finalp.keanu.mark.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by miaos on 2017/3/17.
 */
public class DBhelperStu extends SQLiteOpenHelper{

    //字段 GroupNumber、BuptNumber、QMNumber、SurName、ForeName、SheetName

    private Context context;

    private String CREATE_SQL = "create table " + DBinfoStu.STU_TABLE
            + " (id integer primary key autoincrement, "
            + "GroupNumber integer, "
            + "BuptNumber integer, "
            + "QMNumber integer, "
            + "SurName varchar(100), "
            + "ForeName varchar(100), "
            + "SheetName varchar(100))";

    private String DROP_SQL = "drop table " + DBinfoStu.STU_TABLE;

    public DBhelperStu(Context context, String DB_NAME, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, DB_NAME, factory, version);
        this.context = context;
    }


    //初次生成数据库时才调用,创建表，同时将表名写入配置文件
    @Override
    public void onCreate(SQLiteDatabase db) {
        //自动生成字符串作为表名，然后将名称写入配置文件，并更新
        db.execSQL(CREATE_SQL);
        SPoperation sPoperation = new SPoperation(context);
        sPoperation.getEditor().putInt("stuVersion",1);
        sPoperation.getEditor().commit();
    }

    //升级软件时更新数据表结构
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //写入new版本号
        SPoperation sPoperation = new SPoperation(context);
        sPoperation.getEditor().putInt("stuVersion",newVersion);
        sPoperation.getEditor().commit();
       //删除表后新建表
        db.execSQL(DROP_SQL);
        db.execSQL(CREATE_SQL);
    }

}
