package com.android.bignerdranch.memo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bignerdranch.memo.Activity.LoginActivity;
import com.android.bignerdranch.memo.R;
import com.android.bignerdranch.memo.DataStructure.User;
import com.android.bignerdranch.memo.Database.UserDbManager;
import com.android.bignerdranch.memo.Activity.UserListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by KeanuZhao on 2017/4/22.
 */

public class RegisterFragment extends Fragment {

    private UserDbManager dbManager;

    @Bind(R.id.reg_name)
    EditText mFieldName;
    @Bind(R.id.reg_pass)
    EditText mFieldPass;
    @Bind(R.id.reg_phone)
    EditText mFieldPhone;
    @Bind(R.id.to_list)
    TextView mList;
    @Bind(R.id.reg_button)
    Button mConfirm;

    private User visitor;

    private static final String DIALOG_CONFIRM = "DialogConfirm";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this,v);
        dbManager = new UserDbManager(getActivity() );

//        mFieldName = (EditText)v.findViewById(R.id.reg_name);
//        mFieldPass = (EditText)v.findViewById(R.id.reg_pass);
//        mFieldPhone = (EditText)v.findViewById(R.id.reg_phone);

//        mConfirm = (Button)v.findViewById(R.id.reg_button);
//        mConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int judgeA = isEmpty(mFieldName.getText().toString(),mFieldPass.getText().toString(),mFieldPhone.getText().toString());
//                int judgeB = isRepetition(mFieldName.getText().toString());
//
//                if((judgeA == 0)&&(judgeB == 0)){
//                    if(mFieldPass.getText().length()<9){
//                        Toast.makeText(getActivity(),"The Password should be longer than 8 bits" , Toast.LENGTH_LONG).show();
//                    }else{
//                        visitor = new User(mFieldName.getText().toString(),mFieldPass.getText().toString(),mFieldPhone.getText().toString());
//                        dbManager.addUser(visitor);
//                        dbManager.close();
//                        FragmentManager manager = getFragmentManager();
//                        DialogConfirmFragment dialog = new DialogConfirmFragment();
//                        dialog.show(manager, DIALOG_CONFIRM);
//                    }
//                }else if((judgeA > 0)&&(judgeB == 0)){
//                    Toast.makeText(getActivity(),"Please fill in all the text boxes" , Toast.LENGTH_LONG).show();
//                }else if((judgeA == 0)&&(judgeB < 0)){
//                    Toast.makeText(getActivity(),"This username has been existed" , Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getActivity(),"Please fill in all the text boxes" , Toast.LENGTH_LONG).show();
//                }
//            }
//        });

//        mList = (TextView)v.findViewById(R.id.to_list);
//        mList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), UserListActivity.class);
//                startActivity(intent);
//            }
//        });

        return v;
    }

    @OnClick({R.id.reg_button,R.id.to_list})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.reg_button:
                int judgeA = isEmpty(mFieldName.getText().toString(),mFieldPass.getText().toString(),mFieldPhone.getText().toString());
                int judgeB = isRepetition(mFieldName.getText().toString());

                if((judgeA == 0)&&(judgeB == 0)){
                    if(mFieldPass.getText().length()<9){
                        Toast.makeText(getActivity(),"The Password should be longer than 8 bits" , Toast.LENGTH_LONG).show();
                    }else{
                        visitor = new User(mFieldName.getText().toString(),mFieldPass.getText().toString(),mFieldPhone.getText().toString());
                        dbManager.addUser(visitor);
                        dbManager.close();
                        FragmentManager manager = getFragmentManager();
                        DialogConfirmFragment dialog = new DialogConfirmFragment();
                        dialog.show(manager, DIALOG_CONFIRM);
                    }
                }else if((judgeA > 0)&&(judgeB == 0)){
                    Toast.makeText(getActivity(),"Please fill in all the text boxes" , Toast.LENGTH_LONG).show();
                }else if((judgeA == 0)&&(judgeB < 0)){
                    Toast.makeText(getActivity(),"This username has been existed" , Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),"Please fill in all the text boxes" , Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.to_list:
                Intent intent = new Intent(getActivity(), UserListActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu_back, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_back:
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int isEmpty(String str1, String str2, String str3){
        int a=0;
        if (TextUtils.isEmpty(str1) || str1.equals("null")) {
            a=1;
        }else if(TextUtils.isEmpty(str2) || str2.equals("null")){
            a=2;
        }else if(TextUtils.isEmpty(str3) || str3.equals("null")){
            a=3;
        }
        return a;
    }

    public int isRepetition(String name) {
        int b=0;
        String a;
        UserDbManager dbcheck = new UserDbManager(getActivity() );
        List<User> usercheck = new ArrayList<User>();
        usercheck=dbcheck.getUsers();
        for(User p:usercheck){
            a=p.getAccountName();
            if(name.equals(a)){
                b=-1; //相同
                break;
            }
            else{b=0;} //不同
        }
        dbcheck.close();
        return b;
    }




}
