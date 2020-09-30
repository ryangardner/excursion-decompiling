package org.apache.http.impl.cookie;

import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class DateUtils {
   private static final String[] DEFAULT_PATTERNS = new String[]{"EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};
   private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
   public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
   public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
   public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
   public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

   static {
      Calendar var0 = Calendar.getInstance();
      var0.setTimeZone(GMT);
      var0.set(2000, 0, 1, 0, 0, 0);
      var0.set(14, 0);
      DEFAULT_TWO_DIGIT_YEAR_START = var0.getTime();
   }

   private DateUtils() {
   }

   public static String formatDate(Date var0) {
      return formatDate(var0, "EEE, dd MMM yyyy HH:mm:ss zzz");
   }

   public static String formatDate(Date var0, String var1) {
      if (var0 != null) {
         if (var1 != null) {
            return DateUtils.DateFormatHolder.formatFor(var1).format(var0);
         } else {
            throw new IllegalArgumentException("pattern is null");
         }
      } else {
         throw new IllegalArgumentException("date is null");
      }
   }

   public static Date parseDate(String var0) throws DateParseException {
      return parseDate(var0, (String[])null, (Date)null);
   }

   public static Date parseDate(String var0, String[] var1) throws DateParseException {
      return parseDate(var0, var1, (Date)null);
   }

   public static Date parseDate(String var0, String[] var1, Date var2) throws DateParseException {
      if (var0 == null) {
         throw new IllegalArgumentException("dateValue is null");
      } else {
         String[] var3 = var1;
         if (var1 == null) {
            var3 = DEFAULT_PATTERNS;
         }

         Date var10 = var2;
         if (var2 == null) {
            var10 = DEFAULT_TWO_DIGIT_YEAR_START;
         }

         String var11 = var0;
         if (var0.length() > 1) {
            var11 = var0;
            if (var0.startsWith("'")) {
               var11 = var0;
               if (var0.endsWith("'")) {
                  var11 = var0.substring(1, var0.length() - 1);
               }
            }
         }

         int var4 = var3.length;
         int var5 = 0;

         while(var5 < var4) {
            SimpleDateFormat var7 = DateUtils.DateFormatHolder.formatFor(var3[var5]);
            var7.set2DigitYearStart(var10);

            try {
               Date var8 = var7.parse(var11);
               return var8;
            } catch (ParseException var6) {
               ++var5;
            }
         }

         StringBuilder var9 = new StringBuilder();
         var9.append("Unable to parse the date ");
         var9.append(var11);
         throw new DateParseException(var9.toString());
      }
   }

   static final class DateFormatHolder {
      private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>() {
         protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
            return new SoftReference(new HashMap());
         }
      };

      public static SimpleDateFormat formatFor(String var0) {
         Map var1 = (Map)((SoftReference)THREADLOCAL_FORMATS.get()).get();
         Object var2 = var1;
         if (var1 == null) {
            var2 = new HashMap();
            THREADLOCAL_FORMATS.set(new SoftReference(var2));
         }

         SimpleDateFormat var3 = (SimpleDateFormat)((Map)var2).get(var0);
         SimpleDateFormat var4 = var3;
         if (var3 == null) {
            var4 = new SimpleDateFormat(var0, Locale.US);
            var4.setTimeZone(TimeZone.getTimeZone("GMT"));
            ((Map)var2).put(var0, var4);
         }

         return var4;
      }
   }
}
