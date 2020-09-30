package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;

public class ContentLengthInputStream extends InputStream {
   private static final int BUFFER_SIZE = 2048;
   private boolean closed = false;
   private long contentLength;
   private SessionInputBuffer in = null;
   private long pos = 0L;

   public ContentLengthInputStream(SessionInputBuffer var1, long var2) {
      if (var1 != null) {
         if (var2 >= 0L) {
            this.in = var1;
            this.contentLength = var2;
         } else {
            throw new IllegalArgumentException("Content length may not be negative");
         }
      } else {
         throw new IllegalArgumentException("Input stream may not be null");
      }
   }

   public int available() throws IOException {
      SessionInputBuffer var1 = this.in;
      return var1 instanceof BufferInfo ? Math.min(((BufferInfo)var1).length(), (int)(this.contentLength - this.pos)) : 0;
   }

   public void close() throws IOException {
      if (!this.closed) {
         label96: {
            Throwable var10000;
            label95: {
               byte[] var1;
               boolean var10001;
               try {
                  if (this.pos >= this.contentLength) {
                     break label96;
                  }

                  var1 = new byte[2048];
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label95;
               }

               while(true) {
                  int var2;
                  try {
                     var2 = this.read(var1);
                  } catch (Throwable var7) {
                     var10000 = var7;
                     var10001 = false;
                     break;
                  }

                  if (var2 < 0) {
                     break label96;
                  }
               }
            }

            Throwable var9 = var10000;
            this.closed = true;
            throw var9;
         }

         this.closed = true;
      }

   }

   public int read() throws IOException {
      if (!this.closed) {
         if (this.pos >= this.contentLength) {
            return -1;
         } else {
            int var1 = this.in.read();
            if (var1 == -1) {
               if (this.pos < this.contentLength) {
                  StringBuffer var2 = new StringBuffer();
                  var2.append("Premature end of Content-Length delimited message body (expected: ");
                  var2.append(this.contentLength);
                  var2.append("; received: ");
                  var2.append(this.pos);
                  throw new ConnectionClosedException(var2.toString());
               }
            } else {
               ++this.pos;
            }

            return var1;
         }
      } else {
         throw new IOException("Attempted read from closed stream.");
      }
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (!this.closed) {
         long var4 = this.pos;
         long var6 = this.contentLength;
         if (var4 >= var6) {
            return -1;
         } else {
            int var8 = var3;
            if ((long)var3 + var4 > var6) {
               var8 = (int)(var6 - var4);
            }

            var2 = this.in.read(var1, var2, var8);
            if (var2 == -1 && this.pos < this.contentLength) {
               StringBuffer var9 = new StringBuffer();
               var9.append("Premature end of Content-Length delimited message body (expected: ");
               var9.append(this.contentLength);
               var9.append("; received: ");
               var9.append(this.pos);
               throw new ConnectionClosedException(var9.toString());
            } else {
               if (var2 > 0) {
                  this.pos += (long)var2;
               }

               return var2;
            }
         }
      } else {
         throw new IOException("Attempted read from closed stream.");
      }
   }

   public long skip(long var1) throws IOException {
      if (var1 <= 0L) {
         return 0L;
      } else {
         byte[] var3 = new byte[2048];
         var1 = Math.min(var1, this.contentLength - this.pos);

         long var4;
         long var7;
         for(var4 = 0L; var1 > 0L; var1 -= var7) {
            int var6 = this.read(var3, 0, (int)Math.min(2048L, var1));
            if (var6 == -1) {
               break;
            }

            var7 = (long)var6;
            var4 += var7;
         }

         return var4;
      }
   }
}
