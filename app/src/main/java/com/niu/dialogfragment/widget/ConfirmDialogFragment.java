package com.niu.dialogfragment.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.niu.dialogfragment.R;


/**
 * Created by niuxiaowei on 2015/10/16.
 */
public class ConfirmDialogFragment extends BaseDialogFragment {

    private String message ;
    private ConfirmDialogListener mListener;

    /**
     * 确认对话框的listener
     */
    public interface ConfirmDialogListener extends BaseDialogListener,DialogInterface.OnClickListener{

    }

    /**
     * @param title
     * @param message
     * @param cancelable
     * @return
     */
    public static ConfirmDialogFragment newInstance(String title, String message,boolean cancelable){
        ConfirmDialogFragment instance = new ConfirmDialogFragment();
        Bundle args = new Bundle();
        putTitleParam(args, title);
        putMessageParam(args, message);
        putCancelableParam(args, cancelable);
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected void onReceiveDialogListener(BaseDialogListener listener) {
        if(listener instanceof ConfirmDialogListener){
            mListener = (ConfirmDialogListener)listener;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(!mIsCustomDialog){


            AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle(mTitle== null?getString(R.string.app_name):mTitle).setMessage(message== null?" ":message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mListener.onClick(dialog,which);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mListener.onClick(dialog,which);
                        }
                    }).create();
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

    @Override
    protected void parseArgs(Bundle args) {
        super.parseArgs(args);
        message = parseMessageParam();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
