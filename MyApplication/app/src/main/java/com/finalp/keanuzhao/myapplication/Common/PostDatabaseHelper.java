package com.finalp.keanuzhao.myapplication.Common;

/**
 * Created by KeanuZhao on 11/03/2017.
 */

public class PostDatabaseHelper  {

    private static final String DB_NAME = "ztl.sqlite";
    private static final int VERSION = 1;
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_READ = "read";

    private static final String TABLE_POST = "post";
    private static final String COLUMN_POST_ID = "id";
    private static final String COLUMN_POST_TITLE = "title";
    private static final String COLUMN_POST_DATE = "date";
    private static final String COLUMN_POST_CONTENT = "content";
    private static final String COLUMN_POST_WRITER = "writer";
    private static final String COLUMN_POST_COMMENTS = "comments";

//    public PostDatabaseHelper(Context context) {
//        super(context, DB_NAME, null, VERSION);
//    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS user (" +
//                "  name varchar(128) PRIMARY KEY NOT NULL," +
//                "  password varchar(128) NOT NULL," +
//                "  read text" +
//                ")");
//        db.execSQL("CREATE TABLE IF NOT EXISTS post (" +
//                "  id text PRIMARY KEY NOT NULL," +
//                "  title varchar(128) NOT NULL," +
//                "  date varchar(128) NOT NULL," +
//                "  content text NOT NULL," +
//                "  writer varchar(128) NOT NULL," +
//                "  comments text" +
//                ")");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//
//    }
//
//    public void insertUser(String name,String password,String read){
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_USER_NAME,name);
//        cv.put(COLUMN_USER_PASSWORD,password);
//        cv.put(COLUMN_USER_READ,read);
//        getWritableDatabase().insert(TABLE_USER,null,cv);
//    }
//
//    public void insertPost(String id,String title,String date,String content,String writer,String comments){
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_POST_ID,id);
//        cv.put(COLUMN_POST_TITLE,title);
//        cv.put(COLUMN_POST_DATE,date);
//        cv.put(COLUMN_POST_CONTENT,content);
//        cv.put(COLUMN_POST_WRITER,writer);
//        cv.put(COLUMN_POST_COMMENTS,comments);
//        getWritableDatabase().insert(TABLE_POST,null,cv);
//    }








}
