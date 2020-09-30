package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractSessionInputBuffer implements SessionInputBuffer, BufferInfo {
   private boolean ascii = true;
   private byte[] buffer;
   private int bufferlen;
   private int bufferpos;
   private String charset = "US-ASCII";
   private InputStream instream;
   private ByteArrayBuffer linebuffer = null;
   private int maxLineLen = -1;
   private HttpTransportMetricsImpl metrics;
   private int minChunkLimit = 512;

   private int lineFromLineBuffer(CharArrayBuffer var1) throws IOException {
      int var2 = this.linebuffer.length();
      int var3;
      if (var2 > 0) {
         var3 = var2;
         if (this.linebuffer.byteAt(var2 - 1) == 10) {
            var3 = var2 - 1;
            this.linebuffer.setLength(var3);
         }

         if (var3 > 0 && this.linebuffer.byteAt(var3 - 1) == 13) {
            this.linebuffer.setLength(var3 - 1);
         }
      }

      var3 = this.linebuffer.length();
      if (this.ascii) {
         var1.append((ByteArrayBuffer)this.linebuffer, 0, var3);
      } else {
         String var4 = new String(this.linebuffer.buffer(), 0, var3, this.charset);
         var3 = var4.length();
         var1.append(var4);
      }

      this.linebuffer.clear();
      return var3;
   }

   private int lineFromReadBuffer(CharArrayBuffer var1, int var2) throws IOException {
      int var3 = this.bufferpos;
      this.bufferpos = var2 + 1;
      int var4 = var2;
      if (var2 > 0) {
         var4 = var2;
         if (this.buffer[var2 - 1] == 13) {
            var4 = var2 - 1;
         }
      }

      var2 = var4 - var3;
      if (this.ascii) {
         var1.append(this.buffer, var3, var2);
      } else {
         String var5 = new String(this.buffer, var3, var2, this.charset);
         var1.append(var5);
         var2 = var5.length();
      }

      return var2;
   }

   private int locateLF() {
      for(int var1 = this.bufferpos; var1 < this.bufferlen; ++var1) {
         if (this.buffer[var1] == 10) {
            return var1;
         }
      }

      return -1;
   }

   public int available() {
      return this.capacity() - this.length();
   }

   public int capacity() {
      return this.buffer.length;
   }

   protected HttpTransportMetricsImpl createTransportMetrics() {
      return new HttpTransportMetricsImpl();
   }

   protected int fillBuffer() throws IOException {
      int var1 = this.bufferpos;
      int var2;
      byte[] var3;
      if (var1 > 0) {
         var2 = this.bufferlen - var1;
         if (var2 > 0) {
            var3 = this.buffer;
            System.arraycopy(var3, var1, var3, 0, var2);
         }

         this.bufferpos = 0;
         this.bufferlen = var2;
      }

      var1 = this.bufferlen;
      var3 = this.buffer;
      var2 = var3.length;
      var2 = this.instream.read(var3, var1, var2 - var1);
      if (var2 == -1) {
         return -1;
      } else {
         this.bufferlen = var1 + var2;
         this.metrics.incrementBytesTransferred((long)var2);
         return var2;
      }
   }

   public HttpTransportMetrics getMetrics() {
      return this.metrics;
   }

   protected boolean hasBufferedData() {
      boolean var1;
      if (this.bufferpos < this.bufferlen) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void init(InputStream var1, int var2, HttpParams var3) {
      if (var1 == null) {
         throw new IllegalArgumentException("Input stream may not be null");
      } else if (var2 > 0) {
         if (var3 == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
         } else {
            this.instream = var1;
            this.buffer = new byte[var2];
            boolean var4 = false;
            this.bufferpos = 0;
            this.bufferlen = 0;
            this.linebuffer = new ByteArrayBuffer(var2);
            String var5 = HttpProtocolParams.getHttpElementCharset(var3);
            this.charset = var5;
            if (var5.equalsIgnoreCase("US-ASCII") || this.charset.equalsIgnoreCase("ASCII")) {
               var4 = true;
            }

            this.ascii = var4;
            this.maxLineLen = var3.getIntParameter("http.connection.max-line-length", -1);
            this.minChunkLimit = var3.getIntParameter("http.connection.min-chunk-limit", 512);
            this.metrics = this.createTransportMetrics();
         }
      } else {
         throw new IllegalArgumentException("Buffer size may not be negative or zero");
      }
   }

   public int length() {
      return this.bufferlen - this.bufferpos;
   }

   public int read() throws IOException {
      while(true) {
         if (!this.hasBufferedData()) {
            if (this.fillBuffer() != -1) {
               continue;
            }

            return -1;
         }

         byte[] var1 = this.buffer;
         int var2 = this.bufferpos++;
         return var1[var2] & 255;
      }
   }

   public int read(byte[] var1) throws IOException {
      return var1 == null ? 0 : this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         return 0;
      } else if (this.hasBufferedData()) {
         var3 = Math.min(var3, this.bufferlen - this.bufferpos);
         System.arraycopy(this.buffer, this.bufferpos, var1, var2, var3);
         this.bufferpos += var3;
         return var3;
      } else if (var3 > this.minChunkLimit) {
         var2 = this.instream.read(var1, var2, var3);
         if (var2 > 0) {
            this.metrics.incrementBytesTransferred((long)var2);
         }

         return var2;
      } else {
         do {
            if (this.hasBufferedData()) {
               var3 = Math.min(var3, this.bufferlen - this.bufferpos);
               System.arraycopy(this.buffer, this.bufferpos, var1, var2, var3);
               this.bufferpos += var3;
               return var3;
            }
         } while(this.fillBuffer() != -1);

         return -1;
      }
   }

   public int readLine(CharArrayBuffer var1) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("Char array buffer may not be null");
      } else {
         boolean var2 = true;
         int var3 = 0;

         while(var2) {
            int var4;
            boolean var5;
            label41: {
               var4 = this.locateLF();
               if (var4 != -1) {
                  if (this.linebuffer.isEmpty()) {
                     return this.lineFromReadBuffer(var1, var4);
                  }

                  ++var4;
                  int var6 = this.bufferpos;
                  this.linebuffer.append(this.buffer, var6, var4 - var6);
                  this.bufferpos = var4;
               } else {
                  if (this.hasBufferedData()) {
                     var3 = this.bufferlen;
                     var4 = this.bufferpos;
                     this.linebuffer.append(this.buffer, var4, var3 - var4);
                     this.bufferpos = this.bufferlen;
                  }

                  var3 = this.fillBuffer();
                  var5 = var2;
                  var4 = var3;
                  if (var3 != -1) {
                     break label41;
                  }
               }

               var5 = false;
               var4 = var3;
            }

            var2 = var5;
            var3 = var4;
            if (this.maxLineLen > 0) {
               if (this.linebuffer.length() >= this.maxLineLen) {
                  throw new IOException("Maximum line length limit exceeded");
               }

               var2 = var5;
               var3 = var4;
            }
         }

         if (var3 == -1 && this.linebuffer.isEmpty()) {
            return -1;
         } else {
            return this.lineFromLineBuffer(var1);
         }
      }
   }

   public String readLine() throws IOException {
      CharArrayBuffer var1 = new CharArrayBuffer(64);
      return this.readLine(var1) != -1 ? var1.toString() : null;
   }
}
