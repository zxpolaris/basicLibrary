package com.icow.basiclibrary.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.icow.basiclibrary.R;

/**
 * Created by qihao on 2017/10/30 17:41
 * 2017 to: 邮箱：sin2t@sina.com
 * trunk
 */

public class QuietDialog {

    private AlertDialog mAlertDialog;
    private TextView mTvTitle;
    private View mLineDialogTitle;
    private TextView mTvInfo;
    private Button mBtnPositive;
    private Button mBtnNegative;

    public QuietDialog(Context context, boolean... isSystemAlert) {
        mAlertDialog = new AlertDialog.Builder(context).create();
        if (null != isSystemAlert && isSystemAlert.length > 0) {
            mAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        mAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mAlertDialog.show();
        Window window = mAlertDialog.getWindow();
        window.setContentView(R.layout.dialog_custom);
        window.setBackgroundDrawable(new ColorDrawable()); // 解决在Android 5.0以上使用AlertDialog出现未充满屏幕的灰色背景
        mTvTitle = (TextView) window.findViewById(R.id.txt_dialog_title);
        mLineDialogTitle = window.findViewById(R.id.line_dialog_title);
        mTvTitle.setVisibility(View.GONE);
        mLineDialogTitle.setVisibility(View.GONE);
        mTvInfo = (TextView) window.findViewById(R.id.txt_dialog_info);
        mBtnPositive = (Button) window.findViewById(R.id.btn_dialog_positive);
        mBtnNegative = (Button) window.findViewById(R.id.btn_dialog_negative);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        if (mAlertDialog == null || onDismissListener == null) {
            return;
        }
        mAlertDialog.setOnDismissListener(onDismissListener);
    }

    public void setTitle(String resId) {
        mTvTitle.setText(resId);
        mTvTitle.setVisibility(View.VISIBLE);
        mLineDialogTitle.setVisibility(View.VISIBLE);
    }

    public void setInfo(int resId) {
        mTvInfo.setText(resId);
    }

    public void setInfo(String title) {
        mTvInfo.setText(title);
    }

    public void setPositiveButton(int resId, final View.OnClickListener onClickListener) {
        mBtnPositive.setText(resId);
        mBtnPositive.setOnClickListener(onClickListener);
    }

    public void setNegativeButton(int resId, final View.OnClickListener onClickListener) {
        mBtnNegative.setText(resId);
        mBtnNegative.setOnClickListener(onClickListener);
    }
    public void setPositiveButton(String text, final View.OnClickListener onClickListener) {
        mBtnPositive.setText(text);
        mBtnPositive.setOnClickListener(onClickListener);
    }

    public void setNegativeButton(String text, final View.OnClickListener onClickListener) {
        mBtnNegative.setText(text);
        mBtnNegative.setOnClickListener(onClickListener);
    }
    public void dismiss() {
        if (null != mAlertDialog) {
            mAlertDialog.dismiss();
        }
    }

    public void show() {
        if (null != mAlertDialog && !mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    public void setDialogOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        if (null != mAlertDialog) {
            mAlertDialog.setOnDismissListener(onDismissListener);
        }
    }

}
