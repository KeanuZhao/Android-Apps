package com.finalp.keanuzhao.myapplication.Common;

import java.util.Date;
import java.util.UUID;

/**
 * Created by KeanuZhao on 05/03/2017.
 */

public class Comment {
    private UUID mId;
    private String mWriter;
    private String mDate;
    private String mContent;
    private String mPostId;

    public String getmPostId() {
        return mPostId;
    }

    public void setmPostId(String mPostId) {
        this.mPostId = mPostId;
    }

    public Comment(UUID mId, String writer, String date, String content, String postId) {
        this.mId = mId;
        this.mWriter = writer;
        this.mDate = date;
        this.mContent = content;
        this.mPostId = postId;
    }

    public Comment(){
        this.mId = UUID.randomUUID();
        this.mDate = new Date().toString();
    }

    public UUID getmId() {
        return mId;
    }

    public String getDate(){
        return mDate;
    }

    public String getWriter() {
        return mWriter;
    }

    public void setWriter(String mWriter) {
        this.mWriter = mWriter;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}
