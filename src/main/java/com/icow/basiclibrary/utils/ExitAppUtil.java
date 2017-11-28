package com.icow.basiclibrary.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Android退出程序的工具类 1.在Activity的onCreate()的方法中调用addActivity()方法添加到activityList
 * 2.你可以在Activity的onDestroy()的方法中调用removeActivity()来删除已经销毁的Activity实例
 * 这样避免了activityList容器中有多余的实例而影响程序退出速度
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class ExitAppUtil {

    /**
     * 装载Activity的容器
     */
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ExitAppUtil instance = new ExitAppUtil();

    /**
     * 将构造函数私有化
     */
    private ExitAppUtil() {
    }

    /**
     * 获取ExitAppUtils的实例，保证只有一个ExitAppUtils实例存在
     *
     * @return
     */
    public static ExitAppUtil getInstance() {
        return instance;
    }

    /**
     * 添加Activity实例到activityList中，在onCreate()中调用
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 从容器中删除多余的Activity实例，在onDestroy()中调用
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 退出程序的方法
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public Activity getTopActivity() {
        if (null != activityList && activityList.size() > 0) {
            return activityList.get(activityList.size() - 1);
        } else {
            return null;
        }
    }

    public int getActivitiesCount() {
        return (null == activityList) ? 0 : activityList.size();
    }

    /**
     * 退出程序的方法
     */
    public void exitAllExceptMainActivity() {
        for (Activity activity : activityList) {
            if (null != activity) {
                if (activity.getComponentName().toString().contains("MainActivity")) {
                    continue;
                } else {
                    activity.finish();
                }
            }
        }
    }

    public void clearActivityList(){
        activityList.clear();
    }

}