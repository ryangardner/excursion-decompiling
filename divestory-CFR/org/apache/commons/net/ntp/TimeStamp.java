/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ntp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeStamp
implements Serializable,
Comparable<TimeStamp> {
    public static final String NTP_DATE_FORMAT = "EEE, MMM dd yyyy HH:mm:ss.SSS";
    protected static final long msb0baseTime = 2085978496000L;
    protected static final long msb1baseTime = -2208988800000L;
    private static final long serialVersionUID = 8139806907588338737L;
    private final long ntpTime;
    private DateFormat simpleFormatter;
    private DateFormat utcFormatter;

    public TimeStamp(long l) {
        this.ntpTime = l;
    }

    public TimeStamp(String string2) throws NumberFormatException {
        this.ntpTime = TimeStamp.decodeNtpHexString(string2);
    }

    public TimeStamp(Date date) {
        long l = date == null ? 0L : TimeStamp.toNtpTime(date.getTime());
        this.ntpTime = l;
    }

    private static void appendHexString(StringBuilder stringBuilder, long l) {
        String string2 = Long.toHexString(l);
        int n = string2.length();
        do {
            if (n >= 8) {
                stringBuilder.append(string2);
                return;
            }
            stringBuilder.append('0');
            ++n;
        } while (true);
    }

    protected static long decodeNtpHexString(String string2) throws NumberFormatException {
        if (string2 == null) throw new NumberFormatException("null");
        int n = string2.indexOf(46);
        if (n != -1) return Long.parseLong(string2.substring(0, n), 16) << 32 | Long.parseLong(string2.substring(n + 1), 16);
        if (string2.length() != 0) return Long.parseLong(string2, 16) << 32;
        return 0L;
    }

    public static TimeStamp getCurrentTime() {
        return TimeStamp.getNtpTime(System.currentTimeMillis());
    }

    public static TimeStamp getNtpTime(long l) {
        return new TimeStamp(TimeStamp.toNtpTime(l));
    }

    public static long getTime(long l) {
        long l2 = l >>> 32 & 0xFFFFFFFFL;
        long l3 = Math.round((double)(l & 0xFFFFFFFFL) * 1000.0 / 4.294967296E9);
        if ((0x80000000L & l2) == 0L) {
            l = 2085978496000L;
            return l2 * 1000L + l + l3;
        }
        l = -2208988800000L;
        return l2 * 1000L + l + l3;
    }

    public static TimeStamp parseNtpString(String string2) throws NumberFormatException {
        return new TimeStamp(TimeStamp.decodeNtpHexString(string2));
    }

    protected static long toNtpTime(long l) {
        long l2 = 2085978496000L;
        boolean bl = l < 2085978496000L;
        if (bl) {
            l2 = -2208988800000L;
        }
        l -= l2;
        l2 = l / 1000L;
        long l3 = l % 1000L * 0x100000000L / 1000L;
        l = l2;
        if (!bl) return l3 | l << 32;
        l = l2 | 0x80000000L;
        return l3 | l << 32;
    }

    public static String toString(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        TimeStamp.appendHexString(stringBuilder, l >>> 32 & 0xFFFFFFFFL);
        stringBuilder.append('.');
        TimeStamp.appendHexString(stringBuilder, l & 0xFFFFFFFFL);
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(TimeStamp timeStamp) {
        long l = this.ntpTime LCMP timeStamp.ntpTime;
        if (l < 0) {
            return (int)((long)-1);
        }
        if (l != false) return (int)((long)1);
        return (int)((long)0);
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = object instanceof TimeStamp;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        bl3 = bl;
        if (this.ntpTime != ((TimeStamp)object).ntpValue()) return bl3;
        return true;
    }

    public Date getDate() {
        return new Date(TimeStamp.getTime(this.ntpTime));
    }

    public long getFraction() {
        return this.ntpTime & 0xFFFFFFFFL;
    }

    public long getSeconds() {
        return this.ntpTime >>> 32 & 0xFFFFFFFFL;
    }

    public long getTime() {
        return TimeStamp.getTime(this.ntpTime);
    }

    public int hashCode() {
        long l = this.ntpTime;
        return (int)(l ^ l >>> 32);
    }

    public long ntpValue() {
        return this.ntpTime;
    }

    public String toDateString() {
        Cloneable cloneable;
        if (this.simpleFormatter == null) {
            cloneable = new SimpleDateFormat(NTP_DATE_FORMAT, Locale.US);
            this.simpleFormatter = cloneable;
            ((DateFormat)cloneable).setTimeZone(TimeZone.getDefault());
        }
        cloneable = this.getDate();
        return this.simpleFormatter.format((Date)cloneable);
    }

    public String toString() {
        return TimeStamp.toString(this.ntpTime);
    }

    public String toUTCString() {
        Cloneable cloneable;
        if (this.utcFormatter == null) {
            cloneable = new SimpleDateFormat("EEE, MMM dd yyyy HH:mm:ss.SSS 'UTC'", Locale.US);
            this.utcFormatter = cloneable;
            ((DateFormat)cloneable).setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        cloneable = this.getDate();
        return this.utcFormatter.format((Date)cloneable);
    }
}

