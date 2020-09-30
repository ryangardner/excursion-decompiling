package org.apache.http.impl.cookie;

import java.util.ArrayList;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.ParserCursor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

public class NetscapeDraftHeaderParser {
   public static final NetscapeDraftHeaderParser DEFAULT = new NetscapeDraftHeaderParser();

   private NameValuePair parseNameValuePair(CharArrayBuffer var1, ParserCursor var2) {
      int var3 = var2.getPos();
      int var4 = var2.getPos();
      int var5 = var2.getUpperBound();

      boolean var6;
      boolean var11;
      while(true) {
         var6 = true;
         if (var3 < var5) {
            char var7 = var1.charAt(var3);
            if (var7 != '=') {
               if (var7 == ';') {
                  var11 = true;
                  break;
               }

               ++var3;
               continue;
            }
         }

         var11 = false;
         break;
      }

      String var8;
      if (var3 == var5) {
         var8 = var1.substringTrimmed(var4, var5);
         var11 = true;
      } else {
         var8 = var1.substringTrimmed(var4, var3);
         ++var3;
      }

      if (var11) {
         var2.updatePos(var3);
         return new BasicNameValuePair(var8, (String)null);
      } else {
         for(var4 = var3; var4 < var5; ++var4) {
            if (var1.charAt(var4) == ';') {
               var11 = var6;
               break;
            }
         }

         while(var3 < var4 && HTTP.isWhitespace(var1.charAt(var3))) {
            ++var3;
         }

         int var10;
         for(var10 = var4; var10 > var3 && HTTP.isWhitespace(var1.charAt(var10 - 1)); --var10) {
         }

         String var9 = var1.substring(var3, var10);
         var3 = var4;
         if (var11) {
            var3 = var4 + 1;
         }

         var2.updatePos(var3);
         return new BasicNameValuePair(var8, var9);
      }
   }

   public HeaderElement parseHeader(CharArrayBuffer var1, ParserCursor var2) throws ParseException {
      if (var1 == null) {
         throw new IllegalArgumentException("Char array buffer may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Parser cursor may not be null");
      } else {
         NameValuePair var3 = this.parseNameValuePair(var1, var2);
         ArrayList var4 = new ArrayList();

         while(!var2.atEnd()) {
            var4.add(this.parseNameValuePair(var1, var2));
         }

         return new BasicHeaderElement(var3.getName(), var3.getValue(), (NameValuePair[])var4.toArray(new NameValuePair[var4.size()]));
      }
   }
}
