package com.android.bignerdranch.memo.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.bignerdranch.memo.DataStructure.Memo;

import java.util.Date;
import java.util.UUID;

/**
 * Created by KeanuZhao on 2017/4/25.
 */

public class MemoCursorWrapper extends CursorWrapper {

    public MemoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Memo getMemo() {

        String uuidString = getString(getColumnIndex(MemoDbSchema.MemoTable.Mols.UUID));
        String usidString = getString(getColumnIndex(MemoDbSchema.MemoTable.Mols.USID));
        String title = getString(getColumnIndex(MemoDbSchema.MemoTable.Mols.TITLE));
        String location = getString(getColumnIndex(MemoDbSchema.MemoTable.Mols.LOCATION));
        String content = getString(getColumnIndex(MemoDbSchema.MemoTable.Mols.CONTENT));
        long date = getLong(getColumnIndex(MemoDbSchema.MemoTable.Mols.DATE));
        String photopath = getString(getColumnIndex(MemoDbSchema.MemoTable.Mols.PHOTOPATH));
        String paintpath = getString(getColumnIndex(MemoDbSchema.MemoTable.Mols.PAINTPATH));

        Memo mcuser = new Memo(UUID.fromString(uuidString));
        mcuser.setMemousId(usidString);
        mcuser.setMemoTitle(title);
        mcuser.setMemoLocation(location);
        mcuser.setMemoContent(content);
        mcuser.setMemoDate(new Date(date));
        mcuser.setPhoto(photopath);
        mcuser.setPaint(paintpath);

        return mcuser;
    }

}
