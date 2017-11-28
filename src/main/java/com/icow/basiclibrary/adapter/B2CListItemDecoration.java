package com.icow.basiclibrary.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * B2CListItemDecoration
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class B2CListItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private boolean hasHeader;
    public B2CListItemDecoration(int space) {
        this.space = space;
    }
    public B2CListItemDecoration(int space, boolean hasHeader) {
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

        if (childAdapterPosition <= 1) {
            outRect.top = 0;
        } else {
            outRect.top = space;
        }

        boolean isLeft = childAdapterPosition % 2 == 0;
        if (isLeft) {
            outRect.right = space;
        } else {
            outRect.right = 0;
        }
    }

}
