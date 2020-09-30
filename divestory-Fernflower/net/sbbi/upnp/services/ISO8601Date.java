package net.sbbi.upnp.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class ISO8601Date {
   private static boolean check(StringTokenizer var0, String var1) throws NumberFormatException {
      try {
         if (var0.nextToken().equals(var1)) {
            return true;
         } else {
            StringBuilder var4 = new StringBuilder("Missing [");
            var4.append(var1);
            var4.append("]");
            NumberFormatException var2 = new NumberFormatException(var4.toString());
            throw var2;
         }
      } catch (NoSuchElementException var3) {
         return false;
      }
   }

   private static Calendar getCalendar(String var0) throws NumberFormatException {
      boolean var1;
      if (var0.indexOf(58) != -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      boolean var2;
      if (var0.indexOf(45) != -1 || var0.length() == 4 && !var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      String var3 = var0;
      if (var1) {
         var3 = var0;
         if (!var2) {
            var3 = var0;
            if (!var0.toUpperCase().startsWith("T")) {
               StringBuilder var9 = new StringBuilder("T");
               var9.append(var0);
               var3 = var9.toString();
            }
         }
      }

      StringTokenizer var4 = new StringTokenizer(var3, "-T:.+Z", true);
      GregorianCalendar var10 = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
      var10.clear();
      if (var2) {
         if (!var4.hasMoreTokens()) {
            return var10;
         }

         var10.set(1, Integer.parseInt(var4.nextToken()));
         if (!check(var4, "-") || !var4.hasMoreTokens()) {
            return var10;
         }

         var10.set(2, Integer.parseInt(var4.nextToken()) - 1);
         if (!check(var4, "-") || !var4.hasMoreTokens()) {
            return var10;
         }

         var10.set(5, Integer.parseInt(var4.nextToken()));
      }

      if (check(var4, "T") && var4.hasMoreTokens()) {
         var10.set(11, Integer.parseInt(var4.nextToken()));
         if (check(var4, ":") && var4.hasMoreTokens()) {
            var10.set(12, Integer.parseInt(var4.nextToken()));
            if (!var4.hasMoreTokens()) {
               return var10;
            } else {
               var0 = var4.nextToken();
               if (var0.equals(":")) {
                  if (!var4.hasMoreTokens()) {
                     throw new NumberFormatException("No secondes specified");
                  }

                  var10.set(13, Integer.parseInt(var4.nextToken()));
                  if (!var4.hasMoreTokens()) {
                     return var10;
                  }

                  var0 = var4.nextToken();
                  if (var0.equals(".")) {
                     StringBuilder var6;
                     for(var0 = var4.nextToken(); var0.length() < 3; var0 = var6.toString()) {
                        var6 = new StringBuilder(String.valueOf(var0));
                        var6.append("0");
                     }

                     var10.set(14, Integer.parseInt(var0.substring(0, 3)));
                     if (!var4.hasMoreTokens()) {
                        return var10;
                     }

                     var0 = var4.nextToken();
                  } else {
                     var10.set(14, 0);
                  }
               } else {
                  var10.set(13, 0);
                  var10.set(14, 0);
               }

               if (!var0.equals("Z")) {
                  if (!var0.equals("+") && !var0.equals("-")) {
                     throw new NumberFormatException("only Z, + or - allowed");
                  }

                  boolean var5 = var0.equals("+");
                  if (!var4.hasMoreTokens()) {
                     throw new NumberFormatException("Missing hour field");
                  }

                  int var8 = Integer.parseInt(var4.nextToken());
                  if (!check(var4, ":") || !var4.hasMoreTokens()) {
                     throw new NumberFormatException("Missing minute field");
                  }

                  int var7 = Integer.parseInt(var4.nextToken());
                  if (var5) {
                     var10.add(10, -var8);
                     var10.add(12, -var7);
                  } else {
                     var10.add(10, var8);
                     var10.add(12, var7);
                  }
               }

               return var10;
            }
         } else {
            var10.set(12, 0);
            var10.set(13, 0);
            var10.set(14, 0);
            return var10;
         }
      } else {
         var10.set(11, 0);
         var10.set(12, 0);
         var10.set(13, 0);
         var10.set(14, 0);
         return var10;
      }
   }

   public static String getIsoDate(Date var0) {
      GregorianCalendar var1 = new GregorianCalendar();
      var1.setTime(var0);
      StringBuffer var2 = new StringBuffer();
      var2.append(var1.get(1));
      var2.append("-");
      var2.append(twoDigit(var1.get(2) + 1));
      var2.append("-");
      var2.append(twoDigit(var1.get(5)));
      return var2.toString();
   }

   public static String getIsoDateTime(Date var0) {
      GregorianCalendar var1 = new GregorianCalendar();
      var1.setTime(var0);
      StringBuffer var2 = new StringBuffer();
      var2.append(var1.get(1));
      var2.append("-");
      var2.append(twoDigit(var1.get(2) + 1));
      var2.append("-");
      var2.append(twoDigit(var1.get(5)));
      var2.append("T");
      var2.append(twoDigit(var1.get(11)));
      var2.append(":");
      var2.append(twoDigit(var1.get(12)));
      var2.append(":");
      var2.append(twoDigit(var1.get(13)));
      var2.append(".");
      var2.append(twoDigit(var1.get(14) / 10));
      return var2.toString();
   }

   public static String getIsoDateTimeZone(Date var0) {
      GregorianCalendar var1 = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
      var1.setTime(var0);
      StringBuffer var2 = new StringBuffer();
      var2.append(var1.get(1));
      var2.append("-");
      var2.append(twoDigit(var1.get(2) + 1));
      var2.append("-");
      var2.append(twoDigit(var1.get(5)));
      var2.append("T");
      var2.append(twoDigit(var1.get(11)));
      var2.append(":");
      var2.append(twoDigit(var1.get(12)));
      var2.append(":");
      var2.append(twoDigit(var1.get(13)));
      var2.append(".");
      var2.append(twoDigit(var1.get(14) / 10));
      var2.append("Z");
      return var2.toString();
   }

   public static String getIsoTime(Date var0) {
      GregorianCalendar var1 = new GregorianCalendar();
      var1.setTime(var0);
      StringBuffer var2 = new StringBuffer();
      var2.append(twoDigit(var1.get(11)));
      var2.append(":");
      var2.append(twoDigit(var1.get(12)));
      var2.append(":");
      var2.append(twoDigit(var1.get(13)));
      var2.append(".");
      var2.append(twoDigit(var1.get(14) / 10));
      return var2.toString();
   }

   public static String getIsoTimeZone(Date var0) {
      GregorianCalendar var1 = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
      var1.setTime(var0);
      StringBuffer var2 = new StringBuffer();
      var2.append(twoDigit(var1.get(11)));
      var2.append(":");
      var2.append(twoDigit(var1.get(12)));
      var2.append(":");
      var2.append(twoDigit(var1.get(13)));
      var2.append(".");
      var2.append(twoDigit(var1.get(14) / 10));
      var2.append("Z");
      return var2.toString();
   }

   public static Date parse(String var0) throws NumberFormatException {
      return getCalendar(var0).getTime();
   }

   private static String twoDigit(int var0) {
      if (var0 >= 0 && var0 < 10) {
         StringBuilder var1 = new StringBuilder("0");
         var1.append(String.valueOf(var0));
         return var1.toString();
      } else {
         return String.valueOf(var0);
      }
   }
}
