package com.finalp.keanuzhao.myapplication.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.finalp.keanuzhao.myapplication.Common.Post;
import com.finalp.keanuzhao.myapplication.Common.PostLab;
import com.finalp.keanuzhao.myapplication.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by KeanuZhao on 05/03/2017.
 */

public class PostPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<Post> posts = PostLab.get(this,"1").getPosts();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return posts.size();
            }
            @Override
            public Fragment getItem(int pos) {
                UUID postId =  posts.get(pos).getId();
                return PostFragment.newInstance(postId);
            }
        });

        UUID postId = (UUID)getIntent().getSerializableExtra(PostFragment.EXTRA_POST_ID);
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(postId)) {
                mViewPager.setCurrentItem(i);

                break;
            }
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                Post p = PostLab.get(PostPagerActivity.this).getPosts().get(position);
//                ArrayList<Post> rPosts = PostListFragment.user.getRead();
//                if(!rPosts.contains(p))
//                    rPosts.add(p);
//                System.out.println("Posts: " + rPosts.size());



            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(PostPagerActivity.this,"OnPageSelected "+position,Toast.LENGTH_SHORT).show();
                //System.out.println("OnPageSelected "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                if (state==2)
//                    PostListFragment.user.addRead();
            }
        });
    }
}
