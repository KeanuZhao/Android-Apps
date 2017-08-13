package com.android.bignerdranch.memo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.bignerdranch.memo.DataStructure.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KeanuZhao on 2017/4/23.
 */

public class UserDbManager {


    private SQLiteDatabase mDatabase;
    private Context mContext;



    public UserDbManager(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new UserBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void close(){
        mDatabase.close();
    }



    public void addUser(User u) {
        ContentValues values = getContentValues(u);
        mDatabase.insert(UserDbSchema.UserTable.NAME, null, values);
    }

    public void updateUser(User u) {
        String uuidString = u.getAccountId().toString();
        ContentValues values = getContentValues(u);
        mDatabase.update(UserDbSchema.UserTable.NAME, values,
                UserDbSchema.UserTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }


    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDbSchema.UserTable.Cols.UUID, user.getAccountId().toString());
        values.put(UserDbSchema.UserTable.Cols.NAME, user.getAccountName());
        values.put(UserDbSchema.UserTable.Cols.PASS, user.getAccountPass());
        values.put(UserDbSchema.UserTable.Cols.PHONE, user.getAccountPhone());
        values.put(UserDbSchema.UserTable.Cols.DATE, user.getDate().getTime());
        return values;
    }



    private UserCursorWrapper queryUsers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
               UserDbSchema.UserTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new UserCursorWrapper(cursor);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        UserCursorWrapper cursor = queryUsers(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return users;
    }
}
