package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractSessionOutputBuffer implements SessionOutputBuffer, BufferInfo {
   private static final byte[] CRLF = new byte[]{13, 10};
   private boolean ascii = true;
   private ByteArrayBuffer buffer;
   private String charset = "US-ASCII";
   private HttpTransportMetricsImpl metrics;
   private int minChunkLimit = 512;
   private OutputStream outstream;

   public int available() {
      return this.capacity() - this.length();
   }

   public int capacity() {
      return this.buffer.capacity();
   }

   protected HttpTransportMetricsImpl createTransportMetrics() {
      return new HttpTransportMetricsImpl();
   }

   public void flush() throws IOException {
      this.flushBuffer();
      this.outstream.flush();
   }

   protected void flushBuffer() throws IOException {
      int var1 = this.buffer.length();
      if (var1 > 0) {
         this.outstream.write(this.buffer.buffer(), 0, var1);
         this.buffer.clear();
         this.metrics.incrementBytesTransferred((long)var1);
      }

   }

   public HttpTransportMetrics getMetrics() {
      return this.metrics;
   }

   protected void init(OutputStream var1, int var2, HttpParams var3) {
      if (var1 == null) {
         throw new IllegalArgumentException("Input stream may not be null");
      } else if (var2 > 0) {
         if (var3 == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
         } else {
            this.outstream = var1;
            this.buffer = new ByteArrayBuffer(var2);
            String var5 = HttpProtocolParams.getHttpElementCharset(var3);
            this.charset = var5;
            boolean var4;
            if (!var5.equalsIgnoreCase("US-ASCII") && !this.charset.equalsIgnoreCase("ASCII")) {
               var4 = false;
            } else {
               var4 = true;
            }

            this.ascii = var4;
            this.minChunkLimit = var3.getIntParameter("http.connection.min-chunk-limit", 512);
            this.metrics = this.createTransportMetrics();
         }
      } else {
         throw new IllegalArgumentException("Buffer size may not be negative or zero");
      }
   }

   public int length() {
      return this.buffer.length();
   }

   public void write(int var1) throws IOException {
      if (this.buffer.isFull()) {
         this.flushBuffer();
      }

      this.buffer.append(var1);
   }

   public void write(byte[] var1) throws IOException {
      if (var1 != null) {
         this.write(var1, 0, var1.length);
      }
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if (var1 != null) {
         if (var3 <= this.minChunkLimit && var3 <= this.buffer.capacity()) {
            if (var3 > this.buffer.capacity() - this.buffer.length()) {
               this.flushBuffer();
            }

            this.buffer.append(var1, var2, var3);
         } else {
            this.flushBuffer();
            this.outstream.write(var1, var2, var3);
            this.metrics.incrementBytesTransferred((long)var3);
         }

      }
   }

   public void writeLine(String var1) throws IOException {
      if (var1 != null) {
         if (var1.length() > 0) {
            this.write(var1.getBytes(this.charset));
         }

         this.write(CRLF);
      }
   }

   public void writeLine(CharArrayBuffer var1) throws IOException {
      if (var1 != null) {
         if (this.ascii) {
            int var2 = 0;

            int var4;
            for(int var3 = var1.length(); var3 > 0; var3 -= var4) {
               var4 = Math.min(this.buffer.capacity() - this.buffer.length(), var3);
               if (var4 > 0) {
                  this.buffer.append(var1, var2, var4);
               }

               if (this.buffer.isFull()) {
                  this.flushBuffer();
               }

               var2 += var4;
            }
         } else {
            this.write(var1.toString().getBytes(this.charset));
         }

         this.write(CRLF);
      }
   }
}
