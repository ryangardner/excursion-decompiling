package org.apache.commons.net.ftp.parser;

import java.io.File;
import java.text.ParseException;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

public class OS400FTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
   private static final String DEFAULT_DATE_FORMAT = "yy/MM/dd HH:mm:ss";
   private static final String REGEX = "(\\S+)\\s+(?:(\\d+)\\s+)?(?:(\\S+)\\s+(\\S+)\\s+)?(\\*STMF|\\*DIR|\\*FILE|\\*MEM)\\s+(?:(\\S+)\\s*)?";

   public OS400FTPEntryParser() {
      this((FTPClientConfig)null);
   }

   public OS400FTPEntryParser(FTPClientConfig var1) {
      super("(\\S+)\\s+(?:(\\d+)\\s+)?(?:(\\S+)\\s+(\\S+)\\s+)?(\\*STMF|\\*DIR|\\*FILE|\\*MEM)\\s+(?:(\\S+)\\s*)?");
      this.configure(var1);
   }

   private boolean isNullOrEmpty(String var1) {
      return var1 == null || var1.length() == 0;
   }

   protected FTPClientConfig getDefaultConfiguration() {
      return new FTPClientConfig("OS/400", "yy/MM/dd HH:mm:ss", (String)null, (String)null, (String)null, (String)null);
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2 = new FTPFile();
      var2.setRawListing(var1);
      if (!this.matches(var1)) {
         return null;
      } else {
         String var3 = this.group(1);
         String var4 = this.group(2);
         byte var5 = 3;
         String var6;
         if (this.isNullOrEmpty(this.group(3)) && this.isNullOrEmpty(this.group(4))) {
            var6 = "";
         } else {
            StringBuilder var12 = new StringBuilder();
            var12.append(this.group(3));
            var12.append(" ");
            var12.append(this.group(4));
            var6 = var12.toString();
         }

         String var7 = this.group(5);
         var1 = this.group(6);

         try {
            var2.setTimestamp(super.parseTimestamp(var6));
         } catch (ParseException var11) {
         }

         boolean var9;
         label96: {
            if (var7.equalsIgnoreCase("*STMF")) {
               if (this.isNullOrEmpty(var4) || this.isNullOrEmpty(var1)) {
                  return null;
               }

               var9 = true;
            } else {
               if (var7.equalsIgnoreCase("*DIR")) {
                  if (this.isNullOrEmpty(var4) || this.isNullOrEmpty(var1)) {
                     return null;
                  }

                  var9 = true;
                  var5 = 1;
                  break label96;
               }

               if (var7.equalsIgnoreCase("*FILE")) {
                  if (var1 == null || !var1.toUpperCase().endsWith(".SAVF")) {
                     return null;
                  }
               } else {
                  if (!var7.equalsIgnoreCase("*MEM")) {
                     var9 = true;
                     break label96;
                  }

                  if (this.isNullOrEmpty(var1)) {
                     return null;
                  }

                  if (!this.isNullOrEmpty(var4) || !this.isNullOrEmpty(var6)) {
                     return null;
                  }

                  var1 = var1.replace('/', File.separatorChar);
               }

               var9 = false;
            }

            var5 = 0;
         }

         var2.setType(var5);
         var2.setUser(var3);

         try {
            var2.setSize(Long.parseLong(var4));
         } catch (NumberFormatException var10) {
         }

         var6 = var1;
         if (var1.endsWith("/")) {
            var6 = var1.substring(0, var1.length() - 1);
         }

         var1 = var6;
         if (var9) {
            int var13 = var6.lastIndexOf(47);
            var1 = var6;
            if (var13 > -1) {
               var1 = var6.substring(var13 + 1);
            }
         }

         var2.setName(var1);
         return var2;
      }
   }
}
