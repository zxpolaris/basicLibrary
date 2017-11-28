package com.icow.basiclibrary.adapter;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.icow.basiclibrary.adapter.recyclerview.multi.BaseMultiRecyclerViewAdapter;

/**
 * @author zhujun on 2017/10/20
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private boolean hasHeader;
    private boolean hasFooter;

    private boolean mIsShowFirstUpSpace;
    private boolean mIsShowEndDownSpace;

    public GridItemDecoration(int horizontalSpacing,int verticalSpacing) {
        this.mHorizontalSpacing = horizontalSpacing;
        this.mVerticalSpacing = verticalSpacing;
    }
    public GridItemDecoration(int horizontalSpacing,int verticalSpacing,boolean hasHeader) {
        this.mHorizontalSpacing = horizontalSpacing;
        this.mVerticalSpacing = verticalSpacing;
        this.hasHeader = hasHeader;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = gridLayoutManager.getSpanCount();
        int pos = parent.getChildAdapterPosition(view);

        int childAdapterPosition;

        if (hasHeader && pos < spanCount) {
            outRect.top = 0;
            childAdapterPosition = pos - spanCount;
        }else{
            childAdapterPosition = pos;
        }

        if (childAdapterPosition< spanCount) {
            if(mIsShowFirstUpSpace){
                outRect.top = mHorizontalSpacing;
            }else{
                outRect.top = 0;
            }
        }else{
            outRect.top = mHorizontalSpacing;
        }
        if (mIsShowEndDownSpace) {
            RecyclerView.Adapter adapter = parent.getAdapter();
            int itemCount = adapter.getItemCount();

            int realPosition = itemCount;
            if(hasFooter){
                if(adapter instanceof BaseMultiRecyclerViewAdapter){
                    int footerLayoutCount = ((BaseMultiRecyclerViewAdapter) adapter).getFooterLayoutCount();
                    if(footerLayoutCount == 1){
                        realPosition = itemCount - spanCount;
                    }
                }else{
                    realPosition = itemCount - spanCount;
                }
            }
            int lastPosition = realPosition % spanCount;
            //0 1 2 3
            if (pos + lastPosition > realPosition) {
                outRect.bottom = mHorizontalSpacing;
            }else{
                outRect.bottom = 0;
            }
        }else{
            outRect.bottom = 0;
        }

        //0,1,2,3
        boolean isLeft = childAdapterPosition % spanCount == 0;
        boolean isRight =  childAdapterPosition % spanCount == spanCount-1;
        int spaceCount = spanCount >=4 ? 4 : spanCount;
        if (isLeft) {
            outRect.left = 0;
            outRect.right = mVerticalSpacing * (spaceCount - 1) / spaceCount;
        } else if (isRight) {
            outRect.left = mVerticalSpacing * (spaceCount - 1) / spaceCount;
            outRect.right = 0;
        } else {
            boolean isLeftSeconde = spaceCount != 3 && childAdapterPosition % spanCount == 1;
            boolean isRightSeconde = spaceCount != 3 && childAdapterPosition % spanCount == spanCount-2;

            if (isLeftSeconde) {
                outRect.left = mVerticalSpacing * (spaceCount - 3) / spaceCount;
                outRect.right = mVerticalSpacing * (spaceCount - 2) / spaceCount;
            } else if (isRightSeconde) {
                outRect.left = mVerticalSpacing * (spaceCount - 2) / spaceCount;
                outRect.right = mVerticalSpacing * (spaceCount - 3) / spaceCount;
            }else {
                outRect.left = mVerticalSpacing * (spaceCount - 2) / spaceCount;
                outRect.right = mVerticalSpacing * (spaceCount - 2) / spaceCount;
            }
        }
    }


    public boolean isShowFirstUpSpace() {
        return mIsShowFirstUpSpace;
    }

    public void setShowFirstUpSpace(boolean showFirstUpSpace) {
        mIsShowFirstUpSpace = showFirstUpSpace;
    }

    public boolean isShowEndDownSpace() {
        return mIsShowEndDownSpace;
    }

    public void setShowEndDownSpace(boolean showEndDownSpace) {
        mIsShowEndDownSpace = showEndDownSpace;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GridItemDecoration that = (GridItemDecoration) o;

        if (mHorizontalSpacing != that.mHorizontalSpacing) return false;
        if (mVerticalSpacing != that.mVerticalSpacing) return false;
        if (hasHeader != that.hasHeader) return false;
        if (hasFooter != that.hasFooter) return false;
        if (mIsShowFirstUpSpace != that.mIsShowFirstUpSpace) return false;
        return mIsShowEndDownSpace == that.mIsShowEndDownSpace;

    }

    @Override
    public int hashCode() {
        int result = mHorizontalSpacing;
        result = 31 * result + mVerticalSpacing;
        result = 31 * result + (hasHeader ? 1 : 0);
        result = 31 * result + (hasFooter ? 1 : 0);
        result = 31 * result + (mIsShowFirstUpSpace ? 1 : 0);
        result = 31 * result + (mIsShowEndDownSpace ? 1 : 0);
        return result;
    }
}
