package com.icow.basiclibrary.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;

import com.icow.basiclibrary.utils.BusEvent;
import com.icow.basiclibrary.utils.EventBusUtil;
import com.icow.basiclibrary.utils.T;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Fragment基类
 *
 * @author Xun.Zhang
 * @ClassName: BaseFragment.java
 * @date 2014-12-8 上午11:15:12
 */
public abstract class BaseFragment extends Fragment {

    /**
     * TAG为每一个Activity的类名
     */
    protected String TAG = BaseFragment.class.getSimpleName();

    private ProgressDialog mProgressDialog;

    private BroadcastReceiver mLocalReceiver; // APP本地广播接收器

    /**
     * 初始化全局数据和Intent传递过来的数据
     */
    protected abstract void initExtras();

    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
        if(getArguments()!=null){
            initExtras();
        }

        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        if (null != mLocalReceiver) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mLocalReceiver);
        }
    }
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if (getIsContentViewVisibility() && null != this.getView()) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 默认返回true，但在ViewPager中需要设置为fasle，否者在切换的时候会有一片白.
     * @return
     */
    public boolean getIsContentViewVisibility(){
        return true;
    }

    /**
     * 显示ProgressDialog
     *
     * @param hint 要显示的文字
     */
    public void showProgressDialog(String hint) {
        if (null == mProgressDialog) {
            FragmentActivity activity = getActivity();
            if(activity == null || activity.isDestroyed() || activity.isFinishing()){
                return;
            }
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        try {
                            dismissProgressDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
            });
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(hint);
            mProgressDialog.show();
        }

    }

    /**
     * 关闭ProgressDialog
     */
    public void dismissProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            FragmentActivity activity = getActivity();
            if(activity == null || activity.isDestroyed() || activity.isFinishing()){
                return;
            }
            mProgressDialog.dismiss();
        }
    }

    /**
     * 显示系统Toast
     *
     * @param msg
     */
    public void showToastMsg(String msg, boolean... showLong) {
        if (null != getActivity()) {
            if (null != showLong && showLong.length > 0) {
                if (showLong[0]) {
                    T.showLong(getActivity().getApplicationContext(), msg);
                } else {
                    T.showShort(getActivity().getApplicationContext(), msg);
                }
            } else {
                T.showShort(getActivity().getApplicationContext(), msg);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(BusEvent busEvent) {
        if (busEvent != null) {
            receiveEvent(busEvent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(BusEvent busEvent) {
        if (busEvent != null) {
            receiveStickyEvent(busEvent);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param busEvent 事件
     */
    protected void receiveEvent(BusEvent busEvent) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param busEvent 粘性事件
     */
    protected void receiveStickyEvent(BusEvent busEvent) {

    }

    protected void registerLocalBroadcastReceiver(IntentFilter filter) {
        mLocalReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onLocalBroadcastReceive(context, intent);
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mLocalReceiver, filter);
    }

    protected void onLocalBroadcastReceive(Context context, Intent intent) {
    }


}