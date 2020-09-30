package org.apache.commons.net.ftp.parser;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.net.ftp.FTPClientConfig;

public class VMSVersioningFTPEntryParser extends VMSFTPEntryParser {
   private static final String PRE_PARSE_REGEX = "(.*?);([0-9]+)\\s*.*";
   private final Pattern _preparse_pattern_;

   public VMSVersioningFTPEntryParser() {
      this((FTPClientConfig)null);
   }

   public VMSVersioningFTPEntryParser(FTPClientConfig var1) {
      this.configure(var1);

      try {
         this._preparse_pattern_ = Pattern.compile("(.*?);([0-9]+)\\s*.*");
      } catch (PatternSyntaxException var2) {
         throw new IllegalArgumentException("Unparseable regex supplied:  (.*?);([0-9]+)\\s*.*");
      }
   }

   protected boolean isVersioning() {
      return true;
   }

   public List<String> preParse(List<String> var1) {
      HashMap var2 = new HashMap();
      ListIterator var3 = var1.listIterator();

      while(true) {
         while(true) {
            String var4;
            MatchResult var5;
            Matcher var7;
            Integer var8;
            do {
               if (!var3.hasNext()) {
                  while(var3.hasPrevious()) {
                     var4 = ((String)var3.previous()).trim();
                     var7 = this._preparse_pattern_.matcher(var4);
                     if (var7.matches()) {
                        var5 = var7.toMatchResult();
                        var4 = var5.group(1);
                        var8 = Integer.valueOf(var5.group(2));
                        Integer var9 = (Integer)var2.get(var4);
                        if (var9 != null && var8 < var9) {
                           var3.remove();
                        }
                     }
                  }

                  return var1;
               }

               var4 = ((String)var3.next()).trim();
               var7 = this._preparse_pattern_.matcher(var4);
            } while(!var7.matches());

            var5 = var7.toMatchResult();
            var4 = var5.group(1);
            var8 = Integer.valueOf(var5.group(2));
            Integer var6 = (Integer)var2.get(var4);
            if (var6 != null && var8 < var6) {
               var3.remove();
            } else {
               var2.put(var4, var8);
            }
         }
      }
   }
}
