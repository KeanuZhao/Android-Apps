package com.finalp.keanuzhao.myapplication.Admin;

import android.support.v4.app.Fragment;

import com.finalp.keanuzhao.myapplication.Common.SingleFragmentActivity;


/**
 * Created by KeanuZhao on 19/04/2017.
 */

public class PostForAdminActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PostForAdminFragment();
    }
}
