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

import com.finalp.keanu.mark.R;

import java.util.ArrayList;



/**
 * Created by miaos on 2017/4/8.
 */
public class MatAdapterI extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    public ArrayList<String> arr;

    public MatAdapterI(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        arr = new ArrayList<String>();
    }


    @Override
    public int getCount() {
        return arr.size();
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
            convertView = inflater.inflate(R.layout.input_item,null);
        }
        final TextView itemX = (TextView) convertView.findViewById(R.id.itemX);
        final int pos = position + 1;
        itemX.setText("Item " + pos + ": ");
        final EditText editText = (EditText) convertView.findViewById(R.id.itemContent);
        editText.setText(arr.get(position));
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(arr.size() > 0) {
                    System.out.println("setText size:" + arr.size());
                    System.out.println("setText position:" + position);
                    if(arr.size() != position)
                        arr.set(position,editText.getText().toString());
                    else
                        arr.set(position - 1,editText.getText().toString());
                }
            }
        });

        final Button dells = (Button) convertView.findViewById(R.id.dells);
        dells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("old size:" + arr.size());
                arr.remove(position);
                System.out.println("new size:" + arr.size());
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
