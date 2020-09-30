package javax.mail.internet;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class MailDateFormat extends SimpleDateFormat {
   private static Calendar cal;
   static boolean debug;
   private static final long serialVersionUID = -8148227605210628779L;
   private static TimeZone tz = TimeZone.getTimeZone("GMT");

   static {
      cal = new GregorianCalendar(tz);
   }

   public MailDateFormat() {
      super("EEE, d MMM yyyy HH:mm:ss 'XXXXX' (z)", Locale.US);
   }

   private static Date ourUTC(int var0, int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      synchronized(MailDateFormat.class){}

      Date var8;
      try {
         cal.clear();
         cal.setLenient(var7);
         cal.set(1, var0);
         cal.set(2, var1);
         cal.set(5, var2);
         cal.set(11, var3);
         cal.set(12, var4 + var6);
         cal.set(13, var5);
         var8 = cal.getTime();
      } finally {
         ;
      }

      return var8;
   }

   private static Date parseDate(char[] param0, ParsePosition param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public StringBuffer format(Date var1, StringBuffer var2, FieldPosition var3) {
      int var4 = var2.length();
      super.format(var1, var2, var3);

      for(var4 += 25; var2.charAt(var4) != 'X'; ++var4) {
      }

      this.calendar.clear();
      this.calendar.setTime(var1);
      int var5 = this.calendar.get(15) + this.calendar.get(16);
      int var6;
      if (var5 < 0) {
         var6 = var4 + 1;
         var2.setCharAt(var4, '-');
         var5 = -var5;
         var4 = var6;
      } else {
         var6 = var4 + 1;
         var2.setCharAt(var4, '+');
         var4 = var6;
      }

      var6 = var5 / 60 / 1000;
      var5 = var6 / 60;
      int var7 = var6 % 60;
      var6 = var4 + 1;
      var2.setCharAt(var4, Character.forDigit(var5 / 10, 10));
      var4 = var6 + 1;
      var2.setCharAt(var6, Character.forDigit(var5 % 10, 10));
      var2.setCharAt(var4, Character.forDigit(var7 / 10, 10));
      var2.setCharAt(var4 + 1, Character.forDigit(var7 % 10, 10));
      return var2;
   }

   public Date parse(String var1, ParsePosition var2) {
      return parseDate(var1.toCharArray(), var2, this.isLenient());
   }

   public void setCalendar(Calendar var1) {
      throw new RuntimeException("Method setCalendar() shouldn't be called");
   }

   public void setNumberFormat(NumberFormat var1) {
      throw new RuntimeException("Method setNumberFormat() shouldn't be called");
   }
}
