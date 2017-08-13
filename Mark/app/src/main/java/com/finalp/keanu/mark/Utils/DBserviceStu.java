package com.finalp.keanu.mark.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.finalp.keanu.mark.Entitys.ExportItem;
import com.finalp.keanu.mark.Entitys.SearchingResult;
import com.finalp.keanu.mark.Entitys.StuEntitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaos on 2017/3/27.
 */
public class DBserviceStu {

    private DBhelperStu dBhelperStu = null;
    private SPoperation sPoperation;

    public DBserviceStu(Context context) {
        sPoperation = new SPoperation(context);
        int version = sPoperation.getPreferences().getInt("stuVersion", 1);
        System.out.println("db_version:" + version);
        dBhelperStu = new DBhelperStu(context, DBinfoStu.DB_NAME_STU, null, version);
    }

    public DBserviceStu(Context context, boolean isUpdate) {
        sPoperation = new SPoperation(context);
        int version = sPoperation.getPreferences().getInt("stuVersion", 0);
        if (version == 0) {
            version = 1;
        } else version++;
        sPoperation.getEditor().putInt("stuVersion", version);
        sPoperation.getEditor().commit();
        dBhelperStu = new DBhelperStu(context, DBinfoStu.DB_NAME_STU, null, version);
    }

    //字段 GroupNumber、BuptNumber、QMNumber、SurName、ForeName、SheetName
    public long add(int GroupNumber, int BuptNumber, int QMNumber, String SurName, String ForeName, String SheetName) {
        SQLiteDatabase db = dBhelperStu.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("GroupNumber", GroupNumber);
        values.put("BuptNumber", BuptNumber);
        values.put("QMNumber", QMNumber);
        values.put("SurName", SurName);
        values.put("ForeName", ForeName);
        values.put("SheetName", SheetName);
        long result = db.insert(DBinfoStu.STU_TABLE, null, values);
        db.close();
        return result;
    }

    //批量添加学生信息到数据库
    public long addAll(List<StuEntitys> list) {
        SQLiteDatabase db = dBhelperStu.getWritableDatabase();
        long result = 0;
        int lengt = list.size();
        for (int i = 0; i < lengt; i++) {
            ContentValues values = new ContentValues();
            values.put("GroupNumber", list.get(i).getGroupNumber());
            values.put("BuptNumber", list.get(i).getBuptNumber());
            values.put("QMNumber", list.get(i).getQMNumber());
            values.put("SurName", list.get(i).getSurName());
            values.put("ForeName", list.get(i).getForeName());
            values.put("SheetName", list.get(i).getSheetName());
            result = db.insert(DBinfoStu.STU_TABLE, null, values);
        }

        db.close();
        return result;
    }

    /*
     * @para Group Number, Student Name,  BUPT Number  or QM Number
     * @return listOfStudent
     * 查询功能中，输入查询条件，返回学生列表，只能是一个group的
     */
    public SearchingResult searchingResult(String str) {
        SQLiteDatabase db = dBhelperStu.getReadableDatabase();
        SearchingResult sr = new SearchingResult();
        List<StuEntitys> list = new ArrayList<StuEntitys>();

        //判断输入条件是不是数字，是数字则根据号码查询，否则根据名字查询
        //如果根据组号找到了，那就存储结果直接结束
        //如果是学号找到，那么找到该学生的信息，获取他的组号，然后根据组号找到该组成员
        //如果是根据名字找到，那么同样的，找组号，然后获取成员信息
        if (DBinfoStu.isNumeric(str)) {
//            int searchCondition = Integer.parseInt(str);
            //如果判断条件是groupnumber
            Cursor cursor = db.rawQuery(DBinfoStu.findByGroupNumber, new String[]{str});
            int length = cursor.getCount();
            if (length != 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    int GroupNumber = cursor.getInt(cursor.getColumnIndex("GroupNumber"));
                    int BuptNumber = cursor.getInt(cursor.getColumnIndex("BuptNumber"));
                    int QMNumber = cursor.getInt(cursor.getColumnIndex("QMNumber"));
                    String SurName = cursor.getString(cursor.getColumnIndex("SurName"));
                    String ForeName = cursor.getString(cursor.getColumnIndex("ForeName"));
                    String SheetName = cursor.getString(cursor.getColumnIndex("SheetName"));
                    StuEntitys stuEntitys = new StuEntitys(id, GroupNumber, BuptNumber, QMNumber, SurName, ForeName, SheetName);
                    list.add(stuEntitys);
                }
                sr.setGroupNumber(Integer.parseInt(str));
                sr.setList(list);
                cursor.close();
                db.close();
                return sr;
            } else {//如果判断条件是BuptNumber
                cursor = db.rawQuery(DBinfoStu.findByBuptNumber, new String[]{str});
                length = cursor.getCount();
                if (length != 0) {
                    cursor.moveToFirst();
                    int GroupNumber = cursor.getInt(cursor.getColumnIndex("GroupNumber"));
                    list = getByGroupNumber(GroupNumber + "", db);
                    sr.setGroupNumber(list.size());
                    sr.setList(list);
                    cursor.close();
                    db.close();
                    return sr;
                } else {//如果判断条件是QMnumber
                    cursor = db.rawQuery(DBinfoStu.findByQMnuber, new String[]{str});
                    length = cursor.getCount();
                    if (length != 0) {
                        cursor.moveToFirst();
                        int GroupNumber = cursor.getInt(cursor.getColumnIndex("GroupNumber"));
                        list = getByGroupNumber(GroupNumber + "", db);
                        sr.setGroupNumber(list.size());
                        sr.setList(list);
                        cursor.close();
                        db.close();
                        return sr;
                    }
                }
            }
            cursor.close();
            db.close();
            return null;
        } else {//如果搜索条件为name
            String[] name = str.split(" ");
            if (name.length != 2) {//
                db.close();
                return null;
            } else {
                Cursor cursor = db.rawQuery(DBinfoStu.findByName, new String[]{name[0], name[1]});
                int length = cursor.getCount();
                if (length == 0) {
                    cursor.close();
                    db.close();
                    return null;
                } else {
                    cursor.moveToFirst();
                    int GroupNumber = cursor.getInt(cursor.getColumnIndex("GroupNumber"));
                    list = getByGroupNumber(GroupNumber + "", db);
                    sr.setGroupNumber(list.size());
                    sr.setList(list);
                    cursor.close();
                    db.close();
                    return sr;
                }
            }
        }
    }

    //根据组号返回学生列表
    private List<StuEntitys> getByGroupNumber(String groupNumber, SQLiteDatabase db) {
        List<StuEntitys> list = null;
        Cursor cursor = db.rawQuery(DBinfoStu.findByGroupNumber, new String[]{groupNumber});
        if (cursor.getCount() > 0) {
            list = new ArrayList<StuEntitys>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int GroupNumber = cursor.getInt(cursor.getColumnIndex("GroupNumber"));
                int BuptNumber = cursor.getInt(cursor.getColumnIndex("BuptNumber"));
                int QMNumber = cursor.getInt(cursor.getColumnIndex("QMNumber"));
                String SurName = cursor.getString(cursor.getColumnIndex("SurName"));
                String ForeName = cursor.getString(cursor.getColumnIndex("ForeName"));
                String SheetName = cursor.getString(cursor.getColumnIndex("SheetName"));
                StuEntitys stuEntitys = new StuEntitys(id, GroupNumber, BuptNumber, QMNumber, SurName, ForeName, SheetName);
                list.add(stuEntitys);
            }
        }
        cursor.close();
        return list;
    }

    public List<StuEntitys> getByGroupNumber(int groupNumber) {
        List<StuEntitys> list = null;
        SQLiteDatabase db = dBhelperStu.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBinfoStu.STU_TABLE + " where GroupNumber=?", new String[]{groupNumber + ""});
        if (cursor.getCount() > 0) {
            list = new ArrayList<StuEntitys>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int GroupNumber = cursor.getInt(cursor.getColumnIndex("GroupNumber"));
                int BuptNumber = cursor.getInt(cursor.getColumnIndex("BuptNumber"));
                int QMNumber = cursor.getInt(cursor.getColumnIndex("QMNumber"));
                String SurName = cursor.getString(cursor.getColumnIndex("SurName"));
                String ForeName = cursor.getString(cursor.getColumnIndex("ForeName"));
                String SheetName = cursor.getString(cursor.getColumnIndex("SheetName"));
                StuEntitys stuEntitys = new StuEntitys(id, GroupNumber, BuptNumber, QMNumber, SurName, ForeName, SheetName);
                list.add(stuEntitys);
            }

        }
        cursor.close();
        db.close();
        return list;
    }


    public List<Integer> getGroupName() {
        List<Integer> list = null;
        SQLiteDatabase db = dBhelperStu.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct GroupNumber from " + DBinfoStu.STU_TABLE, null);
        if (cursor.getCount() > 0) {
            list = new ArrayList<Integer>();
            while (cursor.moveToNext()) {
                list.add(cursor.getInt(cursor.getColumnIndex("GroupNumber")));
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    //int groupNumber, int buptNumber, int QMNumber, String surName, String foreName, String sheetName
    public List<ExportItem> exportAll() {
        List<ExportItem> list = null;
        SQLiteDatabase db = dBhelperStu.getReadableDatabase();
        Cursor cursor = db.rawQuery("select GroupNumber,BuptNumber,QMNumber,SurName,ForeName,SheetName from " + DBinfoStu.STU_TABLE + " order by SheetName", null);
        if (cursor.getColumnCount() > 0) {
            list = new ArrayList<ExportItem>();
            while (cursor.moveToNext()) {
                int GroupNumber = cursor.getInt(cursor.getColumnIndex("GroupNumber"));
                int BuptNumber = cursor.getInt(cursor.getColumnIndex("BuptNumber"));
                int QMNumber = cursor.getInt(cursor.getColumnIndex("QMNumber"));
                String SurName = cursor.getString(cursor.getColumnIndex("SurName"));
                String ForeName = cursor.getString(cursor.getColumnIndex("ForeName"));
                String SheetName = cursor.getString(cursor.getColumnIndex("SheetName"));
                ExportItem exportItem = new ExportItem(GroupNumber, BuptNumber, QMNumber, SurName, ForeName, SheetName);
                list.add(exportItem);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    //返回所有的数据表信息字段 GroupNumber、BuptNumber、QMNumber、SurName、ForeName、SheetName
    public List<StuEntitys> findAll() {
        List<StuEntitys> list = null;
        SQLiteDatabase db = dBhelperStu.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBinfoStu.STU_TABLE, null);
        if (cursor.getCount() > 0) {
            list = new ArrayList<StuEntitys>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int GroupNumber = cursor.getInt(cursor.getColumnIndex("GroupNumber"));
                int BuptNumber = cursor.getInt(cursor.getColumnIndex("BuptNumber"));
                int QMNumber = cursor.getInt(cursor.getColumnIndex("QMNumber"));
                String SurName = cursor.getString(cursor.getColumnIndex("SurName"));
                String ForeName = cursor.getString(cursor.getColumnIndex("ForeName"));
                String SheetName = cursor.getString(cursor.getColumnIndex("SheetName"));
                StuEntitys stuEntitys = new StuEntitys(id, GroupNumber, BuptNumber, QMNumber, SurName, ForeName, SheetName);
                list.add(stuEntitys);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

}
