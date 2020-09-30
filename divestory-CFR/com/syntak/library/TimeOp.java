/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 */
package com.syntak.library;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.syntak.library.StringOp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeOp {
    public static final long PERIOD_DAY = 86400000L;
    public static final long PERIOD_HOUR = 3600000L;
    public static final long PERIOD_MINUTE = 60000L;
    public static final long PERIOD_SECOND = 1000L;
    static long time_old;

    public static long DateTimeToMs(int n, int n2, int n3, int n4, int n5) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, n);
        calendar.set(2, n2);
        calendar.set(5, n3);
        calendar.set(11, n4);
        calendar.set(12, n5);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static long DateToMs(String string2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            return simpleDateFormat.parse(string2).getTime();
        }
        catch (ParseException parseException) {
            return 0L;
        }
    }

    public static long HourMinuteToMs(String string2) {
        return (Integer.parseInt(string2.substring(0, 2)) * 60 + Integer.parseInt(string2.substring(3))) * 60 * 1000;
    }

    public static String MsToHourMinute(long l) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        return StringOp.HourMinuteToString(calendar.get(11), calendar.get(12), ":");
    }

    public static String MsToHourMinuteSecond(long l) {
        int n = (int)((l /= 1000L) / 3600L);
        int n2 = (int)((l -= (long)(n * 3600)) / 60L);
        return StringOp.HourMinuteSecondToString(n, n2, (int)l - n2 * 3600, ":");
    }

    public static String MsToHourMinuteYearMonthDayVariable(long l) {
        Calendar calendar = Calendar.getInstance();
        Comparable<Calendar> comparable = Calendar.getInstance();
        ((Calendar)comparable).setTimeInMillis(l);
        int n = ((Calendar)comparable).get(1);
        int n2 = ((Calendar)comparable).get(2);
        int n3 = ((Calendar)comparable).get(5);
        int n4 = ((Calendar)comparable).get(11);
        int n5 = ((Calendar)comparable).get(12);
        comparable = new StringBuilder();
        if (n == calendar.get(1) && n2 == calendar.get(2) && n3 == calendar.get(5)) {
            ((StringBuilder)comparable).append(String.format("%02d", n4));
            ((StringBuilder)comparable).append(":");
            ((StringBuilder)comparable).append(String.format("%02d", n5));
            return ((StringBuilder)comparable).toString();
        }
        ((StringBuilder)comparable).append(String.format("%02d", n2 + 1));
        ((StringBuilder)comparable).append("/");
        ((StringBuilder)comparable).append(String.format("%02d", n3));
        if (n == calendar.get(1)) return ((StringBuilder)comparable).toString();
        ((StringBuilder)comparable).insert(0, "/").insert(0, n);
        return ((StringBuilder)comparable).toString();
    }

    public static String MsToYearMonthDay(long l) {
        Comparable<Calendar> comparable = Calendar.getInstance();
        ((Calendar)comparable).setTimeInMillis(l);
        int n = ((Calendar)comparable).get(1);
        int n2 = ((Calendar)comparable).get(2);
        int n3 = ((Calendar)comparable).get(5);
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append(n);
        ((StringBuilder)comparable).append("/");
        ((StringBuilder)comparable).append(String.format("%02d", n2 + 1));
        ((StringBuilder)comparable).append("/");
        ((StringBuilder)comparable).append(String.format("%02d", n3));
        return ((StringBuilder)comparable).toString();
    }

    public static String MsToYearMonthDayHourMinute(long l) {
        Comparable<Calendar> comparable = Calendar.getInstance();
        ((Calendar)comparable).setTimeInMillis(l);
        int n = ((Calendar)comparable).get(1);
        int n2 = ((Calendar)comparable).get(2);
        int n3 = ((Calendar)comparable).get(5);
        int n4 = ((Calendar)comparable).get(11);
        int n5 = ((Calendar)comparable).get(12);
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append(n);
        ((StringBuilder)comparable).append("/");
        ((StringBuilder)comparable).append(String.format("%02d", n2 + 1));
        ((StringBuilder)comparable).append("/");
        ((StringBuilder)comparable).append(String.format("%02d", n3));
        ((StringBuilder)comparable).append(" ");
        ((StringBuilder)comparable).append(String.format("%02d", n4));
        ((StringBuilder)comparable).append(":");
        ((StringBuilder)comparable).append(String.format("%02d", n5));
        return ((StringBuilder)comparable).toString();
    }

    public static int StrToMinutesInDay(String string2) {
        return Integer.parseInt(string2.substring(0, 2)) * 60 + Integer.parseInt(string2.substring(3, 5));
    }

    public static String UniqueTimeString() {
        long l;
        long l2 = l = Calendar.getInstance().getTimeInMillis();
        if (l == time_old) {
            l2 = l + 1L;
        }
        time_old = l2;
        return String.valueOf(l2);
    }

    public static String getDate() {
        return TimeOp.getDate(-1L);
    }

    public static String getDate(long l) {
        Comparable<Calendar> comparable = Calendar.getInstance();
        if (l >= 0L) {
            ((Calendar)comparable).setTimeInMillis(l);
        }
        int n = ((Calendar)comparable).get(1);
        int n2 = ((Calendar)comparable).get(2);
        int n3 = ((Calendar)comparable).get(5);
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append(n);
        ((StringBuilder)comparable).append("-");
        n = n2 + 1;
        if (n < 10) {
            ((StringBuilder)comparable).append("0");
        }
        ((StringBuilder)comparable).append(n);
        ((StringBuilder)comparable).append("-");
        if (n3 < 10) {
            ((StringBuilder)comparable).append("0");
        }
        ((StringBuilder)comparable).append(n3);
        return ((StringBuilder)comparable).toString();
    }

    public static int getDay(long l) {
        Calendar calendar = Calendar.getInstance();
        if (l < 0L) return calendar.get(5);
        calendar.setTimeInMillis(l);
        return calendar.get(5);
    }

    public static byte[] getExactTime256(long l) {
        int n;
        byte[] arrby = Calendar.getInstance();
        arrby.setTimeInMillis(l);
        int n2 = arrby.get(1);
        int n3 = arrby.get(2);
        int n4 = arrby.get(5);
        int n5 = arrby.get(11);
        int n6 = arrby.get(12);
        int n7 = arrby.get(13);
        int n8 = arrby.get(14);
        int n9 = n = arrby.get(7) - 1;
        if (n == 0) {
            n9 = 7;
        }
        n = n8 / 4;
        arrby = StringOp.uint16ToByteArray(n2);
        return new byte[]{arrby[0], arrby[1], (byte)(n3 + 1), (byte)n4, (byte)n5, (byte)n6, (byte)n7, (byte)n9, (byte)n};
    }

    public static long getGMTms() {
        return Calendar.getInstance().getTimeZone().getRawOffset();
    }

    public static String getHourMinuteString(int n) {
        int n2 = n / 60;
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = String.format("%02d", n2);
        String string3 = String.format("%02d", n % 60);
        stringBuilder.append(string2);
        stringBuilder.append(":");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    public static int getMinuteOfDay(String string2) {
        String string3 = string2.substring(0, 2);
        string2 = string2.substring(3);
        return Integer.parseInt(string3) * 60 + Integer.parseInt(string2);
    }

    public static int getMonth(long l) {
        Calendar calendar = Calendar.getInstance();
        if (l < 0L) return calendar.get(2);
        calendar.setTimeInMillis(l);
        return calendar.get(2);
    }

    public static long getMsOfNextExactTime(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(1);
        calendar.get(2);
        calendar.get(3);
        calendar.get(4);
        calendar.get(6);
        calendar.get(5);
        calendar.get(7);
        calendar.get(11);
        calendar.get(12);
        calendar.get(13);
        calendar.set(14, 0);
        switch (n) {
            default: {
                return calendar.getTimeInMillis();
            }
            case 13: {
                calendar.add(13, 1);
                return calendar.getTimeInMillis();
            }
            case 12: {
                calendar.add(12, 1);
                calendar.set(13, 0);
                return calendar.getTimeInMillis();
            }
            case 11: {
                calendar.add(11, 1);
                calendar.set(12, 0);
                calendar.set(13, 0);
                return calendar.getTimeInMillis();
            }
            case 5: 
            case 6: 
            case 7: {
                calendar.add(6, 1);
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                return calendar.getTimeInMillis();
            }
            case 3: 
            case 4: {
                calendar.add(3, 1);
                calendar.set(7, 1);
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                return calendar.getTimeInMillis();
            }
            case 2: {
                calendar.add(2, 1);
                calendar.set(5, 1);
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                return calendar.getTimeInMillis();
            }
            case 1: 
        }
        calendar.add(1, 1);
        calendar.set(2, 0);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTimeInMillis();
    }

    public static long getMsOfTimeToday(int n, int n2, int n3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, n);
        calendar.set(12, n2);
        calendar.set(13, n3);
        return calendar.getTimeInMillis();
    }

    public static long getNow() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static long getNowGMT() {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = calendar.getTimeZone();
        return calendar.getTimeInMillis() - (long)timeZone.getRawOffset();
    }

    public static String getNowHourMinuteSecond() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%02d:%02d:%02d", calendar.get(11), calendar.get(12), calendar.get(13));
    }

    public static int getNowMinutesInDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(11) * 60 + calendar.get(12);
    }

    public static int getNowSecondsInDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(11) * 3600 + calendar.get(12) * 60 + calendar.get(13);
    }

    public static int getThisYear() {
        return Calendar.getInstance().get(1);
    }

    public static long getTodayTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        int n = calendar.get(1);
        int n2 = calendar.get(2);
        int n3 = calendar.get(5);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("-");
        stringBuilder.append(n2 + 1);
        stringBuilder.append("-");
        stringBuilder.append(n3);
        stringBuilder.append(" 00:00:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            calendar.setTime(simpleDateFormat.parse(stringBuilder.toString()));
            return calendar.getTimeInMillis();
        }
        catch (ParseException parseException) {
            return 0L;
        }
    }

    public static int getYear(long l) {
        Calendar calendar = Calendar.getInstance();
        if (l < 0L) return calendar.get(1);
        calendar.setTimeInMillis(l);
        return calendar.get(1);
    }

    public static long get_MilliSecond_from_DateComponents(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        Calendar calendar = Calendar.getInstance(TimeOp.get_TimeZone_from_UTC_offset(n));
        calendar.set(1, n2);
        calendar.set(2, n3);
        calendar.set(5, n4);
        calendar.set(11, n5);
        calendar.set(12, n6);
        calendar.set(13, n7);
        calendar.set(14, n8);
        return calendar.getTimeInMillis();
    }

    public static String get_Minute_Second_String_from_Seconds(int n) {
        return TimeOp.get_Minute_Second_String_from_Seconds(n, ":");
    }

    public static String get_Minute_Second_String_from_Seconds(int n, String string2) {
        int n2 = n / 60;
        StringBuilder stringBuilder = new StringBuilder();
        String string3 = String.format("%02d", n2);
        String string4 = String.format("%02d", n % 60);
        stringBuilder.append(string3);
        stringBuilder.append(string2);
        stringBuilder.append(string4);
        return stringBuilder.toString();
    }

    public static TimeZone get_TimeZone_from_UTC_offset(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GMT");
        stringBuilder.append(String.format("+%d", n));
        return TimeZone.getTimeZone(stringBuilder.toString());
    }

    public static int get_UTC_offset() {
        return Calendar.getInstance().getTimeZone().getRawOffset() / 1000 / 60 / 60;
    }

    public static boolean isDateExpired(String string2) {
        return TimeOp.isExpired(TimeOp.DateToMs(string2));
    }

    public static boolean isExpired(long l) {
        if (TimeOp.getNow() <= l) return false;
        return true;
    }

    public static void setAlarmRepeatWakeUp(Context context, Class<?> class_, long l, long l2) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService("alarm");
        long l3 = l;
        if (l < System.currentTimeMillis()) {
            l3 = l + l2;
        }
        alarmManager.setInexactRepeating(0, l3, l2, PendingIntent.getBroadcast((Context)context, (int)0, (Intent)new Intent(context, class_), (int)134217728));
    }

    public static void setAlarmWakeUp(Context context, Class<?> class_, long l) {
        ((AlarmManager)context.getSystemService("alarm")).set(0, l, PendingIntent.getBroadcast((Context)context, (int)0, (Intent)new Intent(context, class_), (int)134217728));
    }

    public static void sleep(long l) {
        try {
            Thread.sleep(l);
            return;
        }
        catch (InterruptedException interruptedException) {
            return;
        }
    }

    public static class DateAndTime {
        public int day;
        public int hour;
        public int milli_second;
        public int minute;
        public int month;
        long ms;
        public int second;
        public int utc_offset;
        public int year;

        public DateAndTime() {
        }

        public DateAndTime(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            this.utc_offset = n;
            this.year = n2;
            this.month = n3;
            this.day = n4;
            this.hour = n5;
            this.minute = n6;
            this.second = n7;
            this.milli_second = n8;
            this.ms = TimeOp.get_MilliSecond_from_DateComponents(n, n2, n3, n4, n5, n6, n7, n8);
        }

        public DateAndTime(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, long l) {
            this.utc_offset = n;
            this.year = n2;
            this.month = n3;
            this.day = n4;
            this.hour = n5;
            this.minute = n6;
            this.second = n7;
            this.milli_second = n8;
            this.ms = l;
        }

        public DateAndTime(int n, long l) {
            Calendar calendar = Calendar.getInstance(TimeOp.get_TimeZone_from_UTC_offset(n));
            calendar.setTimeInMillis(l);
            this.utc_offset = n;
            this.year = calendar.get(1);
            this.month = calendar.get(2);
            this.day = calendar.get(5);
            this.hour = calendar.get(11);
            this.minute = calendar.get(12);
            this.second = calendar.get(13);
            this.milli_second = calendar.get(14);
            this.ms = l;
        }

        public DateAndTime add_MilliSecond(long l) {
            long l2 = this.ms;
            return new DateAndTime(this.utc_offset, l2 + l);
        }

        public long convert_to_MilliSecond(DateAndTime dateAndTime) {
            Calendar calendar = Calendar.getInstance(TimeOp.get_TimeZone_from_UTC_offset(dateAndTime.utc_offset));
            calendar.set(1, dateAndTime.year);
            calendar.set(2, dateAndTime.month);
            calendar.set(5, dateAndTime.day);
            calendar.set(11, dateAndTime.hour);
            calendar.set(12, dateAndTime.minute);
            calendar.set(13, dateAndTime.second);
            calendar.set(14, dateAndTime.milli_second);
            return calendar.getTimeInMillis();
        }

        public long get_MilliSecond(DateAndTime dateAndTime) {
            return dateAndTime.ms;
        }
    }

    public static class DelayTask {
        Timer timer = null;
        TimerTask timerTask = null;

        public void TaskAtSchedule() {
        }

        public void TaskAtStart() {
        }

        public void start(int n) {
            Timer timer;
            this.stop();
            this.timerTask = new TimerTask(){

                @Override
                public void run() {
                    DelayTask.this.TaskAtSchedule();
                }
            };
            this.timer = timer = new Timer();
            timer.schedule(this.timerTask, n);
            this.TaskAtStart();
        }

        public void stop() {
            Timer timer = this.timer;
            if (timer == null) return;
            timer.cancel();
            this.timerTask = null;
            this.timer = null;
        }

    }

    public static class DigitalClock {
        public static ScheduledExecutorService executor_scheduled;
        Runnable tick = null;

        public DigitalClock(long l) {
            ScheduledExecutorService scheduledExecutorService;
            long l2 = TimeOp.getNow();
            this.tick = new Runnable(){

                @Override
                public void run() {
                    long l = TimeOp.getNow();
                    DigitalClock.this.OnPeriod(l);
                }
            };
            executor_scheduled = scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(this.tick, l - l2 % l, l, TimeUnit.MILLISECONDS);
            this.OnPeriod(l2);
        }

        public void OnPeriod(long l) {
        }

        public void stop() {
            ScheduledExecutorService scheduledExecutorService = executor_scheduled;
            if (scheduledExecutorService == null) return;
            scheduledExecutorService.shutdownNow();
        }

    }

}

