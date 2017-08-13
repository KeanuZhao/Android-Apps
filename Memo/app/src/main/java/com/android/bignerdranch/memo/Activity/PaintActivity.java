package com.android.bignerdranch.memo.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.bignerdranch.memo.DataStructure.Memo;
import com.android.bignerdranch.memo.Database.MemoDbManager;
import com.android.bignerdranch.memo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static android.R.attr.path;

/**
 * Created by KeanuZhao on 2017/5/7.
 */

public class PaintActivity extends AppCompatActivity {

    private Bitmap mBaseBitmap;
    private ImageView mBaseView;
    private Canvas mCanvas;
    private Paint mPaint;

    private Memo mNewMemo;

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    private Button btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16;

    int width,height;
    private UUID MemoId;
    private String StringId;


    private static final String EXTRA_SEND_TO_PAINT =
            "com.android.bignerdranch.memo.paint";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_paint);



        StringId = getIntent().getStringExtra(EXTRA_SEND_TO_PAINT);
        MemoId = UUID.fromString(StringId);

        mNewMemo = MemoDbManager.get(this).getMemo(MemoId);

        this.mBaseView = (ImageView) this.findViewById(R.id.iv);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //获取像素密度
        float Density = metrics.density;
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        height = height-45;
        // 创建一张空白图片
        mBaseBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        // 创建一张画布
        mCanvas = new Canvas(mBaseBitmap);
        // 画布背景
        mCanvas.drawColor(Color.WHITE);
        // 创建画笔
        mPaint = new Paint();
        // 画笔颜色
        mPaint.setColor(Color.BLACK);
        // 宽度5个像素
        mPaint.setStrokeWidth(5);
        // 先将灰色背景画上
        mCanvas.drawBitmap(mBaseBitmap, new Matrix(), mPaint);
        mBaseView.setImageBitmap(mBaseBitmap);

        mBaseView.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 获取手按下时的坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取手移动后的坐标
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        // 在开始和结束坐标间画一条线
                        mCanvas.drawLine(startX, startY, stopX, stopY, mPaint);
                        // 实时更新开始坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        mBaseView.setImageBitmap(mBaseBitmap);
                        break;
                }
                return true;
            }
        });

        btn1= (Button) findViewById(R.id.btnColorBlack);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.BLACK);
            }
        });

        btn2= (Button) findViewById(R.id.btnColorBlue);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#0033FF"));
            }
        });

        btn3= (Button) findViewById(R.id.btnColorLightBlue);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#33CCFF"));
            }
        });

        btn4= (Button) findViewById(R.id.btnColorGreen);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#33FF33"));
            }
        });

        btn5= (Button) findViewById(R.id.btnColorYellow);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#FFFF33"));
            }
        });

        btn6= (Button) findViewById(R.id.btnColorOrange);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#FF6600"));
            }
        });

        btn7= (Button) findViewById(R.id.btnColorRed);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#ff0000"));
            }
        });

        btn8= (Button) findViewById(R.id.btnColorViolet);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#9900CC"));
            }
        });

        btn9= (Button) findViewById(R.id.btnColorGray);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#999999"));
            }
        });

        btn10= (Button) findViewById(R.id.btnColorLightGray);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#CCCCCC"));
            }
        });

        btn11= (Button) findViewById(R.id.btnColorCream);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#FFEBCD"));
            }
        });

        btn12= (Button) findViewById(R.id.btnColorGold);
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#ffd700"));
            }
        });

        btn13= (Button) findViewById(R.id.btnColorNavyBlue);
        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#006699"));
            }
        });

        btn14= (Button) findViewById(R.id.btnColorBrown);
        btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#663300"));
            }
        });

        btn15= (Button) findViewById(R.id.btnColorDarkGreen);
        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#339900"));
            }
        });

        btn16= (Button) findViewById(R.id.btnColorPink);
        btn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(Color.parseColor("#FF33FF"));
            }
        });

    }

    public void save(View view) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            OutputStream stream = new FileOutputStream(file);
            mBaseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            // 模拟一个广播，通知系统sdcard被挂载
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment
                    .getExternalStorageDirectory()));
            sendBroadcast(intent);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //四个参数的含义。1，group的id,2,item的id,3,是否排序，4，将要显示的内容
        menu.add(0,1,0,"5px");
        menu.add(0,2,0,"10px");
        menu.add(0,3,0,"15px");
        menu.add(0,4,0,"Eraser");
        menu.add(0,5,0,"Clear");
        menu.add(0,6,0,"Save");
        menu.add(0,7,0,"Cancel");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                mPaint.setStrokeWidth(5);
                break;
            case 2:
                mPaint.setStrokeWidth(10);
                break;
            case 3:
                mPaint.setStrokeWidth(15);
                break;
            case 4:
                mPaint.setColor(Color.WHITE);
                mPaint.setStrokeWidth(10);
                break;
            case 5:
                Intent intentdraw = PaintActivity.newIntent(this,StringId);
                startActivity(intentdraw);
                break;
            case 6:
                saveBitmap();
                Intent sendIntent = MemoActivity.newIntent(this, MemoId);
                startActivity(sendIntent);
                break;
            case 7:
                Intent backIntent = MemoActivity.newIntent(this, MemoId);
                startActivity(backIntent);
                break;
        }
        return true;
    }

    public static Intent newIntent(Context packageContext, String getId) {
        Intent i = new Intent(packageContext, PaintActivity.class);
        i.putExtra(EXTRA_SEND_TO_PAINT, getId);
        return i;
    }

    private void saveBitmap(){

        File appDir = new File(Environment.getExternalStorageDirectory(), "MemoPaintImage");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBaseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        mNewMemo.setPaint(file.toString());
        Toast.makeText(this, "图片保存在："+ file.getAbsolutePath(), Toast.LENGTH_LONG).show();
    }
}

