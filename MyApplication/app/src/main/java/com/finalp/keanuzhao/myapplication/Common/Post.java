package com.finalp.keanuzhao.myapplication.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by KeanuZhao on 02/03/2017.
 */

public class Post {
    private UUID mId;//
    private String mTitle;///
    private String mDate;//
    private boolean mRead;
    private String mContent;///
    private String mWriter;///
    private ArrayList<Comment> mComments;//

    public Post(){
        this.mId = UUID.randomUUID();
        this.mDate = new Date().toString();
        this.mComments = new ArrayList<Comment>();

    }

//    public Post(UUID mId, String mTitle,String mDate, String mContent, String mWriter, String comments){
//        this.mId = mId;
//        this.mTitle = mTitle;
//        this.mDate = mDate;
//        this.mContent = mContent;
//        this.mWriter = mWriter;
//        this.mComments = comments;
//    }
    public Post(UUID mId, String mTitle,String mDate, String mContent, String mWriter) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mContent = mContent;
        this.mWriter = mWriter;
        //this.mComments = mComments;
    }

    public boolean isRead() {
        return mRead;
    }

    public void setRead(boolean mRead) {
        this.mRead = mRead;
    }

    public String getDate(){
        return mDate;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getWriter() {
        return mWriter;
    }

    public void setWriter(String mWriter) {
        this.mWriter = mWriter;
    }

    public ArrayList<Comment> getComments(UUID id){
        String load = WebServiceGET.executeHttpLoadComments(id.toString());
        mComments = fromJSON(load);
        //读取数据库的comment
        return mComments;
    }
    private ArrayList<Comment> fromJSON(String json){


        ArrayList<Comment> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);

            if (array.length()>0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jo = (JSONObject) array.get(i);
                    String id = (String) jo.get("id");
                    String postid = (String) jo.get("postid");
                    String date = (String) jo.get("date");
                    String writer = (String) jo.get("writer");
                    String content = (String) jo.get("content");
                    Comment c = new Comment(UUID.fromString(id),writer,date,content,postid);
                    list.add(c);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }

//    public void addComment(Comment c){
//        mComments.add(c);
//    }
//
//    public void removeComment(Comment c){
//        mComments.remove(c);
//    }

}
