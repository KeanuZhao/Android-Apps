package com.android.bignerdranch.memo.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bignerdranch.memo.R;
import com.android.bignerdranch.memo.DataStructure.User;
import com.android.bignerdranch.memo.Database.UserDbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KeanuZhao on 2017/4/24.
 */

public class UserListFragment extends Fragment {

    private RecyclerView mUserRecyclerView;
    private UserAdapter mAdapter;
    private UserDbManager mdbManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        mUserRecyclerView = (RecyclerView) view
                .findViewById(R.id.user_recycler_view);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {

        mdbManager = new UserDbManager(getActivity());
        List<User> allusers = new ArrayList<User>();
        allusers = mdbManager.getUsers();
        mAdapter = new UserAdapter(allusers);
        mUserRecyclerView.setAdapter(mAdapter);
        mdbManager.close();
    }


    private class UserHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mPassTextView;
        private TextView mDateTextView;
        private TextView muuIdTextView;

        public UserHolder(View itemView) {
            super(itemView);

            mNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_user_name_text_view);
            mPassTextView = (TextView)
                    itemView.findViewById(R.id.list_item_user_pass_text_view);
            muuIdTextView = (TextView)
                    itemView.findViewById(R.id.list_item_user_id_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_date_text_view);

        }

    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        private List<User> mUserlist;



        public UserAdapter(List<User> users) {
            mUserlist = users;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_user, parent, false);
            return new UserHolder(view);
        }
        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            User recycleu = mUserlist.get(position);
            holder.mNameTextView.setText(recycleu.getAccountName());
            holder.mPassTextView.setText(recycleu.getAccountPass());
            holder.muuIdTextView.setText(recycleu.getAccountId().toString() );
            holder.mDateTextView.setText(recycleu.getDate().toString());

        }
        @Override
        public int getItemCount() {
            return mUserlist.size();
        }
    }

}
