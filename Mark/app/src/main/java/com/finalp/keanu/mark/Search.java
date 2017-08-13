package com.finalp.keanu.mark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.finalp.keanu.mark.Utils.FileUtils;
import com.finalp.keanu.mark.Utils.SPoperation;

public class Search extends AppCompatActivity {

    private Button marking,searching,managing;

    private SearchView searchView;
    private ListView chooseList;

    private String[] fileds;//输入m之后自动提示的内容
    private ArrayAdapter<String> mAdapter;//ListView的适配器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bitmap bitmap = FileUtils.readBitMap(this,R.drawable.bg);
        Drawable drawable = new BitmapDrawable(bitmap);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootLayout);
        linearLayout.setBackgroundDrawable(drawable);

        initSearchView();

        final TextView courseName = (TextView) findViewById(R.id.courseName);
        SPoperation sp = new SPoperation(this);
        courseName.setText(sp.getPreferences().getString("courseName","courseName") + ":Search");

        initBottomButton();
    }

    private void initSearchView() {

//        fileds = new String[] {"min", "max"};
//
//        chooseList = (ListView) findViewById(R.id.chooseList);
//        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,fileds);
//        chooseList.setAdapter(mAdapter);
//        chooseList.setTextFilterEnabled(true);

        searchView = (SearchView) findViewById(R.id.searchView);
        //设置右边的搜索提交按钮
        searchView.setSubmitButtonEnabled(true);
        //设置初始展开输入
        searchView.onActionViewExpanded();

        //
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length() != 0) {
                    Intent intent = new Intent(Search.this,SearchResult.class);
                    intent.putExtra("Search",query);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private void initBottomButton() {

        marking = (Button) findViewById(R.id.marking);
        marking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Search.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        searching = (Button) findViewById(R.id.searching);
        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(Search.this, Search.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//                finish();
            }
        });

        managing = (Button) findViewById(R.id.managing);
        managing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Search.this, Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
    }
}
