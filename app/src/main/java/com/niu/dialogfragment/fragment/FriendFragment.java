package com.niu.dialogfragment.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.niu.dialogfragment.R;
import com.niu.dialogfragment.adapter.FriendAdapter;
import com.niu.dialogfragment.data.Friend;
import com.niu.dialogfragment.widget.ConfirmDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by niuxiaowei on 2016/1/19.
 */
public class FriendFragment extends BaseFragment implements ConfirmDialogFragment.ConfirmDialogListener{
    private RecyclerView mRecyclerView;
    private FriendAdapter mAdapter;
    private FriendAdapter.FriendsListener mListener = new FriendAdapter.FriendsListener(){

        Friend longClickFriend ;
        @Override
        public void onFriendItemLongClick(Friend longClickFriend) {
            this.longClickFriend = longClickFriend;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        addFriends();
    }

    private void addFriends(){
        List<Friend> fs = new ArrayList<Friend>(5);
        Random r = new Random(1000);
        for (int i = 0; i < 20; i++) {
            Friend f = new Friend();
            f.mLoginId = "11111";
            f.mUserId = r.nextLong() + "";
            f.mName = "张三"+i;
            f.mAge = 20;
            fs.add(f);
        }
        mAdapter.setData(fs);
    }

    public void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mAdapter = new FriendAdapter(mBaseActivity,mDialogFactory,mListener);
        mRecyclerView.setAdapter(mAdapter);



        getView().findViewById(R.id.show_confi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mDialogFactory.showConfirmDialog("fragment调起的对话框","我是fragment调起的确认对话框",true,FriendFragment.this);
            }
        });
        getView().findViewById(R.id.show_pro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogFactory.showProgressDialog("fragment中调起的的进度条 ...",true);
            }
        });
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(getActivity(),"点击了fragment调起的确认对话框 which="+which,Toast.LENGTH_LONG).show();
    }
}
