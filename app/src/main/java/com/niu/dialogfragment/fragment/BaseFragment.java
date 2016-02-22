package com.niu.dialogfragment.fragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.niu.dialogfragment.activity.BaseActivity;
import com.niu.dialogfragment.widget.BaseDialogFragment;
import com.niu.dialogfragment.widget.DialogFactory;


/**

 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    protected DialogFactory mDialogFactory ;

    public BaseDialogFragment.BaseDialogListener getDialogListener(){
        return mDialogFactory.mListenerHolder.getDialogListener();
    }

    /**
     * 清空DialogListenerHolder中的dialog listener
     */
    public void clearDialogListener(){
        mDialogFactory.mListenerHolder.setDialogListener(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDialogFactory.mListenerHolder.saveDialogListenerKey(outState);
    }








    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialogFactory = new DialogFactory(getChildFragmentManager(),savedInstanceState);
        mDialogFactory.restoreDialogListener(this);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseActivity){
            mBaseActivity = (BaseActivity)activity;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }











}
