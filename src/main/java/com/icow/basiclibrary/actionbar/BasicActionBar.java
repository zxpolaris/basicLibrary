package com.icow.basiclibrary.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icow.basiclibrary.R;

/**
 * 通用ActionBar
 *
 * @author Kevin.Zhang
 * @version 1.0
 */
public class BasicActionBar extends RelativeLayout {

    /**
     * 左边图标按钮
     */
    private ImageButton mBtnLeft;

    /**
     * 中间文字标题
     */
    private TextView mTvTitle;

    /**
     * 右边文字按钮
     */
    private TextView mTvRight;

    public BasicActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    public BasicActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public BasicActionBar(Context context) {
        super(context);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_actionbar, this);
        mBtnLeft = (ImageButton) findViewById(R.id.btn_left);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRight = (TextView) findViewById(R.id.tv_right);
    }

    /**
     * 设置中间文字标题（不设置则显示图片LOGO）
     *
     * @param titleResId 资源ID
     */
    public void setActionBarTitle(int titleResId) {
        mTvTitle.setText(titleResId);
    }

    /**
     * 设置中间文字标题（不设置则显示图片LOGO）
     *
     * @param strTitle 资源ID
     */
    public void setActionBarTitle(String strTitle) {
        mTvTitle.setText(strTitle);
    }

    /**
     * 设置标题的点击事件
     *
     * @param onClickListener
     */
    public void setActionBarTitleOnClickListener(OnClickListener onClickListener) {
        mTvTitle.setOnClickListener(onClickListener);
    }

    /**
     * 设置左边图标按钮资源
     *
     * @param imageResId 资源ID
     */
    public void setLeftBtn(int imageResId) {
        mBtnLeft.setImageResource(imageResId);
    }

    /**
     * 设置左边按钮点击事件
     *
     * @param onClickListener
     */
    public void setLeftBtnOnClickListener(OnClickListener onClickListener) {
        mBtnLeft.setOnClickListener(onClickListener);
    }

    public void setTvRight(String strTitle) {
        mTvRight.setText(strTitle);
        mTvRight.setVisibility(View.VISIBLE);
    }

    public void setRightTxtViewEnabled(boolean enabled) {
        mTvRight.setEnabled(enabled);
    }

    public void setRightTxtView(String strTitle) {
        mTvRight.setText(strTitle);
        mTvRight.setVisibility(View.VISIBLE);
    }

    public void setRightTxtView(int resId, OnClickListener onClickListener) {
        mTvRight.setText(resId);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setOnClickListener(onClickListener);
    }

}
