package com.finalp.keanuzhao.myapplication.Common;

import java.util.ArrayList;

/**
 * Created by KeanuZhao on 02/03/2017.
 */

public class User {

    private String mName;
    private String mPassword;
    private ArrayList<String> mRead;

    public User(){
        this.mRead = new ArrayList<String>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public ArrayList<String> getRead() {
        return mRead;
    }

    public void setRead(ArrayList<String> mPosts) {
        this.mRead = mPosts;
    }

    public void addRead(String p){
        mRead.add(p);
    }

    public void removeRead(String p){
        mRead.remove(p);
    }
}
