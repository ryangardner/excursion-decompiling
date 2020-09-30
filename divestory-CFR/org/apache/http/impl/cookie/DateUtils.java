/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.http.impl.cookie.DateParseException;

public final class DateUtils {
    private static final String[] DEFAULT_PATTERNS = new String[]{"EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    public static final TimeZone GMT;
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    static {
        GMT = TimeZone.getTimeZone("GMT");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(GMT);
        calendar.set(2000, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }

    private DateUtils() {
    }

    public static String formatDate(Date date) {
        return DateUtils.formatDate(date, PATTERN_RFC1123);
    }

    public static String formatDate(Date date, String string2) {
        if (date == null) throw new IllegalArgumentException("date is null");
        if (string2 == null) throw new IllegalArgumentException("pattern is null");
        return DateFormatHolder.formatFor(string2).format(date);
    }

    public static Date parseDate(String string2) throws DateParseException {
        return DateUtils.parseDate(string2, null, null);
    }

    public static Date parseDate(String string2, String[] arrstring) throws DateParseException {
        return DateUtils.parseDate(string2, arrstring, null);
    }

    public static Date parseDate(String object, String[] object2, Date object3) throws DateParseException {
        if (object == null) throw new IllegalArgumentException("dateValue is null");
        String[] arrstring = object2;
        if (object2 == null) {
            arrstring = DEFAULT_PATTERNS;
        }
        object2 = object3;
        if (object3 == null) {
            object2 = DEFAULT_TWO_DIGIT_YEAR_START;
        }
        object3 = object;
        if (((String)object).length() > 1) {
            object3 = object;
            if (((String)object).startsWith("'")) {
                object3 = object;
                if (((String)object).endsWith("'")) {
                    object3 = ((String)object).substring(1, ((String)object).length() - 1);
                }
            }
        }
        int n = arrstring.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to parse the date ");
                ((StringBuilder)object).append((String)object3);
                throw new DateParseException(((StringBuilder)object).toString());
            }
            object = DateFormatHolder.formatFor(arrstring[n2]);
            ((SimpleDateFormat)object).set2DigitYearStart((Date)object2);
            try {
                return ((DateFormat)object).parse((String)object3);
            }
            catch (ParseException parseException) {
                ++n2;
                continue;
            }
            break;
        } while (true);
    }

    static final class DateFormatHolder {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>(){

            @Override
            protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference<Map<String, SimpleDateFormat>>(new HashMap());
            }
        };

        DateFormatHolder() {
        }

        public static SimpleDateFormat formatFor(String string2) {
            Object object = THREADLOCAL_FORMATS.get().get();
            Map<String, SimpleDateFormat> map = object;
            if (object == null) {
                map = new HashMap<String, SimpleDateFormat>();
                THREADLOCAL_FORMATS.set(new SoftReference<Map<String, SimpleDateFormat>>(map));
            }
            SimpleDateFormat simpleDateFormat = map.get(string2);
            object = simpleDateFormat;
            if (simpleDateFormat != null) return object;
            object = new SimpleDateFormat(string2, Locale.US);
            ((DateFormat)object).setTimeZone(TimeZone.getTimeZone("GMT"));
            map.put(string2, (SimpleDateFormat)object);
            return object;
        }

    }

}

