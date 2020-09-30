package com.sun.activation.registries;

import java.util.NoSuchElementException;
import java.util.Vector;

class LineTokenizer {
   private static final String singles = "=";
   private int currentPosition = 0;
   private int maxPosition;
   private Vector stack = new Vector();
   private String str;

   public LineTokenizer(String var1) {
      this.str = var1;
      this.maxPosition = var1.length();
   }

   private void skipWhiteSpace() {
      while(true) {
         int var1 = this.currentPosition;
         if (var1 >= this.maxPosition || !Character.isWhitespace(this.str.charAt(var1))) {
            return;
         }

         ++this.currentPosition;
      }
   }

   public boolean hasMoreTokens() {
      if (this.stack.size() > 0) {
         return true;
      } else {
         this.skipWhiteSpace();
         return this.currentPosition < this.maxPosition;
      }
   }

   public String nextToken() {
      int var1 = this.stack.size();
      String var2;
      if (var1 > 0) {
         Vector var9 = this.stack;
         --var1;
         var2 = (String)var9.elementAt(var1);
         this.stack.removeElementAt(var1);
         return var2;
      } else {
         this.skipWhiteSpace();
         int var3 = this.currentPosition;
         if (var3 >= this.maxPosition) {
            throw new NoSuchElementException();
         } else {
            char var6 = this.str.charAt(var3);
            if (var6 == '"') {
               ++this.currentPosition;
               boolean var7 = false;

               while(true) {
                  int var4 = this.currentPosition;
                  if (var4 >= this.maxPosition) {
                     break;
                  }

                  var2 = this.str;
                  this.currentPosition = var4 + 1;
                  char var10 = var2.charAt(var4);
                  if (var10 == '\\') {
                     ++this.currentPosition;
                     var7 = true;
                  } else if (var10 == '"') {
                     if (var7) {
                        StringBuffer var8 = new StringBuffer();

                        for(var1 = var3 + 1; var1 < this.currentPosition - 1; ++var1) {
                           char var5 = this.str.charAt(var1);
                           if (var5 != '\\') {
                              var8.append(var5);
                           }
                        }

                        var2 = var8.toString();
                     } else {
                        var2 = this.str.substring(var3 + 1, this.currentPosition - 1);
                     }

                     return var2;
                  }
               }
            } else if ("=".indexOf(var6) >= 0) {
               ++this.currentPosition;
            } else {
               while(true) {
                  var1 = this.currentPosition;
                  if (var1 >= this.maxPosition || "=".indexOf(this.str.charAt(var1)) >= 0 || Character.isWhitespace(this.str.charAt(this.currentPosition))) {
                     break;
                  }

                  ++this.currentPosition;
               }
            }

            return this.str.substring(var3, this.currentPosition);
         }
      }
   }

   public void pushToken(String var1) {
      this.stack.addElement(var1);
   }
}
