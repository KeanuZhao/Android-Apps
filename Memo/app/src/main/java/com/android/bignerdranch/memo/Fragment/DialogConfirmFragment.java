package com.android.bignerdranch.memo.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.android.bignerdranch.memo.Activity.LoginActivity;
import com.android.bignerdranch.memo.R;
import com.android.bignerdranch.memo.Activity.RegisterActivity;

/**
 * Created by KeanuZhao on 2017/5/21.
 */

public class DialogConfirmFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_confirm, null);

        builder.setView(v).setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(getActivity(),"Register" , Toast.LENGTH_LONG).show();
                Intent intent =new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        builder.setView(v).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(getActivity(),"Login", Toast.LENGTH_LONG).show();
                Intent intent =new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });




        return builder.create();
    }


}
