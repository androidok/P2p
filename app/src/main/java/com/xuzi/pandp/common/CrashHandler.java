package com.xuzi.pandp.common;

import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * Created by xuzi on 8/1/2016.
 * 全局异常处理类，暂时不使用
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler crashHandler = null;

    private Context mContext;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    public void init(Context context) {
        this.mContext = context;
        //获取系统默认的异常处理器
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置异常处理器为CrashHandler类
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

    }

    /**
     * 收集一下崩溃异常信息
     *
     * @param ex
     */
    private void collectionException(Throwable ex) {
        final String deviceInfo = Build.DEVICE + Build.VERSION.SDK_INT + Build.MODEL + Build.PRODUCT;
        final String errorInfo = ex.getMessage();
        new Thread() {
            @Override
            public void run() {
                Log.e("zoubo", "deviceInfo---" + deviceInfo + ":errorInfo" + errorInfo);
            }
        }.start();
    }
}
