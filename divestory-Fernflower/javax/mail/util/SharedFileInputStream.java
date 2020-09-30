package javax.mail.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import javax.mail.internet.SharedInputStream;

public class SharedFileInputStream extends BufferedInputStream implements SharedInputStream {
   private static int defaultBufferSize;
   protected long bufpos;
   protected int bufsize;
   protected long datalen;
   protected RandomAccessFile in;
   private boolean master;
   private SharedFileInputStream.SharedFile sf;
   protected long start;

   public SharedFileInputStream(File var1) throws IOException {
      this(var1, defaultBufferSize);
   }

   public SharedFileInputStream(File var1, int var2) throws IOException {
      super((InputStream)null);
      this.start = 0L;
      this.master = true;
      if (var2 > 0) {
         this.init(new SharedFileInputStream.SharedFile(var1), var2);
      } else {
         throw new IllegalArgumentException("Buffer size <= 0");
      }
   }

   public SharedFileInputStream(String var1) throws IOException {
      this(var1, defaultBufferSize);
   }

   public SharedFileInputStream(String var1, int var2) throws IOException {
      super((InputStream)null);
      this.start = 0L;
      this.master = true;
      if (var2 > 0) {
         this.init(new SharedFileInputStream.SharedFile(var1), var2);
      } else {
         throw new IllegalArgumentException("Buffer size <= 0");
      }
   }

   private SharedFileInputStream(SharedFileInputStream.SharedFile var1, long var2, long var4, int var6) {
      super((InputStream)null);
      this.start = 0L;
      this.master = true;
      this.master = false;
      this.sf = var1;
      this.in = var1.open();
      this.start = var2;
      this.bufpos = var2;
      this.datalen = var4;
      this.bufsize = var6;
      this.buf = new byte[var6];
   }

   private void ensureOpen() throws IOException {
      if (this.in == null) {
         throw new IOException("Stream closed");
      }
   }

   private void fill() throws IOException {
      int var1;
      if (this.markpos < 0) {
         this.pos = 0;
         this.bufpos += (long)this.count;
      } else if (this.pos >= this.buf.length) {
         if (this.markpos > 0) {
            var1 = this.pos - this.markpos;
            System.arraycopy(this.buf, this.markpos, this.buf, 0, var1);
            this.pos = var1;
            this.bufpos += (long)this.markpos;
            this.markpos = 0;
         } else if (this.buf.length >= this.marklimit) {
            this.markpos = -1;
            this.pos = 0;
            this.bufpos += (long)this.count;
         } else {
            int var2 = this.pos * 2;
            var1 = var2;
            if (var2 > this.marklimit) {
               var1 = this.marklimit;
            }

            byte[] var3 = new byte[var1];
            System.arraycopy(this.buf, 0, var3, 0, this.pos);
            this.buf = var3;
         }
      }

      this.count = this.pos;
      this.in.seek(this.bufpos + (long)this.pos);
      var1 = this.buf.length - this.pos;
      long var4 = this.bufpos;
      long var6 = this.start;
      long var8 = (long)this.pos;
      long var10 = (long)var1;
      long var12 = this.datalen;
      if (var4 - var6 + var8 + var10 > var12) {
         var1 = (int)(var12 - (this.bufpos - this.start + (long)this.pos));
      }

      var1 = this.in.read(this.buf, this.pos, var1);
      if (var1 > 0) {
         this.count = var1 + this.pos;
      }

   }

   private int in_available() throws IOException {
      return (int)(this.start + this.datalen - (this.bufpos + (long)this.count));
   }

   private void init(SharedFileInputStream.SharedFile var1, int var2) throws IOException {
      this.sf = var1;
      RandomAccessFile var3 = var1.open();
      this.in = var3;
      this.start = 0L;
      this.datalen = var3.length();
      this.bufsize = var2;
      this.buf = new byte[var2];
   }

   private int read1(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.count - this.pos;
      int var5 = var4;
      if (var4 <= 0) {
         this.fill();
         var4 = this.count - this.pos;
         var5 = var4;
         if (var4 <= 0) {
            return -1;
         }
      }

      var4 = var3;
      if (var5 < var3) {
         var4 = var5;
      }

      System.arraycopy(this.buf, this.pos, var1, var2, var4);
      this.pos += var4;
      return var4;
   }

   public int available() throws IOException {
      synchronized(this){}

      int var1;
      int var2;
      int var3;
      try {
         this.ensureOpen();
         var1 = this.count;
         var2 = this.pos;
         var3 = this.in_available();
      } finally {
         ;
      }

      return var1 - var2 + var3;
   }

   public void close() throws IOException {
      if (this.in != null) {
         try {
            if (this.master) {
               this.sf.forceClose();
            } else {
               this.sf.close();
            }
         } finally {
            this.sf = null;
            this.in = null;
            this.buf = null;
         }

      }
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.close();
   }

   public long getPosition() {
      if (this.in != null) {
         return this.bufpos + (long)this.pos - this.start;
      } else {
         throw new RuntimeException("Stream closed");
      }
   }

   public void mark(int var1) {
      synchronized(this){}

      try {
         this.marklimit = var1;
         this.markpos = this.pos;
      } finally {
         ;
      }

   }

   public boolean markSupported() {
      return true;
   }

   public InputStream newStream(long var1, long var3) {
      if (this.in != null) {
         if (var1 >= 0L) {
            long var5 = var3;
            if (var3 == -1L) {
               var5 = this.datalen;
            }

            return new SharedFileInputStream(this.sf, this.start + (long)((int)var1), (long)((int)(var5 - var1)), this.bufsize);
         } else {
            throw new IllegalArgumentException("start < 0");
         }
      } else {
         throw new RuntimeException("Stream closed");
      }
   }

   public int read() throws IOException {
      synchronized(this){}

      int var1;
      byte[] var11;
      label84: {
         Throwable var10000;
         label83: {
            boolean var10001;
            label82: {
               int var2;
               try {
                  this.ensureOpen();
                  if (this.pos < this.count) {
                     break label82;
                  }

                  this.fill();
                  var1 = this.pos;
                  var2 = this.count;
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label83;
               }

               if (var1 >= var2) {
                  return -1;
               }
            }

            label76:
            try {
               var11 = this.buf;
               var1 = this.pos++;
               break label84;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label76;
            }
         }

         Throwable var3 = var10000;
         throw var3;
      }

      byte var10 = var11[var1];
      return var10 & 255;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label336: {
         boolean var10001;
         try {
            this.ensureOpen();
         } catch (Throwable var35) {
            var10000 = var35;
            var10001 = false;
            break label336;
         }

         int var4 = var2 + var3;

         int var5;
         try {
            var5 = var1.length;
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break label336;
         }

         if ((var2 | var3 | var4 | var5 - var4) >= 0) {
            label337: {
               if (var3 == 0) {
                  return 0;
               }

               try {
                  var5 = this.read1(var1, var2, var3);
               } catch (Throwable var32) {
                  var10000 = var32;
                  var10001 = false;
                  break label337;
               }

               var4 = var5;
               if (var5 <= 0) {
                  return var5;
               }

               while(true) {
                  if (var4 < var3) {
                     try {
                        var5 = this.read1(var1, var2 + var4, var3 - var4);
                     } catch (Throwable var31) {
                        var10000 = var31;
                        var10001 = false;
                        break;
                     }

                     if (var5 > 0) {
                        var4 += var5;
                        continue;
                     }
                  }

                  return var4;
               }
            }
         } else {
            label324:
            try {
               IndexOutOfBoundsException var37 = new IndexOutOfBoundsException();
               throw var37;
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break label324;
            }
         }
      }

      Throwable var36 = var10000;
      throw var36;
   }

   public void reset() throws IOException {
      synchronized(this){}

      try {
         this.ensureOpen();
         if (this.markpos < 0) {
            IOException var1 = new IOException("Resetting to invalid mark");
            throw var1;
         }

         this.pos = this.markpos;
      } finally {
         ;
      }

   }

   public long skip(long var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label241: {
         boolean var10001;
         try {
            this.ensureOpen();
         } catch (Throwable var29) {
            var10000 = var29;
            var10001 = false;
            break label241;
         }

         if (var1 <= 0L) {
            return 0L;
         }

         long var3;
         try {
            var3 = (long)(this.count - this.pos);
         } catch (Throwable var28) {
            var10000 = var28;
            var10001 = false;
            break label241;
         }

         long var5 = var3;
         if (var3 <= 0L) {
            int var7;
            int var8;
            try {
               this.fill();
               var7 = this.count;
               var8 = this.pos;
            } catch (Throwable var27) {
               var10000 = var27;
               var10001 = false;
               break label241;
            }

            var3 = (long)(var7 - var8);
            var5 = var3;
            if (var3 <= 0L) {
               return 0L;
            }
         }

         var3 = var1;
         if (var5 < var1) {
            var3 = var5;
         }

         try {
            this.pos = (int)((long)this.pos + var3);
         } catch (Throwable var26) {
            var10000 = var26;
            var10001 = false;
            break label241;
         }

         return var3;
      }

      Throwable var9 = var10000;
      throw var9;
   }

   static class SharedFile {
      private int cnt;
      private RandomAccessFile in;

      SharedFile(File var1) throws IOException {
         this.in = new RandomAccessFile(var1, "r");
      }

      SharedFile(String var1) throws IOException {
         this.in = new RandomAccessFile(var1, "r");
      }

      public void close() throws IOException {
         synchronized(this){}

         Throwable var10000;
         label84: {
            int var1;
            boolean var10001;
            try {
               if (this.cnt <= 0) {
                  return;
               }

               var1 = this.cnt - 1;
               this.cnt = var1;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label84;
            }

            if (var1 > 0) {
               return;
            }

            label75:
            try {
               this.in.close();
               return;
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break label75;
            }
         }

         Throwable var2 = var10000;
         throw var2;
      }

      protected void finalize() throws Throwable {
         super.finalize();
         this.in.close();
      }

      public void forceClose() throws IOException {
         synchronized(this){}

         try {
            if (this.cnt > 0) {
               this.cnt = 0;
               this.in.close();
            } else {
               try {
                  this.in.close();
               } catch (IOException var4) {
               }
            }
         } finally {
            ;
         }

      }

      public RandomAccessFile open() {
         ++this.cnt;
         return this.in;
      }
   }
}
