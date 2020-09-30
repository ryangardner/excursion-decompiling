package com.google.api.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PemReader {
   private static final Pattern BEGIN_PATTERN = Pattern.compile("-----BEGIN ([A-Z ]+)-----");
   private static final Pattern END_PATTERN = Pattern.compile("-----END ([A-Z ]+)-----");
   private BufferedReader reader;

   public PemReader(Reader var1) {
      this.reader = new BufferedReader(var1);
   }

   public static PemReader.Section readFirstSectionAndClose(Reader var0) throws IOException {
      return readFirstSectionAndClose(var0, (String)null);
   }

   public static PemReader.Section readFirstSectionAndClose(Reader var0, String var1) throws IOException {
      PemReader var4 = new PemReader(var0);

      PemReader.Section var5;
      try {
         var5 = var4.readNextSection(var1);
      } finally {
         var4.close();
      }

      return var5;
   }

   public void close() throws IOException {
      this.reader.close();
   }

   public PemReader.Section readNextSection() throws IOException {
      return this.readNextSection((String)null);
   }

   public PemReader.Section readNextSection(String var1) throws IOException {
      StringBuilder var2 = null;
      Object var3 = var2;

      while(true) {
         String var7;
         do {
            Matcher var6;
            do {
               while(true) {
                  String var4 = this.reader.readLine();
                  if (var4 == null) {
                     boolean var5;
                     if (var3 == null) {
                        var5 = true;
                     } else {
                        var5 = false;
                     }

                     Preconditions.checkArgument(var5, "missing end tag (%s)", var3);
                     return null;
                  }

                  if (var2 == null) {
                     var6 = BEGIN_PATTERN.matcher(var4);
                     break;
                  }

                  var6 = END_PATTERN.matcher(var4);
                  if (var6.matches()) {
                     var1 = var6.group(1);
                     Preconditions.checkArgument(var1.equals(var3), "end tag (%s) doesn't match begin tag (%s)", var1, var3);
                     return new PemReader.Section((String)var3, Base64.decodeBase64(var2.toString()));
                  }

                  var2.append(var4);
               }
            } while(!var6.matches());

            var7 = var6.group(1);
         } while(var1 != null && !var7.equals(var1));

         var2 = new StringBuilder();
         var3 = var7;
      }
   }

   public static final class Section {
      private final byte[] base64decodedBytes;
      private final String title;

      Section(String var1, byte[] var2) {
         this.title = (String)Preconditions.checkNotNull(var1);
         this.base64decodedBytes = (byte[])Preconditions.checkNotNull(var2);
      }

      public byte[] getBase64DecodedBytes() {
         return this.base64decodedBytes;
      }

      public String getTitle() {
         return this.title;
      }
   }
}
