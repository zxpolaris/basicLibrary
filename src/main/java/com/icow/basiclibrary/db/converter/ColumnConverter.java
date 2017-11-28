package com.icow.basiclibrary.db.converter;

import android.database.Cursor;

import com.icow.basiclibrary.db.sqlite.ColumnDbType;


/**
 * Created by elapse.
 */
public interface ColumnConverter<T> {

    T getFieldValue(final Cursor cursor, int index);

    T getFieldValue(String fieldStringValue);

    Object fieldValue2ColumnValue(T fieldValue);

    ColumnDbType getColumnDbType();
}
