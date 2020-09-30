package com.syntak.library;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeOp {
   public static final long PERIOD_DAY = 86400000L;
   public static final long PERIOD_HOUR = 3600000L;
   public static final long PERIOD_MINUTE = 60000L;
   public static final long PERIOD_SECOND = 1000L;
   static long time_old;

   public static long DateTimeToMs(int var0, int var1, int var2, int var3, int var4) {
      Calendar var5 = Calendar.getInstance();
      var5.set(1, var0);
      var5.set(2, var1);
      var5.set(5, var2);
      var5.set(11, var3);
      var5.set(12, var4);
      var5.set(13, 0);
      var5.set(14, 0);
      return var5.getTimeInMillis();
   }

   public static long DateToMs(String var0) {
      long var2;
      try {
         SimpleDateFormat var1 = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
         var2 = var1.parse(var0).getTime();
      } catch (ParseException var4) {
         var2 = 0L;
      }

      return var2;
   }

   public static long HourMinuteToMs(String var0) {
      return (long)((Integer.parseInt(var0.substring(0, 2)) * 60 + Integer.parseInt(var0.substring(3))) * 60 * 1000);
   }

   public static String MsToHourMinute(long var0) {
      Calendar var2 = Calendar.getInstance();
      var2.setTimeInMillis(var0);
      return StringOp.HourMinuteToString(var2.get(11), var2.get(12), ":");
   }

   public static String MsToHourMinuteSecond(long var0) {
      var0 /= 1000L;
      int var2 = (int)(var0 / 3600L);
      var0 -= (long)(var2 * 3600);
      int var3 = (int)(var0 / 60L);
      return StringOp.HourMinuteSecondToString(var2, var3, (int)var0 - var3 * 3600, ":");
   }

   public static String MsToHourMinuteYearMonthDayVariable(long var0) {
      Calendar var2 = Calendar.getInstance();
      Calendar var3 = Calendar.getInstance();
      var3.setTimeInMillis(var0);
      int var4 = var3.get(1);
      int var5 = var3.get(2);
      int var6 = var3.get(5);
      int var7 = var3.get(11);
      int var8 = var3.get(12);
      StringBuilder var9 = new StringBuilder();
      if (var4 == var2.get(1) && var5 == var2.get(2) && var6 == var2.get(5)) {
         var9.append(String.format("%02d", var7));
         var9.append(":");
         var9.append(String.format("%02d", var8));
      } else {
         var9.append(String.format("%02d", var5 + 1));
         var9.append("/");
         var9.append(String.format("%02d", var6));
         if (var4 != var2.get(1)) {
            var9.insert(0, "/").insert(0, var4);
         }
      }

      return var9.toString();
   }

   public static String MsToYearMonthDay(long var0) {
      Calendar var2 = Calendar.getInstance();
      var2.setTimeInMillis(var0);
      int var3 = var2.get(1);
      int var4 = var2.get(2);
      int var5 = var2.get(5);
      StringBuilder var6 = new StringBuilder();
      var6.append(var3);
      var6.append("/");
      var6.append(String.format("%02d", var4 + 1));
      var6.append("/");
      var6.append(String.format("%02d", var5));
      return var6.toString();
   }

   public static String MsToYearMonthDayHourMinute(long var0) {
      Calendar var2 = Calendar.getInstance();
      var2.setTimeInMillis(var0);
      int var3 = var2.get(1);
      int var4 = var2.get(2);
      int var5 = var2.get(5);
      int var6 = var2.get(11);
      int var7 = var2.get(12);
      StringBuilder var8 = new StringBuilder();
      var8.append(var3);
      var8.append("/");
      var8.append(String.format("%02d", var4 + 1));
      var8.append("/");
      var8.append(String.format("%02d", var5));
      var8.append(" ");
      var8.append(String.format("%02d", var6));
      var8.append(":");
      var8.append(String.format("%02d", var7));
      return var8.toString();
   }

   public static int StrToMinutesInDay(String var0) {
      return Integer.parseInt(var0.substring(0, 2)) * 60 + Integer.parseInt(var0.substring(3, 5));
   }

   public static String UniqueTimeString() {
      long var0 = Calendar.getInstance().getTimeInMillis();
      long var2 = var0;
      if (var0 == time_old) {
         var2 = var0 + 1L;
      }

      time_old = var2;
      return String.valueOf(var2);
   }

   public static String getDate() {
      return getDate(-1L);
   }

   public static String getDate(long var0) {
      Calendar var2 = Calendar.getInstance();
      if (var0 >= 0L) {
         var2.setTimeInMillis(var0);
      }

      int var3 = var2.get(1);
      int var4 = var2.get(2);
      int var5 = var2.get(5);
      StringBuilder var6 = new StringBuilder();
      var6.append(var3);
      var6.append("-");
      var3 = var4 + 1;
      if (var3 < 10) {
         var6.append("0");
      }

      var6.append(var3);
      var6.append("-");
      if (var5 < 10) {
         var6.append("0");
      }

      var6.append(var5);
      return var6.toString();
   }

   public static int getDay(long var0) {
      Calendar var2 = Calendar.getInstance();
      if (var0 >= 0L) {
         var2.setTimeInMillis(var0);
      }

      return var2.get(5);
   }

   public static byte[] getExactTime256(long var0) {
      Calendar var2 = Calendar.getInstance();
      var2.setTimeInMillis(var0);
      int var3 = var2.get(1);
      int var4 = var2.get(2);
      int var5 = var2.get(5);
      int var6 = var2.get(11);
      int var7 = var2.get(12);
      int var8 = var2.get(13);
      int var9 = var2.get(14);
      int var10 = var2.get(7) - 1;
      int var11 = var10;
      if (var10 == 0) {
         var11 = 7;
      }

      var10 = var9 / 4;
      byte[] var12 = StringOp.uint16ToByteArray(var3);
      return new byte[]{var12[0], var12[1], (byte)(var4 + 1), (byte)var5, (byte)var6, (byte)var7, (byte)var8, (byte)var11, (byte)var10};
   }

   public static long getGMTms() {
      return (long)Calendar.getInstance().getTimeZone().getRawOffset();
   }

   public static String getHourMinuteString(int var0) {
      int var1 = var0 / 60;
      StringBuilder var2 = new StringBuilder();
      String var3 = String.format("%02d", var1);
      String var4 = String.format("%02d", var0 % 60);
      var2.append(var3);
      var2.append(":");
      var2.append(var4);
      return var2.toString();
   }

   public static int getMinuteOfDay(String var0) {
      String var1 = var0.substring(0, 2);
      var0 = var0.substring(3);
      return Integer.parseInt(var1) * 60 + Integer.parseInt(var0);
   }

   public static int getMonth(long var0) {
      Calendar var2 = Calendar.getInstance();
      if (var0 >= 0L) {
         var2.setTimeInMillis(var0);
      }

      return var2.get(2);
   }

   public static long getMsOfNextExactTime(int var0) {
      Calendar var1 = Calendar.getInstance();
      var1.get(1);
      var1.get(2);
      var1.get(3);
      var1.get(4);
      var1.get(6);
      var1.get(5);
      var1.get(7);
      var1.get(11);
      var1.get(12);
      var1.get(13);
      var1.set(14, 0);
      switch(var0) {
      case 1:
         var1.add(1, 1);
         var1.set(2, 0);
         var1.set(5, 1);
         var1.set(11, 0);
         var1.set(12, 0);
         var1.set(13, 0);
         break;
      case 2:
         var1.add(2, 1);
         var1.set(5, 1);
         var1.set(11, 0);
         var1.set(12, 0);
         var1.set(13, 0);
         break;
      case 3:
      case 4:
         var1.add(3, 1);
         var1.set(7, 1);
         var1.set(11, 0);
         var1.set(12, 0);
         var1.set(13, 0);
         break;
      case 5:
      case 6:
      case 7:
         var1.add(6, 1);
         var1.set(11, 0);
         var1.set(12, 0);
         var1.set(13, 0);
      case 8:
      case 9:
      case 10:
      default:
         break;
      case 11:
         var1.add(11, 1);
         var1.set(12, 0);
         var1.set(13, 0);
         break;
      case 12:
         var1.add(12, 1);
         var1.set(13, 0);
         break;
      case 13:
         var1.add(13, 1);
      }

      return var1.getTimeInMillis();
   }

   public static long getMsOfTimeToday(int var0, int var1, int var2) {
      Calendar var3 = Calendar.getInstance();
      var3.set(11, var0);
      var3.set(12, var1);
      var3.set(13, var2);
      return var3.getTimeInMillis();
   }

   public static long getNow() {
      return Calendar.getInstance().getTimeInMillis();
   }

   public static long getNowGMT() {
      Calendar var0 = Calendar.getInstance();
      TimeZone var1 = var0.getTimeZone();
      return var0.getTimeInMillis() - (long)var1.getRawOffset();
   }

   public static String getNowHourMinuteSecond() {
      Calendar var0 = Calendar.getInstance();
      return String.format("%02d:%02d:%02d", var0.get(11), var0.get(12), var0.get(13));
   }

   public static int getNowMinutesInDay() {
      Calendar var0 = Calendar.getInstance();
      return var0.get(11) * 60 + var0.get(12);
   }

   public static int getNowSecondsInDay() {
      Calendar var0 = Calendar.getInstance();
      return var0.get(11) * 3600 + var0.get(12) * 60 + var0.get(13);
   }

   public static int getThisYear() {
      return Calendar.getInstance().get(1);
   }

   public static long getTodayTimeInMillis() {
      Calendar var0 = Calendar.getInstance();
      int var1 = var0.get(1);
      int var2 = var0.get(2);
      int var3 = var0.get(5);
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append("-");
      var4.append(var2 + 1);
      var4.append("-");
      var4.append(var3);
      var4.append(" 00:00:00");
      SimpleDateFormat var5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

      try {
         var0.setTime(var5.parse(var4.toString()));
         long var6 = var0.getTimeInMillis();
         return var6;
      } catch (ParseException var8) {
         return 0L;
      }
   }

   public static int getYear(long var0) {
      Calendar var2 = Calendar.getInstance();
      if (var0 >= 0L) {
         var2.setTimeInMillis(var0);
      }

      return var2.get(1);
   }

   public static long get_MilliSecond_from_DateComponents(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      Calendar var8 = Calendar.getInstance(get_TimeZone_from_UTC_offset(var0));
      var8.set(1, var1);
      var8.set(2, var2);
      var8.set(5, var3);
      var8.set(11, var4);
      var8.set(12, var5);
      var8.set(13, var6);
      var8.set(14, var7);
      return var8.getTimeInMillis();
   }

   public static String get_Minute_Second_String_from_Seconds(int var0) {
      return get_Minute_Second_String_from_Seconds(var0, ":");
   }

   public static String get_Minute_Second_String_from_Seconds(int var0, String var1) {
      int var2 = var0 / 60;
      StringBuilder var3 = new StringBuilder();
      String var4 = String.format("%02d", var2);
      String var5 = String.format("%02d", var0 % 60);
      var3.append(var4);
      var3.append(var1);
      var3.append(var5);
      return var3.toString();
   }

   public static TimeZone get_TimeZone_from_UTC_offset(int var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("GMT");
      var1.append(String.format("+%d", var0));
      return TimeZone.getTimeZone(var1.toString());
   }

   public static int get_UTC_offset() {
      return Calendar.getInstance().getTimeZone().getRawOffset() / 1000 / 60 / 60;
   }

   public static boolean isDateExpired(String var0) {
      return isExpired(DateToMs(var0));
   }

   public static boolean isExpired(long var0) {
      boolean var2;
      if (getNow() > var0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static void setAlarmRepeatWakeUp(Context var0, Class<?> var1, long var2, long var4) {
      AlarmManager var6 = (AlarmManager)var0.getSystemService("alarm");
      long var7 = var2;
      if (var2 < System.currentTimeMillis()) {
         var7 = var2 + var4;
      }

      var6.setInexactRepeating(0, var7, var4, PendingIntent.getBroadcast(var0, 0, new Intent(var0, var1), 134217728));
   }

   public static void setAlarmWakeUp(Context var0, Class<?> var1, long var2) {
      ((AlarmManager)var0.getSystemService("alarm")).set(0, var2, PendingIntent.getBroadcast(var0, 0, new Intent(var0, var1), 134217728));
   }

   public static void sleep(long var0) {
      try {
         Thread.sleep(var0);
      } catch (InterruptedException var3) {
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

      public DateAndTime(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         this.utc_offset = var1;
         this.year = var2;
         this.month = var3;
         this.day = var4;
         this.hour = var5;
         this.minute = var6;
         this.second = var7;
         this.milli_second = var8;
         this.ms = TimeOp.get_MilliSecond_from_DateComponents(var1, var2, var3, var4, var5, var6, var7, var8);
      }

      public DateAndTime(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9) {
         this.utc_offset = var1;
         this.year = var2;
         this.month = var3;
         this.day = var4;
         this.hour = var5;
         this.minute = var6;
         this.second = var7;
         this.milli_second = var8;
         this.ms = var9;
      }

      public DateAndTime(int var1, long var2) {
         Calendar var4 = Calendar.getInstance(TimeOp.get_TimeZone_from_UTC_offset(var1));
         var4.setTimeInMillis(var2);
         this.utc_offset = var1;
         this.year = var4.get(1);
         this.month = var4.get(2);
         this.day = var4.get(5);
         this.hour = var4.get(11);
         this.minute = var4.get(12);
         this.second = var4.get(13);
         this.milli_second = var4.get(14);
         this.ms = var2;
      }

      public TimeOp.DateAndTime add_MilliSecond(long var1) {
         long var3 = this.ms;
         return new TimeOp.DateAndTime(this.utc_offset, var3 + var1);
      }

      public long convert_to_MilliSecond(TimeOp.DateAndTime var1) {
         Calendar var2 = Calendar.getInstance(TimeOp.get_TimeZone_from_UTC_offset(var1.utc_offset));
         var2.set(1, var1.year);
         var2.set(2, var1.month);
         var2.set(5, var1.day);
         var2.set(11, var1.hour);
         var2.set(12, var1.minute);
         var2.set(13, var1.second);
         var2.set(14, var1.milli_second);
         return var2.getTimeInMillis();
      }

      public long get_MilliSecond(TimeOp.DateAndTime var1) {
         return var1.ms;
      }
   }

   public static class DelayTask {
      Timer timer = null;
      TimerTask timerTask = null;

      public void TaskAtSchedule() {
      }

      public void TaskAtStart() {
      }

      public void start(int var1) {
         this.stop();
         this.timerTask = new TimerTask() {
            public void run() {
               DelayTask.this.TaskAtSchedule();
            }
         };
         Timer var2 = new Timer();
         this.timer = var2;
         var2.schedule(this.timerTask, (long)var1);
         this.TaskAtStart();
      }

      public void stop() {
         Timer var1 = this.timer;
         if (var1 != null) {
            var1.cancel();
            this.timerTask = null;
            this.timer = null;
         }

      }
   }

   public static class DigitalClock {
      public static ScheduledExecutorService executor_scheduled;
      Runnable tick = null;

      public DigitalClock(long var1) {
         long var3 = TimeOp.getNow();
         this.tick = new Runnable() {
            public void run() {
               long var1 = TimeOp.getNow();
               DigitalClock.this.OnPeriod(var1);
            }
         };
         ScheduledExecutorService var5 = Executors.newSingleThreadScheduledExecutor();
         executor_scheduled = var5;
         var5.scheduleAtFixedRate(this.tick, var1 - var3 % var1, var1, TimeUnit.MILLISECONDS);
         this.OnPeriod(var3);
      }

      public void OnPeriod(long var1) {
      }

      public void stop() {
         ScheduledExecutorService var1 = executor_scheduled;
         if (var1 != null) {
            var1.shutdownNow();
         }

      }
   }
}
