package com.icow.basiclibrary.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * O2OListItemDecoration
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class O2OListItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private boolean hasHeader;

    public O2OListItemDecoration(int space, boolean hasHeader) {
        this.space = space;
        this.hasHeader = hasHeader;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);

        int childAdapterPosition;
        if (hasHeader) {
            childAdapterPosition = pos - 1;
        } else {
            childAdapterPosition = pos;
        }

        if (childAdapterPosition <= 0) {
            outRect.top = 0;
        } else {
            outRect.top = space;
        }
    }

}
