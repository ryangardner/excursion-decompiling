package org.apache.commons.net.ntp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeStamp implements Serializable, Comparable<TimeStamp> {
   public static final String NTP_DATE_FORMAT = "EEE, MMM dd yyyy HH:mm:ss.SSS";
   protected static final long msb0baseTime = 2085978496000L;
   protected static final long msb1baseTime = -2208988800000L;
   private static final long serialVersionUID = 8139806907588338737L;
   private final long ntpTime;
   private DateFormat simpleFormatter;
   private DateFormat utcFormatter;

   public TimeStamp(long var1) {
      this.ntpTime = var1;
   }

   public TimeStamp(String var1) throws NumberFormatException {
      this.ntpTime = decodeNtpHexString(var1);
   }

   public TimeStamp(Date var1) {
      long var2;
      if (var1 == null) {
         var2 = 0L;
      } else {
         var2 = toNtpTime(var1.getTime());
      }

      this.ntpTime = var2;
   }

   private static void appendHexString(StringBuilder var0, long var1) {
      String var3 = Long.toHexString(var1);

      for(int var4 = var3.length(); var4 < 8; ++var4) {
         var0.append('0');
      }

      var0.append(var3);
   }

   protected static long decodeNtpHexString(String var0) throws NumberFormatException {
      if (var0 != null) {
         int var1 = var0.indexOf(46);
         if (var1 == -1) {
            return var0.length() == 0 ? 0L : Long.parseLong(var0, 16) << 32;
         } else {
            return Long.parseLong(var0.substring(0, var1), 16) << 32 | Long.parseLong(var0.substring(var1 + 1), 16);
         }
      } else {
         throw new NumberFormatException("null");
      }
   }

   public static TimeStamp getCurrentTime() {
      return getNtpTime(System.currentTimeMillis());
   }

   public static TimeStamp getNtpTime(long var0) {
      return new TimeStamp(toNtpTime(var0));
   }

   public static long getTime(long var0) {
      long var2 = var0 >>> 32 & 4294967295L;
      long var4 = Math.round((double)(var0 & 4294967295L) * 1000.0D / 4.294967296E9D);
      if ((2147483648L & var2) == 0L) {
         var0 = 2085978496000L;
      } else {
         var0 = -2208988800000L;
      }

      return var2 * 1000L + var0 + var4;
   }

   public static TimeStamp parseNtpString(String var0) throws NumberFormatException {
      return new TimeStamp(decodeNtpHexString(var0));
   }

   protected static long toNtpTime(long var0) {
      long var2 = 2085978496000L;
      boolean var4;
      if (var0 < 2085978496000L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var2 = -2208988800000L;
      }

      var0 -= var2;
      var2 = var0 / 1000L;
      long var5 = var0 % 1000L * 4294967296L / 1000L;
      var0 = var2;
      if (var4) {
         var0 = var2 | 2147483648L;
      }

      return var5 | var0 << 32;
   }

   public static String toString(long var0) {
      StringBuilder var2 = new StringBuilder();
      appendHexString(var2, var0 >>> 32 & 4294967295L);
      var2.append('.');
      appendHexString(var2, var0 & 4294967295L);
      return var2.toString();
   }

   public int compareTo(TimeStamp var1) {
      long var4;
      int var2 = (var4 = this.ntpTime - var1.ntpTime) == 0L ? 0 : (var4 < 0L ? -1 : 1);
      byte var3;
      if (var2 < 0) {
         var3 = -1;
      } else if (var2 == 0) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      return var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof TimeStamp;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         var4 = var3;
         if (this.ntpTime == ((TimeStamp)var1).ntpValue()) {
            var4 = true;
         }
      }

      return var4;
   }

   public Date getDate() {
      return new Date(getTime(this.ntpTime));
   }

   public long getFraction() {
      return this.ntpTime & 4294967295L;
   }

   public long getSeconds() {
      return this.ntpTime >>> 32 & 4294967295L;
   }

   public long getTime() {
      return getTime(this.ntpTime);
   }

   public int hashCode() {
      long var1 = this.ntpTime;
      return (int)(var1 ^ var1 >>> 32);
   }

   public long ntpValue() {
      return this.ntpTime;
   }

   public String toDateString() {
      if (this.simpleFormatter == null) {
         SimpleDateFormat var1 = new SimpleDateFormat("EEE, MMM dd yyyy HH:mm:ss.SSS", Locale.US);
         this.simpleFormatter = var1;
         var1.setTimeZone(TimeZone.getDefault());
      }

      Date var2 = this.getDate();
      return this.simpleFormatter.format(var2);
   }

   public String toString() {
      return toString(this.ntpTime);
   }

   public String toUTCString() {
      if (this.utcFormatter == null) {
         SimpleDateFormat var1 = new SimpleDateFormat("EEE, MMM dd yyyy HH:mm:ss.SSS 'UTC'", Locale.US);
         this.utcFormatter = var1;
         var1.setTimeZone(TimeZone.getTimeZone("UTC"));
      }

      Date var2 = this.getDate();
      return this.utcFormatter.format(var2);
   }
}
