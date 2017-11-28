package com.icow.basiclibrary.adapter.recyclerview.multi;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icow.basiclibrary.adapter.CommonAdapterOperate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhujun on 2017/9/28.
 */

public abstract class BaseMultiRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements CommonAdapterOperate<T> {
    protected final int mLayoutId;
    protected final Context mContext;

    /**
     * Adapter的列表对象，注意是用final修饰的，被初始化之后，不能重新赋值，便于保证List<T>的唯一性
     */
    protected final List<T> mDataList;
    private boolean mIsShowHeader;
    private boolean mIsShowFooter;

    private View mHeaderLayout;
    private View mFooterLayout;

    public static final int HEADER_VIEW = 0x10000001;
    public static final int FOOTER_VIEW = 0x10000002;

    private SpanSizeLookup mSpanSizeLookup;
    private OnItemAdapterClickListener mOnItemAdapterClickListener;

    public BaseMultiRecyclerViewAdapter(int layoutId, Context context) {
        mLayoutId = layoutId;
        mContext = context;
        mDataList = new ArrayList<>();
    }
    public Context getContext() {
        return mContext;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder;
        switch (viewType){
            case HEADER_VIEW:
                viewHolder = new BaseViewHolder(mHeaderLayout,mOnItemAdapterClickListener);
                break;
            case FOOTER_VIEW:
                viewHolder = new BaseViewHolder(mFooterLayout,mOnItemAdapterClickListener);
                break;
            default:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
                viewHolder = new BaseViewHolder(itemView,mOnItemAdapterClickListener);
                viewHolder.addOnItemClick();
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case HEADER_VIEW:
                convert(new BaseViewHolder(mHeaderLayout,null),position);
                break;
            case FOOTER_VIEW:
                convert(new BaseViewHolder(mFooterLayout,null),position);
                break;
            default:
                T object = null;
                if (!mDataList.isEmpty()) {
                    object = mDataList.get(position);
                }
                holder.bindData(object,position);
                convert(holder, position, object);
                break;
        }
    }

    protected void convert(BaseViewHolder<T> holder, int position){}

    public abstract void convert(BaseViewHolder<T> holder, int position, T item);

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int headerPosition = getHeaderLayoutCount();
        if(position<headerPosition){
            return HEADER_VIEW;
        }else{
            int realPosition = position - headerPosition;
            int listSize = mDataList.size();
            if(realPosition < listSize){
                return getListViewType(realPosition);
            }else{
                int footerPosition = realPosition - listSize;
                if(footerPosition< getFooterLayoutCount()){
                    return FOOTER_VIEW;
                }
            }
        }
        return super.getItemViewType(position);
    }



    private int getListViewType(int realPosition) {
        return 0;//默认为0，后续添加多种类的时候 需要根据每个entity进行type判断
    }

    @Override
    public int getItemCount() {
        return getHeaderLayoutCount()+mDataList.size()+getFooterLayoutCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int spanSize;
                    int type = getItemViewType(position);
                    if (mSpanSizeLookup == null) {
                        spanSize =  isFixedViewType(type) ? gridManager.getSpanCount() : 1;
                    } else {
                        spanSize =  (isFixedViewType(type)) ? gridManager.getSpanCount() : mSpanSizeLookup.getSpanSize(gridManager,
                                position - getHeaderLayoutCount());
                    }
                    return spanSize;
                }
            });
        }
    }

    protected boolean isFixedViewType(int type) {
        return type == HEADER_VIEW || type == FOOTER_VIEW ;
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    /**
     * @param spanSizeLookup instance to be used to query number of spans occupied by each item
     */
    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public int getHeaderLayoutCount(){
        if(mIsShowHeader && mHeaderLayout !=null){
            return 1;
        }
        return 0;
    }

    public int getFooterLayoutCount(){
        if(mIsShowFooter && mFooterLayout !=null){
            return 1;
        }
        return 0;
    }

    public void setShowHeader(boolean showHeader) {
        mIsShowHeader = showHeader;
    }

    public void setShowFooter(boolean showFooter) {
        mIsShowFooter = showFooter;
    }

    public void setHeaderLayout(View headerLayout) {
        mHeaderLayout = headerLayout;

    }

    public void setFooterLayout(View footerLayout) {
        mFooterLayout = footerLayout;
    }

    @Override
    public void setList(List<T> dataList) {
        mDataList.clear();
        if (null != dataList) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    @Override
    public void addList(List<T> dataList) {
        if (null != dataList) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeItem(int position) {
        if (position < 0 || position >= mDataList.size()) {
            return;
        }
        mDataList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(T object) {
        if (object != null) {
            mDataList.remove(object);
            notifyDataSetChanged();
        }
    }

    @Override
    public List<T> getList() {
        return mDataList;
    }

    @Override
    public void addItem(T object) {
        if (object != null) {
            mDataList.add(object);
            notifyDataSetChanged();
        }
    }

    public void setOnItemAdapterClickListener(OnItemAdapterClickListener<T> onItemAdapterClickListener) {
        mOnItemAdapterClickListener = onItemAdapterClickListener;
    }
}
