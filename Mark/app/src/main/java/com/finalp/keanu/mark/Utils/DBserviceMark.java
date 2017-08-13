package com.finalp.keanu.mark.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.finalp.keanu.mark.Entitys.MarkEntitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaos on 2017/4/10.
 */
public class DBserviceMark {

    private DBhelperMark dBhelperMark = null;
    private Context context;

    public DBserviceMark(Context context) {
        this.context = context;
        SPoperation sPoperation = new SPoperation(context);
        int version = sPoperation.getPreferences().getInt("markVersion", 1);
        System.out.println("db_version:mark" + version);
        dBhelperMark = new DBhelperMark(context, DBinfoStu.DB_NAME_MARK, null, version);
    }

    public DBserviceMark(Context context, boolean isUpdate) {
        this.context = context;
        SPoperation sPoperation = new SPoperation(context);
        int version = sPoperation.getPreferences().getInt("markVersion", 0);
        if(version == 0) {
            version = 1;
        } else version++;
        sPoperation.getEditor().putInt("markVersion", version);
        sPoperation.getEditor().commit();
        System.out.println("db_version:mark update" + version);
        dBhelperMark = new DBhelperMark(context, DBinfoStu.DB_NAME_MARK, null, version);
        SQLiteDatabase db = dBhelperMark.getWritableDatabase();
        db.close();
    }

    public List<MarkEntitys> getGroupNumberbyOrder(String str) {
        List<MarkEntitys> list = null;
        SQLiteDatabase db = dBhelperMark.getReadableDatabase();
        Cursor cursor = null;
        if(str.equals("min")) {
            String sql = "select * from " + DBinfoStu.MARK_TABLE + " where TotalMark=( select min(TotalMark) from " + DBinfoStu.MARK_TABLE + ")";
            System.out.println("sql:" + sql);
            cursor = db.rawQuery(sql,null);
        } else if(str.equals("max")) {
            String sql = "select * from " + DBinfoStu.MARK_TABLE + " where TotalMark=( select max(TotalMark) from " + DBinfoStu.MARK_TABLE + ")";
            System.out.println("sql:" + sql);
            cursor = db.rawQuery(sql,null);
        }
        while(cursor.moveToNext()) {
            if(list == null) {
                list = new ArrayList<MarkEntitys>();
            }
            MarkEntitys mark = null;
            ArrayList<Integer> markMateria = new ArrayList<Integer>();
            mark = new MarkEntitys();
            mark.setId(cursor.getInt(0));
            mark.setGroupNumber(cursor.getInt(1));
            mark.setTotalMark(cursor.getInt(2));
            mark.setReMark(cursor.getString(3));
            mark.setSheetName(cursor.getString(4));
            int leng = cursor.getColumnCount();
            for (int i = 5; i < leng; i++) {
                markMateria.add(cursor.getInt(i));
            }
            mark.setMarkMateria(markMateria);
            list.add(mark);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getTotalMark(int groupNumber) {
        SQLiteDatabase db = dBhelperMark.getReadableDatabase();
        Cursor cursor = db.rawQuery("select TotalMark from " + DBinfoStu.MARK_TABLE + " where GroupNumber=?", new String[] {groupNumber + ""});
        int result = 0;
        if(cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return result;
    }

    public MarkEntitys getGroupMark(int groupNumber) {
        SQLiteDatabase db = dBhelperMark.getReadableDatabase();
        MarkEntitys mark = null;
        Cursor cursor = db.rawQuery(DBinfoStu.findMarkByGroup, new String[]{groupNumber + ""});
        if (cursor.moveToNext()) {
//            cursor.getColumnCount();
            mark = new MarkEntitys();
            ArrayList<Integer> markMateria = new ArrayList<Integer>();

            mark.setId(cursor.getInt(0));
            mark.setGroupNumber(cursor.getInt(1));
            mark.setTotalMark(cursor.getInt(2));
            mark.setReMark(cursor.getString(3));
            mark.setSheetName(cursor.getString(4));
            int leng = cursor.getColumnCount();
            for (int i = 5; i < leng; i++) {
                markMateria.add(cursor.getInt(i));
            }
            mark.setMarkMateria(markMateria);
        }
        cursor.close();
        db.close();
        return mark;
    }

    public long add(MarkEntitys markEntitys) {
        SQLiteDatabase db = dBhelperMark.getWritableDatabase();
        //如果有的话，先delete掉，然后再添加


        ContentValues values = new ContentValues();
        values.put("GroupNumber", markEntitys.getGroupNumber());
        values.put("TotalMark", markEntitys.getTotalMark());
        values.put("Remarks", markEntitys.getReMark());
        values.put("SheetName", markEntitys.getSheetName());
        ArrayList<Integer> arrayList = markEntitys.getMarkMateria();
        for (int i = 0; i < arrayList.size(); i++) {
            values.put("item" + i, arrayList.get(i));
        }

        Cursor cursor = db.rawQuery(DBinfoStu.findMarkByGroup, new String[]{markEntitys.getGroupNumber() + ""});
        if (cursor.moveToNext()) {
            db.delete(DBinfoStu.MARK_TABLE,"GroupNumber=?",new String[] {markEntitys.getGroupNumber() + ""});
        }
        long result = db.insert(DBinfoStu.MARK_TABLE, null, values);

        cursor.close();
        db.close();
        return result;
    }

}
