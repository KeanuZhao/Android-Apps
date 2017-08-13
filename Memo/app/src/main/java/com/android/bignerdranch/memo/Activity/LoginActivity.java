package com.android.bignerdranch.memo.Activity;

import android.support.v4.app.Fragment;

import com.android.bignerdranch.memo.Fragment.LoginFragment;


public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
