package com.android.bignerdranch.memo.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.bignerdranch.memo.Fragment.MemoFragment;

import java.util.UUID;

/**
 * Created by KeanuZhao on 2017/4/25.
 */

public class MemoActivity extends SingleFragmentActivity {

    private static final String EXTRA_MEMO_ID =
            "com.android.bignerdranch.memo.memo_id";


    public static Intent newIntent(Context packageContext, UUID mmId) {
        Intent intent = new Intent(packageContext, MemoActivity.class);
        intent.putExtra(EXTRA_MEMO_ID, mmId);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        UUID mmId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_MEMO_ID);
            return MemoFragment.newInstance(mmId);
    }

}
