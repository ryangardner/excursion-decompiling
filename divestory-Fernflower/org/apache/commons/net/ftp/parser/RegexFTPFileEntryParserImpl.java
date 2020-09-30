package org.apache.commons.net.ftp.parser;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.net.ftp.FTPFileEntryParserImpl;

public abstract class RegexFTPFileEntryParserImpl extends FTPFileEntryParserImpl {
   protected Matcher _matcher_ = null;
   private Pattern pattern = null;
   private MatchResult result = null;

   public RegexFTPFileEntryParserImpl(String var1) {
      this.compileRegex(var1, 0);
   }

   public RegexFTPFileEntryParserImpl(String var1, int var2) {
      this.compileRegex(var1, var2);
   }

   private void compileRegex(String var1, int var2) {
      try {
         this.pattern = Pattern.compile(var1, var2);
      } catch (PatternSyntaxException var4) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Unparseable regex supplied: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public int getGroupCnt() {
      MatchResult var1 = this.result;
      return var1 == null ? 0 : var1.groupCount();
   }

   public String getGroupsAsString() {
      StringBuilder var1 = new StringBuilder();

      for(int var2 = 1; var2 <= this.result.groupCount(); ++var2) {
         var1.append(var2);
         var1.append(") ");
         var1.append(this.result.group(var2));
         var1.append(System.getProperty("line.separator"));
      }

      return var1.toString();
   }

   public String group(int var1) {
      MatchResult var2 = this.result;
      return var2 == null ? null : var2.group(var1);
   }

   public boolean matches(String var1) {
      this.result = null;
      Matcher var3 = this.pattern.matcher(var1);
      this._matcher_ = var3;
      if (var3.matches()) {
         this.result = this._matcher_.toMatchResult();
      }

      boolean var2;
      if (this.result != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean setRegex(String var1) {
      this.compileRegex(var1, 0);
      return true;
   }

   public boolean setRegex(String var1, int var2) {
      this.compileRegex(var1, var2);
      return true;
   }
}
