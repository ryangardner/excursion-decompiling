package com.google.android.material.datepicker;

import java.util.Calendar;
import java.util.TimeZone;

class TimeSource {
   private static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource((Long)null, (TimeZone)null);
   private final Long fixedTimeMs;
   private final TimeZone fixedTimeZone;

   private TimeSource(Long var1, TimeZone var2) {
      this.fixedTimeMs = var1;
      this.fixedTimeZone = var2;
   }

   static TimeSource fixed(long var0) {
      return new TimeSource(var0, (TimeZone)null);
   }

   static TimeSource fixed(long var0, TimeZone var2) {
      return new TimeSource(var0, var2);
   }

   static TimeSource system() {
      return SYSTEM_TIME_SOURCE;
   }

   Calendar now() {
      return this.now(this.fixedTimeZone);
   }

   Calendar now(TimeZone var1) {
      Calendar var3;
      if (var1 == null) {
         var3 = Calendar.getInstance();
      } else {
         var3 = Calendar.getInstance(var1);
      }

      Long var2 = this.fixedTimeMs;
      if (var2 != null) {
         var3.setTimeInMillis(var2);
      }

      return var3;
   }
}
