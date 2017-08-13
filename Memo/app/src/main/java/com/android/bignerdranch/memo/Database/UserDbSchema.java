package com.android.bignerdranch.memo.Database;

/**
 * Created by KeanuZhao on 2017/4/23.
 */

public class UserDbSchema {

    public static final class UserTable {
        public static final String NAME = "users";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String PASS = "pass";
            public static final String PHONE = "phone";
            public static final String DATE = "date";
        }
    }
}
