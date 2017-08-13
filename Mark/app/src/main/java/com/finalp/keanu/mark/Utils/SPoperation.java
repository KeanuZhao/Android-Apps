package com.finalp.keanu.mark.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by miaos on 2017/3/27.
 */
public class SPoperation {

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SPoperation(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("marking", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

}
