package com.niu.dialogfragment.widget;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.niu.dialogfragment.activity.BaseActivity;
import com.niu.dialogfragment.fragment.BaseFragment;

/**
 * Created by niuxiaowei on 2015/10/15.
 * 自定义dialog，是所有自定义dialog的基类
 */
public class BaseDialogFragment extends DialogFragment {

    protected BaseActivity mBaseActivity;


    private static final String EXTRA_DIALOG_TITLE_KEY = "extra_dialog_title_key";
    private static final String EXTRA_DIALOG_MESSAGE_KEY = "extra_dialog_message_key";
    private static final String EXTRA_DIALOG_CANELABLE_KEY = "extra_dialog_cancelable";
    private static final String EXTRA_DIALOG_IS_CUSTOM_KEY = "extra_dialog_is_custom";
    private static final String EXTRA_DIALOG_ID_KEY = "extra_dialog_id";

    //是否是自定义dialog
    protected boolean mIsCustomDialog = false;
    //每个对话框的ID
    protected int mDialogId;
    protected boolean mIsCancelable;
    protected String mTitle;


    protected boolean mIsParseDialogListener;
    /**
     * 基础的dialog listener，没有提供任何的方法，扩展的dialog，若该dialog有listener则必须继承本接口
     */
    public static interface BaseDialogListener{

    }

    @Override
    public void onResume() {
        super.onResume();
        BaseDialogListener listener = null;


        if(!mIsParseDialogListener){
            mIsParseDialogListener = true;


            /*/解析dialog listener，fragment的级别要大于activity，若 (getParentFragment() instanceof BaseFragment)为true
            * ，表明是一个fragment调起的dialog，否则是一个activity调起的diaolog
            * */
            if (getParentFragment() instanceof BaseFragment) {
                listener = ((BaseFragment) getParentFragment()).getDialogListener();
            }else if(mBaseActivity != null){
                listener = mBaseActivity.getDialogListener();
            }
            if (listener != null) {
                onReceiveDialogListener(listener);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getActivity() instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) getActivity();
        }


    }

    /**
     * 接收dialog listener对象，具体由子类进行实现
     * @param listener
     */
    protected void onReceiveDialogListener(BaseDialogListener listener){

    }


    /**
     * 从bundle中解析参数，args有可能来自fragment被系统回收，然后界面又重新被启动系统保存的参数；也有可能是其他使用者带过来的
     * ，具体实现交给子类
     *
     * @param args
     */
    protected void parseArgs(Bundle args) {
        mDialogId = args.getInt(EXTRA_DIALOG_ID_KEY);
        mTitle = args.getString(EXTRA_DIALOG_TITLE_KEY);
        mIsCancelable = args.getBoolean(EXTRA_DIALOG_CANELABLE_KEY);
        mIsCustomDialog = args.getBoolean(EXTRA_DIALOG_IS_CUSTOM_KEY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArgs( getArguments());
        setCancelable(mIsCancelable);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
         /*销毁basefragment或BaseActivity中的dialog listener，
           *  同理BaseFragment级别要高于BaseActivity
            * */
        if (getParentFragment() instanceof BaseFragment) {
            ((BaseFragment) getParentFragment()).clearDialogListener();
        }else if(mBaseActivity != null){
            mBaseActivity.clearDialogListener();
        }

    }


    protected static void putIdParam(Bundle args, int dialogId) {
        if (args != null) {
            args.putInt(EXTRA_DIALOG_ID_KEY, dialogId);
        }
    }

    @NonNull
    protected static void putIsCustomParam(Bundle args, boolean isCustomDialog) {
        args.putBoolean(EXTRA_DIALOG_IS_CUSTOM_KEY, isCustomDialog);
    }

    @NonNull
    protected static void putTitleParam(Bundle bundler, String title) {

        bundler.putString(EXTRA_DIALOG_TITLE_KEY, title);
    }

    @NonNull
    protected static void putCancelableParam(Bundle bundle, boolean cancelable) {
        bundle.putBoolean(EXTRA_DIALOG_CANELABLE_KEY, cancelable);
    }

    @NonNull
    protected static void putMessageParam(Bundle bundler, String message) {

        bundler.putString(EXTRA_DIALOG_MESSAGE_KEY, message);
    }


    protected String parseMessageParam() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return null;
        }
        return bundle.getString(EXTRA_DIALOG_MESSAGE_KEY);
    }


}
