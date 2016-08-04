package com.xuzi.pandp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.xuzi.pandp.ui.LoadingPage;

import butterknife.ButterKnife;

/**
 * Created by xuzi on 8/2/2016.
 */
public abstract class BaseFragmrnt extends Fragment {

    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingPage = new LoadingPage(container.getContext()) {
            @Override
            protected int LayoutId() {
                return getLayoutId();
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(BaseFragmrnt.this, successView);
                initTitle();
                initData(resultState);
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };
/*        View view = UiUtils.inflate(getLayoutId());
        ButterKnife.bind(this, view);
        initTitle();
        initData();*/
        return loadingPage;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingPage.show();
    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    protected abstract void initData(LoadingPage.ResultState resultState);

    protected abstract void initTitle();

    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
