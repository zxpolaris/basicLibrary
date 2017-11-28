package com.icow.basiclibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 工具类 - Android系统常用
 *
 * @author Xun.Zhang
 * @version 1.0
 */
public class AndroidUtil {

    /**
     * 拨打电话
     *
     * @param activity
     * @param phone
     */
    public static void callPhone(Activity activity, String phone) {
        try {
            Uri uri = Uri.parse("tel:" + phone);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showMessage(activity, "不能调用系统拨号程序");
        }
    }

    /**
     * 发送短信
     *
     * @param activity
     * @param phone
     * @param msg
     */
    public static void sendMessage(Activity activity, String phone, String msg) {
        try {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", msg);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showMessage(activity, "不能调用系统短信程序");
        }

    }

    /**
     * 选择系统联系人
     */
    public static void chooseContacts(Activity activity, int requestCode) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showMessage(activity, "不能调用系统联系人程序");
        }
    }

    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    public static void startAppMarket(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showMessage(context, "没有找到相应程序");
        }
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            String pkName = context.getPackageName();
            versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (Exception e) {
        }
        return versionName;
    }

    public static void openBrowser(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showMessage(context, "没有找到相应程序");
        }
    }

    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static boolean isTopActivity(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ComponentName componentName = activityManager.getRunningTasks(1).get(0).topActivity;
        return componentName.getClassName().contains(className);
    }

    public static String getFromAssets(Context context, String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n\r");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 判断是wifi还是3g网络
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkINfo && ConnectivityManager.TYPE_WIFI == networkINfo.getType()) {
            return true;
        }
        return false;
    }

    /**
     * 复制文本到系统剪切板
     *
     * @param context
     * @param clipText
     */
    public static void copyTextToClipboard(Context context, String clipText) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        clipboardManager.setText(clipText);
        ToastUtil.showMessage(context, "已复制到剪切板");
    }

    /**
     * 获取手机设备号（IMEI）
     *
     * @param context
     * @return
     */
    public static String getPhoneDeviceIds(Context context) {
        List<String> deviceIds = new ArrayList<>();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != telephonyManager) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (int slotId = 0; slotId < telephonyManager.getPhoneCount(); slotId++) {
                    deviceIds.add(telephonyManager.getDeviceId(slotId));
                }
            } else {
                deviceIds.add(telephonyManager.getDeviceId());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < deviceIds.size(); i++) {
            sb.append(deviceIds.get(i));
            if (i < deviceIds.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static void startSystemView(Context context,String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
