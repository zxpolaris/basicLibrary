package com.icow.basiclibrary.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 屏幕工具类
 *
 * @author Kevin.Zhang
 * @version 1.0.0
 */
public class ScreenUtil {

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;  // 屏幕宽度（像素）
    }


}
