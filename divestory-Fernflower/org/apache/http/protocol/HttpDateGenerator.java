package org.apache.http.protocol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HttpDateGenerator {
   public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
   public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
   private long dateAsLong = 0L;
   private String dateAsText = null;
   private final DateFormat dateformat;

   public HttpDateGenerator() {
      SimpleDateFormat var1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
      this.dateformat = var1;
      var1.setTimeZone(GMT);
   }

   public String getCurrentDate() {
      synchronized(this){}

      String var7;
      try {
         long var1 = System.currentTimeMillis();
         if (var1 - this.dateAsLong > 1000L) {
            DateFormat var3 = this.dateformat;
            Date var4 = new Date(var1);
            this.dateAsText = var3.format(var4);
            this.dateAsLong = var1;
         }

         var7 = this.dateAsText;
      } finally {
         ;
      }

      return var7;
   }
}
