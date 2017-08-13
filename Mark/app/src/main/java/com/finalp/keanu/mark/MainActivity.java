package com.finalp.keanu.mark;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.finalp.keanu.mark.Adapter.GroupAdapter;
import com.finalp.keanu.mark.Adapter.GroupNumAdapter;
import com.finalp.keanu.mark.Adapter.MarkAdapter;
import com.finalp.keanu.mark.Entitys.MarkEntitys;
import com.finalp.keanu.mark.Entitys.MarkItem;
import com.finalp.keanu.mark.Entitys.StuEntitys;
import com.finalp.keanu.mark.Utils.DBserviceMark;
import com.finalp.keanu.mark.Utils.DBserviceStu;
import com.finalp.keanu.mark.Utils.FileUtils;
import com.finalp.keanu.mark.Utils.SPoperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Handler handler;

    private Button marking;
    private Button searching;
    private Button managing;

    private ListView groupList;
    private GroupAdapter groupAdapter;
    private List<Integer> getGroup = new ArrayList<Integer>();

    private ListView markList1;
    private MarkAdapter markAdapter1;

    private ListView markList2;
    private MarkAdapter markAdapter2;

    private Button saving;
    private Button resetting;

    private String remarkContent;

    private int clickedGroupNumber;

    private ListView groupNumList;
    private GroupNumAdapter groupNumAdapter;
    private List<StuEntitys> stuNumList = new ArrayList<StuEntitys>();

    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = FileUtils.readBitMap(this, R.drawable.bg);
        Drawable drawable = new BitmapDrawable(bitmap);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootLayout);
        linearLayout.setBackgroundDrawable(drawable);

        initStatus();//设置是否已经导入学生和评分细则信息
        if (!status) {
            final AlertDialog.Builder tipDialog = new AlertDialog.Builder(this)
                    .setTitle("Notice:")
                    .setMessage(R.string.importTip)
		    .setCancelable(false);
            setPositiveButton1(tipDialog).create().show();
        } else {
            initHandler();
            initBottomButton();
            getGroupList();
            initMark();
            initOPbutton();
            getCriteriaWidth();
        }
    }


    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x111) {
                    initGroupList();
                    initDefaultGroupNumber();
                } else if (msg.what == 0x222) {
                    //保存成功
                    Toast.makeText(MainActivity.this, "saving success!", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 0x404) {
                    Toast.makeText(MainActivity.this, "Some items has not been filled in!", Toast.LENGTH_SHORT).show();
                }else if (msg.what == 0x405) {
                    Toast.makeText(MainActivity.this, "Error mark!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void initOPbutton() {

        resetting = (Button) findViewById(R.id.reseting);
        resetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int leng = markAdapter1.datas.size();
                for (int i = 0; i < leng; i++) {
                    markAdapter1.datas.get(i).setItemContent("");
                }
                markAdapter1.notifyDataSetChanged();
                leng = markAdapter2.datas.size();
                for (int i = 0; i < leng; i++) {
                    markAdapter2.datas.get(i).setItemContent("");
                }
                markAdapter2.notifyDataSetChanged();
                setRemark();
            }
        });
        saving = (Button) findViewById(R.id.saving);
        saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isContinue = true;
                int totalMarks = 0;
                for (int i = 0; i < markAdapter1.datas.size(); i++) {
                    if (markAdapter1.datas.get(i).getItemContent().equals("")) {
                        isContinue = false;
                        break;
                    }
                    int tempMark = Integer.parseInt("0" + markAdapter1.datas.get(i).getItemContent());
                    totalMarks += tempMark;
                }
                for (int i = 0; i < markAdapter2.datas.size(); i++) {
                    if (markAdapter2.datas.get(i).getItemContent().equals("")) {
                        isContinue = false;
                        break;
                    }
                    int tempMark = Integer.parseInt("0" + markAdapter2.datas.get(i).getItemContent());
                    totalMarks += tempMark;
                }

                boolean MarkisTrue = true;
                SPoperation sPoperation = new SPoperation(MainActivity.this);
                String totalMark = sPoperation.getPreferences().getString("totalMark", "-1");
                System.out.println("mark:" + totalMark + "  totalmarks:" + totalMarks);
                if (Integer.parseInt("0" + totalMark) < totalMarks) {
                    isContinue = false;
                    MarkisTrue = false;
                }

                if (isContinue) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MarkEntitys markEntitys = new MarkEntitys();
                            markEntitys.setGroupNumber(groupAdapter.datas.get(clickedGroupNumber));
                            markEntitys.setSheetName(groupNumAdapter.list.get(0).getSheetName());
                            final EditText remarks = (EditText) findViewById(R.id.remarks);
                            remarkContent = remarks.getText().toString();
                            markEntitys.setReMark(remarkContent);
                            int totalMarks = 0;
                            ArrayList<Integer> listMark = new ArrayList<Integer>();
                            for (int i = 0; i < markAdapter1.datas.size(); i++) {
                                int tempMark = Integer.parseInt("0" + markAdapter1.datas.get(i).getItemContent());
                                listMark.add(tempMark);
                                totalMarks += tempMark;
                            }
                            for (int i = 0; i < markAdapter2.datas.size(); i++) {
                                int tempMark = Integer.parseInt("0" + markAdapter2.datas.get(i).getItemContent());
                                listMark.add(tempMark);
                                totalMarks += tempMark;
                            }
                            System.out.println("totalmark: saving" + totalMarks);
                            markEntitys.setTotalMark(totalMarks);
                            markEntitys.setMarkMateria(listMark);
                            DBserviceMark dBserviceMark = new DBserviceMark(MainActivity.this);
                            dBserviceMark.add(markEntitys);
                            handler.sendEmptyMessage(0x222);
                        }
                    }).start();
                } else {
                    if (MarkisTrue == false) {
                        handler.sendEmptyMessage(0x405);
                    } else
                        handler.sendEmptyMessage(0x404);
                }

            }
        });
    }

    private void initDefaultGroupNumber() {
        groupNumList = (ListView) findViewById(R.id.groupNumber);
        groupNumAdapter = new GroupNumAdapter(this);
        groupNumAdapter.list.addAll(setGroupNumber(getGroup.get(0)));
        groupNumList.setAdapter(groupNumAdapter);
        setListViewHeightBasedOnChildren(groupNumList);
    }

    private void initCriteria() {
        final TextView criterias = (TextView) findViewById(R.id.markMateria);
        StringBuffer temp = new StringBuffer();
        temp.append("Criteria:" + "\n");
        StringBuffer nextLine = new StringBuffer();
//        criterias.getWidth()
        if (getFromS(3) != null) {
            List<String> list = getFromS(3);
            int len = criterias.getWidth() / list.size();
//            System.out.println("criterias.width:" + criterias.getWidth() + "   len" + len);
            for (int i = 0; i < list.size(); i++) {
                System.out.println("initCriteriaList:" + list.get(i).toString());
                String[] tmpStr = list.get(i).split("%");
                temp.append(getFixedLenString(tmpStr[0].trim(), 18, ' '));
//                System.out.println("initCriteriaTemp:" + temp);
                if (tmpStr.length > 2)
                    nextLine.append(getFixedLenString(("" + tmpStr[1].trim() + "-" + tmpStr[2].trim()) + "               ", 20 + i % 3 * 4, ' '));
//                System.out.println("initCriteriaNextLine:" + nextLine);
            }
            temp.append("\n").append(nextLine);
            System.out.println("initCriteria:" + temp);
            criterias.setText(temp);
        }

    }

    private View preView;

    private void initGroupList() {
        groupList = (ListView) findViewById(R.id.groupList);
        groupAdapter = new GroupAdapter(this);
        groupAdapter.datas.addAll(getGroup);
        groupList.setAdapter(groupAdapter);
        setListViewHeightBasedOnChildren(groupList);
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button btn = (Button) view.findViewById(R.id.groupID);
                if (preView == null) {
                    btn.setBackgroundResource(R.drawable.groupclicked);
                    preView = btn;
                } else {
                    preView.setBackgroundResource(R.drawable.groupunclick);
                    btn.setBackgroundResource(R.drawable.groupclicked);
                    preView = btn;
                }

                System.out.println("clickListener:" + "itemClick");
                System.out.println("initGroupList:" + position);
                clickedGroupNumber = position;
                //获取到组号，根据组号获取到所在组的成员信息，然后刷新listview的内容，并重置填写的分数
                groupNumAdapter.list.clear();
                groupNumAdapter.list.addAll(setGroupNumber(getGroup.get(position)));
                groupNumAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(groupNumList);

                //清空填写的分数,markAdapterI+2
                //判断数据库中是否有当前小组的分数记录，有的话，就显示出来，没有就清空
                DBserviceMark db = new DBserviceMark(MainActivity.this);
                MarkEntitys mark = db.getGroupMark(getGroup.get(position));
                if (mark != null) {
                    System.out.println("dbData:" + mark.toString());
                    int length1 = markAdapter1.datas.size();
                    for (int i = 0; i < length1; i++) {
                        markAdapter1.datas.get(i).setItemContent(mark.getMarkMateria().get(i) + "");
                    }
                    markAdapter1.notifyDataSetChanged();

                    int length2 = markAdapter2.datas.size();
                    for (int i = 0; i < length2; i++) {
                        markAdapter2.datas.get(i).setItemContent(mark.getMarkMateria().get(length1 + i) + "");
                    }
                    markAdapter2.notifyDataSetChanged();

                    final EditText remark = (EditText) findViewById(R.id.remarks);
                    remark.setText(mark.getReMark());
                } else {
                    int length = markAdapter1.datas.size();
                    for (int i = 0; i < length; i++) {
                        markAdapter1.datas.get(i).setItemContent("");
                    }
                    markAdapter1.notifyDataSetChanged();

                    length = markAdapter2.datas.size();
                    for (int i = 0; i < length; i++) {
                        markAdapter2.datas.get(i).setItemContent("");
                    }
                    markAdapter2.notifyDataSetChanged();

                    setRemark();
                }

            }
        });

    }

    private void setRemark() {
        final EditText remarking = (EditText) findViewById(R.id.remarks);
        remarking.setText("");
    }

    private void initMark() {

        final TextView inputName1 = (TextView) findViewById(R.id.inputName1);
        final TextView inputName2 = (TextView) findViewById(R.id.inputName2);
        SPoperation sp = new SPoperation(this);
        inputName1.setText(sp.getPreferences().getString("inputName1", "inputName1"));
        inputName2.setText(sp.getPreferences().getString("inputName2", "inputName2"));


        markList1 = (ListView) findViewById(R.id.markingList1);
        markAdapter1 = new MarkAdapter(this);
        markList1.setAdapter(markAdapter1);
        markAdapter1.datas.clear();
        //从sp中获取条目数据，并放到数据中
        if (getFromS(1) != null) {
            for (String str : getFromS(1)) {
                markAdapter1.datas.add(new MarkItem(str.trim()));
                System.out.println("initMark1:" + str);
            }
            markAdapter1.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(markList1);
        }

        markList2 = (ListView) findViewById(R.id.markingList2);
        markAdapter2 = new MarkAdapter(this);
        markList2.setAdapter(markAdapter2);
        markAdapter2.datas.clear();
        if (getFromS(2) != null) {
            for (String str : getFromS(2)) {
                markAdapter2.datas.add(new MarkItem(str.trim()));
                System.out.println("initMark2:" + str);
            }
            markAdapter2.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(markList2);
        }


        final EditText remarks = (EditText) findViewById(R.id.remarks);
        remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setTotalMark();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setTotalMark() {
        int totalMarks = 0;
        ArrayList<Integer> listMark = new ArrayList<Integer>();
        for (int i = 0; i < markAdapter1.datas.size(); i++) {
            int tempMark = Integer.parseInt("0" + markAdapter1.datas.get(i).getItemContent());
            listMark.add(tempMark);
            totalMarks += tempMark;
        }
        for (int i = 0; i < markAdapter2.datas.size(); i++) {
            int tempMark = Integer.parseInt("0" + markAdapter2.datas.get(i).getItemContent());
            listMark.add(tempMark);
            totalMarks += tempMark;
        }
        System.out.println("totalmark:change:" + totalMarks);
        final TextView totalmark = (TextView) findViewById(R.id.totalMark);
        totalmark.setText(totalMarks + "");
    }

    private ArrayList<String> getFromS(int type) {
        SPoperation sPoperation = new SPoperation(this);
        if (type == 1) {
            String result1 = sPoperation.getPreferences().getString("inputName1item", null);
            if (result1 != null) {
                result1 = result1.substring(1, result1.length() - 1);

//                String[] str = result1.split(",");
                List<String> list = Arrays.asList(result1.split(","));
//                resultList = new ArrayList<String>(list);
                return new ArrayList<String>(list);
            }
        } else if (type == 2) {
            String result2 = sPoperation.getPreferences().getString("inputName2item", null);
            if (result2 != null) {
                result2 = result2.substring(1, result2.length() - 1);
//                String[] str = result2.split(",");
                List<String> list = Arrays.asList(result2.split(","));
//                resultList = new ArrayList<String>(list);
                return new ArrayList<String>(list);
            }
        } else if (type == 3) {
            String result = sPoperation.getPreferences().getString("cri", null);
            if (result != null) {
                result = result.substring(1, result.length() - 1);
                String[] str = result.split(",");
                List<String> list = Arrays.asList(result.split(","));
//                resultList = new ArrayList<String>(list);
                return new ArrayList<String>(list);
            }
        }
        return null;
    }

    private List<StuEntitys> setGroupNumber(int groupNumber) {
        DBserviceStu dBserviceStu = new DBserviceStu(this);
        return dBserviceStu.getByGroupNumber(groupNumber);
    }


    private void initStatus() {
//        status
        SPoperation sp = new SPoperation(this);
        boolean importMark = sp.getPreferences().getBoolean("importStu", false);
        boolean importMateria = sp.getPreferences().getBoolean("importMateria", false);
        if (importMark && importMateria) {
            status = true;
        }

    }

    private AlertDialog.Builder setPositiveButton1(AlertDialog.Builder builder) {
        return builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击了确认按钮
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                MainActivity.this.finish();
            }
        });
    }

    private void getGroupList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBserviceStu dBserviceMark = new DBserviceStu(MainActivity.this);
                List<Integer> list = dBserviceMark.getGroupName();
                if (list != null) {
                    getGroup.addAll(dBserviceMark.getGroupName());
                    handler.sendEmptyMessage(0x111);
                }
            }
        }).start();
    }


    private void initBottomButton() {

        final TextView courseName = (TextView) findViewById(R.id.courseName);
        SPoperation sp = new SPoperation(this);
        courseName.setText(sp.getPreferences().getString("courseName","courseName") + ":Please give marks");

        marking = (Button) findViewById(R.id.marking);
        marking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//                finish();
            }
        });

        searching = (Button) findViewById(R.id.searching);
        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Search.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        managing = (Button) findViewById(R.id.managing);
        managing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

    public static String getFixedLenString(String str, int len, char c) {
        if (str == null || str.length() == 0) {
            str = "";
        }
        if (str.length() == len) {
            return str;
        }
        if (str.length() > len) {
            return str.substring(0, len);
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < len) {
            sb.append(c);
        }
        return sb.toString();
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

    private void getCriteriaWidth() {
        ViewTreeObserver vto = findViewById(R.id.markMateria).getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                findViewById(R.id.markMateria).getViewTreeObserver().removeGlobalOnLayoutListener(this);
                initCriteria();
            }
        });
//        ViewTreeObserver vto = view.getViewTreeObserver(); 
//        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener(){
//                @Override 
//                        public voidonGlobalLayout() {
//                      view.getViewTreeObserver().removeGlobalOnLayoutListener(this);     
//                    int height =view.getMeasuredHeight();
//                     int width =view.getMeasuredWidth(); 
//                    } 
//        });
    }
}
