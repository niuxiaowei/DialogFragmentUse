package com.niu.dialogfragment.widget;

import android.os.Bundle;

import java.lang.reflect.Field;

/**
 * 对话框listener持有者
 * Created by niuxiaowei on 2016/2/19.
 */
public class DialogListenerHolder {

    private BaseDialogFragment.BaseDialogListener mDialogListener;

    /**
     * 对话框的listener的key值，用类名作为key值，主要用来在手机配置发生变化时（横屏换为竖屏），当现场恢复时，能正确的找到对话框的listener
     */
    private String mDialogListenerKey;

    /**
     * 获取dialog的listener，具体由子类实现
     * @return
     */
    public BaseDialogFragment.BaseDialogListener getDialogListener(){
        return mDialogListener;
    }


    public void setDialogListener(BaseDialogFragment.BaseDialogListener listener){
        mDialogListener = listener;
        mDialogListenerKey = listener == null ?null:listener.getClass().getName();
    }

    /**
     * 把listener的key值保存在bundle中，配置发生变化的情况下（横屏换为竖屏），在从bundle中取listener的key值
     * @param outState
     */
    public void saveDialogListenerKey(Bundle outState){
        if(outState != null){
            outState.putString("key",mDialogListenerKey);
        }
    }

    /**
     * 从bundle中尝试取出dialog listener key
     * @param savedInstanceState
     */
    public void getDialogListenerKey(Bundle savedInstanceState){
        if(savedInstanceState != null){
            mDialogListenerKey = savedInstanceState.getString("key");
        }
    }

    /**
     * 这个方法很重要，是恢复dialog listener的一个关键点，在初始化DialogFactory或把DialogFactory赋值后，调用该方法，把调用该方法所在
     * 的类的实例作为参数。 该方法会把param中的属性依次遍历，尝试找属性是BaseDialogFragment.BaseDialogListener的实例，
     * 并且该属性就是保存在bundle中的dialog listener key对应的dialog listener
     * @param o
     */
    public void restoreDialogListener(Object o){
        if(o == null){
            return;
        }
        if(!isNeedRestoreDialogListener()){
            return;
        }

        //先尝试找传进来的实例
        if(o instanceof BaseDialogFragment.BaseDialogListener && o.getClass().getName().equals(mDialogListenerKey)){
            setDialogListener((BaseDialogFragment.BaseDialogListener)o);
            return;
        }
        Class c = o.getClass();
        Field[] fs = c.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            try {
                Object instance = f.get(o);
                if((instance instanceof BaseDialogFragment.BaseDialogListener) && instance.getClass().getName().equals(mDialogListenerKey)){
                    setDialogListener((BaseDialogFragment.BaseDialogListener) f.get(o));

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }
    }

    private boolean isNeedRestoreDialogListener(){
        return mDialogListenerKey == null? false:mDialogListener== null;

    }



}
