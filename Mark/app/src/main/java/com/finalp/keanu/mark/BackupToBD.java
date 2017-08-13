package com.finalp.keanu.mark;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finalp.keanu.mark.Entitys.ExportItem;
import com.finalp.keanu.mark.Utils.DBinfoStu;
import com.finalp.keanu.mark.Utils.DBserviceMark;
import com.finalp.keanu.mark.Utils.DBserviceStu;
import com.finalp.keanu.mark.Utils.MysqlConn;
import com.finalp.keanu.mark.Utils.SPoperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by miaos on 2017/5/10.
 */
public class BackupToBD extends AppCompatActivity {

    private Button back, commit;
    private EditText ip, dbname, username, password;

    private Handler handler;

    private Connection conn;

    private Dialog progressDialog;

    private List<ExportItem> list = new ArrayList<ExportItem>();

    private long currentMiles = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backuptoserver);

        initView();

        initHandler();
    }

    private void initView() {

        ip = (EditText) findViewById(R.id.ipaddress);
        dbname = (EditText) findViewById(R.id.dbname);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        final TextView courseName = (TextView) findViewById(R.id.courseName);
        SPoperation sp = new SPoperation(this);
        courseName.setText(sp.getPreferences().getString("courseName", "courseName") + ":Backup to Server");

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackupToBD.this.finish();
            }
        });

        commit = (Button) findViewById(R.id.backup);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ip.getText().toString().equals("") || dbname.getText().toString().equals("") || username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    handler.sendEmptyMessage(0x123);
                } else {
                    final String ipAddress = ip.getText().toString().trim();
                    final String URL = "jdbc:mysql://" + ipAddress + "/" + dbname.getText().toString().trim();
                    final String name = username.getText().toString().trim();
                    final String passwd = password.getText().toString().trim();
                    System.out.println("mysql:" + "information:" + URL + " " + name + " " + passwd);

                    progressDialog = new Dialog(BackupToBD.this,R.style.progress_dialog);
                    progressDialog.setContentView(R.layout.dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
                    msg.setText("fighting!!!");
                    progressDialog.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            conn = MysqlConn.openConnection(URL, name, passwd);
                            if (conn == null) {
                                handler.sendEmptyMessage(0x404);
                            } else {
                                getStuList();
                            }
                        }
                    }).start();
                }
            }
        });

    }

    private void backupinfor() {
        //把list导入到数据库
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建表
                try {
                    PreparedStatement ps = conn.prepareStatement(CREATE_SQL());
                    ps.executeUpdate(CREATE_SQL());
                    ps.close();
//                    (id int auto_increment primary key not null, "
//                            + "GroupNumber int, "
//                            + "BuptNumber int, "
//                            + "QMNumber int, "
//                            + "SurName varchar(100), "
//                            + "ForeName varchar(100), "
//                            + "totalMark int);";


                    int length = list.size();
                    for (int i = 0; i < length; i++) {
                        String sql = "insert into mark" + currentMiles + "(GroupNumber,BuptNumber,QMNumber,SurName,ForeName,totalMark) values("
                                + list.get(i).getGroupNumber() + ","
                                + list.get(i).getBuptNumber() + ","
                                + list.get(i).getQMNumber() + ","
                                + '\'' + list.get(i).getSurName() + '\'' + ","
                                + '\'' +list.get(i).getForeName() + '\'' + ","
                                + list.get(i).getTotalMark() + ")";
                        MysqlConn.execSQL(conn,sql);
                        System.out.println("mysql:insert sql:" + sql);
                    }
                    handler.sendEmptyMessage(0x666666);
                    conn.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0x404404);
                }
            }
        }).start();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    Toast.makeText(BackupToBD.this, "Please input the black!", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 0x666) {
                    //得到了list数组，需要导入到数据库了
                    backupinfor();
                } else if (msg.what == 0x404) {
                    progressDialog.dismiss();
                    Toast.makeText(BackupToBD.this, "Parameter error!", Toast.LENGTH_SHORT).show();
                } else if(msg.what == 0x404404) {
                    progressDialog.dismiss();
                    Toast.makeText(BackupToBD.this, "Network ERROR!", Toast.LENGTH_SHORT).show();
                } else if(msg.what == 0x666666) {
                    progressDialog.dismiss();
                    Toast.makeText(BackupToBD.this, "Backup successful!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    //获取学生信息
    private void getStuList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBserviceStu dBserviceStu = new DBserviceStu(BackupToBD.this);
                list = dBserviceStu.exportAll();

                int length = list.size();
                for (int i = 0; i < length; i++) {
                    DBserviceMark dBserviceMark = new DBserviceMark(BackupToBD.this);
                    list.get(i).setTotalMark(dBserviceMark.getTotalMark(list.get(i).getGroupNumber()));
                }
                handler.sendEmptyMessage(0x666);
            }
        }).start();

    }
//    private int totalMark;
//    //    GroupNumber、BuptNumber、QMNumber、SurName、ForeName、
//    private int GroupNumber;
//    private int BuptNumber;
//    private int QMNumber;
//    private String SurName;
//    private String ForeName;
//    private String SheetName;

    private String CREATE_SQL() {
        if (currentMiles == 0)
            currentMiles = System.currentTimeMillis();
        return "create table mark" + currentMiles
                + " (id int auto_increment primary key not null, "
                + "GroupNumber int, "
                + "BuptNumber int, "
                + "QMNumber int, "
                + "SurName varchar(100), "
                + "ForeName varchar(100), "
                + "totalMark int);";
    }

    private String DROP_STU_SQL = "drop table if exists " + DBinfoStu.STU_TABLE;

    private String CREATE_STU_SQL = "create table " + DBinfoStu.STU_TABLE
            + " (id integer primary key autoincrement, "
            + "GroupNumber integer, "
            + "BuptNumber integer, "
            + "QMNumber integer, "
            + "SurName varchar(100), "
            + "ForeName varchar(100), "
            + "SheetName varchar(100))";

    private String DROP_MARK_SQL = "drop table if exists " + DBinfoStu.MARK_TABLE;

    private String CREATE_MARK_SQL() {
        StringBuffer createSQ = null;
        ArrayList<String> arrayList = getCreateSQL();//获取评分细则
        if (arrayList != null) {
            createSQ = new StringBuffer();
            createSQ.append("create table " + DBinfoStu.MARK_TABLE
                    + " (id integer primary key autoincrement, "
                    + "GroupNumber integer, "
                    + "TotalMark integer, "
                    + "Remarks varchar(200), "
                    + "sheetName varchar(100), ");
            int length = arrayList.size() - 1, i;
            for (i = 0; i < length; i++) {
                createSQ.append("item" + i + " integer, ");
            }
            createSQ.append("item" + i + " integer)");
        }
        System.out.println("create:" + createSQ);
        return createSQ.toString();
    }

    //获取存储的评分细则
    private ArrayList<String> getCreateSQL() {
        ArrayList<String> arrayList = null;
        SPoperation sPoperation = new SPoperation(this);
        String materias1 = sPoperation.getPreferences().getString("inputName1item", null);
        if (materias1 != null) {
            String materias2 = sPoperation.getPreferences().getString("inputName2item", null);
            if (materias2 != null) {
                materias1 = materias1.substring(1, materias1.length() - 1);
                materias2 = materias2.substring(1, materias2.length() - 1);
                arrayList = new ArrayList<String>();
                List<String> list1 = Arrays.asList(materias1.split(","));
                List<String> list2 = Arrays.asList(materias2.split(","));
                arrayList.addAll(list1);
                arrayList.addAll(list2);
            }
        }
        return arrayList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            } finally {
                conn = null;
            }
        }
    }

}
