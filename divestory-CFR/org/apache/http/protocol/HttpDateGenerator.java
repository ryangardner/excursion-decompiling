/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HttpDateGenerator {
    public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private long dateAsLong = 0L;
    private String dateAsText = null;
    private final DateFormat dateformat;

    public HttpDateGenerator() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_RFC1123, Locale.US);
        this.dateformat = simpleDateFormat;
        simpleDateFormat.setTimeZone(GMT);
    }

    public String getCurrentDate() {
        synchronized (this) {
            long l = System.currentTimeMillis();
            if (l - this.dateAsLong <= 1000L) return this.dateAsText;
            DateFormat dateFormat = this.dateformat;
            Object object = new Date(l);
            this.dateAsText = dateFormat.format((Date)object);
            this.dateAsLong = l;
            return this.dateAsText;
        }
    }
}

