package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

public class MacOsPeterFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
   static final String DEFAULT_DATE_FORMAT = "MMM d yyyy";
   static final String DEFAULT_RECENT_DATE_FORMAT = "MMM d HH:mm";
   private static final String REGEX = "([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s+((folder\\s+)|((\\d+)\\s+(\\d+)\\s+))(\\d+)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s+(\\S*)(\\s*.*)";

   public MacOsPeterFTPEntryParser() {
      this((FTPClientConfig)null);
   }

   public MacOsPeterFTPEntryParser(FTPClientConfig var1) {
      super("([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s+((folder\\s+)|((\\d+)\\s+(\\d+)\\s+))(\\d+)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s+(\\S*)(\\s*.*)");
      this.configure(var1);
   }

   protected FTPClientConfig getDefaultConfiguration() {
      return new FTPClientConfig("UNIX", "MMM d yyyy", "MMM d HH:mm", (String)null, (String)null, (String)null);
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2 = new FTPFile();
      var2.setRawListing(var1);
      if (this.matches(var1)) {
         String var3 = this.group(1);
         String var4 = this.group(20);
         StringBuilder var14 = new StringBuilder();
         var14.append(this.group(21));
         var14.append(" ");
         var14.append(this.group(22));
         String var5 = var14.toString();
         var1 = this.group(23);
         String var6 = this.group(24);

         try {
            var2.setTimestamp(super.parseTimestamp(var5));
         } catch (ParseException var13) {
         }

         boolean var8;
         byte var16;
         label100: {
            label83: {
               label82: {
                  char var7 = var3.charAt(0);
                  if (var7 != '-') {
                     if (var7 == 'l') {
                        break label82;
                     }

                     switch(var7) {
                     case 'b':
                     case 'c':
                        var16 = 0;
                        var8 = true;
                        break label100;
                     case 'd':
                        var16 = 1;
                        break label83;
                     case 'e':
                        break label82;
                     case 'f':
                        break;
                     default:
                        var16 = 3;
                        break label83;
                     }
                  }

                  var16 = 0;
                  break label83;
               }

               var16 = 2;
            }

            var8 = false;
         }

         var2.setType(var16);
         int var9 = 0;

         for(int var10 = 4; var9 < 3; var10 += 4) {
            var2.setPermission(var9, 0, this.group(var10).equals("-") ^ true);
            var2.setPermission(var9, 1, this.group(var10 + 1).equals("-") ^ true);
            var3 = this.group(var10 + 2);
            if (!var3.equals("-") && !Character.isUpperCase(var3.charAt(0))) {
               var2.setPermission(var9, 2, true);
            } else {
               var2.setPermission(var9, 2, false);
            }

            ++var9;
         }

         if (!var8) {
            try {
               var2.setHardLinkCount(Integer.parseInt("0"));
            } catch (NumberFormatException var12) {
            }
         }

         var2.setUser((String)null);
         var2.setGroup((String)null);

         try {
            var2.setSize(Long.parseLong(var4));
         } catch (NumberFormatException var11) {
         }

         if (var6 == null) {
            var2.setName(var1);
         } else {
            StringBuilder var15 = new StringBuilder();
            var15.append(var1);
            var15.append(var6);
            var1 = var15.toString();
            if (var16 == 2) {
               int var17 = var1.indexOf(" -> ");
               if (var17 == -1) {
                  var2.setName(var1);
               } else {
                  var2.setName(var1.substring(0, var17));
                  var2.setLink(var1.substring(var17 + 4));
               }
            } else {
               var2.setName(var1);
            }
         }

         return var2;
      } else {
         return null;
      }
   }
}
