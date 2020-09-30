package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;

public class DefaultIndenter extends DefaultPrettyPrinter.NopIndenter {
   private static final int INDENT_LEVELS = 16;
   public static final DefaultIndenter SYSTEM_LINEFEED_INSTANCE;
   public static final String SYS_LF;
   private static final long serialVersionUID = 1L;
   private final int charsPerLevel;
   private final String eol;
   private final char[] indents;

   static {
      String var0;
      try {
         var0 = System.getProperty("line.separator");
      } finally {
         ;
      }

      SYS_LF = var0;
      SYSTEM_LINEFEED_INSTANCE = new DefaultIndenter("  ", SYS_LF);
   }

   public DefaultIndenter() {
      this("  ", SYS_LF);
   }

   public DefaultIndenter(String var1, String var2) {
      this.charsPerLevel = var1.length();
      this.indents = new char[var1.length() * 16];
      int var3 = 0;

      for(int var4 = 0; var3 < 16; ++var3) {
         var1.getChars(0, var1.length(), this.indents, var4);
         var4 += var1.length();
      }

      this.eol = var2;
   }

   public String getEol() {
      return this.eol;
   }

   public String getIndent() {
      return new String(this.indents, 0, this.charsPerLevel);
   }

   public boolean isInline() {
      return false;
   }

   public DefaultIndenter withIndent(String var1) {
      return var1.equals(this.getIndent()) ? this : new DefaultIndenter(var1, this.eol);
   }

   public DefaultIndenter withLinefeed(String var1) {
      return var1.equals(this.eol) ? this : new DefaultIndenter(this.getIndent(), var1);
   }

   public void writeIndentation(JsonGenerator var1, int var2) throws IOException {
      var1.writeRaw(this.eol);
      if (var2 > 0) {
         var2 *= this.charsPerLevel;

         while(true) {
            char[] var3 = this.indents;
            if (var2 <= var3.length) {
               var1.writeRaw((char[])var3, 0, var2);
               break;
            }

            var1.writeRaw((char[])var3, 0, var3.length);
            var2 -= this.indents.length;
         }
      }

   }
}
