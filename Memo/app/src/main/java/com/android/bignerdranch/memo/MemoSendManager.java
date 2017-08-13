package com.android.bignerdranch.memo;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;


import com.android.bignerdranch.memo.DataStructure.Memo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KeanuZhao on 2017/5/24.
 */

public class MemoSendManager {

    public static String writeBase64(String path) {//path图片路径
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(data, Base64.NO_WRAP);//注意这里是Base64.NO_WRAP　　
    }

    public static File writeCsv(Memo memo) {

        File PhotoPath=null;
        File PaintPath=null;

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/csv/";   //CSV文件路径

        File csv = new File(path + memo.getMemoTitle()+".csv");
        File pa = new File(path);
        if (!pa.exists()) {
            pa.mkdirs();
        }
        if (!csv.exists()) {
            try {
                csv.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                csv.delete();   //这里写的如果文件存在会删除文件新建一个文件
                csv.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            bw.append("uuid,usid,title,content,location,photopath,paintpath,date\r\n");
            bw.write(memo.getMemoId() + "," + memo.getMemousId() + "," + memo.getMemoTitle()+ "," +memo.getMemoContent()+ "," +memo.getMemoLocation()+ "," +memo.getPhoto()+ "," +memo.getPaint()+ "," +memo.getMemoDate());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csv;
    }

    public static Intent generateIntentPic(Memo memo){

        ArrayList<Uri> uriList = new ArrayList<>();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.setType("image/*");
        if(memo.getPhoto()!=null){
            File fPhoto=new File(memo.getPhoto());
            Uri uPhoto = Uri.fromFile(fPhoto);
            uriList.add(uPhoto);
        }
        if(memo.getPaint()!=null){
            File fPaint=new File(memo.getPaint());
            Uri uPaint = Uri.fromFile(fPaint);
            uriList.add(uPaint);
        }

        sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, memo.getMemoTitle());
        return sendIntent;
    }

    public static Intent generateIntent(Memo memo){

        File csv = writeCsv(memo);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(csv));
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, memo.getMemoTitle());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        sendIntent.putExtra(Intent.EXTRA_TEXT, "There is CSV. " + df.format(new Date()));
        sendIntent.setType("text/comma-separated-values");
        return sendIntent;

    }

}
