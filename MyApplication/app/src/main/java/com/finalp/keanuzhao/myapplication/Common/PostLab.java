package com.finalp.keanuzhao.myapplication.Common;

import android.content.Context;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by KeanuZhao on 02/03/2017.
 */

public class PostLab {

    private ArrayList<Post> mPosts;
    public static PostLab sPostLab;
    private Context mAppContext;
    private Handler handler = new Handler();
    private String info;
   // private static final int COMPLETED = 0;

    private PostLab(Context appContext, String id){


        this.mAppContext = appContext;
        mPosts = new ArrayList<Post>();
        if (id.equals("admin")){
           Thread t1 = new Thread(new MyAdminThread());
            t1.start();

        }
        else{
            Thread t = new Thread(new MyThread());
            t.start();

        }

    }

    public static PostLab get(Context c, String id) {

        if (sPostLab==null){
            sPostLab = new PostLab(c.getApplicationContext(),id);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return sPostLab;
        }
        else {
            return sPostLab;
        }
    }

    public Post getPost(UUID id) {
        for (Post p : mPosts) {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }
    public ArrayList<Post> getPosts() {
        return mPosts;
    }

    public void addPost(Post p){
        mPosts.add(p);
    }

    public void deletePost(Post p){
        mPosts.remove(p);
    }


    private class MyAdminThread implements Runnable{

        @Override
        public void run() {
            info = WebServiceGET.executeHttpAdminGetRead();
            //System.out.println("myadminthread: " + info);
            final ArrayList<Post> temp = new ArrayList<>();
            if (info!=null) {
                try {

                    JSONArray array = new JSONArray(info);

                    //Toast.makeText(mAppContext,""+array.length(),Toast.LENGTH_SHORT).show();
                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jo = (JSONObject) array.get(i);
                            String id = (String) jo.get("id");
                            String title = (String) jo.get("title");
                            String date = (String) jo.get("date");
                            String content = (String) jo.get("content");
                            String writer = (String) jo.get("writer");
                            Post p = new Post(UUID.fromString(id), title.replace("qazwsx", " ").replace("ehbfg", "?"), date, content.replace("qazwsx", " ").replace("ehbfg", "?"), writer);
                            temp.add(p);
                        }
                        //System.out.println(temp.size() + "-----------------");
                        mPosts = temp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            handler.post(new Runnable() {
                @Override
                public void run() {

                    System.out.println("mposts:: " + mPosts.size());
                }
            });





        }
    }
    private class MyThread implements Runnable{

        @Override
        public void run() {
            info = WebServiceGET.executeHttpGetRead();
            final ArrayList<Post> temp = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(info);
                //Toast.makeText(mAppContext,""+array.length(),Toast.LENGTH_SHORT).show();
                if (array.length()>0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = (JSONObject) array.get(i);
                        String id = (String) jo.get("id");
                        String title = (String) jo.get("title");
                        String date = (String) jo.get("date");
                        String content = (String) jo.get("content");
                        String writer = (String) jo.get("writer");
                        Post p = new Post(UUID.fromString(id), title.replace("qazwsx"," ").replace("ehbfg","?"), date, content.replace("qazwsx"," ").replace("ehbfg","?"), writer);
                        temp.add(p);
                    }
                    mPosts = temp;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    System.out.println("mposts:: " + mPosts.size());
                }
            });




        }
    }
}

