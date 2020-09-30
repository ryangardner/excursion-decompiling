package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

public class OS2FTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
   private static final String DEFAULT_DATE_FORMAT = "MM-dd-yy HH:mm";
   private static final String REGEX = "\\s*([0-9]+)\\s*(\\s+|[A-Z]+)\\s*(DIR|\\s+)\\s*(\\S+)\\s+(\\S+)\\s+(\\S.*)";

   public OS2FTPEntryParser() {
      this((FTPClientConfig)null);
   }

   public OS2FTPEntryParser(FTPClientConfig var1) {
      super("\\s*([0-9]+)\\s*(\\s+|[A-Z]+)\\s*(DIR|\\s+)\\s*(\\S+)\\s+(\\S+)\\s+(\\S.*)");
      this.configure(var1);
   }

   protected FTPClientConfig getDefaultConfiguration() {
      return new FTPClientConfig("OS/2", "MM-dd-yy HH:mm", (String)null, (String)null, (String)null, (String)null);
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2 = new FTPFile();
      if (this.matches(var1)) {
         String var3 = this.group(1);
         String var4 = this.group(2);
         var1 = this.group(3);
         StringBuilder var5 = new StringBuilder();
         var5.append(this.group(4));
         var5.append(" ");
         var5.append(this.group(5));
         String var6 = var5.toString();
         String var8 = this.group(6);

         try {
            var2.setTimestamp(super.parseTimestamp(var6));
         } catch (ParseException var7) {
         }

         if (!var1.trim().equals("DIR") && !var4.trim().equals("DIR")) {
            var2.setType(0);
         } else {
            var2.setType(1);
         }

         var2.setName(var8.trim());
         var2.setSize(Long.parseLong(var3.trim()));
         return var2;
      } else {
         return null;
      }
   }
}
