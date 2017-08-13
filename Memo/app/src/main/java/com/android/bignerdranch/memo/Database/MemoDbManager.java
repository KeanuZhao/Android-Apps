package com.android.bignerdranch.memo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.android.bignerdranch.memo.DataStructure.Memo;
import com.android.bignerdranch.memo.Database.MemoDbSchema.MemoTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by KeanuZhao on 2017/4/25.
 */

public class MemoDbManager {

    private static MemoDbManager sMemoLab;
    private ArrayList<Memo> sMemoList;

    private SQLiteDatabase mDatabase;
    private Context mContext;


    public static MemoDbManager get(Context context) {
        if (sMemoLab == null) {
            sMemoLab = new MemoDbManager(context);
        }
        return sMemoLab;


    }

    public MemoDbManager(Context context) {
        mContext = context.getApplicationContext();  //db创建
        mDatabase = new MemoBaseHelper(mContext)
                .getWritableDatabase();

        sMemoList = new ArrayList<>();
    }



    public List<Memo> getMemos() {
        return sMemoList;
    }


    public void addMemo(Memo m) {

        sMemoList.add(m);
    }
    public void deleteMemo(Memo m) {

        sMemoList.remove(m);
    }


    public void addMemoDB(Memo m){ //添加方式分离
        ContentValues values = getContentValues(m);
        mDatabase.insert(MemoTable.NAME, null, values);
    }

    public void updateMemoDB(Memo m) {
        String uuidString = m.getMemoId().toString();
        ContentValues values = getContentValues(m);
        mDatabase.update(MemoTable.NAME, values,
                MemoTable.Mols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void deleteMemoDB(Memo m) {
        String uuidString = m.getMemoId().toString();
        ContentValues values = getContentValues(m);
        mDatabase.delete(MemoTable.NAME,
                MemoTable.Mols.UUID + " = ?",
                new String[] { uuidString });
    }

    private MemoCursorWrapper queryMemosDB(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MemoTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new MemoCursorWrapper(cursor);
    }

    public List<Memo> getMemosDB() {
        List<Memo> memos = new ArrayList<>();
        MemoCursorWrapper cursor = queryMemosDB(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                memos.add(cursor.getMemo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return memos;
    }



    public Memo getMemo(UUID id) {
        for (Memo m : sMemoList) {
            if (m.getMemoId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    private static ContentValues getContentValues(Memo memo) {
        ContentValues values = new ContentValues();
        values.put(MemoDbSchema.MemoTable.Mols.UUID, memo.getMemoId().toString());
        values.put(MemoDbSchema.MemoTable.Mols.USID, memo.getMemousId());
        values.put(MemoDbSchema.MemoTable.Mols.TITLE, memo.getMemoTitle());
        values.put(MemoDbSchema.MemoTable.Mols.DATE, memo.getMemoDate().getTime());
        values.put(MemoDbSchema.MemoTable.Mols.LOCATION, memo.getMemoLocation() );
        values.put(MemoDbSchema.MemoTable.Mols.CONTENT, memo.getMemoContent());
        values.put(MemoDbSchema.MemoTable.Mols.PHOTOPATH, memo.getPhoto() );
        values.put(MemoDbSchema.MemoTable.Mols.PAINTPATH, memo.getPaint());
        return values;
    }

    public File getPhotoFile(Memo m) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, m.getPhotoFilename());
    }

    public void close(){
        mDatabase.close();
    }

}
