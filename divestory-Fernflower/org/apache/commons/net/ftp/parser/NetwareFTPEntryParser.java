package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

public class NetwareFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
   private static final String DEFAULT_DATE_FORMAT = "MMM dd yyyy";
   private static final String DEFAULT_RECENT_DATE_FORMAT = "MMM dd HH:mm";
   private static final String REGEX = "(d|-){1}\\s+\\[([-A-Z]+)\\]\\s+(\\S+)\\s+(\\d+)\\s+(\\S+\\s+\\S+\\s+((\\d+:\\d+)|(\\d{4})))\\s+(.*)";

   public NetwareFTPEntryParser() {
      this((FTPClientConfig)null);
   }

   public NetwareFTPEntryParser(FTPClientConfig var1) {
      super("(d|-){1}\\s+\\[([-A-Z]+)\\]\\s+(\\S+)\\s+(\\d+)\\s+(\\S+\\s+\\S+\\s+((\\d+:\\d+)|(\\d{4})))\\s+(.*)");
      this.configure(var1);
   }

   protected FTPClientConfig getDefaultConfiguration() {
      return new FTPClientConfig("NETWARE", "MMM dd yyyy", "MMM dd HH:mm", (String)null, (String)null, (String)null);
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2 = new FTPFile();
      if (this.matches(var1)) {
         String var3 = this.group(1);
         String var4 = this.group(2);
         String var5 = this.group(3);
         String var6 = this.group(4);
         String var7 = this.group(5);
         var1 = this.group(9);

         try {
            var2.setTimestamp(super.parseTimestamp(var7));
         } catch (ParseException var8) {
         }

         if (var3.trim().equals("d")) {
            var2.setType(1);
         } else {
            var2.setType(0);
         }

         var2.setUser(var5);
         var2.setName(var1.trim());
         var2.setSize(Long.parseLong(var6.trim()));
         if (var4.indexOf("R") != -1) {
            var2.setPermission(0, 0, true);
         }

         if (var4.indexOf("W") != -1) {
            var2.setPermission(0, 1, true);
         }

         return var2;
      } else {
         return null;
      }
   }
}
