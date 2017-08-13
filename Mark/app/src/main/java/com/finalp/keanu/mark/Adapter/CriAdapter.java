package com.finalp.keanu.mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.finalp.keanu.mark.Entitys.MatEntitys;
import com.finalp.keanu.mark.R;

import java.util.ArrayList;

/**
 * Created by miaos on 2017/4/8.
 */
public class CriAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    public ArrayList<MatEntitys> datas;

    public CriAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        datas = new ArrayList<MatEntitys>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.materia_row,null);
        }
        final TextView itemX = (TextView) convertView.findViewById(R.id.criteriaX);
        final int pos = position + 1;
        itemX.setText("Mark Descriptor " + pos + ": ");
        final EditText editText = (EditText) convertView.findViewById(R.id.criteriaContent);
        editText.setText(datas.get(position).getCriteriaContent());
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(datas.size() > 0) {
                    if(datas.size() != position)
                        datas.get(position).setCriteriaContent(editText.getText().toString());
                    else
                        datas.get(position - 1).setCriteriaContent(editText.getText().toString());
                }
            }
        });

        final EditText markFrom = (EditText) convertView.findViewById(R.id.markFrom);
        markFrom.setText(datas.get(position).getMarkFrom() + "");
        markFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(datas.size() > 0) {
                    if(datas.size() != position)
                        datas.get(position).setMarkFrom(markFrom.getText().toString());
                    else
                        datas.get(position - 1).setMarkFrom(markFrom.getText().toString());

                }
            }
        });

        final EditText markTo = (EditText) convertView.findViewById(R.id.markTo);
        markTo.setText(datas.get(position).getMarkTo() + "");
        markTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(datas.size() > 0) {
                    if(datas.size() != position)
                        datas.get(position).setMarkTo(markTo.getText().toString());
                    else
                        datas.get(position - 1).setMarkTo(markTo.getText().toString());


                }
            }
        });

        final Button dells = (Button) convertView.findViewById(R.id.dels);
        dells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(position);
                setListViewHeightBasedOnChildren((ListView)parent);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
    private void setAdapter(ListView listView) {
        listView.setAdapter(this);
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
}


