package com.xuzi.pandp.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by xuzi on 2016/8/1.
 * 自定义可伸缩（弹性）ScrollView
 */
public class MyScrollView extends ScrollView {

    private View view;
    private float y;
    private Rect normal = new Rect();
    private boolean animatiomnFinish = true;

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
        if (animatiomnFinish) {
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
                    if (isNeedMove()) {
                        //
                        if (normal.isEmpty()) {
                            normal.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                        }
                        view.layout(view.getLeft(), view.getTop() - detailY / 2, view.getRight(), view.getBottom() - detailY / 2);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    y = 0;
                    if (isNeedAnimation()) {
                        animation();
                    }
                    break;
            }
        }
    }

    private void animation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, normal.top - view.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                animatiomnFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                animatiomnFinish = true;
                view.layout(normal.left, normal.top, normal.right, normal.bottom);
                normal.setEmpty();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(ta);
    }

    /**
     * 判断是否需要移动
     *
     * @return
     */
    public boolean isNeedMove() {
        int offset = view.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        Log.e("xuzi","offset:"+offset+"scrollY:"+scrollY+"getMeasuredHeight:"+view.getMeasuredHeight());
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要回滚
     *
     * @return
     */
    public boolean isNeedAnimation() {
        if (!normal.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }
}
