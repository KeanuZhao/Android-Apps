package com.finalp.keanu.mark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.finalp.keanu.mark.Adapter.CriAdapter;
import com.finalp.keanu.mark.Adapter.MatAdapterI;
import com.finalp.keanu.mark.Entitys.MatEntitys;
import com.finalp.keanu.mark.Utils.DBserviceMark;
import com.finalp.keanu.mark.Utils.FileUtils;
import com.finalp.keanu.mark.Utils.SPoperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ChangeMateria extends AppCompatActivity {

    //从两个inputname中获取到大标题，然后从三个listview中获取到具体的数据条目，然后点击保存按钮之后
    //将评分细则保存下来，并保存到数据库，动态建立打分表
    private Handler handler;

    private Button addMore1;
    private Button addMore2;
    private Button addMore3;

    private Button saving;

    private ListView listView1;
    private ListView listView2;
    private ListView listView3;

    public MatAdapterI matAdapterI;
    public MatAdapterI matAdapterII;
    public CriAdapter criAdapter;

    private Button marking;
    private Button searching;
    private Button managing;

    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_materia);

        Bitmap bitmap = FileUtils.readBitMap(this,R.drawable.bg);
        Drawable drawable = new BitmapDrawable(bitmap);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootLayout);
        linearLayout.setBackgroundDrawable(drawable);

        initView();
        initHandler();
        initBottomButton();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x444) {
                    Toast.makeText(ChangeMateria.this,"please input input_nameX!",Toast.LENGTH_SHORT).show();
                }else if(msg.what == 0x123) {
                    Toast.makeText(ChangeMateria.this,"save success!",Toast.LENGTH_SHORT).show();
                    SPoperation sp = new SPoperation(ChangeMateria.this);
                    sp.getEditor().putBoolean("importMateria",true);
                    sp.getEditor().commit();
                    finish();
                }
            }
        };
    }

    private void initView() {

        final EditText inputName1 = (EditText) findViewById(R.id.inputName1);
        inputName1.setText(getFromSPinput(1));
        final EditText inputName2 = (EditText) findViewById(R.id.inputName2);
        inputName2.setText(getFromSPinput(2));
        final EditText totalMark = (EditText) findViewById(R.id.totalMark);
        totalMark.setText(getFromSPinput(3));

        listView1 = (ListView) findViewById(R.id.listName1);
        addMore1 = (Button) findViewById(R.id.addMore1);
        matAdapterI = new MatAdapterI(this);
        listView1.setAdapter(matAdapterI);
        if(getFromS(1) != null) {
            matAdapterI.arr.addAll(getFromS(1));
        } else {
            matAdapterI.arr.add("");
        }
        matAdapterI.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listView1);
        addMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matAdapterI.arr.add("");
                matAdapterI.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listView1);
            }
        });

        listView2 = (ListView) findViewById(R.id.listName2);
        addMore2 = (Button) findViewById(R.id.addMore2);
        matAdapterII = new MatAdapterI(this);
        listView2.setAdapter(matAdapterII);
        if(getFromS(2) != null) {
            matAdapterII.arr.addAll(getFromS(2));
        } else {
            matAdapterII.arr.add("");
        }
        matAdapterII.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listView2);
        addMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matAdapterII.arr.add("");
                matAdapterII.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listView2);
            }
        });

        listView3 = (ListView) findViewById(R.id.listName3);
        addMore3 = (Button) findViewById(R.id.addMore3);
        criAdapter= new CriAdapter(this);
        listView3.setAdapter(criAdapter);
        if(getFromS(3) != null) {
            //从两个hashmap获取到数据并回填到listview中
            List<MatEntitys> listMat = new ArrayList<MatEntitys>();
            List<String> list = getFromS(3);
            for(int i = 0; i < list.size(); i++) {
                String[] temp = list.get(i).split("%");
                MatEntitys matEntitys = new MatEntitys();
                matEntitys.setCriteriaContent(temp[0].trim());
                matEntitys.setMarkFrom(temp[1].trim());
                matEntitys.setMarkTo(temp[2].trim());
                listMat.add(matEntitys);
            }
            criAdapter.datas.addAll(listMat);
        } else {
            criAdapter.datas.add(new MatEntitys("","",""));
        }
        criAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listView3);
        addMore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criAdapter.datas.add(new MatEntitys("","",""));
                criAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listView3);
            }
        });

        saving = (Button)findViewById(R.id.saving);
        saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingOP();
            }
        });
    }

    //点击保存按钮之后的操作
    private void savingOP() {
        final EditText inputName1 = (EditText) findViewById(R.id.inputName1);
        final EditText inputName2 = (EditText) findViewById(R.id.inputName2);
        final EditText totalMark = (EditText) findViewById(R.id.totalMark);
        final EditText courseName = (EditText) findViewById(R.id.courseName);
        if(inputName1.getText().toString().equals("") || inputName2.getText().toString().equals("") || courseName.getText().toString().equals("")) {
            handler.sendEmptyMessage(0x444);
        }
//        测试结果的正确性
//        for(int i = 0; i < matAdapterI.arr.size();i++ ) {
//            System.out.println("matAdapterI:" + matAdapterI.arr.get(i));
//        }
//        for(int i = 0; i < matAdapterII.arr.size();i++) {
//            System.out.println("matAdapterII:" + matAdapterII.arr.get(i));
//        }
//        for(int i = 0; i < criAdapter.datas.size();i++) {
//            System.out.println("criAdapter:" + criAdapter.datas.get(i).getCriteriaContent());
//        }
        else {
            System.out.println("testArray1:" + matAdapterI.arr.toString());
            System.out.println("testArray2:" + criAdapter.datas.toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //保存到sp，inputname1，inputname1item，用两个set保存分数区间
                    SPoperation sPoperation = new SPoperation(ChangeMateria.this);
                    sPoperation.getEditor().putString("inputName1",inputName1.getText().toString());

//                    Set<String> name1Set = new LinkedHashSet<String>();
//                    int length = matAdapterI.arr.size();
//                    for(int i = 0; i < length; i++) {
//                        name1Set.add(matAdapterI.arr.get(i));
//                    }
//                    sPoperation.getEditor().putStringSet("inputName1item",name1Set);
                    System.out.println("testOutput:" + matAdapterI.arr.toString());
                    sPoperation.getEditor().putString("inputName1item",matAdapterI.arr.toString());


                    sPoperation.getEditor().putString("inputName2",inputName2.getText().toString());
//                    Set<String> name2Set = new LinkedHashSet<String>();
//                    length = matAdapterII.arr.size();
//                    for(int i = 0; i < length; i++) {
//                        name2Set.add(matAdapterII.arr.get(i));
//                    }
//                    sPoperation.getEditor().putStringSet("inputName2item",name2Set);
                    sPoperation.getEditor().putString("inputName2item",matAdapterII.arr.toString());

//                    Set<String> criName = new LinkedHashSet<String>();
//                    Set<String> criMark = new LinkedHashSet<String>();
//                    length = criAdapter.datas.size();
//                    for(int i = 0; i < length; i++) {
//                        MatEntitys matEntitys = criAdapter.datas.get(i);
//                        criName.add(matEntitys.getCriteriaContent());
//                        criMark.add(matEntitys.getMarkFrom() + "");
//                        criMark.add(matEntitys.getMarkTo() + "");
//                    }
//                    sPoperation.getEditor().putStringSet("criName",criName);
//                    sPoperation.getEditor().putStringSet("criMark",criMark);
                    sPoperation.getEditor().putString("totalMark",totalMark.getText().toString());
                    sPoperation.getEditor().putString("courseName",courseName.getText().toString());
                    sPoperation.getEditor().putString("cri",criAdapter.datas.toString());
                    sPoperation.getEditor().commit();

                    //更改数据库
                    DBserviceMark dBserviceMark = new DBserviceMark(ChangeMateria.this,true);
                    handler.sendEmptyMessage(0x123);
                }
            }).start();
        }

    }

    //从sharedpreference中获取之前保存的评分细则
    //1 inputName1item 2
    //3 criName  4 inputName2item
    private Set<String> getFromSPold(int type) {
        SPoperation sPoperation = new SPoperation(this);
        LinkedHashSet<String> hashSet = null;
        if(type == 1) {
            hashSet = new LinkedHashSet<>(sPoperation.getPreferences().getStringSet("inputName1item",new HashSet<String>()));
            for(String str:hashSet) {
                System.out.println("hashSet:" + str);
            }
        } else if(type == 2) {
            hashSet = new LinkedHashSet<>(sPoperation.getPreferences().getStringSet("inputName2item",new LinkedHashSet<String>()));
        } else if(type == 3) {
            hashSet = new LinkedHashSet<>(sPoperation.getPreferences().getStringSet("criName",new LinkedHashSet<String>()));
        } else if(type == 4) {
            hashSet = new LinkedHashSet<>(sPoperation.getPreferences().getStringSet("criMark",new LinkedHashSet<String>()));
        }
        return hashSet;
    }
    private ArrayList<String> getFromS(int type) {
        SPoperation sPoperation = new SPoperation(this);
        ArrayList resultList = null;
        if(type == 1) {
            String result1 = sPoperation.getPreferences().getString("inputName1item",null);
            if(result1 != null) {
                result1 = result1.substring(1,result1.length() - 1);

//                String[] str = result1.split(",");
                List<String> list = Arrays.asList(result1.split(","));
//                resultList = new ArrayList<String>(list);
                return new ArrayList<String>(list);
            }
        } else if(type == 2) {
            String result2 = sPoperation.getPreferences().getString("inputName2item",null);
            if(result2 != null) {
                result2 = result2.substring(1,result2.length() - 1);
//                String[] str = result2.split(",");
                List<String> list = Arrays.asList(result2.split(","));
//                resultList = new ArrayList<String>(list);
                return new ArrayList<String>(list);
            }
        } else if(type == 3) {
            String result = sPoperation.getPreferences().getString("cri",null);
            if(result != null) {
                result = result.substring(1,result.length() - 1);
                String[] str = result.split(",");
                List<String> list = Arrays.asList(result.split(","));
//                resultList = new ArrayList<String>(list);
                return new ArrayList<String>(list);
            }
        }
        return null;
    }

    private String getFromSPinput(int type) {
        SPoperation sPoperation = new SPoperation(this);
        if(type == 1) {
            return sPoperation.getPreferences().getString("inputName1","");
        } else if(type == 2) {
            return sPoperation.getPreferences().getString("inputName2","");
        } else if(type == 3) {
            return sPoperation.getPreferences().getString("totalMark","");
        }
        return "";
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listView.setLayoutParams(params);
    }

    private void initBottomButton() {

        marking = (Button) findViewById(R.id.marking);
        marking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChangeMateria.this, MainActivity.class);
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
                intent.setClass(ChangeMateria.this, Search.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        managing = (Button) findViewById(R.id.managing);
        managing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChangeMateria.this, Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
    }

}
