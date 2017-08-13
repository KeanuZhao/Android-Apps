package com.finalp.keanuzhao.myapplication.User;

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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.finalp.keanuzhao.myapplication.Common.LoginActivity;
import com.finalp.keanuzhao.myapplication.Common.Post;
import com.finalp.keanuzhao.myapplication.Common.PostDatabaseHelper;
import com.finalp.keanuzhao.myapplication.Common.PostLab;
import com.finalp.keanuzhao.myapplication.Common.User;
import com.finalp.keanuzhao.myapplication.Common.WebServiceGET;
import com.finalp.keanuzhao.myapplication.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by KeanuZhao on 02/03/2017.
 */

public class PostListFragment extends ListFragment {

    private ArrayList<Post> mPosts;
    public static String  USER_NAME;
    public static String USER_READ = null;
   // public static ArrayList<>
    private String info;
    public static User user;
    private PostDatabaseHelper mHelper;
    private Handler handler = new Handler();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        USER_NAME = getActivity().getIntent().getStringExtra("id");
        USER_READ = getActivity().getIntent().getStringExtra("read");
       // Toast.makeText(getActivity(),"PostListFragment " ,Toast.LENGTH_SHORT).show();
        if (USER_NAME==null)
            return ;
        //Toast.makeText(getActivity(),"PostList " + USER_NAME + USER_READ,Toast.LENGTH_SHORT).show();
        user = new User();
        user.setName(USER_NAME);
        if (USER_READ!=null) {
            ArrayList<String> read = new ArrayList<String>();
            read = splitRead(USER_READ);
            user.setRead(read);
            for (int i=0;i<read.size();i++){
                UUID id = UUID.fromString(read.get(i));
                PostLab.get(getActivity(),"1").getPost(id).setRead(true);
            }


        }
        getActivity().setTitle(R.string.posts_title);
       // new Thread(new MyThread()).start();

        mPosts = PostLab.get(getActivity(),"1").getPosts();
        PostAdapter adapter = new PostAdapter(mPosts);
        setListAdapter(adapter);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);
        View emptyView = inflater.inflate(R.layout.empty_post,null);
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        ((ViewGroup)listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
        registerForContextMenu(listView);

        mPosts = PostLab.get(getActivity(),"1").getPosts();
        PostAdapter adapter = new PostAdapter(mPosts);
        setListAdapter(adapter);
        getActivity().setTitle(R.string.posts_title);
        setHasOptionsMenu(true);
        return v;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_post_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_post:

                if (user.getRead().size()<3) {
                    Toast.makeText(getActivity(), "You must comment three posts first.", Toast.LENGTH_SHORT).show();
                    return super.onOptionsItemSelected(item);
                }
                else {
                    Intent in = new Intent(getActivity(), PostPublishActivity.class);
                    startActivityForResult(in,1);
                    return true;
                }
            case R.id.menu_item_show_read:
                Toast.makeText(getActivity(),""+user.getRead().size(),Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_logout:
                final StringBuilder sb = new StringBuilder();
                if (user.getRead().size()!=0) {
                    for (int i = 0; i < user.getRead().size(); i++) {
                        sb.append(user.getRead().get(i));
                        if (i != (user.getRead().size() - 1))
                            sb.append(",");

                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("---------------------tuichu : " + sb.toString());
                            info = WebServiceGET.executeHttpAddRead(user.getName(), sb.toString());

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (info.equals("suc")) {
                                        USER_NAME = null;
                                        USER_READ = null;
                                        PostLab.sPostLab = null;

                                    }
                                }
                            });
                        }
                    }).start();
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    return true;
                }
                else{
                    Toast.makeText(getActivity(),"No read",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
                //Toast.makeText(getActivity(), "StringBuilder: " + sb.toString(), Toast.LENGTH_SHORT).show();



            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((PostAdapter)getListAdapter()).notifyDataSetChanged();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ((PostAdapter)getListAdapter()).notifyDataSetChanged();
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.post_list_item_delete,menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Post p = ((PostAdapter)getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(), PostPagerActivity.class);
        i.putExtra(PostFragment.EXTRA_POST_ID, p.getId());
        startActivityForResult(i, 0);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        PostAdapter adapter = (PostAdapter) getListAdapter();
        Post p = adapter.getItem(pos);
        if(USER_NAME.equals("admin")) {
            switch (item.getItemId()) {
                case R.id.menu_item_delete_post:
                    if (p.getWriter().equals(USER_NAME)) {
                        PostLab.get(getActivity(),"1").deletePost(p);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                    Toast.makeText(getActivity(), "You can't delete others' posts", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            return super.onContextItemSelected(item);
        }
        return  super.onContextItemSelected(item);

    }

    private ArrayList<String> splitRead(String read){
        String[] userRead = read.split(",");
        ArrayList<String> readArr = new ArrayList<String>();
        for (int i=0;i<userRead.length;i++){
            readArr.add(userRead[i]);
        }
        return readArr;
    }



    private class PostAdapter extends ArrayAdapter<Post>{
        public PostAdapter(ArrayList<Post> posts){
            super(getActivity(),android.R.layout.simple_list_item_1,posts);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_post,null);
            }
            Post c = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.post_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.post_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());
            CheckBox solvedCheckBox =
                    (CheckBox)convertView.findViewById(R.id.post_list_item_readCheckBox);
            solvedCheckBox.setChecked(c.isRead());

            return convertView;

        }
    }

}

