package org.apache.commons.net.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public final class CRLFLineReader extends BufferedReader {
   private static final char CR = '\r';
   private static final char LF = '\n';

   public CRLFLineReader(Reader var1) {
      super(var1);
   }

   public String readLine() throws IOException {
      StringBuilder var1 = new StringBuilder();
      Object var2 = this.lock;
      synchronized(var2){}
      boolean var3 = false;

      Throwable var10000;
      boolean var10001;
      while(true) {
         int var4;
         try {
            var4 = this.read();
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break;
         }

         String var35;
         if (var4 != -1) {
            if (var3 && var4 == 10) {
               try {
                  var35 = var1.substring(0, var1.length() - 1);
                  return var35;
               } catch (Throwable var31) {
                  var10000 = var31;
                  var10001 = false;
                  break;
               }
            } else {
               if (var4 == 13) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               try {
                  var1.append((char)var4);
               } catch (Throwable var32) {
                  var10000 = var32;
                  var10001 = false;
                  break;
               }
            }
         } else {
            try {
               ;
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break;
            }

            var35 = var1.toString();
            String var37 = var35;
            if (var35.length() == 0) {
               var37 = null;
            }

            return var37;
         }
      }

      while(true) {
         Throwable var36 = var10000;

         try {
            throw var36;
         } catch (Throwable var30) {
            var10000 = var30;
            var10001 = false;
            continue;
         }
      }
   }
}
