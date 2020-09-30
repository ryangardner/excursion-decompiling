/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.parser.FTPTimestampParser;

public class FTPTimestampParserImpl
implements FTPTimestampParser,
Configurable {
    private static final int[] CALENDAR_UNITS = new int[]{14, 13, 12, 11, 5, 2, 1};
    private SimpleDateFormat defaultDateFormat;
    private int defaultDateSmallestUnitIndex;
    private boolean lenientFutureDates = false;
    private SimpleDateFormat recentDateFormat;
    private int recentDateSmallestUnitIndex;

    public FTPTimestampParserImpl() {
        this.setDefaultDateFormat("MMM d yyyy", null);
        this.setRecentDateFormat("MMM d HH:mm", null);
    }

    private static int getEntry(SimpleDateFormat object) {
        if (object == null) {
            return 0;
        }
        object = ((SimpleDateFormat)object).toPattern();
        char[] arrc = "SsmHdM".toCharArray();
        int n = arrc.length;
        int n2 = 0;
        while (n2 < n) {
            char c = arrc[n2];
            if (((String)object).indexOf(c) != -1) {
                if (c == 'H') return FTPTimestampParserImpl.indexOf(11);
                if (c == 'M') return FTPTimestampParserImpl.indexOf(2);
                if (c == 'S') return FTPTimestampParserImpl.indexOf(14);
                if (c == 'd') return FTPTimestampParserImpl.indexOf(5);
                if (c == 'm') return FTPTimestampParserImpl.indexOf(12);
                if (c == 's') return FTPTimestampParserImpl.indexOf(13);
            }
            ++n2;
        }
        return 0;
    }

    private static int indexOf(int n) {
        int[] arrn;
        int n2 = 0;
        while (n2 < (arrn = CALENDAR_UNITS).length) {
            if (n == arrn[n2]) {
                return n2;
            }
            ++n2;
        }
        return 0;
    }

    private void setDefaultDateFormat(String string2, DateFormatSymbols dateFormatSymbols) {
        if (string2 != null) {
            this.defaultDateFormat = dateFormatSymbols != null ? new SimpleDateFormat(string2, dateFormatSymbols) : new SimpleDateFormat(string2);
            this.defaultDateFormat.setLenient(false);
        } else {
            this.defaultDateFormat = null;
        }
        this.defaultDateSmallestUnitIndex = FTPTimestampParserImpl.getEntry(this.defaultDateFormat);
    }

    private static void setPrecision(int n, Calendar calendar) {
        if (n <= 0) {
            return;
        }
        if (calendar.get(n = CALENDAR_UNITS[n - 1]) != 0) {
            return;
        }
        calendar.clear(n);
    }

    private void setRecentDateFormat(String string2, DateFormatSymbols dateFormatSymbols) {
        if (string2 != null) {
            this.recentDateFormat = dateFormatSymbols != null ? new SimpleDateFormat(string2, dateFormatSymbols) : new SimpleDateFormat(string2);
            this.recentDateFormat.setLenient(false);
        } else {
            this.recentDateFormat = null;
        }
        this.recentDateSmallestUnitIndex = FTPTimestampParserImpl.getEntry(this.recentDateFormat);
    }

    private void setServerTimeZone(String object) {
        TimeZone timeZone = TimeZone.getDefault();
        if (object != null) {
            timeZone = TimeZone.getTimeZone((String)object);
        }
        this.defaultDateFormat.setTimeZone(timeZone);
        object = this.recentDateFormat;
        if (object == null) return;
        ((DateFormat)object).setTimeZone(timeZone);
    }

    @Override
    public void configure(FTPClientConfig fTPClientConfig) {
        Object object = fTPClientConfig.getServerLanguageCode();
        String string2 = fTPClientConfig.getShortMonthNames();
        object = string2 != null ? FTPClientConfig.getDateFormatSymbols(string2) : (object != null ? FTPClientConfig.lookupDateFormatSymbols((String)object) : FTPClientConfig.lookupDateFormatSymbols("en"));
        this.setRecentDateFormat(fTPClientConfig.getRecentDateFormatStr(), (DateFormatSymbols)object);
        string2 = fTPClientConfig.getDefaultDateFormatStr();
        if (string2 == null) throw new IllegalArgumentException("defaultFormatString cannot be null");
        this.setDefaultDateFormat(string2, (DateFormatSymbols)object);
        this.setServerTimeZone(fTPClientConfig.getServerTimeZoneId());
        this.lenientFutureDates = fTPClientConfig.isLenientFutureDates();
    }

    public SimpleDateFormat getDefaultDateFormat() {
        return this.defaultDateFormat;
    }

    public String getDefaultDateFormatString() {
        return this.defaultDateFormat.toPattern();
    }

    public SimpleDateFormat getRecentDateFormat() {
        return this.recentDateFormat;
    }

    public String getRecentDateFormatString() {
        return this.recentDateFormat.toPattern();
    }

    public TimeZone getServerTimeZone() {
        return this.defaultDateFormat.getTimeZone();
    }

    public String[] getShortMonths() {
        return this.defaultDateFormat.getDateFormatSymbols().getShortMonths();
    }

    boolean isLenientFutureDates() {
        return this.lenientFutureDates;
    }

    @Override
    public Calendar parseTimestamp(String string2) throws ParseException {
        return this.parseTimestamp(string2, Calendar.getInstance());
    }

    public Calendar parseTimestamp(String string2, Calendar calendar) throws ParseException {
        Object object;
        Object object2;
        Comparable<Calendar> comparable = (Calendar)calendar.clone();
        ((Calendar)comparable).setTimeZone(this.getServerTimeZone());
        if (this.recentDateFormat != null) {
            object = (Calendar)calendar.clone();
            ((Calendar)object).setTimeZone(this.getServerTimeZone());
            if (this.lenientFutureDates) {
                ((Calendar)object).add(5, 1);
            }
            object2 = Integer.toString(((Calendar)object).get(1));
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append(string2);
            ((StringBuilder)object3).append(" ");
            ((StringBuilder)object3).append((String)object2);
            object2 = ((StringBuilder)object3).toString();
            object3 = new StringBuilder();
            ((StringBuilder)object3).append(this.recentDateFormat.toPattern());
            ((StringBuilder)object3).append(" yyyy");
            Cloneable cloneable = new SimpleDateFormat(((StringBuilder)object3).toString(), this.recentDateFormat.getDateFormatSymbols());
            cloneable.setLenient(false);
            cloneable.setTimeZone(this.recentDateFormat.getTimeZone());
            object3 = new ParsePosition(0);
            cloneable = cloneable.parse((String)object2, (ParsePosition)object3);
            if (cloneable != null && ((ParsePosition)object3).getIndex() == ((String)object2).length()) {
                ((Calendar)comparable).setTime((Date)cloneable);
                if (((Calendar)comparable).after(object)) {
                    ((Calendar)comparable).add(1, -1);
                }
                FTPTimestampParserImpl.setPrecision(this.recentDateSmallestUnitIndex, comparable);
                return comparable;
            }
        }
        if ((object2 = this.defaultDateFormat.parse(string2, (ParsePosition)(object = new ParsePosition(0)))) != null && ((ParsePosition)object).getIndex() == string2.length()) {
            ((Calendar)comparable).setTime((Date)object2);
            FTPTimestampParserImpl.setPrecision(this.defaultDateSmallestUnitIndex, comparable);
            return comparable;
        }
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append("Timestamp '");
        ((StringBuilder)comparable).append(string2);
        ((StringBuilder)comparable).append("' could not be parsed using a server time of ");
        ((StringBuilder)comparable).append(calendar.getTime().toString());
        throw new ParseException(((StringBuilder)comparable).toString(), ((ParsePosition)object).getErrorIndex());
    }

    void setLenientFutureDates(boolean bl) {
        this.lenientFutureDates = bl;
    }
}

