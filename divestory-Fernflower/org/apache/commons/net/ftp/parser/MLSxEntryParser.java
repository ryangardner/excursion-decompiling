package org.apache.commons.net.ftp.parser;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParserImpl;

public class MLSxEntryParser extends FTPFileEntryParserImpl {
   private static final MLSxEntryParser PARSER = new MLSxEntryParser();
   private static final HashMap<String, Integer> TYPE_TO_INT;
   private static int[] UNIX_GROUPS;
   private static int[][] UNIX_PERMS;

   static {
      HashMap var0 = new HashMap();
      TYPE_TO_INT = var0;
      var0.put("file", 0);
      HashMap var1 = TYPE_TO_INT;
      Integer var3 = 1;
      var1.put("cdir", var3);
      TYPE_TO_INT.put("pdir", var3);
      TYPE_TO_INT.put("dir", var3);
      UNIX_GROUPS = new int[]{0, 1, 2};
      int[] var4 = new int[]{2};
      int[] var5 = new int[]{0, 2};
      int[] var2 = new int[]{0, 1};
      UNIX_PERMS = new int[][]{new int[0], var4, {1}, {2, 1}, {0}, var5, var2, {0, 1, 2}};
   }

   private void doUnixPerms(FTPFile var1, String var2) {
      char[] var6 = var2.toCharArray();
      int var3 = var6.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var6[var4];
         if (var5 != 'a') {
            if (var5 != 'p') {
               if (var5 != 'r') {
                  if (var5 != 'w') {
                     if (var5 != 'l') {
                        if (var5 != 'm') {
                           switch(var5) {
                           case 'c':
                              var1.setPermission(0, 1, true);
                              break;
                           case 'd':
                              var1.setPermission(0, 1, true);
                              break;
                           case 'e':
                              var1.setPermission(0, 0, true);
                           }
                        } else {
                           var1.setPermission(0, 1, true);
                        }
                     } else {
                        var1.setPermission(0, 2, true);
                     }
                  } else {
                     var1.setPermission(0, 1, true);
                  }
               } else {
                  var1.setPermission(0, 0, true);
               }
            } else {
               var1.setPermission(0, 1, true);
            }
         } else {
            var1.setPermission(0, 1, true);
         }
      }

   }

   public static MLSxEntryParser getInstance() {
      return PARSER;
   }

   public static FTPFile parseEntry(String var0) {
      return PARSER.parseFTPEntry(var0);
   }

   public static Calendar parseGMTdateTime(String var0) {
      SimpleDateFormat var1;
      boolean var2;
      if (var0.contains(".")) {
         var1 = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
         var2 = true;
      } else {
         var1 = new SimpleDateFormat("yyyyMMddHHmmss");
         var2 = false;
      }

      TimeZone var3 = TimeZone.getTimeZone("GMT");
      var1.setTimeZone(var3);
      GregorianCalendar var4 = new GregorianCalendar(var3);
      ParsePosition var6 = new ParsePosition(0);
      var1.setLenient(false);
      Date var5 = var1.parse(var0, var6);
      if (var6.getIndex() != var0.length()) {
         return null;
      } else {
         var4.setTime(var5);
         if (!var2) {
            var4.clear(14);
         }

         return var4;
      }
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2;
      if (var1.startsWith(" ")) {
         if (var1.length() > 1) {
            var2 = new FTPFile();
            var2.setRawListing(var1);
            var2.setName(var1.substring(1));
            return var2;
         } else {
            return null;
         }
      } else {
         String[] var3 = var1.split(" ", 2);
         if (var3.length == 2 && var3[1].length() != 0) {
            String var4 = var3[0];
            if (!var4.endsWith(";")) {
               return null;
            } else {
               var2 = new FTPFile();
               var2.setRawListing(var1);
               var2.setName(var3[1]);
               String[] var14 = var4.split(";");
               boolean var5 = var3[0].toLowerCase(Locale.ENGLISH).contains("unix.mode=");
               int var6 = var14.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  var3 = var14[var7].split("=", -1);
                  if (var3.length != 2) {
                     return null;
                  }

                  var4 = var3[0].toLowerCase(Locale.ENGLISH);
                  String var15 = var3[1];
                  if (var15.length() != 0) {
                     String var8 = var15.toLowerCase(Locale.ENGLISH);
                     if ("size".equals(var4)) {
                        var2.setSize(Long.parseLong(var15));
                     } else if ("sizd".equals(var4)) {
                        var2.setSize(Long.parseLong(var15));
                     } else if ("modify".equals(var4)) {
                        Calendar var17 = parseGMTdateTime(var15);
                        if (var17 == null) {
                           return null;
                        }

                        var2.setTimestamp(var17);
                     } else if ("type".equals(var4)) {
                        Integer var16 = (Integer)TYPE_TO_INT.get(var8);
                        if (var16 == null) {
                           var2.setType(3);
                        } else {
                           var2.setType(var16);
                        }
                     } else if (!var4.startsWith("unix.")) {
                        if (!var5 && "perm".equals(var4)) {
                           this.doUnixPerms(var2, var8);
                        }
                     } else {
                        var4 = var4.substring(5).toLowerCase(Locale.ENGLISH);
                        if ("group".equals(var4)) {
                           var2.setGroup(var15);
                        } else if ("owner".equals(var4)) {
                           var2.setUser(var15);
                        } else if ("mode".equals(var4)) {
                           int var9 = var15.length();

                           for(int var10 = 0; var10 < 3; ++var10) {
                              int var11 = var15.charAt(var9 - 3 + var10) - 48;
                              if (var11 >= 0 && var11 <= 7) {
                                 int[] var18 = UNIX_PERMS[var11];
                                 int var12 = var18.length;

                                 for(var11 = 0; var11 < var12; ++var11) {
                                    int var13 = var18[var11];
                                    var2.setPermission(UNIX_GROUPS[var10], var13, true);
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               return var2;
            }
         } else {
            return null;
         }
      }
   }
}
