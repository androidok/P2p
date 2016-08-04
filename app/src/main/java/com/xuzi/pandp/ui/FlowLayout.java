package com.xuzi.pandp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuzi on 8/4/2016.
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量  ，得到控件的宽高
     *
     * @param widthMeasureSpec  ----宽度测量规格
     * @param heightMeasureSpec ----高度测量规格
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        //MeasureSpec.AT_MOST模式下求取控件的宽高值
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //测量子View的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();

            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin > widthSize) {
                //换行
                width = Math.max(width, lineWidth);
                height += lineHeight;
                //重置变量
                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            } else {
                //不换行
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            }

            if (i == count - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    //每一行所包含的所有子View
    private List<List<View>> allViews = new ArrayList<>();

    private List<Integer> allHeights = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineWidth = 0;
        int lineHeight = 0;

        int width = getWidth();
        int childCount = getChildCount();

        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();
            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin > width) {
                //换行
                allViews.add(lineViews);
                allHeights.add(lineHeight);
                //重置变量
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            }
            //不换行
            lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            lineViews.add(child);
            if (i == childCount - 1) {
                allViews.add(lineViews);
                allHeights.add(lineHeight);
            }
        }
        Log.e("xuzi", allViews.size() + "---" + allHeights.size());

        //摆放每一个子View

        int top = 0;
        for (int i = 0; i < allViews.size(); i++) {
            int left = 0;
            int curLineHeight = allHeights.get(i);
            List<View> views = allViews.get(i);
            for (View view : views) {
                int viewWidth = view.getMeasuredWidth();
                int viewHeight = view.getMeasuredHeight();
                MarginLayoutParams mp = (MarginLayoutParams) view.getLayoutParams();
                left += mp.leftMargin;
                view.layout(left, top + mp.topMargin, left + viewWidth, top + viewHeight + mp.topMargin);
                left += viewWidth + mp.rightMargin;
            }
            top += curLineHeight;
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(getContext(), attrs);
        return marginLayoutParams;
    }
}
