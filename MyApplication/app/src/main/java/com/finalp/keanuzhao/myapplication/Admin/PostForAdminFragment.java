package com.finalp.keanuzhao.myapplication.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finalp.keanuzhao.myapplication.Common.Post;
import com.finalp.keanuzhao.myapplication.Common.PostLab;
import com.finalp.keanuzhao.myapplication.R;

import java.util.UUID;

/**
 * Created by KeanuZhao on 19/04/2017.
 */

public class PostForAdminFragment extends Fragment {

    public static final String EXTRA_POST_ID = "NewFinal.CRIME_ID";
    private Post mPost;
    private TextView mTitleField;
    private TextView mContentField;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID postId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_POST_ID);
        mPost = PostLab.get(getActivity(),"admin").getPost(postId);
        getActivity().setTitle("Post");



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_post_display_for_admin,container,false);
        mTitleField = (TextView) v.findViewById(R.id.crime_title);
        mTitleField.setText(mPost.getTitle());
        mContentField = (TextView) v.findViewById(R.id.content);
        mContentField.setText(mPost.getContent());
//        ListView listView = (ListView) v.findViewById(R.id.post_comments_list_view);
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = 800;
//        listView.setLayoutParams(params);
//
//        ArrayList<HashMap<String,String>> myList = new ArrayList<HashMap<String, String>>();
//        for (int i=0;i<mComments.size();i++){
//            HashMap<String,String> map = new HashMap<String, String>();
//            map.put("title",mComments.get(i).getContent());
//            map.put("writer",mComments.get(i).getWriter());
//            map.put("date",mComments.get(i).getDate().toString());
//            myList.add(map);
//        }
//        SimpleAdapter adapter = new SimpleAdapter(getActivity(),myList,R.layout.list_item_post_comment,new String[]{"title","writer","date"},new int[]{R.id.post_list_item_comment_contentTextView,R.id.post_list_item_comment_writer,R.id.post_list_item_comment_dateTextView});
//        listView.setAdapter(adapter);
//        Utility.setListViewHeightBasedOnChildren(listView);
//
//        mCommentField = (EditText) v.findViewById(R.id.user_designed_comment);
//        mButton = (Button) v.findViewById(R.id.comment_submit);
//
//        new Thread(new MyCountDownTimer()).start();
//
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s = mCommentField.getText().toString();
//                if (s.equals("")) {
//                    Toast.makeText(getActivity(),"null",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Comment c = new Comment();
//                c.setContent(s);
//                c.setWriter(PostListFragment.USER_NAME);
//                mComments.add(c);
//                Utility.setUserReadPosts(mPost);
//                mPost.setRead(true);
//                Toast.makeText(getActivity(),"success add "+c.getWriter(),Toast.LENGTH_SHORT).show();
//                v.postInvalidate();
//                mCommentField.setText("");
//
//            }
//        });


        return v;
    }
}
