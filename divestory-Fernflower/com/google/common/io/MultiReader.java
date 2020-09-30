package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class MultiReader extends Reader {
   @NullableDecl
   private Reader current;
   private final Iterator<? extends CharSource> it;

   MultiReader(Iterator<? extends CharSource> var1) throws IOException {
      this.it = var1;
      this.advance();
   }

   private void advance() throws IOException {
      this.close();
      if (this.it.hasNext()) {
         this.current = ((CharSource)this.it.next()).openStream();
      }

   }

   public void close() throws IOException {
      Reader var1 = this.current;
      if (var1 != null) {
         try {
            var1.close();
         } finally {
            this.current = null;
         }
      }

   }

   public int read(@NullableDecl char[] var1, int var2, int var3) throws IOException {
      Reader var4 = this.current;
      if (var4 == null) {
         return -1;
      } else {
         int var5 = var4.read(var1, var2, var3);
         if (var5 == -1) {
            this.advance();
            return this.read(var1, var2, var3);
         } else {
            return var5;
         }
      }
   }

   public boolean ready() throws IOException {
      Reader var1 = this.current;
      boolean var2;
      if (var1 != null && var1.ready()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public long skip(long var1) throws IOException {
      long var8;
      int var3 = (var8 = var1 - 0L) == 0L ? 0 : (var8 < 0L ? -1 : 1);
      boolean var4;
      if (var3 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "n is negative");
      if (var3 > 0) {
         while(true) {
            Reader var5 = this.current;
            if (var5 == null) {
               break;
            }

            long var6 = var5.skip(var1);
            if (var6 > 0L) {
               return var6;
            }

            this.advance();
         }
      }

      return 0L;
   }
}
