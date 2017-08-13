package com.finalp.keanu.mark.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.finalp.keanu.mark.R;

import java.util.ArrayList;

/**
 * Created by miaos on 2017/4/10.
 */
public class GroupAdapter extends BaseAdapter{

    private View preView;

    private Context context;
    private LayoutInflater inflater;
    public ArrayList<Integer> datas;


    public GroupAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        datas = new ArrayList<Integer>();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.group_item,null);
        }
        final Button groupId = (Button) convertView.findViewById(R.id.groupID);
//        Toast.makeText(context,"clicked!",Toast.LENGTH_SHORT).show();

        int pos = position + 1;
        groupId.setText("Group " + pos);
        return convertView;
    }

    private class listener implements View.OnClickListener {

        private View view;

        public listener(View view) {
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            if(preView == null) {
                view.setBackgroundResource(R.drawable.groupclicked);
                preView = view;
            } else {
                preView.setBackgroundDrawable(view.getBackground());
                view.setBackgroundResource(R.drawable.groupclicked);
                preView = view;
            }
        }
    }

}
