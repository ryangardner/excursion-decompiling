package org.apache.http.message;

import java.util.ArrayList;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeaderValueParser implements HeaderValueParser {
   private static final char[] ALL_DELIMITERS = new char[]{';', ','};
   public static final BasicHeaderValueParser DEFAULT = new BasicHeaderValueParser();
   private static final char ELEM_DELIMITER = ',';
   private static final char PARAM_DELIMITER = ';';

   private static boolean isOneOf(char var0, char[] var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            if (var0 == var1[var2]) {
               return true;
            }
         }
      }

      return false;
   }

   public static final HeaderElement[] parseElements(String var0, HeaderValueParser var1) throws ParseException {
      if (var0 != null) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT;
         }

         CharArrayBuffer var3 = new CharArrayBuffer(var0.length());
         var3.append(var0);
         return ((HeaderValueParser)var2).parseElements(var3, new ParserCursor(0, var0.length()));
      } else {
         throw new IllegalArgumentException("Value to parse may not be null");
      }
   }

   public static final HeaderElement parseHeaderElement(String var0, HeaderValueParser var1) throws ParseException {
      if (var0 != null) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT;
         }

         CharArrayBuffer var3 = new CharArrayBuffer(var0.length());
         var3.append(var0);
         return ((HeaderValueParser)var2).parseHeaderElement(var3, new ParserCursor(0, var0.length()));
      } else {
         throw new IllegalArgumentException("Value to parse may not be null");
      }
   }

   public static final NameValuePair parseNameValuePair(String var0, HeaderValueParser var1) throws ParseException {
      if (var0 != null) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT;
         }

         CharArrayBuffer var3 = new CharArrayBuffer(var0.length());
         var3.append(var0);
         return ((HeaderValueParser)var2).parseNameValuePair(var3, new ParserCursor(0, var0.length()));
      } else {
         throw new IllegalArgumentException("Value to parse may not be null");
      }
   }

   public static final NameValuePair[] parseParameters(String var0, HeaderValueParser var1) throws ParseException {
      if (var0 != null) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT;
         }

         CharArrayBuffer var3 = new CharArrayBuffer(var0.length());
         var3.append(var0);
         return ((HeaderValueParser)var2).parseParameters(var3, new ParserCursor(0, var0.length()));
      } else {
         throw new IllegalArgumentException("Value to parse may not be null");
      }
   }

   protected HeaderElement createHeaderElement(String var1, String var2, NameValuePair[] var3) {
      return new BasicHeaderElement(var1, var2, var3);
   }

   protected NameValuePair createNameValuePair(String var1, String var2) {
      return new BasicNameValuePair(var1, var2);
   }

   public HeaderElement[] parseElements(CharArrayBuffer var1, ParserCursor var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Char array buffer may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Parser cursor may not be null");
      } else {
         ArrayList var3 = new ArrayList();

         while(true) {
            HeaderElement var4;
            do {
               if (var2.atEnd()) {
                  return (HeaderElement[])var3.toArray(new HeaderElement[var3.size()]);
               }

               var4 = this.parseHeaderElement(var1, var2);
            } while(var4.getName().length() == 0 && var4.getValue() == null);

            var3.add(var4);
         }
      }
   }

   public HeaderElement parseHeaderElement(CharArrayBuffer var1, ParserCursor var2) {
      if (var1 != null) {
         if (var2 != null) {
            NameValuePair var3 = this.parseNameValuePair(var1, var2);
            Object var4 = null;
            NameValuePair[] var5 = (NameValuePair[])var4;
            if (!var2.atEnd()) {
               var5 = (NameValuePair[])var4;
               if (var1.charAt(var2.getPos() - 1) != ',') {
                  var5 = this.parseParameters(var1, var2);
               }
            }

            return this.createHeaderElement(var3.getName(), var3.getValue(), var5);
         } else {
            throw new IllegalArgumentException("Parser cursor may not be null");
         }
      } else {
         throw new IllegalArgumentException("Char array buffer may not be null");
      }
   }

   public NameValuePair parseNameValuePair(CharArrayBuffer var1, ParserCursor var2) {
      return this.parseNameValuePair(var1, var2, ALL_DELIMITERS);
   }

   public NameValuePair parseNameValuePair(CharArrayBuffer var1, ParserCursor var2, char[] var3) {
      if (var1 == null) {
         throw new IllegalArgumentException("Char array buffer may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Parser cursor may not be null");
      } else {
         int var4 = var2.getPos();
         int var5 = var2.getPos();
         int var6 = var2.getUpperBound();

         boolean var7;
         char var8;
         boolean var9;
         while(true) {
            var7 = true;
            if (var4 < var6) {
               var8 = var1.charAt(var4);
               if (var8 != '=') {
                  if (isOneOf(var8, var3)) {
                     var9 = true;
                     break;
                  }

                  ++var4;
                  continue;
               }
            }

            var9 = false;
            break;
         }

         String var10;
         if (var4 == var6) {
            var10 = var1.substringTrimmed(var5, var6);
            var9 = true;
         } else {
            var10 = var1.substringTrimmed(var5, var4);
            ++var4;
         }

         if (var9) {
            var2.updatePos(var4);
            return this.createNameValuePair(var10, (String)null);
         } else {
            var5 = var4;
            boolean var11 = false;
            boolean var12 = false;

            while(true) {
               if (var5 >= var6) {
                  var11 = var9;
                  break;
               }

               var8 = var1.charAt(var5);
               boolean var13 = var12;
               if (var8 == '"') {
                  var13 = var12;
                  if (!var11) {
                     var13 = var12 ^ true;
                  }
               }

               if (!var13 && !var11 && isOneOf(var8, var3)) {
                  var11 = var7;
                  break;
               }

               if (!var11 && var13 && var8 == '\\') {
                  var11 = true;
               } else {
                  var11 = false;
               }

               ++var5;
               var12 = var13;
            }

            while(var4 < var5 && HTTP.isWhitespace(var1.charAt(var4))) {
               ++var4;
            }

            int var15;
            for(var15 = var5; var15 > var4 && HTTP.isWhitespace(var1.charAt(var15 - 1)); --var15) {
            }

            int var16 = var4;
            int var17 = var15;
            if (var15 - var4 >= 2) {
               var16 = var4;
               var17 = var15;
               if (var1.charAt(var4) == '"') {
                  var16 = var4;
                  var17 = var15;
                  if (var1.charAt(var15 - 1) == '"') {
                     var16 = var4 + 1;
                     var17 = var15 - 1;
                  }
               }
            }

            String var14 = var1.substring(var16, var17);
            var4 = var5;
            if (var11) {
               var4 = var5 + 1;
            }

            var2.updatePos(var4);
            return this.createNameValuePair(var10, var14);
         }
      }
   }

   public NameValuePair[] parseParameters(CharArrayBuffer var1, ParserCursor var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Char array buffer may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Parser cursor may not be null");
      } else {
         int var3 = var2.getPos();

         for(int var4 = var2.getUpperBound(); var3 < var4 && HTTP.isWhitespace(var1.charAt(var3)); ++var3) {
         }

         var2.updatePos(var3);
         if (var2.atEnd()) {
            return new NameValuePair[0];
         } else {
            ArrayList var5 = new ArrayList();

            while(!var2.atEnd()) {
               var5.add(this.parseNameValuePair(var1, var2));
               if (var1.charAt(var2.getPos() - 1) == ',') {
                  break;
               }
            }

            return (NameValuePair[])var5.toArray(new NameValuePair[var5.size()]);
         }
      }
   }
}
