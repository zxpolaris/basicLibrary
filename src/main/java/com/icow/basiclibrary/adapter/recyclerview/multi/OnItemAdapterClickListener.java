package com.icow.basiclibrary.adapter.recyclerview.multi;

import android.view.View;

/**
 * @author zhujun on 2017/10/20
 */

public interface OnItemAdapterClickListener<T> {
    void onItemClick(View v,int position,T t);
    void onItemChildClick(View v,int position,T t);
}
