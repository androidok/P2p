package com.xuzi.pandp.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.xuzi.pandp.common.XuziApplication;

/**
 * Created by xuzi on 7/31/2016.
 */
public class UiUtils {

    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    public static Context getContext() {
        return XuziApplication.context;
    }

    public static Handler getHander() {
        return XuziApplication.handler;
    }

    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    public static String[] getStringArray(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    /**
     * dp->px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().densityDpi;
        return (int) (dp * density + 0.5);
    }

    /**
     * px->dp
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().densityDpi;
        return (int) (px / density + 0.5);
    }
}
