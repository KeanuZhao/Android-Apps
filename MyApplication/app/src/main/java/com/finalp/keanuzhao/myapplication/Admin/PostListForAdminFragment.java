package com.finalp.keanuzhao.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.finalp.keanuzhao.myapplication.Common.LoginActivity;
import com.finalp.keanuzhao.myapplication.Common.Post;
import com.finalp.keanuzhao.myapplication.Common.PostLab;
import com.finalp.keanuzhao.myapplication.Common.WebServiceGET;
import com.finalp.keanuzhao.myapplication.R;
import com.finalp.keanuzhao.myapplication.User.PostListFragment;

import java.util.ArrayList;

/**
 * Created by KeanuZhao on 19/04/2017.
 */

public class PostListForAdminFragment extends ListFragment {

    private ArrayList<Post> mPosts;
    private Handler handler = new Handler();
    private String info,id;
    private Post tPost;
    private PostAdapter tAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosts = PostLab.get(getActivity(),"admin").getPosts();
        PostAdapter adapter = new PostAdapter(mPosts);
        setListAdapter(adapter);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.admin_posts);
        //Toast.makeText(getActivity(),"onCreate" + mPosts.size(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);
        View emptyView = inflater.inflate(R.layout.empty_post,null);
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        ((ViewGroup)listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
        registerForContextMenu(listView);

//        mPosts = PostLab.get(getActivity(),"admin").getPosts();
//        PostAdapter adapter = new PostAdapter(mPosts);
//        setListAdapter(adapter);
//        getActivity().setTitle("Posts waiting for check");
        setHasOptionsMenu(true);
        //Toast.makeText(getActivity(),"onCreateView" + mPosts.size(),Toast.LENGTH_SHORT).show();
        return v;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((PostAdapter)getListAdapter()).notifyDataSetChanged();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_admin_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_logout:
                PostListFragment.USER_NAME = null;
                PostListFragment.USER_READ = null;
                PostLab.sPostLab = null;
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.post_list_permit,menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Post p = ((PostAdapter)getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(), PostForAdminActivity.class);
        i.putExtra(PostForAdminFragment.EXTRA_POST_ID, p.getId());
        startActivityForResult(i, 0);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        tAdapter = (PostAdapter) getListAdapter();
        tPost = tAdapter.getItem(pos);

        switch (item.getItemId()) {
            case R.id.menu_item_permit_post:
                PostLab.get(getActivity(),"admin").deletePost(tPost);
                tAdapter.notifyDataSetChanged();
                new Thread(new MyThread()).start();
              //  PostLab.get(getActivity(),"admin").deletePost(p);
             //   adapter.notifyDataSetChanged();
                return true;


        }


        return  super.onContextItemSelected(item);

    }


    private class PostAdapter extends ArrayAdapter<Post> {
        public PostAdapter(ArrayList<Post> posts){
            super(getActivity(),android.R.layout.simple_list_item_1,posts);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_post_for_admin,null);
            }
            Post c = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.post_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.post_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());


            return convertView;

        }
    }

    private class MyThread implements Runnable{

        @Override
        public void run() {


            info = WebServiceGET.executeHttpPermit(tPost.getId().toString());



            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (info.equals("suc")){
                        Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getActivity(),"Failure",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
