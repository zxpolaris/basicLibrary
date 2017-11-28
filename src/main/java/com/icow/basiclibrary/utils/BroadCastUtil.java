package com.icow.basiclibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 本地广播发送工具类
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class BroadCastUtil {

    /**
     * 发送本地广播(不带Bundle数据)
     *
     * @param context
     * @param action
     */
    public static void sendBroadCast(Context context, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 发送本地广播(带Bundle数据)
     *
     * @param context
     * @param action
     * @param extras
     */
    public static void sendBroadCast(Context context, String action, Bundle extras) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtras(extras);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
