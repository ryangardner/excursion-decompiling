package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class ResultParser {
   private static final Pattern AMPERSAND = Pattern.compile("&");
   private static final String BYTE_ORDER_MARK = "\ufeff";
   private static final Pattern DIGITS = Pattern.compile("\\d+");
   private static final Pattern EQUALS = Pattern.compile("=");
   private static final ResultParser[] PARSERS = new ResultParser[]{new BookmarkDoCoMoResultParser(), new AddressBookDoCoMoResultParser(), new EmailDoCoMoResultParser(), new AddressBookAUResultParser(), new VCardResultParser(), new BizcardResultParser(), new VEventResultParser(), new EmailAddressResultParser(), new SMTPResultParser(), new TelResultParser(), new SMSMMSResultParser(), new SMSTOMMSTOResultParser(), new GeoResultParser(), new WifiResultParser(), new URLTOResultParser(), new URIResultParser(), new ISBNResultParser(), new ProductResultParser(), new ExpandedProductResultParser(), new VINResultParser()};

   private static void appendKeyValue(CharSequence var0, Map<String, String> var1) {
      String[] var2 = EQUALS.split(var0, 2);
      if (var2.length == 2) {
         String var4 = var2[0];
         String var5 = var2[1];

         try {
            var1.put(var4, urlDecode(var5));
         } catch (IllegalArgumentException var3) {
         }
      }

   }

   private static int countPrecedingBackslashes(CharSequence var0, int var1) {
      --var1;

      int var2;
      for(var2 = 0; var1 >= 0 && var0.charAt(var1) == '\\'; --var1) {
         ++var2;
      }

      return var2;
   }

   protected static String getMassagedText(Result var0) {
      String var1 = var0.getText();
      String var2 = var1;
      if (var1.startsWith("\ufeff")) {
         var2 = var1.substring(1);
      }

      return var2;
   }

   protected static boolean isStringOfDigits(CharSequence var0, int var1) {
      boolean var2;
      if (var0 != null && var1 > 0 && var1 == var0.length() && DIGITS.matcher(var0).matches()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected static boolean isSubstringOfDigits(CharSequence var0, int var1, int var2) {
      boolean var3 = false;
      boolean var4 = var3;
      if (var0 != null) {
         if (var2 <= 0) {
            var4 = var3;
         } else {
            var2 += var1;
            var4 = var3;
            if (var0.length() >= var2) {
               var4 = var3;
               if (DIGITS.matcher(var0.subSequence(var1, var2)).matches()) {
                  var4 = true;
               }
            }
         }
      }

      return var4;
   }

   static String[] matchPrefixedField(String var0, String var1, char var2, boolean var3) {
      int var4 = var1.length();
      ArrayList var5 = null;
      int var6 = 0;

      label53:
      while(var6 < var4) {
         var6 = var1.indexOf(var0, var6);
         if (var6 < 0) {
            break;
         }

         int var7 = var6 + var0.length();
         boolean var8 = true;
         var6 = var7;

         while(true) {
            while(true) {
               if (!var8) {
                  continue label53;
               }

               var6 = var1.indexOf(var2, var6);
               if (var6 < 0) {
                  var6 = var1.length();
               } else {
                  if (countPrecedingBackslashes(var1, var6) % 2 != 0) {
                     ++var6;
                     continue;
                  }

                  ArrayList var9 = var5;
                  if (var5 == null) {
                     var9 = new ArrayList(3);
                  }

                  String var10 = unescapeBackslash(var1.substring(var7, var6));
                  String var11 = var10;
                  if (var3) {
                     var11 = var10.trim();
                  }

                  if (!var11.isEmpty()) {
                     var9.add(var11);
                  }

                  ++var6;
                  var5 = var9;
               }

               var8 = false;
            }
         }
      }

      return var5 != null && !var5.isEmpty() ? (String[])var5.toArray(new String[var5.size()]) : null;
   }

   static String matchSinglePrefixedField(String var0, String var1, char var2, boolean var3) {
      String[] var4 = matchPrefixedField(var0, var1, var2, var3);
      if (var4 == null) {
         var0 = null;
      } else {
         var0 = var4[0];
      }

      return var0;
   }

   protected static void maybeAppend(String var0, StringBuilder var1) {
      if (var0 != null) {
         var1.append('\n');
         var1.append(var0);
      }

   }

   protected static void maybeAppend(String[] var0, StringBuilder var1) {
      if (var0 != null) {
         int var2 = var0.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = var0[var3];
            var1.append('\n');
            var1.append(var4);
         }
      }

   }

   protected static String[] maybeWrap(String var0) {
      String[] var1;
      if (var0 == null) {
         var1 = null;
      } else {
         var1 = new String[]{var0};
      }

      return var1;
   }

   protected static int parseHexDigit(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else {
         byte var1 = 97;
         if (var0 < 'a' || var0 > 'f') {
            var1 = 65;
            if (var0 < 'A' || var0 > 'F') {
               return -1;
            }
         }

         return var0 - var1 + 10;
      }
   }

   static Map<String, String> parseNameValuePairs(String var0) {
      int var1 = var0.indexOf(63);
      if (var1 < 0) {
         return null;
      } else {
         HashMap var2 = new HashMap(3);
         String[] var4 = AMPERSAND.split(var0.substring(var1 + 1));
         int var3 = var4.length;

         for(var1 = 0; var1 < var3; ++var1) {
            appendKeyValue(var4[var1], var2);
         }

         return var2;
      }
   }

   public static ParsedResult parseResult(Result var0) {
      ResultParser[] var1 = PARSERS;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ParsedResult var4 = var1[var3].parse(var0);
         if (var4 != null) {
            return var4;
         }
      }

      return new TextParsedResult(var0.getText(), (String)null);
   }

   protected static String unescapeBackslash(String var0) {
      int var1 = var0.indexOf(92);
      if (var1 < 0) {
         return var0;
      } else {
         int var2 = var0.length();
         StringBuilder var3 = new StringBuilder(var2 - 1);
         var3.append(var0.toCharArray(), 0, var1);

         for(boolean var4 = false; var1 < var2; ++var1) {
            char var5 = var0.charAt(var1);
            if (!var4 && var5 == '\\') {
               var4 = true;
            } else {
               var3.append(var5);
               var4 = false;
            }
         }

         return var3.toString();
      }
   }

   static String urlDecode(String var0) {
      try {
         var0 = URLDecoder.decode(var0, "UTF-8");
         return var0;
      } catch (UnsupportedEncodingException var1) {
         throw new IllegalStateException(var1);
      }
   }

   public abstract ParsedResult parse(Result var1);
}
