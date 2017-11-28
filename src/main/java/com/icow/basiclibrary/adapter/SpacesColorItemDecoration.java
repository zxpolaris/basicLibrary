package com.icow.basiclibrary.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhujun on 2017/9/12.
 */

public class SpacesColorItemDecoration extends  RecyclerView.ItemDecoration{
    private int mDividerHeight;
    private Paint mDividerPaint;
    private boolean hasHeader;
    private boolean hasFooter;
    public  SpacesColorItemDecoration(Context context, @ColorRes int colorId, int px){
        mDividerPaint = new Paint();
        mDividerPaint.setColor(context.getResources().getColor(colorId));
        mDividerHeight = px;
    }

    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public boolean isHasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mDividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            if(hasHeader && i == 0){
                continue;
            }
            if (hasFooter && i == childCount - 1) {
                continue;
            }
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + mDividerHeight;
            c.drawRect(left, top, right, bottom, mDividerPaint);
        }
    }
}
