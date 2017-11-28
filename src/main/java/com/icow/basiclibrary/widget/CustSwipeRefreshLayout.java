package com.icow.basiclibrary.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.icow.basiclibrary.R;

/**
 * Google自带下拉刷新控件
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class CustSwipeRefreshLayout extends SwipeRefreshLayout {

    public CustSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setColorSchemeResources(R.color.swipe_refresh_color, R.color.swipe_refresh_color, R.color.swipe_refresh_color, R.color.swipe_refresh_color);
        setProgressViewOffset(false, -getResources().getDimensionPixelOffset(R.dimen.swipe_refresh_start), getResources().getDimensionPixelOffset
                (R.dimen.swipe_refresh_end));
    }
}
