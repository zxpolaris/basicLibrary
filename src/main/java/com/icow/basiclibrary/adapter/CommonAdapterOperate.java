package com.icow.basiclibrary.adapter;

import java.util.List;

/**
 * 泛型接口 - 通用Adapter操作方法
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public interface CommonAdapterOperate<T> {

    /**
     * 设置列表数据
     *
     * @param dataList
     */
    void setList(List<T> dataList);

    /**
     * 添加列表数据
     *
     * @param dataList
     */
    void addList(List<T> dataList);

    /**
     * 移除一个列表数据
     *
     * @param position
     */
    void removeItem(int position);

    /**
     * 移除一个列表数据
     *
     * @param object
     */
    void removeItem(T object);

    /**
     * 获取List<T>
     *
     * @return
     */
    List<T> getList();

    /**
     * 添加一个数据
     *
     * @param object
     */
    void addItem(T object);
}
