package com.niu.dialogfragment.widget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.niu.dialogfragment.R;


/**
 * Created by niuxiaowei on 2015/10/15.
 * 自定义进度dialog
 */
public class ProgressDialogFragment extends BaseDialogFragment {

    public static ProgressDialogFragment newInstance(String message, boolean cancelable){

        ProgressDialogFragment dialog = new ProgressDialogFragment();
        Bundle bundle = new Bundle();
        putMessageParam(bundle,message);
        putCancelableParam(bundle,cancelable);
        dialog.setArguments(bundle);
        return  dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(!mIsCustomDialog){

            ProgressDialog dialog = new ProgressDialog(getActivity());
            String message = parseMessageParam();
            dialog.setMessage(message);
            dialog.setCancelable(mIsCancelable);
            return dialog;
        }else{
            return super.onCreateDialog(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mIsCustomDialog){
            View view = inflater.inflate(R.layout.dialog_custom_progress,container,false);
            //启用窗体的扩展特性。
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            return view;
        }else{
            return super.onCreateView(inflater,container,savedInstanceState);
        }
    }
}
