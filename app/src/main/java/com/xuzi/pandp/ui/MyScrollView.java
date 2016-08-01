package com.xuzi.pandp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by xuzi on 2016/8/1.
 */
public class MyScrollView extends ScrollView {

    private View view;
    private float y;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count > 0) {
            view = getChildAt(0);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (view == null) {
            return super.onTouchEvent(ev);
        } else {
            commonTouchEvent(ev);
        }
        return super.onTouchEvent(ev);

    }

    /**
     * 自定义的tocuh事件
     *
     * @param ev
     */
    private void commonTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float preY = y == 0 ? ev.getY() : y;
                float nowY = ev.getY();
                int detailY = (int) (preY - nowY);
                y = nowY;
                if (isNeedMove()){
                    
                }
                break;
            case MotionEvent.ACTION_UP:
                y = 0;
                break;
        }
    }

    /**
     * 判断是否需要移动
     * @return
     */
    public boolean isNeedMove() {

        return false;
    }
}
