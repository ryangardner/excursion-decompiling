package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;

public abstract class ConfigurableFTPFileEntryParserImpl extends RegexFTPFileEntryParserImpl implements Configurable {
   private final FTPTimestampParser timestampParser = new FTPTimestampParserImpl();

   public ConfigurableFTPFileEntryParserImpl(String var1) {
      super(var1);
   }

   public ConfigurableFTPFileEntryParserImpl(String var1, int var2) {
      super(var1, var2);
   }

   public void configure(FTPClientConfig var1) {
      if (this.timestampParser instanceof Configurable) {
         FTPClientConfig var2 = this.getDefaultConfiguration();
         if (var1 != null) {
            if (var1.getDefaultDateFormatStr() == null) {
               var1.setDefaultDateFormatStr(var2.getDefaultDateFormatStr());
            }

            if (var1.getRecentDateFormatStr() == null) {
               var1.setRecentDateFormatStr(var2.getRecentDateFormatStr());
            }

            ((Configurable)this.timestampParser).configure(var1);
         } else {
            ((Configurable)this.timestampParser).configure(var2);
         }
      }

   }

   protected abstract FTPClientConfig getDefaultConfiguration();

   public Calendar parseTimestamp(String var1) throws ParseException {
      return this.timestampParser.parseTimestamp(var1);
   }
}
