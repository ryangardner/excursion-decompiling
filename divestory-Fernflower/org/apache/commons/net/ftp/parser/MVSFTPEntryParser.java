package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.List;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

public class MVSFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
   static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm";
   static final String FILE_LIST_REGEX = "\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+[FV]\\S*\\s+\\S+\\s+\\S+\\s+(PS|PO|PO-E)\\s+(\\S+)\\s*";
   static final int FILE_LIST_TYPE = 0;
   static final String JES_LEVEL_1_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*";
   static final int JES_LEVEL_1_LIST_TYPE = 3;
   static final String JES_LEVEL_2_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+).*";
   static final int JES_LEVEL_2_LIST_TYPE = 4;
   static final String MEMBER_LIST_REGEX = "(\\S+)\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*";
   static final int MEMBER_LIST_TYPE = 1;
   static final int UNIX_LIST_TYPE = 2;
   static final int UNKNOWN_LIST_TYPE = -1;
   private int isType = -1;
   private UnixFTPEntryParser unixFTPEntryParser;

   public MVSFTPEntryParser() {
      super("");
      super.configure((FTPClientConfig)null);
   }

   private boolean parseFileList(FTPFile var1, String var2) {
      if (this.matches(var2)) {
         var1.setRawListing(var2);
         var2 = this.group(2);
         String var3 = this.group(1);
         var1.setName(var2);
         if ("PS".equals(var3)) {
            var1.setType(0);
         } else {
            if (!"PO".equals(var3) && !"PO-E".equals(var3)) {
               return false;
            }

            var1.setType(1);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean parseJeslevel1List(FTPFile var1, String var2) {
      if (this.matches(var2) && this.group(3).equalsIgnoreCase("OUTPUT")) {
         var1.setRawListing(var2);
         var1.setName(this.group(2));
         var1.setType(0);
         return true;
      } else {
         return false;
      }
   }

   private boolean parseJeslevel2List(FTPFile var1, String var2) {
      if (this.matches(var2) && this.group(4).equalsIgnoreCase("OUTPUT")) {
         var1.setRawListing(var2);
         var1.setName(this.group(2));
         var1.setType(0);
         return true;
      } else {
         return false;
      }
   }

   private boolean parseMemberList(FTPFile var1, String var2) {
      if (this.matches(var2)) {
         var1.setRawListing(var2);
         var2 = this.group(1);
         StringBuilder var3 = new StringBuilder();
         var3.append(this.group(2));
         var3.append(" ");
         var3.append(this.group(3));
         String var5 = var3.toString();
         var1.setName(var2);
         var1.setType(0);

         try {
            var1.setTimestamp(super.parseTimestamp(var5));
            return true;
         } catch (ParseException var4) {
            var4.printStackTrace();
         }
      }

      return false;
   }

   private boolean parseSimpleEntry(FTPFile var1, String var2) {
      if (var2 != null && var2.trim().length() > 0) {
         var1.setRawListing(var2);
         var1.setName(var2.split(" ")[0]);
         var1.setType(0);
         return true;
      } else {
         return false;
      }
   }

   private boolean parseUnixList(FTPFile var1, String var2) {
      return this.unixFTPEntryParser.parseFTPEntry(var2) != null;
   }

   protected FTPClientConfig getDefaultConfiguration() {
      return new FTPClientConfig("MVS", "yyyy/MM/dd HH:mm", (String)null, (String)null, (String)null, (String)null);
   }

   public FTPFile parseFTPEntry(String var1) {
      FTPFile var2 = new FTPFile();
      int var3 = this.isType;
      boolean var4;
      if (var3 == 0) {
         var4 = this.parseFileList(var2, var1);
      } else if (var3 == 1) {
         var4 = this.parseMemberList(var2, var1);
         if (!var4) {
            var4 = this.parseSimpleEntry(var2, var1);
         }
      } else if (var3 == 2) {
         var4 = this.parseUnixList(var2, var1);
      } else if (var3 == 3) {
         var4 = this.parseJeslevel1List(var2, var1);
      } else if (var3 == 4) {
         var4 = this.parseJeslevel2List(var2, var1);
      } else {
         var4 = false;
      }

      FTPFile var5 = var2;
      if (!var4) {
         var5 = null;
      }

      return var5;
   }

   public List<String> preParse(List<String> var1) {
      if (var1 != null && var1.size() > 0) {
         String var2 = (String)var1.get(0);
         if (var2.indexOf("Volume") >= 0 && var2.indexOf("Dsname") >= 0) {
            this.setType(0);
            super.setRegex("\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+[FV]\\S*\\s+\\S+\\s+\\S+\\s+(PS|PO|PO-E)\\s+(\\S+)\\s*");
         } else if (var2.indexOf("Name") >= 0 && var2.indexOf("Id") >= 0) {
            this.setType(1);
            super.setRegex("(\\S+)\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*");
         } else if (var2.indexOf("total") == 0) {
            this.setType(2);
            this.unixFTPEntryParser = new UnixFTPEntryParser();
         } else if (var2.indexOf("Spool Files") >= 30) {
            this.setType(3);
            super.setRegex("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*");
         } else if (var2.indexOf("JOBNAME") == 0 && var2.indexOf("JOBID") > 8) {
            this.setType(4);
            super.setRegex("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+).*");
         } else {
            this.setType(-1);
         }

         if (this.isType != 3) {
            var1.remove(0);
         }
      }

      return var1;
   }

   void setType(int var1) {
      this.isType = var1;
   }
}
