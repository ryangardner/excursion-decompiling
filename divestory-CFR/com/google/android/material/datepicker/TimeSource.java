/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.datepicker;

import java.util.Calendar;
import java.util.TimeZone;

class TimeSource {
    private static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource(null, null);
    private final Long fixedTimeMs;
    private final TimeZone fixedTimeZone;

    private TimeSource(Long l, TimeZone timeZone) {
        this.fixedTimeMs = l;
        this.fixedTimeZone = timeZone;
    }

    static TimeSource fixed(long l) {
        return new TimeSource(l, null);
    }

    static TimeSource fixed(long l, TimeZone timeZone) {
        return new TimeSource(l, timeZone);
    }

    static TimeSource system() {
        return SYSTEM_TIME_SOURCE;
    }

    Calendar now() {
        return this.now(this.fixedTimeZone);
    }

    Calendar now(TimeZone cloneable) {
        cloneable = cloneable == null ? Calendar.getInstance() : Calendar.getInstance((TimeZone)cloneable);
        Long l = this.fixedTimeMs;
        if (l == null) return cloneable;
        ((Calendar)cloneable).setTimeInMillis(l);
        return cloneable;
    }
}

