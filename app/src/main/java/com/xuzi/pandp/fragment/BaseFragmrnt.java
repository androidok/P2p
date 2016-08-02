package com.xuzi.pandp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuzi.pandp.util.UiUtils;

import butterknife.ButterKnife;

/**
 * Created by xuzi on 8/2/2016.
 */
public abstract class  BaseFragmrnt extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UiUtils.inflate(getLayoutId());
        ButterKnife.bind(this, view);
        initTitle();
        initData();
        return view;

    }

    protected abstract void initData();

    protected abstract void initTitle();

    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
