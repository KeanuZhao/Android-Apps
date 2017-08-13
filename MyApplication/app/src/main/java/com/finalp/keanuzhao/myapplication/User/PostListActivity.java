package com.finalp.keanuzhao.myapplication.User;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.finalp.keanuzhao.myapplication.Common.SingleFragmentActivity;


public class PostListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PostListFragment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

}
