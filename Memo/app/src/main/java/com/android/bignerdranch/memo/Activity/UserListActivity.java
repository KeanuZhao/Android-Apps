package com.android.bignerdranch.memo.Activity;

import android.support.v4.app.Fragment;

import com.android.bignerdranch.memo.Fragment.UserListFragment;

/**
 * Created by KeanuZhao on 2017/4/24.
 */

public class UserListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new UserListFragment();
    }
}
