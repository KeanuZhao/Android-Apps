package com.android.bignerdranch.memo.Database;

/**
 * Created by KeanuZhao on 2017/4/25.
 */

public class MemoDbSchema {

    public static final class MemoTable {

        public static final String NAME = "memos";

        public static final class Mols {
            public static final String UUID = "uuid";
            public static final String USID = "usid";
            public static final String TITLE = "title";
            public static final String CONTENT = "content";
            public static final String LOCATION = "location";
            public static final String DATE = "date";
            public static final String PHOTOPATH = "photopath";
            public static final String PAINTPATH = "paintpath";
        }
    }
}
