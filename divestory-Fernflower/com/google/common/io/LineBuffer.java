package com.google.common.io;

import java.io.IOException;

abstract class LineBuffer {
   private StringBuilder line = new StringBuilder();
   private boolean sawReturn;

   private boolean finishLine(boolean var1) throws IOException {
      String var2;
      if (this.sawReturn) {
         if (var1) {
            var2 = "\r\n";
         } else {
            var2 = "\r";
         }
      } else if (var1) {
         var2 = "\n";
      } else {
         var2 = "";
      }

      this.handleLine(this.line.toString(), var2);
      this.line = new StringBuilder();
      this.sawReturn = false;
      return var1;
   }

   protected void add(char[] var1, int var2, int var3) throws IOException {
      boolean var4;
      int var5;
      label47: {
         if (this.sawReturn && var3 > 0) {
            if (var1[var2] == '\n') {
               var4 = true;
            } else {
               var4 = false;
            }

            if (this.finishLine(var4)) {
               var5 = var2 + 1;
               break label47;
            }
         }

         var5 = var2;
      }

      int var6 = var2 + var3;
      var3 = var5;

      for(var2 = var5; var2 < var6; var3 = var5) {
         label37: {
            char var7 = var1[var2];
            if (var7 != '\n') {
               if (var7 != '\r') {
                  var5 = var3;
                  break label37;
               }

               this.line.append(var1, var3, var2 - var3);
               this.sawReturn = true;
               var5 = var2 + 1;
               var3 = var2;
               if (var5 < var6) {
                  if (var1[var5] == '\n') {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  var3 = var2;
                  if (this.finishLine(var4)) {
                     var3 = var5;
                  }
               }
            } else {
               this.line.append(var1, var3, var2 - var3);
               this.finishLine(true);
               var3 = var2;
            }

            var5 = var3 + 1;
            var2 = var3;
         }

         ++var2;
      }

      this.line.append(var1, var3, var6 - var3);
   }

   protected void finish() throws IOException {
      if (this.sawReturn || this.line.length() > 0) {
         this.finishLine(false);
      }

   }

   protected abstract void handleLine(String var1, String var2) throws IOException;
}
