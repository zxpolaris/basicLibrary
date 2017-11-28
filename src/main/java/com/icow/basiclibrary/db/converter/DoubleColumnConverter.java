package com.icow.basiclibrary.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

import com.icow.basiclibrary.db.sqlite.ColumnDbType;


/**
 * Created by elapse.
 */
public class DoubleColumnConverter implements ColumnConverter<Double> {
    @Override
    public Double getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : cursor.getDouble(index);
    }

    @Override
    public Double getFieldValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) return null;
        return Double.valueOf(fieldStringValue);
    }

    @Override
    public Object fieldValue2ColumnValue(Double fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.REAL;
    }
}
