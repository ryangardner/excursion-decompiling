package org.apache.commons.net.ftp.parser;

import java.util.Calendar;
import org.apache.commons.net.ftp.FTPFile;

public class EnterpriseUnixFTPEntryParser extends RegexFTPFileEntryParserImpl {
   private static final String MONTHS = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)";
   private static final String REGEX = "(([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z]))(\\S*)\\s*(\\S+)\\s*(\\S*)\\s*(\\d*)\\s*(\\d*)\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*((?:[012]\\d*)|(?:3[01]))\\s*((\\d\\d\\d\\d)|((?:[01]\\d)|(?:2[0123])):([012345]\\d))\\s(\\S*)(\\s*.*)";

   public EnterpriseUnixFTPEntryParser() {
      super("(([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z]))(\\S*)\\s*(\\S+)\\s*(\\S*)\\s*(\\d*)\\s*(\\d*)\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*((?:[012]\\d*)|(?:3[01]))\\s*((\\d\\d\\d\\d)|((?:[01]\\d)|(?:2[0123])):([012345]\\d))\\s(\\S*)(\\s*.*)");
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2 = new FTPFile();
      var2.setRawListing(var1);
      if (this.matches(var1)) {
         String var3 = this.group(14);
         String var4 = this.group(15);
         String var5 = this.group(16);
         String var6 = this.group(17);
         String var7 = this.group(18);
         String var8 = this.group(20);
         String var9 = this.group(21);
         String var10 = this.group(22);
         var1 = this.group(23);
         var2.setType(0);
         var2.setUser(var3);
         var2.setGroup(var4);

         try {
            var2.setSize(Long.parseLong(var5));
         } catch (NumberFormatException var15) {
         }

         label64: {
            Calendar var21 = Calendar.getInstance();
            var21.set(14, 0);
            byte var11 = 13;
            var21.set(13, 0);
            var21.set(12, 0);
            var21.set(11, 0);
            int var12 = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)".indexOf(var6) / 4;
            boolean var10001;
            byte var22;
            if (var8 != null) {
               try {
                  var21.set(1, Integer.parseInt(var8));
               } catch (NumberFormatException var19) {
                  var10001 = false;
                  break label64;
               }

               var22 = 11;
            } else {
               int var14;
               try {
                  var14 = var21.get(1);
               } catch (NumberFormatException var18) {
                  var10001 = false;
                  break label64;
               }

               int var13 = var14;

               label50: {
                  try {
                     if (var21.get(2) >= var12) {
                        break label50;
                     }
                  } catch (NumberFormatException var20) {
                     var10001 = false;
                     break label64;
                  }

                  var13 = var14 - 1;
               }

               try {
                  var21.set(1, var13);
                  var21.set(11, Integer.parseInt(var9));
                  var21.set(12, Integer.parseInt(var10));
               } catch (NumberFormatException var17) {
                  var10001 = false;
                  break label64;
               }

               var22 = var11;
            }

            try {
               var21.set(2, var12);
               var21.set(5, Integer.parseInt(var7));
               var21.clear(var22);
               var2.setTimestamp(var21);
            } catch (NumberFormatException var16) {
               var10001 = false;
            }
         }

         var2.setName(var1);
         return var2;
      } else {
         return null;
      }
   }
}
