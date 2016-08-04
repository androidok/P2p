package com.xuzi.pandp.common;

import android.view.View;

/**
 * Created by xuzi on 8/4/2016.
 */
public abstract class BaseHolder<T> {
    private View rootView;
    private T mData;

    public BaseHolder() {
        this.rootView = initView();
        this.rootView.setTag(this);
    }


    public View getRootView() {
        return rootView;
    }

    public void setData(T t) {
        this.mData = t;
        refreshView();
    }


    public T getData() {
        return mData;
    }

    protected abstract View initView();

    protected abstract void refreshView();
}
