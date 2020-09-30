package net.sbbi.upnp;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpResponse {
   private String body;
   private Map fields;
   private String header;

   protected HttpResponse(String var1) throws IllegalArgumentException {
      if (var1 != null && var1.trim().length() != 0) {
         StringBuffer var2 = new StringBuffer();
         this.fields = new HashMap();
         String[] var3 = var1.split("\\r\\n");
         this.header = var3[0].trim();
         int var4 = 1;

         boolean var5;
         boolean var6;
         for(var5 = false; var4 < var3.length; var5 = var6) {
            var1 = var3[var4];
            if (var1.length() == 0) {
               var6 = true;
            } else if (var5) {
               var2.append(var1);
               var2.append("\r\n");
               var6 = var5;
            } else {
               var6 = var5;
               if (var1.length() > 0) {
                  int var9 = var1.indexOf(58);
                  if (var9 == -1) {
                     StringBuilder var8 = new StringBuilder("Invalid HTTP message header :");
                     var8.append(var1);
                     throw new IllegalArgumentException(var8.toString());
                  }

                  String var7 = var1.substring(0, var9).toUpperCase();
                  var1 = var1.substring(var9 + 1).trim();
                  this.fields.put(var7, var1);
                  var6 = var5;
               }
            }

            ++var4;
         }

         if (var5) {
            this.body = var2.toString();
         }

      } else {
         throw new IllegalArgumentException("Empty HTTP response message");
      }
   }

   public String getBody() {
      return this.body;
   }

   public String getHTTPFieldElement(String var1, String var2) throws IllegalArgumentException {
      String var3 = this.getHTTPHeaderField(var1);
      if (var1 != null) {
         StringTokenizer var6 = new StringTokenizer(var3.trim(), ",");

         while(var6.countTokens() > 0) {
            var1 = var6.nextToken().trim();
            if (var1.startsWith(var2)) {
               int var4 = var1.indexOf("=");
               if (var4 != -1) {
                  return var1.substring(var4 + 1).trim();
               }
            }
         }
      }

      StringBuilder var5 = new StringBuilder("HTTP element field ");
      var5.append(var2);
      var5.append(" is not present");
      throw new IllegalArgumentException(var5.toString());
   }

   public String getHTTPHeaderField(String var1) throws IllegalArgumentException {
      String var2 = (String)this.fields.get(var1.toUpperCase());
      if (var2 != null) {
         return var2;
      } else {
         StringBuilder var3 = new StringBuilder("HTTP field ");
         var3.append(var1);
         var3.append(" is not present");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public String getHeader() {
      return this.header;
   }
}
