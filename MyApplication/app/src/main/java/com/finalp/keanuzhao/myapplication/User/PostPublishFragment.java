package com.finalp.keanuzhao.myapplication.User;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.finalp.keanuzhao.myapplication.Common.Post;
import com.finalp.keanuzhao.myapplication.Common.WebServiceGET;
import com.finalp.keanuzhao.myapplication.R;


/**
 * Created by KeanuZhao on 07/03/2017.
 */

public class PostPublishFragment extends Fragment {

    private EditText mTitleField;
    private EditText mContentField;
    private Button mButton;
    private String info,id,title,date,content,writer;
    private Handler handler = new Handler();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.publish_own_post);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_publish,null);
        mTitleField = (EditText) v.findViewById(R.id.post_title);
        mContentField = (EditText) v.findViewById(R.id.post_details);
        mButton = (Button) v.findViewById(R.id.publish_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = mTitleField.getText().toString();
                content = mContentField.getText().toString();
                String username = PostListFragment.USER_NAME;
                Post p = new Post();
                p.setContent(content);
                p.setWriter(username);
                p.setTitle(title);
                id = p.getId().toString();
                date = p.getDate();
                writer = p.getWriter();

                new Thread(new MyThread()).start();


                //Toast.makeText(getActivity(),""+PostLab.get(getActivity()).getPosts().size(),Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
    private class MyThread implements Runnable{

        @Override
        public void run() {
            info = WebServiceGET.executeHttpNewPost(id,title,date.replace(" ","_"),content,writer);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (info.equals("suc")){
                        Toast.makeText(getActivity(),"Waiting for the check",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(),PostListActivity.class);
                        startActivity(i);
                    }
                    if (info.equals("err")){
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                        return ;

                    }
                }
            });
        }
    }
}
