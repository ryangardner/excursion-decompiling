package org.apache.http.impl.cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class PublicSuffixListParser {
   private static final int MAX_LINE_LEN = 256;
   private final PublicSuffixFilter filter;

   PublicSuffixListParser(PublicSuffixFilter var1) {
      this.filter = var1;
   }

   private boolean readLine(Reader var1, StringBuilder var2) throws IOException {
      boolean var3 = false;
      var2.setLength(0);
      boolean var4 = false;

      int var5;
      while(true) {
         var5 = var1.read();
         if (var5 == -1) {
            break;
         }

         char var6 = (char)var5;
         if (var6 == '\n') {
            break;
         }

         if (Character.isWhitespace(var6)) {
            var4 = true;
         }

         if (!var4) {
            var2.append(var6);
         }

         if (var2.length() > 256) {
            throw new IOException("Line too long");
         }
      }

      if (var5 != -1) {
         var3 = true;
      }

      return var3;
   }

   public void parse(Reader var1) throws IOException {
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();
      BufferedReader var4 = new BufferedReader(var1);
      StringBuilder var5 = new StringBuilder(256);
      boolean var6 = true;

      while(var6) {
         var6 = this.readLine(var4, var5);
         String var7 = var5.toString();
         if (var7.length() != 0 && !var7.startsWith("//")) {
            String var9 = var7;
            if (var7.startsWith(".")) {
               var9 = var7.substring(1);
            }

            boolean var8 = var9.startsWith("!");
            var7 = var9;
            if (var8) {
               var7 = var9.substring(1);
            }

            if (var8) {
               var3.add(var7);
            } else {
               var2.add(var7);
            }
         }
      }

      this.filter.setPublicSuffixes(var2);
      this.filter.setExceptions(var3);
   }
}
