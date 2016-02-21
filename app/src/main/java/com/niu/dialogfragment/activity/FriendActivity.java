package com.niu.dialogfragment.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.niu.dialogfragment.R;
import com.niu.dialogfragment.widget.ConfirmDialogFragment;


/**
 * Created by niuxiaowei on 2016/1/19.
 */
public class FriendActivity extends BaseActivity implements ConfirmDialogFragment.ConfirmDialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        initViews();
    }

    private void initViews(){
        findViewById(R.id.show_pro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogFactory.showProgressDialog("Activity调起的进度条...",true);
            }
        });

        findViewById(R.id.show_confi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogFactory.showConfirmDialog("activity调起确认框","我是Activity中的确认框",true,FriendActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(this,"点击了Activity调起的确认对话框 i="+i,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
