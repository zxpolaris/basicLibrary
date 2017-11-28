package com.icow.basiclibrary.utils;

import android.util.Log;

import com.icow.basiclibrary.config.AppUrlConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Logger工具类
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class LoggerUtil {

    static {
        Logger.init("iCow").methodCount(0).hideThreadInfo().logLevel(AppUrlConfig.DEBUG_LOG_ENABLE ? LogLevel.FULL : LogLevel.NONE);
    }

    public static void v(String msg) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.v(msg);
        }
    }

    public static void d(String msg) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.d(msg);
        }
    }

    public static void i(String msg) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.i(msg);
        }
    }

    public static void w(String msg) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.w(msg);
        }
    }

    public static void json(String json) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.xml(xml);
        }
    }

    public static void e(Throwable throwable, String msg) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.e(throwable, msg);
        }
    }

    public static void e(String msg) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.e(msg);
        }
    }

    public static void e(Throwable tr) {
        if(AppUrlConfig.DEBUG_LOG_ENABLE) {
            Logger.t(generateTag(getCallerStackTraceElement()));
            Logger.e(Log.getStackTraceString(tr));
        }
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
