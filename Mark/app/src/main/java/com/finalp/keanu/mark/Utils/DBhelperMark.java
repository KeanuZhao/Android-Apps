package com.finalp.keanu.mark.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by miaos on 2017/4/10.
 */
public class DBhelperMark extends SQLiteOpenHelper {

    /*
    private int id;
    private int groupNumber;
    private String sheetName;
    private ArrayList<Integer> markMateria;//细分
    private String reMark;
    private int totalMark;
     */

    private Context context;
    private String DROP_SQL = "drop table " + DBinfoStu.MARK_TABLE;

    public DBhelperMark(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSQL());
        SPoperation sPoperation = new SPoperation(context);
        sPoperation.getEditor().putInt("markVersion",1);
        sPoperation.getEditor().commit();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SPoperation sPoperation = new SPoperation(context);
        sPoperation.getEditor().putInt("markVersion",newVersion);
        sPoperation.getEditor().commit();
        db.execSQL(DROP_SQL);
        db.execSQL(createSQL());
    }

//    "create table " + DBinfoStu.STU_TABLE
//    + " (id integer primary key autoincrement, "
//            + "GroupNumber integer, "
//            + "BuptNumber integer, "
//            + "QMNumber integer"
//            + "SurName varchar(100), "
//            + "ForeName varchar(100), "
//            + "SheetName varchar(100))";
//private int id;
//    private int groupNumber;
//    private String sheetName;
//    private ArrayList<Integer> markMateria;//细分
//    private String reMark;
//    private int totalMark;
    private String createSQL() {
        StringBuffer createSQ = null;
        ArrayList<String> arrayList = getCreateSQL();//获取评分细则
        if(arrayList != null) {
            createSQ = new StringBuffer();
            createSQ.append("create table " + DBinfoStu.MARK_TABLE
                + " (id integer primary key autoincrement, "
                + "GroupNumber integer, "
                + "TotalMark integer, "
                + "Remarks varchar(200), "
                + "sheetName varchar(100), ");
            int length = arrayList.size() - 1,i;
            for(i = 0; i < length; i++) {
                createSQ.append("item" + i + " integer, ");
            }
            createSQ.append("item" + i + " integer)");
        }
        System.out.println("create:" + createSQ);
        return createSQ.toString();
    }

    //获取存储的评分细则
    private ArrayList<String> getCreateSQL() {
        ArrayList<String> arrayList = null;
        SPoperation sPoperation = new SPoperation(context);
        String materias1 = sPoperation.getPreferences().getString("inputName1item", null);
        if (materias1 != null) {
            String materias2 = sPoperation.getPreferences().getString("inputName2item", null);
            if (materias2 != null) {
                materias1 = materias1.substring(1, materias1.length() - 1);
                materias2 = materias2.substring(1, materias2.length() - 1);
                arrayList = new ArrayList<String>();
                List<String> list1 = Arrays.asList(materias1.split(","));
                List<String> list2 = Arrays.asList(materias2.split(","));
                arrayList.addAll(list1);
                arrayList.addAll(list2);
            }
        }
        return arrayList;
    }

}
