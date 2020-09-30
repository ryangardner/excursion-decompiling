package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VCardResultParser extends ResultParser {
   private static final Pattern BEGIN_VCARD = Pattern.compile("BEGIN:VCARD", 2);
   private static final Pattern COMMA = Pattern.compile(",");
   private static final Pattern CR_LF_SPACE_TAB = Pattern.compile("\r\n[ \t]");
   private static final Pattern EQUALS = Pattern.compile("=");
   private static final Pattern NEWLINE_ESCAPE = Pattern.compile("\\\\[nN]");
   private static final Pattern SEMICOLON = Pattern.compile(";");
   private static final Pattern SEMICOLON_OR_COMMA = Pattern.compile("[;,]");
   private static final Pattern UNESCAPED_SEMICOLONS = Pattern.compile("(?<!\\\\);+");
   private static final Pattern VCARD_ESCAPES = Pattern.compile("\\\\([,;\\\\])");
   private static final Pattern VCARD_LIKE_DATE = Pattern.compile("\\d{4}-?\\d{2}-?\\d{2}");

   private static String decodeQuotedPrintable(CharSequence var0, String var1) {
      int var2 = var0.length();
      StringBuilder var3 = new StringBuilder(var2);
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();

      int var7;
      for(int var5 = 0; var5 < var2; var5 = var7 + 1) {
         char var6 = var0.charAt(var5);
         var7 = var5;
         if (var6 != '\n') {
            var7 = var5;
            if (var6 != '\r') {
               if (var6 != '=') {
                  maybeAppendFragment(var4, var1, var3);
                  var3.append(var6);
                  var7 = var5;
               } else {
                  var7 = var5;
                  if (var5 < var2 - 2) {
                     char var8 = var0.charAt(var5 + 1);
                     var7 = var5;
                     if (var8 != '\r') {
                        var7 = var5;
                        if (var8 != '\n') {
                           var5 += 2;
                           var6 = var0.charAt(var5);
                           int var9 = parseHexDigit(var8);
                           int var10 = parseHexDigit(var6);
                           var7 = var5;
                           if (var9 >= 0) {
                              var7 = var5;
                              if (var10 >= 0) {
                                 var4.write((var9 << 4) + var10);
                                 var7 = var5;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      maybeAppendFragment(var4, var1, var3);
      return var3.toString();
   }

   private static void formatNames(Iterable<List<String>> var0) {
      if (var0 != null) {
         Iterator var7 = var0.iterator();

         while(var7.hasNext()) {
            List var1 = (List)var7.next();
            String var2 = (String)var1.get(0);
            String[] var3 = new String[5];
            int var4 = 0;

            int var5;
            int var6;
            for(var5 = 0; var4 < 4; var5 = var6 + 1) {
               var6 = var2.indexOf(59, var5);
               if (var6 < 0) {
                  break;
               }

               var3[var4] = var2.substring(var5, var6);
               ++var4;
            }

            var3[var4] = var2.substring(var5);
            StringBuilder var8 = new StringBuilder(100);
            maybeAppendComponent(var3, 3, var8);
            maybeAppendComponent(var3, 1, var8);
            maybeAppendComponent(var3, 2, var8);
            maybeAppendComponent(var3, 0, var8);
            maybeAppendComponent(var3, 4, var8);
            var1.set(0, var8.toString().trim());
         }
      }

   }

   private static boolean isLikeVCardDate(CharSequence var0) {
      boolean var1;
      if (var0 != null && !VCARD_LIKE_DATE.matcher(var0).matches()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   static List<String> matchSingleVCardPrefixedField(CharSequence var0, String var1, boolean var2, boolean var3) {
      List var4 = matchVCardPrefixedField(var0, var1, var2, var3);
      if (var4 != null && !var4.isEmpty()) {
         var4 = (List)var4.get(0);
      } else {
         var4 = null;
      }

      return var4;
   }

   static List<List<String>> matchVCardPrefixedField(CharSequence var0, String var1, boolean var2, boolean var3) {
      int var4 = var1.length();
      int var5 = 0;
      ArrayList var6 = null;

      while(var5 < var4) {
         StringBuilder var7 = new StringBuilder();
         var7.append("(?:^|\n)");
         var7.append(var0);
         var7.append("(?:;([^:]*))?:");
         Matcher var19 = Pattern.compile(var7.toString(), 2).matcher(var1);
         int var8 = var5;
         if (var5 > 0) {
            var8 = var5 - 1;
         }

         if (!var19.find(var8)) {
            break;
         }

         int var9 = var19.end(0);
         String var20 = var19.group(1);
         int var12;
         ArrayList var13;
         ArrayList var14;
         String var15;
         boolean var22;
         String var24;
         if (var20 != null) {
            String[] var10 = SEMICOLON.split(var20);
            int var11 = var10.length;
            var12 = 0;
            var13 = null;
            boolean var17 = false;
            var20 = null;

            while(true) {
               var14 = var13;
               var22 = var17;
               var15 = var20;
               if (var12 >= var11) {
                  break;
               }

               var15 = var10[var12];
               var14 = var13;
               if (var13 == null) {
                  var14 = new ArrayList(1);
               }

               var14.add(var15);
               String[] var23 = EQUALS.split(var15, 2);
               var22 = var17;
               var15 = var20;
               if (var23.length > 1) {
                  String var16 = var23[0];
                  var24 = var23[1];
                  if ("ENCODING".equalsIgnoreCase(var16) && "QUOTED-PRINTABLE".equalsIgnoreCase(var24)) {
                     var22 = true;
                     var15 = var20;
                  } else {
                     var22 = var17;
                     var15 = var20;
                     if ("CHARSET".equalsIgnoreCase(var16)) {
                        var15 = var24;
                        var22 = var17;
                     }
                  }
               }

               ++var12;
               var13 = var14;
               var17 = var22;
               var20 = var15;
            }
         } else {
            var14 = null;
            var22 = false;
            var15 = null;
         }

         var5 = var9;

         while(true) {
            var12 = var1.indexOf(10, var5);
            if (var12 < 0) {
               break;
            }

            if (var12 < var1.length() - 1) {
               var5 = var12 + 1;
               if (var1.charAt(var5) == ' ' || var1.charAt(var5) == '\t') {
                  var5 = var12 + 2;
                  continue;
               }
            }

            if (!var22 || (var12 < 1 || var1.charAt(var12 - 1) != '=') && (var12 < 2 || var1.charAt(var12 - 2) != '=')) {
               break;
            }

            var5 = var12 + 1;
         }

         if (var12 < 0) {
            var5 = var4;
         } else {
            var5 = var12;
            ArrayList var21 = var6;
            if (var12 > var9) {
               var21 = var6;
               if (var6 == null) {
                  var21 = new ArrayList(1);
               }

               var5 = var12;
               if (var12 >= 1) {
                  var5 = var12;
                  if (var1.charAt(var12 - 1) == '\r') {
                     var5 = var12 - 1;
                  }
               }

               var24 = var1.substring(var9, var5);
               String var18 = var24;
               if (var2) {
                  var18 = var24.trim();
               }

               if (var22) {
                  var24 = decodeQuotedPrintable(var18, var15);
                  var18 = var24;
                  if (var3) {
                     var18 = UNESCAPED_SEMICOLONS.matcher(var24).replaceAll("\n").trim();
                  }
               } else {
                  var24 = var18;
                  if (var3) {
                     var24 = UNESCAPED_SEMICOLONS.matcher(var18).replaceAll("\n").trim();
                  }

                  var18 = CR_LF_SPACE_TAB.matcher(var24).replaceAll("");
                  var18 = NEWLINE_ESCAPE.matcher(var18).replaceAll("\n");
                  var18 = VCARD_ESCAPES.matcher(var18).replaceAll("$1");
               }

               if (var14 == null) {
                  var13 = new ArrayList(1);
                  var13.add(var18);
                  var21.add(var13);
               } else {
                  var14.add(0, var18);
                  var21.add(var14);
               }
            }

            ++var5;
            var6 = var21;
         }
      }

      return var6;
   }

   private static void maybeAppendComponent(String[] var0, int var1, StringBuilder var2) {
      if (var0[var1] != null && !var0[var1].isEmpty()) {
         if (var2.length() > 0) {
            var2.append(' ');
         }

         var2.append(var0[var1]);
      }

   }

   private static void maybeAppendFragment(ByteArrayOutputStream var0, String var1, StringBuilder var2) {
      if (var0.size() > 0) {
         byte[] var3 = var0.toByteArray();
         if (var1 == null) {
            var1 = new String(var3, Charset.forName("UTF-8"));
         } else {
            label23: {
               String var4;
               try {
                  var4 = new String(var3, var1);
               } catch (UnsupportedEncodingException var5) {
                  var1 = new String(var3, Charset.forName("UTF-8"));
                  break label23;
               }

               var1 = var4;
            }
         }

         var0.reset();
         var2.append(var1);
      }

   }

   private static String toPrimaryValue(List<String> var0) {
      String var1;
      if (var0 != null && !var0.isEmpty()) {
         var1 = (String)var0.get(0);
      } else {
         var1 = null;
      }

      return var1;
   }

   private static String[] toPrimaryValues(Collection<List<String>> var0) {
      if (var0 != null && !var0.isEmpty()) {
         ArrayList var1 = new ArrayList(var0.size());
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            String var3 = (String)((List)var2.next()).get(0);
            if (var3 != null && !var3.isEmpty()) {
               var1.add(var3);
            }
         }

         return (String[])var1.toArray(new String[var0.size()]);
      } else {
         return null;
      }
   }

   private static String[] toTypes(Collection<List<String>> var0) {
      if (var0 != null && !var0.isEmpty()) {
         ArrayList var1 = new ArrayList(var0.size());

         String var5;
         label32:
         for(Iterator var2 = var0.iterator(); var2.hasNext(); var1.add(var5)) {
            List var3 = (List)var2.next();

            for(int var4 = 1; var4 < var3.size(); ++var4) {
               var5 = (String)var3.get(var4);
               int var6 = var5.indexOf(61);
               if (var6 < 0) {
                  continue label32;
               }

               if ("TYPE".equalsIgnoreCase(var5.substring(0, var6))) {
                  var5 = var5.substring(var6 + 1);
                  continue label32;
               }
            }

            var5 = null;
         }

         return (String[])var1.toArray(new String[var0.size()]);
      } else {
         return null;
      }
   }

   public AddressBookParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      Matcher var14 = BEGIN_VCARD.matcher(var2);
      if (var14.find() && var14.start() == 0) {
         List var15 = matchVCardPrefixedField("FN", var2, true, false);
         List var3 = var15;
         if (var15 == null) {
            var3 = matchVCardPrefixedField("N", var2, true, false);
            formatNames(var3);
         }

         var15 = matchSingleVCardPrefixedField("NICKNAME", var2, true, false);
         String[] var4;
         if (var15 == null) {
            var4 = null;
         } else {
            var4 = COMMA.split((CharSequence)var15.get(0));
         }

         List var5 = matchVCardPrefixedField("TEL", var2, true, false);
         List var6 = matchVCardPrefixedField("EMAIL", var2, true, false);
         List var7 = matchSingleVCardPrefixedField("NOTE", var2, false, false);
         List var8 = matchVCardPrefixedField("ADR", var2, true, true);
         List var9 = matchSingleVCardPrefixedField("ORG", var2, true, true);
         List var10 = matchSingleVCardPrefixedField("BDAY", var2, true, false);
         if (var10 != null && !isLikeVCardDate((CharSequence)var10.get(0))) {
            var10 = null;
         }

         List var11 = matchSingleVCardPrefixedField("TITLE", var2, true, false);
         List var12 = matchVCardPrefixedField("URL", var2, true, false);
         List var13 = matchSingleVCardPrefixedField("IMPP", var2, true, false);
         var15 = matchSingleVCardPrefixedField("GEO", var2, true, false);
         String[] var16;
         if (var15 == null) {
            var16 = null;
         } else {
            var16 = SEMICOLON_OR_COMMA.split((CharSequence)var15.get(0));
         }

         if (var16 != null && var16.length != 2) {
            var16 = null;
         }

         return new AddressBookParsedResult(toPrimaryValues(var3), var4, (String)null, toPrimaryValues(var5), toTypes(var5), toPrimaryValues(var6), toTypes(var6), toPrimaryValue(var13), toPrimaryValue(var7), toPrimaryValues(var8), toTypes(var8), toPrimaryValue(var9), toPrimaryValue(var10), toPrimaryValue(var11), toPrimaryValues(var12), var16);
      } else {
         return null;
      }
   }
}
