package com.android.bignerdranch.memo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.bignerdranch.memo.Fragment.MemoFragment;
import com.android.bignerdranch.memo.DataStructure.Memo;
import com.android.bignerdranch.memo.Database.MemoDbManager;
import com.android.bignerdranch.memo.R;

import java.util.List;
import java.util.UUID;

/**
 * Created by KeanuZhao on 2017/4/26.
 */

public class MemoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_PAGE_ID =
            "com.android.bignerdranch.memo.memo_id";

    private ViewPager mViewPager;
    private List<Memo> mMemoPageList;


    public static Intent newIntent(Context packageContext, UUID pmId) {
        Intent intent = new Intent(packageContext, MemoPagerActivity.class);
        intent.putExtra(EXTRA_PAGE_ID, pmId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_pager);

        UUID mId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PAGE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_memo_pager_view_pager);
        mMemoPageList = MemoDbManager.get(this).getMemos();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Memo pmemo = mMemoPageList.get(position);
                return MemoFragment.newInstance(pmemo.getMemoId());
            }
            @Override
            public int getCount() {
                return mMemoPageList.size();
            }
        });

        for (int i = 0; i < mMemoPageList.size(); i++) {
            if (mMemoPageList.get(i).getMemoId().equals(mId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
