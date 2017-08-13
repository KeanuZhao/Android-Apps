package com.android.bignerdranch.memo.Activity;


import android.support.v4.app.Fragment;

import com.android.bignerdranch.memo.Fragment.RegisterFragment;

/**
 * Created by KeanuZhao on 2017/4/22.
 */

public class RegisterActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new RegisterFragment() ;
    }
}
