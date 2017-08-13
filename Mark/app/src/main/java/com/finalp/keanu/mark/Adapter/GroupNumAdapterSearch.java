package com.finalp.keanu.mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.finalp.keanu.mark.Entitys.StuEntitys;
import com.finalp.keanu.mark.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaos on 2017/4/10.
 */
public class GroupNumAdapterSearch extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    public List<StuEntitys> list;

    public GroupNumAdapterSearch(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<StuEntitys>();
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.group_num_item_search,null);
        }
        final TextView surname = (TextView)convertView.findViewById(R.id.surname);
        final TextView forename = (TextView) convertView.findViewById(R.id.forename);
        final TextView buptnumber = (TextView) convertView.findViewById(R.id.buptnumber);
        final TextView qmnumber = (TextView) convertView.findViewById(R.id.qmnumber);

        System.out.println("list:" + list.toString());

        surname.setText(list.get(position).getSurName());
        forename.setText(list.get(position).getForeName());
        buptnumber.setText(list.get(position).getBuptNumber() + "");
        qmnumber.setText(list.get(position).getQMNumber() + "");

        return convertView;
    }
}
