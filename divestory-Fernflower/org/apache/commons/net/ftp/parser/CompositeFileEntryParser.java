package org.apache.commons.net.ftp.parser;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.FTPFileEntryParserImpl;

public class CompositeFileEntryParser extends FTPFileEntryParserImpl {
   private FTPFileEntryParser cachedFtpFileEntryParser = null;
   private final FTPFileEntryParser[] ftpFileEntryParsers;

   public CompositeFileEntryParser(FTPFileEntryParser[] var1) {
      this.ftpFileEntryParsers = var1;
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFileEntryParser var2 = this.cachedFtpFileEntryParser;
      if (var2 != null) {
         FTPFile var7 = var2.parseFTPEntry(var1);
         if (var7 != null) {
            return var7;
         }
      } else {
         FTPFileEntryParser[] var8 = this.ftpFileEntryParsers;
         int var3 = var8.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            FTPFileEntryParser var5 = var8[var4];
            FTPFile var6 = var5.parseFTPEntry(var1);
            if (var6 != null) {
               this.cachedFtpFileEntryParser = var5;
               return var6;
            }
         }
      }

      return null;
   }
}
