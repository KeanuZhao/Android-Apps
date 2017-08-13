package com.finalp.keanuzhao.myapplication.User;

import android.support.v4.app.Fragment;

import com.finalp.keanuzhao.myapplication.Common.SingleFragmentActivity;


/**
 * Created by KeanuZhao on 07/03/2017.
 */

public class PostPublishActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PostPublishFragment();
    }
}

