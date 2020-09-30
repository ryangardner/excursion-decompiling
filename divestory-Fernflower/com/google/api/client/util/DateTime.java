package com.google.api.client.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateTime implements Serializable {
   private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
   private static final Pattern RFC3339_PATTERN = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d{1,9})?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?");
   private static final String RFC3339_REGEX = "(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d{1,9})?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?";
   private static final long serialVersionUID = 1L;
   private final boolean dateOnly;
   private final int tzShift;
   private final long value;

   public DateTime(long var1) {
      this(false, var1, (Integer)null);
   }

   public DateTime(long var1, int var3) {
      this(false, var1, var3);
   }

   public DateTime(String var1) {
      DateTime var2 = parseRfc3339(var1);
      this.dateOnly = var2.dateOnly;
      this.value = var2.value;
      this.tzShift = var2.tzShift;
   }

   public DateTime(Date var1) {
      this(var1.getTime());
   }

   public DateTime(Date var1, TimeZone var2) {
      long var3 = var1.getTime();
      Integer var5;
      if (var2 == null) {
         var5 = null;
      } else {
         var5 = var2.getOffset(var1.getTime()) / '\uea60';
      }

      this(false, var3, var5);
   }

   public DateTime(boolean var1, long var2, Integer var4) {
      this.dateOnly = var1;
      this.value = var2;
      int var5;
      if (var1) {
         var5 = 0;
      } else if (var4 == null) {
         var5 = TimeZone.getDefault().getOffset(var2) / '\uea60';
      } else {
         var5 = var4;
      }

      this.tzShift = var5;
   }

   private static void appendInt(StringBuilder var0, int var1, int var2) {
      int var3 = var1;
      if (var1 < 0) {
         var0.append('-');
         var3 = -var1;
      }

      for(var1 = var3; var1 > 0; --var2) {
         var1 /= 10;
      }

      for(var1 = 0; var1 < var2; ++var1) {
         var0.append('0');
      }

      if (var3 != 0) {
         var0.append(var3);
      }

   }

   public static DateTime parseRfc3339(String var0) {
      return parseRfc3339WithNanoSeconds(var0).toDateTime();
   }

   public static DateTime.SecondsAndNanos parseRfc3339ToSecondsAndNanos(String var0) {
      return parseRfc3339WithNanoSeconds(var0).toSecondsAndNanos();
   }

   private static DateTime.Rfc3339ParseResult parseRfc3339WithNanoSeconds(String var0) throws NumberFormatException {
      Matcher var1 = RFC3339_PATTERN.matcher(var0);
      StringBuilder var6;
      if (var1.matches()) {
         int var2 = Integer.parseInt(var1.group(1));
         int var3 = Integer.parseInt(var1.group(2));
         int var4 = Integer.parseInt(var1.group(3));
         boolean var5;
         if (var1.group(4) != null) {
            var5 = true;
         } else {
            var5 = false;
         }

         String var16 = var1.group(9);
         boolean var7;
         if (var16 != null) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7 && !var5) {
            var6 = new StringBuilder();
            var6.append("Invalid date/time format, cannot specify time zone shift without specifying time: ");
            var6.append(var0);
            throw new NumberFormatException(var6.toString());
         } else {
            int var8;
            int var9;
            int var10;
            int var11;
            if (var5) {
               var8 = Integer.parseInt(var1.group(5));
               var9 = Integer.parseInt(var1.group(6));
               var10 = Integer.parseInt(var1.group(7));
               if (var1.group(8) != null) {
                  var11 = Integer.parseInt(com.google.common.base.Strings.padEnd(var1.group(8).substring(1), 9, '0'));
               } else {
                  var11 = 0;
               }
            } else {
               var8 = 0;
               var9 = 0;
               var10 = 0;
               var11 = 0;
            }

            GregorianCalendar var14 = new GregorianCalendar(GMT);
            var14.clear();
            var14.set(var2, var3 - 1, var4, var8, var9, var10);
            long var12 = var14.getTimeInMillis();
            Integer var15;
            if (var5 && var7) {
               if (Character.toUpperCase(var16.charAt(0)) != 'Z') {
                  var10 = Integer.parseInt(var1.group(11)) * 60 + Integer.parseInt(var1.group(12));
                  int var17 = var10;
                  if (var1.group(10).charAt(0) == '-') {
                     var17 = -var10;
                  }

                  var12 -= (long)var17 * 60000L;
                  var15 = var17;
               } else {
                  var15 = 0;
               }
            } else {
               var15 = null;
            }

            return new DateTime.Rfc3339ParseResult(var12 / 1000L, var11, var5, var15);
         }
      } else {
         var6 = new StringBuilder();
         var6.append("Invalid date/time format: ");
         var6.append(var0);
         throw new NumberFormatException(var6.toString());
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof DateTime)) {
         return false;
      } else {
         DateTime var3 = (DateTime)var1;
         if (this.dateOnly != var3.dateOnly || this.value != var3.value || this.tzShift != var3.tzShift) {
            var2 = false;
         }

         return var2;
      }
   }

   public int getTimeZoneShift() {
      return this.tzShift;
   }

   public long getValue() {
      return this.value;
   }

   public int hashCode() {
      long var1 = this.value;
      long var3;
      if (this.dateOnly) {
         var3 = 1L;
      } else {
         var3 = 0L;
      }

      return Arrays.hashCode(new long[]{var1, var3, (long)this.tzShift});
   }

   public boolean isDateOnly() {
      return this.dateOnly;
   }

   public String toString() {
      return this.toStringRfc3339();
   }

   public String toStringRfc3339() {
      StringBuilder var1 = new StringBuilder();
      GregorianCalendar var2 = new GregorianCalendar(GMT);
      var2.setTimeInMillis(this.value + (long)this.tzShift * 60000L);
      appendInt(var1, var2.get(1), 4);
      var1.append('-');
      appendInt(var1, var2.get(2) + 1, 2);
      var1.append('-');
      appendInt(var1, var2.get(5), 2);
      if (!this.dateOnly) {
         var1.append('T');
         appendInt(var1, var2.get(11), 2);
         var1.append(':');
         appendInt(var1, var2.get(12), 2);
         var1.append(':');
         appendInt(var1, var2.get(13), 2);
         if (var2.isSet(14)) {
            var1.append('.');
            appendInt(var1, var2.get(14), 3);
         }

         int var3 = this.tzShift;
         if (var3 == 0) {
            var1.append('Z');
         } else {
            if (var3 > 0) {
               var1.append('+');
            } else {
               var1.append('-');
               var3 = -var3;
            }

            appendInt(var1, var3 / 60, 2);
            var1.append(':');
            appendInt(var1, var3 % 60, 2);
         }
      }

      return var1.toString();
   }

   private static class Rfc3339ParseResult implements Serializable {
      private final int nanos;
      private final long seconds;
      private final boolean timeGiven;
      private final Integer tzShift;

      private Rfc3339ParseResult(long var1, int var3, boolean var4, Integer var5) {
         this.seconds = var1;
         this.nanos = var3;
         this.timeGiven = var4;
         this.tzShift = var5;
      }

      // $FF: synthetic method
      Rfc3339ParseResult(long var1, int var3, boolean var4, Integer var5, Object var6) {
         this(var1, var3, var4, var5);
      }

      private DateTime toDateTime() {
         long var1 = TimeUnit.SECONDS.toMillis(this.seconds);
         long var3 = TimeUnit.NANOSECONDS.toMillis((long)this.nanos);
         return new DateTime(this.timeGiven ^ true, var1 + var3, this.tzShift);
      }

      private DateTime.SecondsAndNanos toSecondsAndNanos() {
         return new DateTime.SecondsAndNanos(this.seconds, this.nanos);
      }
   }

   public static final class SecondsAndNanos implements Serializable {
      private final int nanos;
      private final long seconds;

      private SecondsAndNanos(long var1, int var3) {
         this.seconds = var1;
         this.nanos = var3;
      }

      // $FF: synthetic method
      SecondsAndNanos(long var1, int var3, Object var4) {
         this(var1, var3);
      }

      public static DateTime.SecondsAndNanos ofSecondsAndNanos(long var0, int var2) {
         return new DateTime.SecondsAndNanos(var0, var2);
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            DateTime.SecondsAndNanos var3 = (DateTime.SecondsAndNanos)var1;
            if (this.seconds != var3.seconds || this.nanos != var3.nanos) {
               var2 = false;
            }

            return var2;
         } else {
            return false;
         }
      }

      public int getNanos() {
         return this.nanos;
      }

      public long getSeconds() {
         return this.seconds;
      }

      public int hashCode() {
         return java.util.Objects.hash(new Object[]{this.seconds, this.nanos});
      }

      public String toString() {
         return String.format("Seconds: %d, Nanos: %d", this.seconds, this.nanos);
      }
   }
}
