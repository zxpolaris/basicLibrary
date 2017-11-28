package com.icow.basiclibrary.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icow.basiclibrary.adapter.CommonAdapterOperate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象类 - 程序中通用泛型Adapter，继承Android本身的RecyclerView.Adapter
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CommonAdapterOperate<T> {

    protected final int mLayoutId;
    private final Context mContext;

    /**
     * Adapter的列表对象，注意是用final修饰的，被初始化之后，不能重新赋值，便于保证List<T>的唯一性
     */
    protected final List<T> mDataList;

    public BaseRecyclerViewAdapter(int layoutId, Context context) {
        mLayoutId = layoutId;
        mContext = context;
        mDataList = new ArrayList<>();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        T object = null;
        if (!mDataList.isEmpty()) {
            object = mDataList.get(position);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    T object = null;
                    if (!mDataList.isEmpty()) {
                        object = mDataList.get(position);
                    }
                    mOnItemClickListener.onItemClick(position, object, v);
                }
            }
        });
        viewHolderMap.put(position, holder);
        convert(new ViewHolder(holder.itemView), position, object);
    }

    //用map保存对应位置的viewholder
    private Map<Integer, RecyclerView.ViewHolder> viewHolderMap = new HashMap<>();

    public Map getViewHolderMap() {
        return viewHolderMap;
    }

    @Override
    public long getItemId(int position) {
        return position;
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

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T t, View view);
    }

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public abstract void convert(ViewHolder itemView, int position, T item);

}
