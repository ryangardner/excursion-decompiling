package org.apache.commons.net.ftp.parser;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;

public class FTPTimestampParserImpl implements FTPTimestampParser, Configurable {
   private static final int[] CALENDAR_UNITS = new int[]{14, 13, 12, 11, 5, 2, 1};
   private SimpleDateFormat defaultDateFormat;
   private int defaultDateSmallestUnitIndex;
   private boolean lenientFutureDates = false;
   private SimpleDateFormat recentDateFormat;
   private int recentDateSmallestUnitIndex;

   public FTPTimestampParserImpl() {
      this.setDefaultDateFormat("MMM d yyyy", (DateFormatSymbols)null);
      this.setRecentDateFormat("MMM d HH:mm", (DateFormatSymbols)null);
   }

   private static int getEntry(SimpleDateFormat var0) {
      if (var0 == null) {
         return 0;
      } else {
         String var5 = var0.toPattern();
         char[] var1 = "SsmHdM".toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var4 = var1[var3];
            if (var5.indexOf(var4) != -1) {
               if (var4 == 'H') {
                  return indexOf(11);
               }

               if (var4 == 'M') {
                  return indexOf(2);
               }

               if (var4 == 'S') {
                  return indexOf(14);
               }

               if (var4 == 'd') {
                  return indexOf(5);
               }

               if (var4 == 'm') {
                  return indexOf(12);
               }

               if (var4 == 's') {
                  return indexOf(13);
               }
            }
         }

         return 0;
      }
   }

   private static int indexOf(int var0) {
      int var1 = 0;

      while(true) {
         int[] var2 = CALENDAR_UNITS;
         if (var1 >= var2.length) {
            return 0;
         }

         if (var0 == var2[var1]) {
            return var1;
         }

         ++var1;
      }
   }

   private void setDefaultDateFormat(String var1, DateFormatSymbols var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.defaultDateFormat = new SimpleDateFormat(var1, var2);
         } else {
            this.defaultDateFormat = new SimpleDateFormat(var1);
         }

         this.defaultDateFormat.setLenient(false);
      } else {
         this.defaultDateFormat = null;
      }

      this.defaultDateSmallestUnitIndex = getEntry(this.defaultDateFormat);
   }

   private static void setPrecision(int var0, Calendar var1) {
      if (var0 > 0) {
         var0 = CALENDAR_UNITS[var0 - 1];
         if (var1.get(var0) == 0) {
            var1.clear(var0);
         }

      }
   }

   private void setRecentDateFormat(String var1, DateFormatSymbols var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.recentDateFormat = new SimpleDateFormat(var1, var2);
         } else {
            this.recentDateFormat = new SimpleDateFormat(var1);
         }

         this.recentDateFormat.setLenient(false);
      } else {
         this.recentDateFormat = null;
      }

      this.recentDateSmallestUnitIndex = getEntry(this.recentDateFormat);
   }

   private void setServerTimeZone(String var1) {
      TimeZone var2 = TimeZone.getDefault();
      if (var1 != null) {
         var2 = TimeZone.getTimeZone(var1);
      }

      this.defaultDateFormat.setTimeZone(var2);
      SimpleDateFormat var3 = this.recentDateFormat;
      if (var3 != null) {
         var3.setTimeZone(var2);
      }

   }

   public void configure(FTPClientConfig var1) {
      String var2 = var1.getServerLanguageCode();
      String var3 = var1.getShortMonthNames();
      DateFormatSymbols var4;
      if (var3 != null) {
         var4 = FTPClientConfig.getDateFormatSymbols(var3);
      } else if (var2 != null) {
         var4 = FTPClientConfig.lookupDateFormatSymbols(var2);
      } else {
         var4 = FTPClientConfig.lookupDateFormatSymbols("en");
      }

      this.setRecentDateFormat(var1.getRecentDateFormatStr(), var4);
      var3 = var1.getDefaultDateFormatStr();
      if (var3 != null) {
         this.setDefaultDateFormat(var3, var4);
         this.setServerTimeZone(var1.getServerTimeZoneId());
         this.lenientFutureDates = var1.isLenientFutureDates();
      } else {
         throw new IllegalArgumentException("defaultFormatString cannot be null");
      }
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

   public Calendar parseTimestamp(String var1) throws ParseException {
      return this.parseTimestamp(var1, Calendar.getInstance());
   }

   public Calendar parseTimestamp(String var1, Calendar var2) throws ParseException {
      Calendar var3 = (Calendar)var2.clone();
      var3.setTimeZone(this.getServerTimeZone());
      if (this.recentDateFormat != null) {
         Calendar var4 = (Calendar)var2.clone();
         var4.setTimeZone(this.getServerTimeZone());
         if (this.lenientFutureDates) {
            var4.add(5, 1);
         }

         String var5 = Integer.toString(var4.get(1));
         StringBuilder var6 = new StringBuilder();
         var6.append(var1);
         var6.append(" ");
         var6.append(var5);
         var5 = var6.toString();
         var6 = new StringBuilder();
         var6.append(this.recentDateFormat.toPattern());
         var6.append(" yyyy");
         SimpleDateFormat var7 = new SimpleDateFormat(var6.toString(), this.recentDateFormat.getDateFormatSymbols());
         var7.setLenient(false);
         var7.setTimeZone(this.recentDateFormat.getTimeZone());
         ParsePosition var11 = new ParsePosition(0);
         Date var12 = var7.parse(var5, var11);
         if (var12 != null && var11.getIndex() == var5.length()) {
            var3.setTime(var12);
            if (var3.after(var4)) {
               var3.add(1, -1);
            }

            setPrecision(this.recentDateSmallestUnitIndex, var3);
            return var3;
         }
      }

      ParsePosition var9 = new ParsePosition(0);
      Date var10 = this.defaultDateFormat.parse(var1, var9);
      if (var10 != null && var9.getIndex() == var1.length()) {
         var3.setTime(var10);
         setPrecision(this.defaultDateSmallestUnitIndex, var3);
         return var3;
      } else {
         StringBuilder var8 = new StringBuilder();
         var8.append("Timestamp '");
         var8.append(var1);
         var8.append("' could not be parsed using a server time of ");
         var8.append(var2.getTime().toString());
         throw new ParseException(var8.toString(), var9.getErrorIndex());
      }
   }

   void setLenientFutureDates(boolean var1) {
      this.lenientFutureDates = var1;
   }
}
