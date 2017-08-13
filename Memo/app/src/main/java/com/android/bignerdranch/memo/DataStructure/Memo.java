package com.android.bignerdranch.memo.DataStructure;


import java.util.Date;
import java.util.UUID;

/**
 * Created by KeanuZhao on 2017/4/25.
 */

public class Memo {

    private UUID mMemoId;
    private String mMemousId;
    private String MemoTitle;
    private Date mMemoDate;
    private String mMemoLocation;
    private String mPhoto;
    private String mPaint;
    private String mMemoContent;

    public Memo() {
        mMemoId = UUID.randomUUID();
        mMemoDate = new Date();
    }

    public Memo(UUID id) {
        mMemoId = id;
        mMemoDate = new Date();
    }

    public UUID getMemoId() {
        return mMemoId;
    }

    public String getMemoTitle() {
        return MemoTitle;
    }

    public void setMemoTitle(String memotitle) {
        MemoTitle = memotitle;
    }

    public Date getMemoDate() {
        return mMemoDate;
    }

    public void setMemoDate(Date memoDate) {
        mMemoDate = memoDate;
    }

    public String getMemoLocation() {
        return mMemoLocation;
    }

    public void setMemoLocation(String memoLocation) {
        mMemoLocation = memoLocation;
    }

    public String getMemoContent() {
        return mMemoContent;
    }

    public void setMemoContent(String memocontent) {
        mMemoContent = memocontent;
    }

    public String getMemousId() {
        return mMemousId;
    }

    public void setMemousId(String memousId) {
        mMemousId = memousId;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getPaint() {
        return mPaint;
    }

    public void setPaint(String paint) {
        mPaint = paint;
    }

    public String getPhotoFilename() {
        return "IMG_" + getMemoId().toString() + ".jpg";
    }
}
