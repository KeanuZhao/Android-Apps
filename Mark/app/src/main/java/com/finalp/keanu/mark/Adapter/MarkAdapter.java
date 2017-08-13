package com.finalp.keanu.mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.finalp.keanu.mark.Entitys.MarkItem;
import com.finalp.keanu.mark.R;

import java.util.ArrayList;

/**
 * Created by miaos on 2017/4/11.
 */
public class MarkAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    public ArrayList<MarkItem> datas;

    public MarkAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        datas = new ArrayList<MarkItem>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.mark_item,null);
        }
        final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        itemName.setText(datas.get(position).getItemName());
        final EditText itemContent = (EditText) convertView.findViewById(R.id.itemContent);
        itemContent.setText(datas.get(position).getItemContent() + "");
        itemContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(datas.size() > 0) {
                    if(datas.size() != position)
                        datas.get(position).setItemContent(itemContent.getText().toString());
                    else
                        datas.get(position - 1).setItemContent(itemContent.getText().toString());
                }
            }
        });
        return convertView;
    }
}
