package org.apache.http.client.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class URLEncodedUtils {
   public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
   private static final String NAME_VALUE_SEPARATOR = "=";
   private static final String PARAMETER_SEPARATOR = "&";

   private static String decode(String var0, String var1) {
      if (var1 == null) {
         var1 = "ISO-8859-1";
      }

      try {
         var0 = URLDecoder.decode(var0, var1);
         return var0;
      } catch (UnsupportedEncodingException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   private static String encode(String var0, String var1) {
      if (var1 == null) {
         var1 = "ISO-8859-1";
      }

      try {
         var0 = URLEncoder.encode(var0, var1);
         return var0;
      } catch (UnsupportedEncodingException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   public static String format(List<? extends NameValuePair> var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         NameValuePair var5 = (NameValuePair)var3.next();
         String var4 = encode(var5.getName(), var1);
         String var6 = var5.getValue();
         if (var6 != null) {
            var6 = encode(var6, var1);
         } else {
            var6 = "";
         }

         if (var2.length() > 0) {
            var2.append("&");
         }

         var2.append(var4);
         var2.append("=");
         var2.append(var6);
      }

      return var2.toString();
   }

   public static boolean isEncoded(HttpEntity var0) {
      Header var1 = var0.getContentType();
      if (var1 != null) {
         HeaderElement[] var2 = var1.getElements();
         if (var2.length > 0) {
            return var2[0].getName().equalsIgnoreCase("application/x-www-form-urlencoded");
         }
      }

      return false;
   }

   public static List<NameValuePair> parse(URI var0, String var1) {
      List var2 = Collections.emptyList();
      String var3 = var0.getRawQuery();
      Object var4 = var2;
      if (var3 != null) {
         var4 = var2;
         if (var3.length() > 0) {
            var4 = new ArrayList();
            parse((List)var4, new Scanner(var3), var1);
         }
      }

      return (List)var4;
   }

   public static List<NameValuePair> parse(HttpEntity var0) throws IOException {
      List var1;
      String var3;
      String var4;
      label25: {
         var1 = Collections.emptyList();
         Header var2 = var0.getContentType();
         var3 = null;
         var4 = null;
         if (var2 != null) {
            HeaderElement[] var6 = var2.getElements();
            if (var6.length > 0) {
               HeaderElement var7 = var6[0];
               var3 = var7.getName();
               NameValuePair var8 = var7.getParameterByName("charset");
               if (var8 != null) {
                  var4 = var8.getValue();
               }
               break label25;
            }
         }

         var4 = null;
      }

      Object var9 = var1;
      if (var3 != null) {
         var9 = var1;
         if (var3.equalsIgnoreCase("application/x-www-form-urlencoded")) {
            String var5 = EntityUtils.toString(var0, "ASCII");
            var9 = var1;
            if (var5 != null) {
               var9 = var1;
               if (var5.length() > 0) {
                  var9 = new ArrayList();
                  parse((List)var9, new Scanner(var5), var4);
               }
            }
         }
      }

      return (List)var9;
   }

   public static void parse(List<NameValuePair> var0, Scanner var1, String var2) {
      var1.useDelimiter("&");

      String var4;
      String var5;
      for(; var1.hasNext(); var0.add(new BasicNameValuePair(var4, var5))) {
         String[] var3 = var1.next().split("=");
         if (var3.length == 0 || var3.length > 2) {
            throw new IllegalArgumentException("bad parameter");
         }

         var4 = decode(var3[0], var2);
         var5 = null;
         if (var3.length == 2) {
            var5 = decode(var3[1], var2);
         }
      }

   }
}
