package com.android.bignerdranch.memo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bignerdranch.memo.Activity.MemoPagerActivity;
import com.android.bignerdranch.memo.DataStructure.Memo;
import com.android.bignerdranch.memo.Database.MemoDbManager;
import com.android.bignerdranch.memo.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MemoListFragment extends Fragment {

    //private RecyclerView mMemoRecyclerView;
    private MemoAdapter mMemoAdapter;

    private MemoDbManager mdbManager;

    @Bind(R.id.memo_recycler_view)
    RecyclerView mMemoRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);
        ButterKnife.bind(this,view);
//        mMemoRecyclerView = (RecyclerView) view
//                .findViewById(R.id.memo_recycler_view);
        mMemoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //menu构筑
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_memo:
                Memo m = new Memo();
                MemoDbManager.get(getActivity()).addMemo(m);
                Intent intent = MemoPagerActivity
                        .newIntent(getActivity(), m.getMemoId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {

        MemoDbManager memoLab = MemoDbManager.get(getActivity());
        List<Memo> allmemos = memoLab.getMemos();

        if (mMemoAdapter == null) {
            mMemoAdapter = new MemoAdapter(allmemos);
            mMemoRecyclerView.setAdapter(mMemoAdapter);
        } else {
            mMemoAdapter.notifyDataSetChanged();
        }
    }

    private class MemoHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;

//        @Bind(R.id.list_item_memo_title_text_view)
//        TextView mTitleTextView;
//        @Bind(R.id.list_item_memo_date_text_view)
//        TextView mDateTextView;

        private Memo mMemo;

        public MemoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_memo_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_memo_date_text_view);
            
        }

        public void bindMemo(Memo m) {
            mMemo = m;
            mTitleTextView.setText(mMemo.getMemoTitle());
            mDateTextView.setText(mMemo.getMemoDate().toString());
            
        }


        @Override
        public void onClick(View v) {
            Intent intent = MemoPagerActivity.newIntent(getActivity(), mMemo.getMemoId());
            startActivity(intent);
        }
    }

    private class MemoAdapter extends RecyclerView.Adapter<MemoHolder> {

        private List<Memo> mMemolist;

        public MemoAdapter(List<Memo> crimes) {
            mMemolist = crimes;
        }

        @Override
        public MemoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_memo, parent, false);
            return new MemoHolder(view);
        }

        @Override
        public void onBindViewHolder(MemoHolder holder, int position) {
            Memo crime = mMemolist.get(position);
            holder.bindMemo(crime);
        }

        @Override
        public int getItemCount() {
            return mMemolist.size();
        }
    }
}
