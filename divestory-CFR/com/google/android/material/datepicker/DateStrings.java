/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.material.datepicker;

import android.os.Build;
import androidx.core.util.Pair;
import com.google.android.material.datepicker.UtcDates;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class DateStrings {
    private DateStrings() {
    }

    static Pair<String, String> getDateRangeString(Long l, Long l2) {
        return DateStrings.getDateRangeString(l, l2, null);
    }

    static Pair<String, String> getDateRangeString(Long comparable, Long comparable2, SimpleDateFormat simpleDateFormat) {
        if (comparable == null && comparable2 == null) {
            return Pair.create(null, null);
        }
        if (comparable == null) {
            return Pair.create(null, DateStrings.getDateString((Long)comparable2, simpleDateFormat));
        }
        if (comparable2 == null) {
            return Pair.create(DateStrings.getDateString((Long)comparable, simpleDateFormat), null);
        }
        Calendar calendar = UtcDates.getTodayCalendar();
        Calendar calendar2 = UtcDates.getUtcCalendar();
        calendar2.setTimeInMillis((Long)comparable);
        Calendar calendar3 = UtcDates.getUtcCalendar();
        calendar3.setTimeInMillis((Long)comparable2);
        if (simpleDateFormat != null) {
            comparable = new Date((Long)comparable);
            comparable2 = new Date((Long)comparable2);
            return Pair.create(simpleDateFormat.format((Date)comparable), simpleDateFormat.format((Date)comparable2));
        }
        if (calendar2.get(1) != calendar3.get(1)) return Pair.create(DateStrings.getYearMonthDay((Long)comparable, Locale.getDefault()), DateStrings.getYearMonthDay((Long)comparable2, Locale.getDefault()));
        if (calendar2.get(1) != calendar.get(1)) return Pair.create(DateStrings.getMonthDay((Long)comparable, Locale.getDefault()), DateStrings.getYearMonthDay((Long)comparable2, Locale.getDefault()));
        return Pair.create(DateStrings.getMonthDay((Long)comparable, Locale.getDefault()), DateStrings.getMonthDay((Long)comparable2, Locale.getDefault()));
    }

    static String getDateString(long l) {
        return DateStrings.getDateString(l, null);
    }

    static String getDateString(long l, SimpleDateFormat simpleDateFormat) {
        Calendar calendar = UtcDates.getTodayCalendar();
        Calendar calendar2 = UtcDates.getUtcCalendar();
        calendar2.setTimeInMillis(l);
        if (simpleDateFormat != null) {
            return simpleDateFormat.format(new Date(l));
        }
        if (calendar.get(1) != calendar2.get(1)) return DateStrings.getYearMonthDay(l);
        return DateStrings.getMonthDay(l);
    }

    static String getMonthDay(long l) {
        return DateStrings.getMonthDay(l, Locale.getDefault());
    }

    static String getMonthDay(long l, Locale locale) {
        if (Build.VERSION.SDK_INT < 24) return UtcDates.getMediumNoYear(locale).format(new Date(l));
        return UtcDates.getAbbrMonthDayFormat(locale).format(new Date(l));
    }

    static String getMonthDayOfWeekDay(long l) {
        return DateStrings.getMonthDayOfWeekDay(l, Locale.getDefault());
    }

    static String getMonthDayOfWeekDay(long l, Locale locale) {
        if (Build.VERSION.SDK_INT < 24) return UtcDates.getFullFormat(locale).format(new Date(l));
        return UtcDates.getAbbrMonthWeekdayDayFormat(locale).format(new Date(l));
    }

    static String getYearMonthDay(long l) {
        return DateStrings.getYearMonthDay(l, Locale.getDefault());
    }

    static String getYearMonthDay(long l, Locale locale) {
        if (Build.VERSION.SDK_INT < 24) return UtcDates.getMediumFormat(locale).format(new Date(l));
        return UtcDates.getYearAbbrMonthDayFormat(locale).format(new Date(l));
    }

    static String getYearMonthDayOfWeekDay(long l) {
        return DateStrings.getYearMonthDayOfWeekDay(l, Locale.getDefault());
    }

    static String getYearMonthDayOfWeekDay(long l, Locale locale) {
        if (Build.VERSION.SDK_INT < 24) return UtcDates.getFullFormat(locale).format(new Date(l));
        return UtcDates.getYearAbbrMonthWeekdayDayFormat(locale).format(new Date(l));
    }
}

