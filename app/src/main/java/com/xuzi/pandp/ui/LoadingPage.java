package com.xuzi.pandp.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xuzi.pandp.R;
import com.xuzi.pandp.util.UiUtils;

/**
 * Created by xuzi on 8/3/2016.
 */
public abstract class LoadingPage extends FrameLayout {

    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    private static final int PAGE_LOADING_STATE = 1;
    private static final int PAGE_ERROR_STATE = 2;
    private static final int PAGE_EMPTY_STATE = 3;
    private static final int PAGE_SUCCESS_STATE = 4;

    private int PAGE_CURRENT_STATE = 1;

    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;

    private LayoutParams lp;

    private ResultState resultState = null;
    private Context mContext;


    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     * 初始化三个界面
     */
    private void init() {
        lp = new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if (loadingView == null) {
            loadingView = UiUtils.inflate(R.layout.page_loading);
            addView(loadingView, lp);
        }
        if (errorView == null) {
            errorView = UiUtils.inflate(R.layout.page_error);
            addView(errorView, lp);
        }
        if (emptyView == null) {
            emptyView = UiUtils.inflate(R.layout.page_empty);
            addView(emptyView, lp);
        }

        showSafePage();
    }

    /**
     * 安全展示界面,在主线程中运行
     */
    private void showSafePage() {
        UiUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    private void showPage() {
        loadingView.setVisibility(PAGE_CURRENT_STATE == PAGE_LOADING_STATE ? VISIBLE : GONE);
        errorView.setVisibility(PAGE_CURRENT_STATE == PAGE_ERROR_STATE ? VISIBLE : GONE);
        emptyView.setVisibility(PAGE_CURRENT_STATE == PAGE_EMPTY_STATE ? VISIBLE : GONE);
        if (successView == null) {
            successView = View.inflate(mContext, LayoutId(), null);
            addView(successView, lp);
        }
        successView.setVisibility(PAGE_CURRENT_STATE == PAGE_SUCCESS_STATE ? VISIBLE : GONE);
    }

    /**
     * @return
     */
    protected abstract int LayoutId();

    /**
     * 网络请求
     */
    public void show() {
        //状态归为
        if (PAGE_CURRENT_STATE != PAGE_LOADING_STATE) {
            PAGE_CURRENT_STATE = PAGE_LOADING_STATE;
        }
        //处理不需要发送请求的情况
        if (TextUtils.isEmpty(url())) {
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
        } else {
            asyncHttpClient.get(url(), params(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String content) {
                    Log.e("xuzi", "comtent:" + content);
                    if (TextUtils.isEmpty(content)) {
                        resultState = resultState.EMPTY;
                        resultState.setContent("");

                    } else {
                        resultState = resultState.SUCCESS;
                        resultState.setContent(content);
                    }
                    loadPage();
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    resultState = resultState.ERROR;
                    resultState.setContent("");
                    loadPage();
                }
            });
        }

    }

    /**
     * 根据请求结果更改当前要显示的状态
     */
    private void loadPage() {
        switch (resultState) {
            case ERROR:
                PAGE_CURRENT_STATE = PAGE_ERROR_STATE;
                break;
            case EMPTY:
                PAGE_CURRENT_STATE = PAGE_EMPTY_STATE;
                break;
            case SUCCESS:
                PAGE_CURRENT_STATE = PAGE_SUCCESS_STATE;
                break;
        }
        showSafePage();
        if (PAGE_CURRENT_STATE == PAGE_SUCCESS_STATE) {
            OnSuccess(resultState, successView);
        }
    }

    protected abstract void OnSuccess(ResultState resultState, View successView);

    /**
     * 网络请求参数
     *
     * @return
     */
    protected abstract RequestParams params();

    /**
     * 网络请求地址
     *
     * @return
     */
    protected abstract String url();

    public enum ResultState {
        ERROR(2), EMPTY(3), SUCCESS(4);

        private int state;

        private String content;

        ResultState(int state) {
            this.state = state;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
