package com.finalp.keanuzhao.myapplication.Admin;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.finalp.keanuzhao.myapplication.Common.SingleFragmentActivity;


/**
 * Created by KeanuZhao on 19/04/2017.
 */

public class PostListForAdminActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PostListForAdminFragment();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
