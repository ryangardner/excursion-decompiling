package com.sun.activation.registries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class MimeTypeFile {
   private String fname = null;
   private Hashtable type_hash = new Hashtable();

   public MimeTypeFile() {
   }

   public MimeTypeFile(InputStream var1) throws IOException {
      this.parse(new BufferedReader(new InputStreamReader(var1, "iso-8859-1")));
   }

   public MimeTypeFile(String var1) throws IOException {
      this.fname = var1;
      FileReader var8 = new FileReader(new File(this.fname));

      try {
         BufferedReader var2 = new BufferedReader(var8);
         this.parse(var2);
      } finally {
         try {
            var8.close();
         } catch (IOException var6) {
         }

      }

   }

   private void parse(BufferedReader var1) throws IOException {
      while(true) {
         String var2 = null;

         while(true) {
            String var3 = var1.readLine();
            if (var3 == null) {
               if (var2 != null) {
                  this.parseEntry(var2);
               }

               return;
            }

            if (var2 == null) {
               var2 = var3;
            } else {
               StringBuilder var5 = new StringBuilder(String.valueOf(var2));
               var5.append(var3);
               var2 = var5.toString();
            }

            int var4 = var2.length();
            if (var2.length() > 0) {
               --var4;
               if (var2.charAt(var4) == '\\') {
                  var2 = var2.substring(0, var4);
                  continue;
               }
            }

            this.parseEntry(var2);
            break;
         }
      }
   }

   private void parseEntry(String var1) {
      String var2 = var1.trim();
      if (var2.length() != 0) {
         if (var2.charAt(0) != '#') {
            if (var2.indexOf(61) > 0) {
               LineTokenizer var3 = new LineTokenizer(var2);
               String var4 = null;

               while(true) {
                  while(var3.hasMoreTokens()) {
                     String var5 = var3.nextToken();
                     if (var3.hasMoreTokens() && var3.nextToken().equals("=") && var3.hasMoreTokens()) {
                        var1 = var3.nextToken();
                     } else {
                        var1 = null;
                     }

                     if (var1 == null) {
                        if (LogSupport.isLoggable()) {
                           StringBuilder var9 = new StringBuilder("Bad .mime.types entry: ");
                           var9.append(var2);
                           LogSupport.log(var9.toString());
                        }

                        return;
                     }

                     if (var5.equals("type")) {
                        var4 = var1;
                     } else if (var5.equals("exts")) {
                        StringTokenizer var8 = new StringTokenizer(var1, ",");

                        while(var8.hasMoreTokens()) {
                           String var6 = var8.nextToken();
                           MimeTypeEntry var13 = new MimeTypeEntry(var4, var6);
                           this.type_hash.put(var6, var13);
                           if (LogSupport.isLoggable()) {
                              StringBuilder var14 = new StringBuilder("Added: ");
                              var14.append(var13.toString());
                              LogSupport.log(var14.toString());
                           }
                        }
                     }
                  }

                  return;
               }
            } else {
               StringTokenizer var7 = new StringTokenizer(var2);
               if (var7.countTokens() != 0) {
                  var1 = var7.nextToken();

                  while(var7.hasMoreTokens()) {
                     String var10 = var7.nextToken();
                     MimeTypeEntry var12 = new MimeTypeEntry(var1, var10);
                     this.type_hash.put(var10, var12);
                     if (LogSupport.isLoggable()) {
                        StringBuilder var11 = new StringBuilder("Added: ");
                        var11.append(var12.toString());
                        LogSupport.log(var11.toString());
                     }
                  }

               }
            }
         }
      }
   }

   public void appendToRegistry(String var1) {
      try {
         StringReader var3 = new StringReader(var1);
         BufferedReader var2 = new BufferedReader(var3);
         this.parse(var2);
      } catch (IOException var4) {
      }

   }

   public String getMIMETypeString(String var1) {
      MimeTypeEntry var2 = this.getMimeTypeEntry(var1);
      return var2 != null ? var2.getMIMEType() : null;
   }

   public MimeTypeEntry getMimeTypeEntry(String var1) {
      return (MimeTypeEntry)this.type_hash.get(var1);
   }
}
