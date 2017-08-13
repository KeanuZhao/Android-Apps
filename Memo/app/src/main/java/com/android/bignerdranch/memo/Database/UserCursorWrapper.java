package com.android.bignerdranch.memo.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.bignerdranch.memo.DataStructure.User;
import com.android.bignerdranch.memo.Database.UserDbSchema.UserTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by KeanuZhao on 2017/4/24.
 */

public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String uuidString = getString(getColumnIndex(UserTable.Cols.UUID));
        String name = getString(getColumnIndex(UserTable.Cols.NAME));
        String pass = getString(getColumnIndex(UserTable.Cols.PASS));
        String phone = getString(getColumnIndex(UserTable.Cols.PHONE));
        long date = getLong(getColumnIndex(UserTable.Cols.DATE));

        User ucuser = new User(UUID.fromString(uuidString));
        ucuser.setAccountName(name);
        ucuser.setAccountPass(pass);
        ucuser.setAccountPhone(phone);
        ucuser.setDate(new Date(date));
        return ucuser;
    }


}
