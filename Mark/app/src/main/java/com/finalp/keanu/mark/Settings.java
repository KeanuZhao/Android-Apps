package com.finalp.keanu.mark;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finalp.keanu.mark.Utils.ExportMark;
import com.finalp.keanu.mark.Utils.FileUtils;
import com.finalp.keanu.mark.Utils.ImportStuInfo;
import com.finalp.keanu.mark.Utils.SPoperation;

public class Settings extends AppCompatActivity {

    private Button marking,searching,managing;

    private Handler handler;

    private Button importstu;
    private Button changeMateriaBut;
    private Button exportinfo;
    private Button backtoserver;

    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bitmap bitmap = FileUtils.readBitMap(this,R.drawable.bg);
        Drawable drawable = new BitmapDrawable(bitmap);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootLayout);
        linearLayout.setBackgroundDrawable(drawable);

        final TextView courseName = (TextView) findViewById(R.id.courseName);
        SPoperation sp = new SPoperation(this);
        courseName.setText(sp.getPreferences().getString("courseName","courseName") + ":Management");

        initHandler();
        initView();
        initBottomButton();
    }

    private void initView() {

        importstu = (Button) findViewById(R.id.imortstu);
        changeMateriaBut = (Button) findViewById(R.id.changeMateria);
        exportinfo = (Button) findViewById(R.id.exportinfo);

        importstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTipDialog(1);
            }
        });

        changeMateriaBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTipDialog(2);
            }
        });

        exportinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportMark();
            }
        });

        backtoserver = (Button) findViewById(R.id.backtoserver);
        backtoserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this,BackupToBD.class);
                startActivity(intent);
            }
        });
    }

    private void exportMark() {
        final ExportMark exportMark = new ExportMark(this);

        progressDialog = new Dialog(Settings.this,R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("fighting!!!");
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = exportMark.exportmark();
                if(result) {
                    handler.sendEmptyMessage(0x666);
                } else {
                    handler.sendEmptyMessage(0x777);
                }
            }
        }).start();
    }

    private void changeMat() {
        Intent intent = new Intent();
        intent.setClass(Settings.this,ChangeMateria.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    progressDialog.dismiss();
                    Toast.makeText(Settings.this,"import success!",Toast.LENGTH_SHORT).show();
                    SPoperation sp = new SPoperation(Settings.this);
                    sp.getEditor().putBoolean("importStu",true);
                    sp.getEditor().commit();
                }
                else if(msg.what == 0x234) {
                    progressDialog.dismiss();
                    Toast.makeText(Settings.this,"import fail!",Toast.LENGTH_SHORT).show();
                }
                else if(msg.what == 0x666) {
                    progressDialog.dismiss();
                    Toast.makeText(Settings.this,"export success!",Toast.LENGTH_SHORT).show();
                } else if(msg.what == 0x777) {
                    progressDialog.dismiss();
                    Toast.makeText(Settings.this,"export fail!",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void showTipDialog(int type) {
        if(type == 1) {
            final AlertDialog.Builder tipDialog = new AlertDialog.Builder(this)
                    .setTitle("Notice:")
                    .setMessage(R.string.importTip1);
            setPositiveButton1(tipDialog);
            setNegativeButton1(tipDialog).create().show();
        }
        else if(type == 2) {
            final AlertDialog.Builder tipDialog = new AlertDialog.Builder(this)
                    .setTitle("Notice：")
                    .setMessage(R.string.importTip2);
            setPositiveButton2(tipDialog);
            setNegativeButton2(tipDialog).create().show();
        }

    }

    private AlertDialog.Builder setPositiveButton1(AlertDialog.Builder builder) {
        return builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击了确认按钮
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.ms-excel");//设置excel文件类型
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });
    }

    private AlertDialog.Builder setPositiveButton2(AlertDialog.Builder builder) {
        return builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeMat();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {//如果选择了文件

            progressDialog = new Dialog(Settings.this,R.style.progress_dialog);
            progressDialog.setContentView(R.layout.dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            msg.setText("fighting!!!");
            progressDialog.show();

            Uri uri = data.getData();
            final String filePath = FileUtils.getPath(this, uri);
            System.out.println("filePath:uri" + uri + "----path:" + filePath);
            Toast.makeText(this,"filePath:" + filePath, Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //在线程中分析excel表中的信息，并导入到本地数据库中
                    boolean result = importInfor(filePath);
                    if(result) {
                        handler.sendEmptyMessage(0x123);
                    }
                    else {
                        handler.sendEmptyMessage(0x234);
                    }
                }
            }).start();
        }
    }

    private AlertDialog.Builder setNegativeButton1(AlertDialog.Builder builder) {
        return builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击了取消按钮
            }
        });
    }
    private AlertDialog.Builder setNegativeButton2(AlertDialog.Builder builder) {
        return builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private boolean importInfor(String filePath) {
        ImportStuInfo importStuInfo = new ImportStuInfo(this,filePath);
        return importStuInfo.importInfo();
    }

    private void initBottomButton() {

        marking = (Button) findViewById(R.id.marking);
        marking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Settings.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        searching = (Button) findViewById(R.id.searching);
        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Settings.this, Search.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        managing = (Button) findViewById(R.id.managing);
        managing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(Settings.this, Settings.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//                finish();
            }
        });
    }


}
