package com.finalp.keanuzhao.myapplication.User;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.finalp.keanuzhao.myapplication.Common.Comment;
import com.finalp.keanuzhao.myapplication.Common.Post;
import com.finalp.keanuzhao.myapplication.Common.PostLab;
import com.finalp.keanuzhao.myapplication.Common.Utility;
import com.finalp.keanuzhao.myapplication.Common.WebServiceGET;
import com.finalp.keanuzhao.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by KeanuZhao on 05/03/2017.
 */

public class PostFragment extends Fragment {
    public static final String EXTRA_POST_ID = "NewFinal.CRIME_ID";
    private int count = 10;
    private Handler mHandler = new Handler();
    private Handler commentHandler = new Handler();
    private Handler loadHandler = new Handler();
    private Post mPost;
    private TextView mTitleField;
    private TextView mContentField;
    private EditText mCommentField;
    private ArrayList<Comment> mComments;
    private Button mButton;
    private String info,id,postid,date,writer,content,load;
    private UUID postId;
    private Comment c;
    private SimpleAdapter adapter;
    private ListView mListView;

    public static PostFragment newInstance(UUID postId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_POST_ID, postId);

        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postId = (UUID)getArguments().getSerializable(EXTRA_POST_ID);
        mPost = PostLab.get(getActivity(),"1").getPost(postId);


//        for (int i=0;i<12;i++){
//            Comment c = new Comment();
//            c.setWriter("ZTL " + i);
//            c.setContent(i + "ZTL");
//            mComments.add(c);
//
//        }



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_post_display,container,false);
        mTitleField = (TextView) v.findViewById(R.id.crime_title);
        mTitleField.setText(mPost.getTitle());
        mContentField = (TextView) v.findViewById(R.id.content);
        mContentField.setText(mPost.getContent());
        mListView = (ListView) v.findViewById(R.id.post_comments_list_view);

        new Thread(new loadThread()).start();



        mCommentField = (EditText) v.findViewById(R.id.user_designed_comment);
        mButton = (Button) v.findViewById(R.id.comment_submit);

        new Thread(new MyCountDownTimer()).start();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mCommentField.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(getActivity(),"No content",Toast.LENGTH_SHORT).show();
                    return;
                }
                c = new Comment();
                c.setContent(s);
                c.setWriter(PostListFragment.USER_NAME);

                id = c.getmId().toString();
                postid = postId.toString();
                date = c.getDate();
                writer = c.getWriter();
                content = c.getContent();
                mPost.setRead(true);
               // v.postInvalidate();
                if (!PostListFragment.user.getRead().contains(postid))
                    PostListFragment.user.addRead(postid);
                new Thread(new MyThread()).start();


            }

        });


        return v;
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            firstRefresh();
//        }
//    }


    private class loadThread implements Runnable{

        @Override
        public void run() {

            mComments = mPost.getComments(postId);

            if (mComments == null){

                mComments = new ArrayList<>();
            }
            ArrayList<HashMap<String,String>> myList = new ArrayList<HashMap<String, String>>();

            for (int i = 0; i < mComments.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("title", mComments.get(i).getContent().replace("rfvtgbyhn"," ").replace("qazwsxedc","?"));
                map.put("writer", mComments.get(i).getWriter());
                map.put("date", mComments.get(i).getDate().toString());
                myList.add(map);
            }
            adapter = new SimpleAdapter(getActivity(), myList, R.layout.list_item_post_comment, new String[]{"title", "writer", "date"}, new int[]{R.id.post_list_item_comment_contentTextView, R.id.post_list_item_comment_writer, R.id.post_list_item_comment_dateTextView});
            loadHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListView.setAdapter(adapter);
                    Utility.setListViewHeightBasedOnChildren(mListView);
                    //System.out.println("Finish-------------------------");
                }
            });

        }
    }

    private class MyThread implements Runnable{

        @Override
        public void run() {
            info = WebServiceGET.executeHttpAddComment(id,postid,date.replace(" ","_"),writer,content);
            //System.out.println("info: " + info);
            commentHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (info.equals("suc")){
                        mComments.add(c);
                        mPost.setRead(true);
                        Toast.makeText(getActivity(),"Success add ",Toast.LENGTH_SHORT).show();
                        mCommentField.setText("");

                        adapter.notifyDataSetChanged();

                        ArrayList<HashMap<String,String>> myList = new ArrayList<>();
                        for (int i = 0; i < mComments.size(); i++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("title", mComments.get(i).getContent().replace("rfvtgbyhn"," ").replace("qazwsxedc","?"));
                            map.put("writer", mComments.get(i).getWriter());
                            map.put("date", mComments.get(i).getDate().toString());
                            myList.add(map);
                        }
                        adapter = new SimpleAdapter(getActivity(), myList, R.layout.list_item_post_comment, new String[]{"title", "writer", "date"}, new int[]{R.id.post_list_item_comment_contentTextView, R.id.post_list_item_comment_writer, R.id.post_list_item_comment_dateTextView});
                        mListView.setAdapter(adapter);
                        Utility.setListViewHeightBasedOnChildren(mListView);
                    }
                    else{
                        Toast.makeText(getActivity(),"Fail add",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private class MyCountDownTimer implements Runnable{

        @Override
        public void run() {
            while(count > 0){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mButton.setClickable(false);
                        mButton.setEnabled(false);
                        mButton.setText("After " + count +"s you can public comments.");
                    }
                });
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mButton.setClickable(true);
                    mButton.setEnabled(true);
                    mButton.setText("Submit");
                }
            });

        }
    }


}
