package com.niu.dialogfragment.widget;

import android.app.AlertDialog;
import android.app.Dialog;
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
 * Created by niuxiaowei on 2016/2/3.
 * 列表dialog
 */
public class ListDialogFragment extends BaseDialogFragment{

    private String[] mItemContents;
    private ListDialogListener mListener;

    public static interface ListDialogListener extends BaseDialogListener{
        void onItemClick(int position);
    }

    /**
     * @param itemContents
     * @param cancelable
     * @return
     */
    public static ListDialogFragment newInstance(String[] itemContents,boolean cancelable){

        ListDialogFragment dialog = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("items", itemContents);
        putCancelableParam(bundle, cancelable);
        dialog.setArguments(bundle);
        return  dialog;
    }

    @Override
    protected void onReceiveDialogListener(BaseDialogListener listener) {
        super.onReceiveDialogListener(listener);
        if(listener instanceof ListDialogListener){
            mListener = (ListDialogListener)listener;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(!mIsCustomDialog){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setItems(mItemContents, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(mListener != null){
                        mListener.onItemClick(which);
                    }
                }
            });

            return builder.create();
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
        mItemContents = args.getStringArray("items");
    }
}
