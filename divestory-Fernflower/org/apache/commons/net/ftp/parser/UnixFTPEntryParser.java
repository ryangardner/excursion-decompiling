package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

public class UnixFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
   static final String DEFAULT_DATE_FORMAT = "MMM d yyyy";
   static final String DEFAULT_RECENT_DATE_FORMAT = "MMM d HH:mm";
   public static final FTPClientConfig NUMERIC_DATE_CONFIG = new FTPClientConfig("UNIX", "yyyy-MM-dd HH:mm", (String)null, (String)null, (String)null, (String)null);
   static final String NUMERIC_DATE_FORMAT = "yyyy-MM-dd HH:mm";
   private static final String REGEX = "([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s*(\\d+)\\s+(?:(\\S+(?:\\s\\S+)*?)\\s+)?(?:(\\S+(?:\\s\\S+)*)\\s+)?(\\d+(?:,\\s*\\d+)?)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s(.*)";
   private final boolean trimLeadingSpaces;

   public UnixFTPEntryParser() {
      this((FTPClientConfig)null);
   }

   public UnixFTPEntryParser(FTPClientConfig var1) {
      this(var1, false);
   }

   public UnixFTPEntryParser(FTPClientConfig var1, boolean var2) {
      super("([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s*(\\d+)\\s+(?:(\\S+(?:\\s\\S+)*?)\\s+)?(?:(\\S+(?:\\s\\S+)*)\\s+)?(\\d+(?:,\\s*\\d+)?)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s(.*)");
      this.configure(var1);
      this.trimLeadingSpaces = var2;
   }

   protected FTPClientConfig getDefaultConfiguration() {
      return new FTPClientConfig("UNIX", "MMM d yyyy", "MMM d HH:mm", (String)null, (String)null, (String)null);
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2 = new FTPFile();
      var2.setRawListing(var1);
      if (!this.matches(var1)) {
         return null;
      } else {
         String var3 = this.group(1);
         String var4 = this.group(15);
         String var5 = this.group(16);
         String var6 = this.group(17);
         String var7 = this.group(18);
         StringBuilder var17 = new StringBuilder();
         var17.append(this.group(19));
         var17.append(" ");
         var17.append(this.group(20));
         String var8 = var17.toString();
         String var9 = this.group(21);
         var1 = var9;
         if (this.trimLeadingSpaces) {
            var1 = var9.replaceFirst("^\\s+", "");
         }

         try {
            var2.setTimestamp(super.parseTimestamp(var8));
         } catch (ParseException var16) {
         }

         boolean var11;
         byte var18;
         label100: {
            label81: {
               label80: {
                  char var10 = var3.charAt(0);
                  if (var10 != '-') {
                     if (var10 == 'l') {
                        break label80;
                     }

                     switch(var10) {
                     case 'b':
                     case 'c':
                        var18 = 0;
                        var11 = true;
                        break label100;
                     case 'd':
                        var18 = 1;
                        break label81;
                     case 'e':
                        break label80;
                     case 'f':
                        break;
                     default:
                        var18 = 3;
                        break label81;
                     }
                  }

                  var18 = 0;
                  break label81;
               }

               var18 = 2;
            }

            var11 = false;
         }

         var2.setType(var18);
         int var12 = 0;

         for(int var13 = 4; var12 < 3; var13 += 4) {
            var2.setPermission(var12, 0, this.group(var13).equals("-") ^ true);
            var2.setPermission(var12, 1, this.group(var13 + 1).equals("-") ^ true);
            var9 = this.group(var13 + 2);
            if (!var9.equals("-") && !Character.isUpperCase(var9.charAt(0))) {
               var2.setPermission(var12, 2, true);
            } else {
               var2.setPermission(var12, 2, false);
            }

            ++var12;
         }

         if (!var11) {
            try {
               var2.setHardLinkCount(Integer.parseInt(var4));
            } catch (NumberFormatException var15) {
            }
         }

         var2.setUser(var5);
         var2.setGroup(var6);

         try {
            var2.setSize(Long.parseLong(var7));
         } catch (NumberFormatException var14) {
         }

         if (var18 == 2) {
            int var19 = var1.indexOf(" -> ");
            if (var19 == -1) {
               var2.setName(var1);
            } else {
               var2.setName(var1.substring(0, var19));
               var2.setLink(var1.substring(var19 + 4));
            }
         } else {
            var2.setName(var1);
         }

         return var2;
      }
   }

   public List<String> preParse(List<String> var1) {
      ListIterator var2 = var1.listIterator();

      while(var2.hasNext()) {
         if (((String)var2.next()).matches("^total \\d+$")) {
            var2.remove();
         }
      }

      return var1;
   }
}
