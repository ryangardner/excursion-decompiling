package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

public class NTFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
   private static final String DEFAULT_DATE_FORMAT = "MM-dd-yy hh:mma";
   private static final String DEFAULT_DATE_FORMAT2 = "MM-dd-yy kk:mm";
   private static final String REGEX = "(\\S+)\\s+(\\S+)\\s+(?:(<DIR>)|([0-9]+))\\s+(\\S.*)";
   private final FTPTimestampParser timestampParser;

   public NTFTPEntryParser() {
      this((FTPClientConfig)null);
   }

   public NTFTPEntryParser(FTPClientConfig var1) {
      super("(\\S+)\\s+(\\S+)\\s+(?:(<DIR>)|([0-9]+))\\s+(\\S.*)", 32);
      this.configure(var1);
      FTPClientConfig var2 = new FTPClientConfig("WINDOWS", "MM-dd-yy kk:mm", (String)null, (String)null, (String)null, (String)null);
      var2.setDefaultDateFormatStr("MM-dd-yy kk:mm");
      FTPTimestampParserImpl var3 = new FTPTimestampParserImpl();
      this.timestampParser = var3;
      ((Configurable)var3).configure(var2);
   }

   public FTPClientConfig getDefaultConfiguration() {
      return new FTPClientConfig("WINDOWS", "MM-dd-yy hh:mma", (String)null, (String)null, (String)null, (String)null);
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2 = new FTPFile();
      var2.setRawListing(var1);
      if (this.matches(var1)) {
         StringBuilder var9 = new StringBuilder();
         var9.append(this.group(1));
         var9.append(" ");
         var9.append(this.group(2));
         String var3 = var9.toString();
         String var4 = this.group(3);
         String var5 = this.group(4);
         var1 = this.group(5);

         try {
            var2.setTimestamp(super.parseTimestamp(var3));
         } catch (ParseException var8) {
            try {
               var2.setTimestamp(this.timestampParser.parseTimestamp(var3));
            } catch (ParseException var7) {
            }
         }

         if (var1 != null && !var1.equals(".") && !var1.equals("..")) {
            var2.setName(var1);
            if ("<DIR>".equals(var4)) {
               var2.setType(1);
               var2.setSize(0L);
            } else {
               var2.setType(0);
               if (var5 != null) {
                  var2.setSize(Long.parseLong(var5));
               }
            }

            return var2;
         }
      }

      return null;
   }
}
