package com.android.bignerdranch.memo.DataStructure;


import java.util.Date;
import java.util.UUID;

/**
 * Created by KeanuZhao on 2017/3/6.
 */

public class User {

    private UUID mAccountId;
    private String mAccountName;
    private String mAccountPass;
    private String mAccountPhone;
    private Date mDate;

    public User() {
        mAccountId = UUID.randomUUID();
        mDate = new Date();
    }

    public User(UUID id){
        mAccountId = id;
        mDate = new Date();
    }

    public User(String name,String pass,String phone){
        this.mAccountId = UUID.randomUUID();
        this.mAccountName=name;
        this.mAccountPass=pass;
        this.mAccountPhone=phone;
        this.mDate = new Date();
    }

    public UUID getAccountId() {
        return mAccountId;
    }

    public void setAccountId(UUID accountId) {
        mAccountId = accountId;
    }


    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public String getAccountPass() {
        return mAccountPass;
    }

    public void setAccountPass(String accountPass) {
        mAccountPass = accountPass;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public String getAccountPhone() {
        return mAccountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        mAccountPhone = accountPhone;
    }

    public String toString() {
        return  mAccountId + "," + mAccountName + ", " + mAccountPhone+ ", " + mDate ;
    }
}
