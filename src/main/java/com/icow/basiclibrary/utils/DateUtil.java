package com.icow.basiclibrary.utils;

import android.content.Context;

import com.icow.basiclibrary.R;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期格式化工具类
 *
 * @author Xun.Zhang
 * @version 1.0
 */
public class DateUtil {

    private static final long ONE_HOUR = 60 * 60 * 1000;
    private static final long ONE_MINUTE = 60 * 1000;

    public static String convertDateRelativeTime(Context ctx, long time, boolean isNewLine) {

        String relativeTime;

        GregorianCalendar currentDal = new GregorianCalendar(Locale.getDefault());
        GregorianCalendar todayDar = new GregorianCalendar(currentDal.get(GregorianCalendar.YEAR),
                currentDal.get(GregorianCalendar.MONTH), currentDal.get(GregorianCalendar.DAY_OF_MONTH));
        GregorianCalendar thisWeekDar = new GregorianCalendar(currentDal.get(GregorianCalendar.YEAR),
                currentDal.get(GregorianCalendar.MONTH),
                currentDal.get(GregorianCalendar.DAY_OF_MONTH) - (currentDal.get(GregorianCalendar.DAY_OF_WEEK) - 1));
        GregorianCalendar thisYearDar = new GregorianCalendar(currentDal.get(GregorianCalendar.YEAR),
                GregorianCalendar.JANUARY, 1);

        String lineSpace = isNewLine ? "\n" : " ";

        long timeIntervals = System.currentTimeMillis() - time;
        if (timeIntervals < 3 * ONE_MINUTE) {
            relativeTime = ctx.getString(R.string.basic_library_just_now);
        } else if (timeIntervals < ONE_HOUR) {
            relativeTime = ctx.getString(R.string.basic_library_format_x_minutes_ago, String.valueOf(timeIntervals / ONE_MINUTE));
        } else if (time >= todayDar.getTimeInMillis()) {
            relativeTime = convertDateHHmm(time);
        } else if (time >= thisWeekDar.getTimeInMillis()) {
            relativeTime = convertDate("E", time) + lineSpace + convertDateHHmm(time);
        } else if (time >= thisYearDar.getTimeInMillis()) {
            relativeTime = convertDate(ctx.getString(R.string.basic_library_time_mm_dd), time) + lineSpace + convertDateHHmm(time);
        } else {
            relativeTime = convertDate(ctx.getString(R.string.basic_library_time_yyyy_mm_dd), time);
        }

        return relativeTime;
    }

    private static String convertDateHHmm(long time) {
        return convertDate("HH:mm", time);
    }

    private static String convertDate(String strFormat, long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat, Locale.getDefault());
        return simpleDateFormat.format(time);
    }

}
