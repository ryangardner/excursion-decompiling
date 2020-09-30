package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

final class CharSequenceReader extends Reader {
   private int mark;
   private int pos;
   private CharSequence seq;

   public CharSequenceReader(CharSequence var1) {
      this.seq = (CharSequence)Preconditions.checkNotNull(var1);
   }

   private void checkOpen() throws IOException {
      if (this.seq == null) {
         throw new IOException("reader closed");
      }
   }

   private boolean hasRemaining() {
      boolean var1;
      if (this.remaining() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private int remaining() {
      return this.seq.length() - this.pos;
   }

   public void close() throws IOException {
      synchronized(this){}

      try {
         this.seq = null;
      } finally {
         ;
      }

   }

   public void mark(int var1) throws IOException {
      synchronized(this){}
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      try {
         Preconditions.checkArgument(var2, "readAheadLimit (%s) may not be negative", var1);
         this.checkOpen();
         this.mark = this.pos;
      } finally {
         ;
      }

   }

   public boolean markSupported() {
      return true;
   }

   public int read() throws IOException {
      synchronized(this){}
      boolean var4 = false;

      int var2;
      try {
         var4 = true;
         this.checkOpen();
         if (this.hasRemaining()) {
            CharSequence var1 = this.seq;
            var2 = this.pos++;
            var2 = var1.charAt(var2);
            var4 = false;
            return var2;
         }

         var4 = false;
      } finally {
         if (var4) {
            ;
         }
      }

      var2 = -1;
      return var2;
   }

   public int read(CharBuffer var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label163: {
         boolean var2;
         boolean var10001;
         try {
            Preconditions.checkNotNull(var1);
            this.checkOpen();
            var2 = this.hasRemaining();
         } catch (Throwable var18) {
            var10000 = var18;
            var10001 = false;
            break label163;
         }

         if (!var2) {
            return -1;
         }

         int var3;
         try {
            var3 = Math.min(var1.remaining(), this.remaining());
         } catch (Throwable var17) {
            var10000 = var17;
            var10001 = false;
            break label163;
         }

         int var4 = 0;

         while(true) {
            if (var4 >= var3) {
               return var3;
            }

            try {
               CharSequence var5 = this.seq;
               int var6 = this.pos++;
               var1.put(var5.charAt(var6));
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break;
            }

            ++var4;
         }
      }

      Throwable var19 = var10000;
      throw var19;
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label163: {
         boolean var4;
         boolean var10001;
         try {
            Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);
            this.checkOpen();
            var4 = this.hasRemaining();
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            break label163;
         }

         if (!var4) {
            return -1;
         }

         int var5;
         try {
            var5 = Math.min(var3, this.remaining());
         } catch (Throwable var18) {
            var10000 = var18;
            var10001 = false;
            break label163;
         }

         var3 = 0;

         while(true) {
            if (var3 >= var5) {
               return var5;
            }

            try {
               CharSequence var6 = this.seq;
               int var7 = this.pos++;
               var1[var2 + var3] = var6.charAt(var7);
            } catch (Throwable var17) {
               var10000 = var17;
               var10001 = false;
               break;
            }

            ++var3;
         }
      }

      Throwable var20 = var10000;
      throw var20;
   }

   public boolean ready() throws IOException {
      synchronized(this){}

      try {
         this.checkOpen();
      } finally {
         ;
      }

      return true;
   }

   public void reset() throws IOException {
      synchronized(this){}

      try {
         this.checkOpen();
         this.pos = this.mark;
      } finally {
         ;
      }

   }

   public long skip(long var1) throws IOException {
      synchronized(this){}
      boolean var3;
      if (var1 >= 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var7 = false;

      int var4;
      try {
         var7 = true;
         Preconditions.checkArgument(var3, "n (%s) may not be negative", var1);
         this.checkOpen();
         var4 = (int)Math.min((long)this.remaining(), var1);
         this.pos += var4;
         var7 = false;
      } finally {
         if (var7) {
            ;
         }
      }

      var1 = (long)var4;
      return var1;
   }
}
