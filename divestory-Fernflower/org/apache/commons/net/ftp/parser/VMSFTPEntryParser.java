package org.apache.commons.net.ftp.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.StringTokenizer;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

public class VMSFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
   private static final String DEFAULT_DATE_FORMAT = "d-MMM-yyyy HH:mm:ss";
   private static final String REGEX = "(.*?;[0-9]+)\\s*(\\d+)/\\d+\\s*(\\S+)\\s+(\\S+)\\s+\\[(([0-9$A-Za-z_]+)|([0-9$A-Za-z_]+),([0-9$a-zA-Z_]+))\\]?\\s*\\([a-zA-Z]*,([a-zA-Z]*),([a-zA-Z]*),([a-zA-Z]*)\\)";

   public VMSFTPEntryParser() {
      this((FTPClientConfig)null);
   }

   public VMSFTPEntryParser(FTPClientConfig var1) {
      super("(.*?;[0-9]+)\\s*(\\d+)/\\d+\\s*(\\S+)\\s+(\\S+)\\s+\\[(([0-9$A-Za-z_]+)|([0-9$A-Za-z_]+),([0-9$a-zA-Z_]+))\\]?\\s*\\([a-zA-Z]*,([a-zA-Z]*),([a-zA-Z]*),([a-zA-Z]*)\\)");
      this.configure(var1);
   }

   protected FTPClientConfig getDefaultConfiguration() {
      return new FTPClientConfig("VMS", "d-MMM-yyyy HH:mm:ss", (String)null, (String)null, (String)null, (String)null);
   }

   protected boolean isVersioning() {
      return false;
   }

   public FTPFile parseFTPEntry(String var1) {
      boolean var2 = this.matches(var1);
      String var3 = null;
      if (var2) {
         FTPFile var4 = new FTPFile();
         var4.setRawListing(var1);
         String var5 = this.group(1);
         String var6 = this.group(2);
         StringBuilder var13 = new StringBuilder();
         var13.append(this.group(3));
         var13.append(" ");
         var13.append(this.group(4));
         String var7 = var13.toString();
         var1 = this.group(5);
         String var8 = this.group(9);
         String var9 = this.group(10);
         String var10 = this.group(11);

         try {
            var4.setTimestamp(super.parseTimestamp(var7));
         } catch (ParseException var12) {
         }

         StringTokenizer var14 = new StringTokenizer(var1, ",");
         int var11 = var14.countTokens();
         if (var11 != 1) {
            if (var11 != 2) {
               var1 = null;
            } else {
               var3 = var14.nextToken();
               var1 = var14.nextToken();
            }
         } else {
            var1 = var14.nextToken();
         }

         if (var5.lastIndexOf(".DIR") != -1) {
            var4.setType(1);
         } else {
            var4.setType(0);
         }

         if (this.isVersioning()) {
            var4.setName(var5);
         } else {
            var4.setName(var5.substring(0, var5.lastIndexOf(";")));
         }

         var4.setSize(Long.parseLong(var6) * 512L);
         var4.setGroup(var3);
         var4.setUser(var1);

         for(var11 = 0; var11 < 3; ++var11) {
            var1 = (new String[]{var8, var9, var10})[var11];
            if (var1.indexOf(82) >= 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            var4.setPermission(var11, 0, var2);
            if (var1.indexOf(87) >= 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            var4.setPermission(var11, 1, var2);
            if (var1.indexOf(69) >= 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            var4.setPermission(var11, 2, var2);
         }

         return var4;
      } else {
         return null;
      }
   }

   @Deprecated
   public FTPFile[] parseFileList(InputStream var1) throws IOException {
      FTPListParseEngine var2 = new FTPListParseEngine(this);
      var2.readServerList(var1, (String)null);
      return var2.getFiles();
   }

   public String readNextEntry(BufferedReader var1) throws IOException {
      String var2 = var1.readLine();
      StringBuilder var3 = new StringBuilder();

      while(var2 != null) {
         if (!var2.startsWith("Directory") && !var2.startsWith("Total")) {
            var3.append(var2);
            if (var2.trim().endsWith(")")) {
               break;
            }

            var2 = var1.readLine();
         } else {
            var2 = var1.readLine();
         }
      }

      String var4;
      if (var3.length() == 0) {
         var4 = null;
      } else {
         var4 = var3.toString();
      }

      return var4;
   }
}
