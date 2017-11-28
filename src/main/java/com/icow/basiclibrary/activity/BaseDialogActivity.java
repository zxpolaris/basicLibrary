package com.icow.basiclibrary.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.icow.basiclibrary.utils.ExitAppUtil;
import com.icow.basiclibrary.utils.LoggerUtil;
import com.icow.basiclibrary.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

/**
 * 本程序Activity的基类
 *
 * @author Xun.Zhang
 * @version 1.0
 */
public abstract class BaseDialogActivity extends Activity {

    /**
     * TAG为每一个Activity的类名
     */
    protected String TAG = BaseDialogActivity.class.getSimpleName();

    protected Activity mActivity;

    private InputMethodManager mInputMethodManager;
    protected ProgressDialog mProgressDialog;
    protected String mImageloaderCachePath = ImageLoader.getInstance().getDiskCache().getDirectory().getAbsolutePath();

    private BroadcastReceiver mLocalReceiver; // APP本地广播接收器
    private BroadcastReceiver mGlobalReceiver; // APP全局广播接收器

    /**
     * 初始化全局数据和Intent传递过来的数据
     */
    protected abstract void initExtras();

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        TAG = this.getClass().getSimpleName();
        mActivity.getWindow().getDecorView().setFitsSystemWindows(true);

        // 初始化数据
        initExtras();

        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        ExitAppUtil.getInstance().addActivity(this);
        // 初始化控件
        initViews(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(mActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(mActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftInput();
        if (null != mLocalReceiver) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mLocalReceiver);
        }
        if (null != mGlobalReceiver) {
            unregisterReceiver(mGlobalReceiver);
        }
        ExitAppUtil.getInstance().removeActivity(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 点击空白区域隐藏输入法
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            if (null != getCurrentFocus() && null != getCurrentFocus().getWindowToken()) {
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 隐藏输入法
     */
    public boolean hideSoftInput(View v) {
        return mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入法
     */
    public void hideSoftInput() {
        if (null != getWindow()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    /**
     * 开关输入法
     */
    public void toggleSoftInput() {
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示系统toast
     *
     * @param msg
     */
    public void showToastMsg(String msg, boolean... showLong) {
        if (null != showLong && showLong.length > 0) {
            if (showLong[0]) {
                ToastUtil.showMessageLong(this, msg);
            } else {
                ToastUtil.showMessage(this, msg);
            }
        } else {
            ToastUtil.showMessage(this, msg);
        }
    }

    public ProgressDialog showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCanceledOnTouchOutside(false);// 不能取消
            mProgressDialog.setIndeterminate(true);// 设置进度条是否不明确
        }

        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        LoggerUtil.v("showProgressDialog message = " + message);
        return mProgressDialog;
    }

    /**
     * 关闭ProgressDialogs
     */
    public void dismissProgressDialog() {
        if (null != this && !this.isFinishing() && null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void registerLocalBroadcastReceiver(IntentFilter filter) {
        mLocalReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onLocalBroadcastReceive(context, intent);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mLocalReceiver, filter);
    }

    protected void registerGlobalBroadcastReceiver(IntentFilter filter) {
        mGlobalReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onGlobalBroadcastReceive(context, intent);
            }
        };
        registerReceiver(mGlobalReceiver, filter);
    }

    protected void onLocalBroadcastReceive(Context context, Intent intent) {
    }

    protected void onGlobalBroadcastReceive(Context context, Intent intent) {
    }

    public static final int PERMISSION_REQUEST_CODE = 911; // 权限RequestCode

    protected void checkRuntimePermission(String permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permissions}, PERMISSION_REQUEST_CODE);
        } else {
            onPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (null != grantResults && grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissionGranted();
                } else {
                    showToastMsg("权限申请被拒绝，你没有此访问权限");
                }
                return;
            }
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onPermissionGranted() {
    }

}
