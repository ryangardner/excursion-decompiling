package com.google.zxing.client.result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CalendarParsedResult extends ParsedResult {
   private static final Pattern DATE_TIME = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");
   private static final Pattern RFC2445_DURATION = Pattern.compile("P(?:(\\d+)W)?(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?");
   private static final long[] RFC2445_DURATION_FIELD_UNITS = new long[]{604800000L, 86400000L, 3600000L, 60000L, 1000L};
   private final String[] attendees;
   private final String description;
   private final Date end;
   private final boolean endAllDay;
   private final double latitude;
   private final String location;
   private final double longitude;
   private final String organizer;
   private final Date start;
   private final boolean startAllDay;
   private final String summary;

   public CalendarParsedResult(String var1, String var2, String var3, String var4, String var5, String var6, String[] var7, String var8, double var9, double var11) {
      super(ParsedResultType.CALENDAR);
      this.summary = var1;

      try {
         this.start = parseDate(var2);
      } catch (ParseException var19) {
         throw new IllegalArgumentException(var19.toString());
      }

      if (var3 == null) {
         long var13 = parseDurationMS(var4);
         Date var20;
         if (var13 < 0L) {
            var20 = null;
         } else {
            var20 = new Date(this.start.getTime() + var13);
         }

         this.end = var20;
      } else {
         try {
            this.end = parseDate(var3);
         } catch (ParseException var18) {
            throw new IllegalArgumentException(var18.toString());
         }
      }

      int var15 = var2.length();
      boolean var16 = true;
      boolean var17;
      if (var15 == 8) {
         var17 = true;
      } else {
         var17 = false;
      }

      this.startAllDay = var17;
      if (var3 != null && var3.length() == 8) {
         var17 = var16;
      } else {
         var17 = false;
      }

      this.endAllDay = var17;
      this.location = var5;
      this.organizer = var6;
      this.attendees = var7;
      this.description = var8;
      this.latitude = var9;
      this.longitude = var11;
   }

   private static DateFormat buildDateFormat() {
      SimpleDateFormat var0 = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
      var0.setTimeZone(TimeZone.getTimeZone("GMT"));
      return var0;
   }

   private static DateFormat buildDateTimeFormat() {
      return new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH);
   }

   private static String format(boolean var0, Date var1) {
      if (var1 == null) {
         return null;
      } else {
         DateFormat var2;
         if (var0) {
            var2 = DateFormat.getDateInstance(2);
         } else {
            var2 = DateFormat.getDateTimeInstance(2, 2);
         }

         return var2.format(var1);
      }
   }

   private static Date parseDate(String var0) throws ParseException {
      if (DATE_TIME.matcher(var0).matches()) {
         if (var0.length() == 8) {
            return buildDateFormat().parse(var0);
         } else {
            Date var4;
            if (var0.length() == 16 && var0.charAt(15) == 'Z') {
               Date var1 = buildDateTimeFormat().parse(var0.substring(0, 15));
               GregorianCalendar var5 = new GregorianCalendar();
               long var2 = var1.getTime() + (long)var5.get(15);
               var5.setTime(new Date(var2));
               var4 = new Date(var2 + (long)var5.get(16));
            } else {
               var4 = buildDateTimeFormat().parse(var0);
            }

            return var4;
         }
      } else {
         throw new ParseException(var0, 0);
      }
   }

   private static long parseDurationMS(CharSequence var0) {
      if (var0 == null) {
         return -1L;
      } else {
         Matcher var8 = RFC2445_DURATION.matcher(var0);
         if (!var8.matches()) {
            return -1L;
         } else {
            long var1 = 0L;

            long var6;
            for(int var3 = 0; var3 < RFC2445_DURATION_FIELD_UNITS.length; var1 = var6) {
               int var4 = var3 + 1;
               String var5 = var8.group(var4);
               var6 = var1;
               if (var5 != null) {
                  var6 = var1 + RFC2445_DURATION_FIELD_UNITS[var3] * (long)Integer.parseInt(var5);
               }

               var3 = var4;
            }

            return var1;
         }
      }
   }

   public String[] getAttendees() {
      return this.attendees;
   }

   public String getDescription() {
      return this.description;
   }

   public String getDisplayResult() {
      StringBuilder var1 = new StringBuilder(100);
      maybeAppend(this.summary, var1);
      maybeAppend(format(this.startAllDay, this.start), var1);
      maybeAppend(format(this.endAllDay, this.end), var1);
      maybeAppend(this.location, var1);
      maybeAppend(this.organizer, var1);
      maybeAppend(this.attendees, var1);
      maybeAppend(this.description, var1);
      return var1.toString();
   }

   public Date getEnd() {
      return this.end;
   }

   public double getLatitude() {
      return this.latitude;
   }

   public String getLocation() {
      return this.location;
   }

   public double getLongitude() {
      return this.longitude;
   }

   public String getOrganizer() {
      return this.organizer;
   }

   public Date getStart() {
      return this.start;
   }

   public String getSummary() {
      return this.summary;
   }

   public boolean isEndAllDay() {
      return this.endAllDay;
   }

   public boolean isStartAllDay() {
      return this.startAllDay;
   }
}
