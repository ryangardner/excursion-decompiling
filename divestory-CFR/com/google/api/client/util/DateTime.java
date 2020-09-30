/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.common.base.Strings;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateTime
implements Serializable {
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    private static final Pattern RFC3339_PATTERN = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d{1,9})?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?");
    private static final String RFC3339_REGEX = "(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d{1,9})?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?";
    private static final long serialVersionUID = 1L;
    private final boolean dateOnly;
    private final int tzShift;
    private final long value;

    public DateTime(long l) {
        this(false, l, null);
    }

    public DateTime(long l, int n) {
        this(false, l, n);
    }

    public DateTime(String object) {
        object = DateTime.parseRfc3339((String)object);
        this.dateOnly = ((DateTime)object).dateOnly;
        this.value = ((DateTime)object).value;
        this.tzShift = ((DateTime)object).tzShift;
    }

    public DateTime(Date date) {
        this(date.getTime());
    }

    public DateTime(Date comparable, TimeZone timeZone) {
        long l = comparable.getTime();
        comparable = timeZone == null ? null : Integer.valueOf(timeZone.getOffset(comparable.getTime()) / 60000);
        this(false, l, (Integer)comparable);
    }

    public DateTime(boolean bl, long l, Integer n) {
        this.dateOnly = bl;
        this.value = l;
        int n2 = bl ? 0 : (n == null ? TimeZone.getDefault().getOffset(l) / 60000 : n);
        this.tzShift = n2;
    }

    private static void appendInt(StringBuilder stringBuilder, int n, int n2) {
        int n3 = n;
        if (n < 0) {
            stringBuilder.append('-');
            n3 = -n;
        }
        for (n = n3; n > 0; n /= 10, --n2) {
        }
        n = 0;
        do {
            if (n >= n2) {
                if (n3 == 0) return;
                stringBuilder.append(n3);
                return;
            }
            stringBuilder.append('0');
            ++n;
        } while (true);
    }

    public static DateTime parseRfc3339(String string2) {
        return DateTime.parseRfc3339WithNanoSeconds(string2).toDateTime();
    }

    public static SecondsAndNanos parseRfc3339ToSecondsAndNanos(String string2) {
        return DateTime.parseRfc3339WithNanoSeconds(string2).toSecondsAndNanos();
    }

    private static Rfc3339ParseResult parseRfc3339WithNanoSeconds(String object) throws NumberFormatException {
        int n;
        int n2;
        int n3;
        int n4;
        Matcher matcher = RFC3339_PATTERN.matcher((CharSequence)object);
        if (!matcher.matches()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid date/time format: ");
            stringBuilder.append((String)object);
            throw new NumberFormatException(stringBuilder.toString());
        }
        int n5 = Integer.parseInt(matcher.group(1));
        int n6 = Integer.parseInt(matcher.group(2));
        int n7 = Integer.parseInt(matcher.group(3));
        boolean bl = matcher.group(4) != null;
        CharSequence charSequence = matcher.group(9);
        int n8 = charSequence != null ? 1 : 0;
        if (n8 != 0 && !bl) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid date/time format, cannot specify time zone shift without specifying time: ");
            ((StringBuilder)charSequence).append((String)object);
            throw new NumberFormatException(((StringBuilder)charSequence).toString());
        }
        if (bl) {
            n2 = Integer.parseInt(matcher.group(5));
            n4 = Integer.parseInt(matcher.group(6));
            n = Integer.parseInt(matcher.group(7));
            n3 = matcher.group(8) != null ? Integer.parseInt(Strings.padEnd(matcher.group(8).substring(1), 9, '0')) : 0;
        } else {
            n2 = 0;
            n4 = 0;
            n = 0;
            n3 = 0;
        }
        object = new GregorianCalendar(GMT);
        ((Calendar)object).clear();
        ((Calendar)object).set(n5, n6 - 1, n7, n2, n4, n);
        long l = ((Calendar)object).getTimeInMillis();
        if (bl && n8 != 0) {
            if (Character.toUpperCase(((String)charSequence).charAt(0)) == 'Z') {
                object = 0;
                return new Rfc3339ParseResult(l / 1000L, n3, bl, (Integer)object);
            }
            n8 = n = Integer.parseInt(matcher.group(11)) * 60 + Integer.parseInt(matcher.group(12));
            if (matcher.group(10).charAt(0) == '-') {
                n8 = -n;
            }
            object = n8;
            return new Rfc3339ParseResult((l -= (long)n8 * 60000L) / 1000L, n3, bl, (Integer)object);
        }
        object = null;
        return new Rfc3339ParseResult(l / 1000L, n3, bl, (Integer)object);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof DateTime)) {
            return false;
        }
        object = (DateTime)object;
        if (this.dateOnly != ((DateTime)object).dateOnly) return false;
        if (this.value != ((DateTime)object).value) return false;
        if (this.tzShift != ((DateTime)object).tzShift) return false;
        return bl;
    }

    public int getTimeZoneShift() {
        return this.tzShift;
    }

    public long getValue() {
        return this.value;
    }

    public int hashCode() {
        long l = this.value;
        long l2 = this.dateOnly ? 1L : 0L;
        return Arrays.hashCode(new long[]{l, l2, this.tzShift});
    }

    public boolean isDateOnly() {
        return this.dateOnly;
    }

    public String toString() {
        return this.toStringRfc3339();
    }

    public String toStringRfc3339() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(GMT);
        gregorianCalendar.setTimeInMillis(this.value + (long)this.tzShift * 60000L);
        DateTime.appendInt(stringBuilder, gregorianCalendar.get(1), 4);
        stringBuilder.append('-');
        DateTime.appendInt(stringBuilder, gregorianCalendar.get(2) + 1, 2);
        stringBuilder.append('-');
        DateTime.appendInt(stringBuilder, gregorianCalendar.get(5), 2);
        if (this.dateOnly) return stringBuilder.toString();
        stringBuilder.append('T');
        DateTime.appendInt(stringBuilder, gregorianCalendar.get(11), 2);
        stringBuilder.append(':');
        DateTime.appendInt(stringBuilder, gregorianCalendar.get(12), 2);
        stringBuilder.append(':');
        DateTime.appendInt(stringBuilder, gregorianCalendar.get(13), 2);
        if (gregorianCalendar.isSet(14)) {
            stringBuilder.append('.');
            DateTime.appendInt(stringBuilder, gregorianCalendar.get(14), 3);
        }
        if ((n = this.tzShift) == 0) {
            stringBuilder.append('Z');
            return stringBuilder.toString();
        }
        if (n > 0) {
            stringBuilder.append('+');
        } else {
            stringBuilder.append('-');
            n = -n;
        }
        DateTime.appendInt(stringBuilder, n / 60, 2);
        stringBuilder.append(':');
        DateTime.appendInt(stringBuilder, n % 60, 2);
        return stringBuilder.toString();
    }

    private static class Rfc3339ParseResult
    implements Serializable {
        private final int nanos;
        private final long seconds;
        private final boolean timeGiven;
        private final Integer tzShift;

        private Rfc3339ParseResult(long l, int n, boolean bl, Integer n2) {
            this.seconds = l;
            this.nanos = n;
            this.timeGiven = bl;
            this.tzShift = n2;
        }

        private DateTime toDateTime() {
            long l = TimeUnit.SECONDS.toMillis(this.seconds);
            long l2 = TimeUnit.NANOSECONDS.toMillis(this.nanos);
            return new DateTime(this.timeGiven ^ true, l + l2, this.tzShift);
        }

        private SecondsAndNanos toSecondsAndNanos() {
            return new SecondsAndNanos(this.seconds, this.nanos);
        }
    }

    public static final class SecondsAndNanos
    implements Serializable {
        private final int nanos;
        private final long seconds;

        private SecondsAndNanos(long l, int n) {
            this.seconds = l;
            this.nanos = n;
        }

        public static SecondsAndNanos ofSecondsAndNanos(long l, int n) {
            return new SecondsAndNanos(l, n);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object == null) return false;
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (SecondsAndNanos)object;
            if (this.seconds != ((SecondsAndNanos)object).seconds) return false;
            if (this.nanos != ((SecondsAndNanos)object).nanos) return false;
            return bl;
        }

        public int getNanos() {
            return this.nanos;
        }

        public long getSeconds() {
            return this.seconds;
        }

        public int hashCode() {
            return Objects.hash(this.seconds, this.nanos);
        }

        public String toString() {
            return String.format("Seconds: %d, Nanos: %d", this.seconds, this.nanos);
        }
    }

}

