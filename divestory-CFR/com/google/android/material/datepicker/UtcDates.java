/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.icu.text.DateFormat
 *  android.icu.util.TimeZone
 */
package com.google.android.material.datepicker;

import android.content.res.Resources;
import android.icu.text.DateFormat;
import android.icu.util.TimeZone;
import com.google.android.material.R;
import com.google.android.material.datepicker.TimeSource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

class UtcDates {
    static final String UTC = "UTC";
    static AtomicReference<TimeSource> timeSourceRef = new AtomicReference();

    private UtcDates() {
    }

    static long canonicalYearMonthDay(long l) {
        Calendar calendar = UtcDates.getUtcCalendar();
        calendar.setTimeInMillis(l);
        return UtcDates.getDayCopy(calendar).getTimeInMillis();
    }

    private static int findCharactersInDateFormatPattern(String string2, String string3, int n, int n2) {
        while (n2 >= 0) {
            if (n2 >= string2.length()) return n2;
            if (string3.indexOf(string2.charAt(n2)) != -1) return n2;
            int n3 = n2;
            if (string2.charAt(n2) == '\'') {
                do {
                    n3 = n2 += n;
                    if (n2 < 0) break;
                    n3 = n2;
                    if (n2 >= string2.length()) break;
                    n3 = n2;
                } while (string2.charAt(n2) != '\'');
            }
            n2 = n3 + n;
        }
        return n2;
    }

    static DateFormat getAbbrMonthDayFormat(Locale locale) {
        return UtcDates.getAndroidFormat("MMMd", locale);
    }

    static DateFormat getAbbrMonthWeekdayDayFormat(Locale locale) {
        return UtcDates.getAndroidFormat("MMMEd", locale);
    }

    private static DateFormat getAndroidFormat(String string2, Locale locale) {
        string2 = DateFormat.getInstanceForSkeleton((String)string2, (Locale)locale);
        string2.setTimeZone(UtcDates.getUtcAndroidTimeZone());
        return string2;
    }

    static Calendar getDayCopy(Calendar calendar) {
        Calendar calendar2 = UtcDates.getUtcCalendarOf(calendar);
        calendar = UtcDates.getUtcCalendar();
        calendar.set(calendar2.get(1), calendar2.get(2), calendar2.get(5));
        return calendar;
    }

    private static java.text.DateFormat getFormat(int n, Locale cloneable) {
        cloneable = java.text.DateFormat.getDateInstance(n, (Locale)cloneable);
        ((java.text.DateFormat)cloneable).setTimeZone(UtcDates.getTimeZone());
        return cloneable;
    }

    static java.text.DateFormat getFullFormat() {
        return UtcDates.getFullFormat(Locale.getDefault());
    }

    static java.text.DateFormat getFullFormat(Locale locale) {
        return UtcDates.getFormat(0, locale);
    }

    static java.text.DateFormat getMediumFormat() {
        return UtcDates.getMediumFormat(Locale.getDefault());
    }

    static java.text.DateFormat getMediumFormat(Locale locale) {
        return UtcDates.getFormat(2, locale);
    }

    static java.text.DateFormat getMediumNoYear() {
        return UtcDates.getMediumNoYear(Locale.getDefault());
    }

    static java.text.DateFormat getMediumNoYear(Locale cloneable) {
        cloneable = (SimpleDateFormat)UtcDates.getMediumFormat((Locale)cloneable);
        ((SimpleDateFormat)cloneable).applyPattern(UtcDates.removeYearFromDateFormatPattern(((SimpleDateFormat)cloneable).toPattern()));
        return cloneable;
    }

    static SimpleDateFormat getSimpleFormat(String string2) {
        return UtcDates.getSimpleFormat(string2, Locale.getDefault());
    }

    private static SimpleDateFormat getSimpleFormat(String object, Locale locale) {
        object = new SimpleDateFormat((String)object, locale);
        ((java.text.DateFormat)object).setTimeZone(UtcDates.getTimeZone());
        return object;
    }

    static SimpleDateFormat getTextInputFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(((SimpleDateFormat)java.text.DateFormat.getDateInstance(3, Locale.getDefault())).toLocalizedPattern().replaceAll("\\s+", ""), Locale.getDefault());
        simpleDateFormat.setTimeZone(UtcDates.getTimeZone());
        simpleDateFormat.setLenient(false);
        return simpleDateFormat;
    }

    static String getTextInputHint(Resources resources, SimpleDateFormat object) {
        object = ((SimpleDateFormat)object).toLocalizedPattern();
        String string2 = resources.getString(R.string.mtrl_picker_text_input_year_abbr);
        String string3 = resources.getString(R.string.mtrl_picker_text_input_month_abbr);
        return ((String)object).replaceAll("d", resources.getString(R.string.mtrl_picker_text_input_day_abbr)).replaceAll("M", string3).replaceAll("y", string2);
    }

    static TimeSource getTimeSource() {
        TimeSource timeSource;
        TimeSource timeSource2 = timeSource = timeSourceRef.get();
        if (timeSource != null) return timeSource2;
        return TimeSource.system();
    }

    private static java.util.TimeZone getTimeZone() {
        return java.util.TimeZone.getTimeZone(UTC);
    }

    static Calendar getTodayCalendar() {
        Calendar calendar = UtcDates.getTimeSource().now();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.setTimeZone(UtcDates.getTimeZone());
        return calendar;
    }

    private static TimeZone getUtcAndroidTimeZone() {
        return TimeZone.getTimeZone((String)UTC);
    }

    static Calendar getUtcCalendar() {
        return UtcDates.getUtcCalendarOf(null);
    }

    static Calendar getUtcCalendarOf(Calendar calendar) {
        Calendar calendar2 = Calendar.getInstance(UtcDates.getTimeZone());
        if (calendar == null) {
            calendar2.clear();
            return calendar2;
        }
        calendar2.setTimeInMillis(calendar.getTimeInMillis());
        return calendar2;
    }

    static DateFormat getYearAbbrMonthDayFormat(Locale locale) {
        return UtcDates.getAndroidFormat("yMMMd", locale);
    }

    static DateFormat getYearAbbrMonthWeekdayDayFormat(Locale locale) {
        return UtcDates.getAndroidFormat("yMMMEd", locale);
    }

    static SimpleDateFormat getYearMonthFormat() {
        return UtcDates.getYearMonthFormat(Locale.getDefault());
    }

    private static SimpleDateFormat getYearMonthFormat(Locale locale) {
        return UtcDates.getSimpleFormat("LLLL, yyyy", locale);
    }

    private static String removeYearFromDateFormatPattern(String string2) {
        int n = UtcDates.findCharactersInDateFormatPattern(string2, "yY", 1, 0);
        if (n >= string2.length()) {
            return string2;
        }
        CharSequence charSequence = "EMd";
        int n2 = UtcDates.findCharactersInDateFormatPattern(string2, "EMd", 1, n);
        if (n2 >= string2.length()) return string2.replace(string2.substring(UtcDates.findCharactersInDateFormatPattern(string2, (String)charSequence, -1, n) + 1, n2), " ").trim();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("EMd");
        ((StringBuilder)charSequence).append(",");
        charSequence = ((StringBuilder)charSequence).toString();
        return string2.replace(string2.substring(UtcDates.findCharactersInDateFormatPattern(string2, (String)charSequence, -1, n) + 1, n2), " ").trim();
    }

    static void setTimeSource(TimeSource timeSource) {
        timeSourceRef.set(timeSource);
    }
}

