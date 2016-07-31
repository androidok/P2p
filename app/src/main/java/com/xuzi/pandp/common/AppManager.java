package com.xuzi.pandp.common;

import android.app.Activity;

import java.util.Stack;

/**
 * 统一app程序当中所有的activity栈管理
 * <p/>
 * 添加、删除指定、删除当前、删除所有、求栈大小。。。
 */
public class AppManager {
    private static AppManager appManager = null;
    private Stack<Activity> activityStack = new Stack<>();

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (appManager != null) {
            appManager = new AppManager();
        }
        return appManager;
    }

    /**
     * 添加activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 移除指定的activity
     * @param activity  指定的activity
     */
    public void removeActivity(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            if (activity1.getClass().equals(activity.getClass())) {
                activity1.finish();
                activityStack.remove(activity1);
                break;
            }
        }
    }

    /**
     * 移除当前activity
     */
    public void removeCurrent() {
        Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }

    /**
     * 移除栈中的所有activity
     */
    public void removeAll() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            activity1.finish();
            activityStack.remove(activity1);
        }
    }

    /**
     * 获取activity栈中的大小
     * @return
     */
    public int getSize(){
        return activityStack.size();
    }

}
