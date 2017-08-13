package com.android.bignerdranch.memo.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bignerdranch.memo.Activity.MemoListActivity;
import com.android.bignerdranch.memo.Activity.RegisterActivity;
import com.android.bignerdranch.memo.DataStructure.User;
import com.android.bignerdranch.memo.Database.UserDbManager;
import com.android.bignerdranch.memo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KeanuZhao on 2017/3/6.
 */

public class LoginFragment extends Fragment {

    private EditText mFieldName;
    private EditText mFieldPass;
    private Button mConfirm;
    private TextView mRegister;


//    @Bind(R.id.login_name)
//    EditText mFieldName;
//
//    @Bind(R.id.login_pass)
//    EditText mFieldPass;
//
//    @Bind(R.id.login_button)
//    Button mConfirm;
//
//    @Bind(R.id.login_register)
//    Button mRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(getActivity());

    }

//    @Override
//    public int getContentViewId() {
//        return R.layout.fragment_login;
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,v);
        mFieldName = (EditText)v.findViewById(R.id.login_name);
        mFieldPass = (EditText)v.findViewById(R.id.login_pass);

        mConfirm = (Button)v.findViewById(R.id.login_button);


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int judge = userCheck(mFieldName.getText().toString() ,mFieldPass.getText().toString() );

                if(judge == 1){
                    //Toast.makeText(getActivity(),uuid.toString() , Toast.LENGTH_LONG).show();

                    Intent intent =new Intent(getActivity(), MemoListActivity.class);
                    startActivity(intent);

                }
                else if(judge == 0){
                    Toast.makeText(getActivity(), "Incorrect in Username or Password", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Some thing got wrong? ", Toast.LENGTH_LONG).show();
                }


            }
        });

        mRegister = (TextView)v.findViewById(R.id.login_register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);

            }
        });


        return v;

    }

//    @Override
//    protected void initAllMembersView(Bundle savedInstanceState) {
//
//    }

    @OnClick({R.id.login_button,R.id.login_register})
    public void onViewClicked(View view){
        switch(view.getId()){
            case R.id.login_button:
                int judge = userCheck(mFieldName.getText().toString() ,mFieldPass.getText().toString() );
                if(judge == 1){
                    //Toast.makeText(getActivity(),uuid.toString() , Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity(), MemoListActivity.class);
                    startActivity(intent);

                }
                else if(judge == 0){
                    Toast.makeText(getActivity(), "Incorrect in Username or Password", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Some thing got wrong? ", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.login_register:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;

        }

    }

    public int userCheck(String name, String pass){
        int  i=-1;
        String a,b;
        UUID d;
        UserDbManager dbcheck = new UserDbManager(getActivity() );
        List<User> usercheck = new ArrayList<User>();
        usercheck=dbcheck.getUsers();

        for(User p:usercheck){
            a=p.getAccountName();
            b=p.getAccountPass();
            if(name.equals(a)&& pass.equals(b)){

                i=1; //相同
                break;
            }
            else{i=0;} //不同
        }
        dbcheck.close();
        return i;
    }



}
