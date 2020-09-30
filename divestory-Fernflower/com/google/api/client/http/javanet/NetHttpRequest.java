package com.google.api.client.http.javanet;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class NetHttpRequest extends LowLevelHttpRequest {
   private static final NetHttpRequest.OutputWriter DEFAULT_CONNECTION_WRITER = new NetHttpRequest.DefaultOutputWriter();
   private final HttpURLConnection connection;
   private int writeTimeout;

   NetHttpRequest(HttpURLConnection var1) {
      this.connection = var1;
      this.writeTimeout = 0;
      var1.setInstanceFollowRedirects(false);
   }

   private boolean hasResponse(HttpURLConnection var1) {
      boolean var2 = false;

      int var3;
      try {
         var3 = var1.getResponseCode();
      } catch (IOException var4) {
         return var2;
      }

      if (var3 > 0) {
         var2 = true;
      }

      return var2;
   }

   private void writeContentToOutputStream(final NetHttpRequest.OutputWriter var1, final OutputStream var2) throws IOException {
      if (this.writeTimeout == 0) {
         var1.write(var2, this.getStreamingContent());
      } else {
         Callable var7 = new Callable<Boolean>(this.getStreamingContent()) {
            // $FF: synthetic field
            final StreamingContent val$content;

            {
               this.val$content = var4;
            }

            public Boolean call() throws IOException {
               var1.write(var2, this.val$content);
               return Boolean.TRUE;
            }
         };
         ExecutorService var6 = Executors.newSingleThreadExecutor();
         Future var8 = var6.submit(new FutureTask(var7), (Object)null);
         var6.shutdown();

         try {
            var8.get((long)this.writeTimeout, TimeUnit.MILLISECONDS);
         } catch (InterruptedException var3) {
            throw new IOException("Socket write interrupted", var3);
         } catch (ExecutionException var4) {
            throw new IOException("Exception in socket write", var4);
         } catch (TimeoutException var5) {
            throw new IOException("Socket write timed out", var5);
         }

         if (!var6.isTerminated()) {
            var6.shutdown();
         }
      }

   }

   public void addHeader(String var1, String var2) {
      this.connection.addRequestProperty(var1, var2);
   }

   public LowLevelHttpResponse execute() throws IOException {
      return this.execute(DEFAULT_CONNECTION_WRITER);
   }

   LowLevelHttpResponse execute(NetHttpRequest.OutputWriter var1) throws IOException {
      HttpURLConnection var2 = this.connection;
      if (this.getStreamingContent() != null) {
         String var3 = this.getContentType();
         if (var3 != null) {
            this.addHeader("Content-Type", var3);
         }

         var3 = this.getContentEncoding();
         if (var3 != null) {
            this.addHeader("Content-Encoding", var3);
         }

         long var4 = this.getContentLength();
         long var52;
         int var6 = (var52 = var4 - 0L) == 0L ? 0 : (var52 < 0L ? -1 : 1);
         if (var6 >= 0) {
            var2.setRequestProperty("Content-Length", Long.toString(var4));
         }

         var3 = var2.getRequestMethod();
         boolean var7;
         if (!"POST".equals(var3) && !"PUT".equals(var3)) {
            if (var6 == 0) {
               var7 = true;
            } else {
               var7 = false;
            }

            Preconditions.checkArgument(var7, "%s with non-zero content length is not supported", var3);
         } else {
            label442: {
               var2.setDoOutput(true);
               if (var6 >= 0 && var4 <= 2147483647L) {
                  var2.setFixedLengthStreamingMode((int)var4);
               } else {
                  var2.setChunkedStreamingMode(0);
               }

               OutputStream var51 = var2.getOutputStream();

               label437: {
                  Throwable var10000;
                  label438: {
                     IOException var48;
                     boolean var10001;
                     try {
                        try {
                           this.writeContentToOutputStream(var1, var51);
                           break label437;
                        } catch (IOException var46) {
                           var48 = var46;
                        }
                     } catch (Throwable var47) {
                        var10000 = var47;
                        var10001 = false;
                        break label438;
                     }

                     try {
                        var7 = this.hasResponse(var2);
                     } catch (Throwable var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label438;
                     }

                     if (var7) {
                        try {
                           var51.close();
                        } catch (IOException var42) {
                        }
                        break label442;
                     }

                     label412:
                     try {
                        throw var48;
                     } catch (Throwable var44) {
                        var10000 = var44;
                        var10001 = false;
                        break label412;
                     }
                  }

                  Throwable var49 = var10000;

                  try {
                     var51.close();
                  } catch (IOException var40) {
                  }

                  throw var49;
               }

               try {
                  var51.close();
               } catch (IOException var43) {
                  throw var43;
               }
            }
         }
      }

      try {
         var2.connect();
         NetHttpResponse var50 = new NetHttpResponse(var2);
         return var50;
      } finally {
         var2.disconnect();
      }
   }

   String getRequestProperty(String var1) {
      return this.connection.getRequestProperty(var1);
   }

   public void setTimeout(int var1, int var2) {
      this.connection.setReadTimeout(var2);
      this.connection.setConnectTimeout(var1);
   }

   public void setWriteTimeout(int var1) throws IOException {
      this.writeTimeout = var1;
   }

   static class DefaultOutputWriter implements NetHttpRequest.OutputWriter {
      public void write(OutputStream var1, StreamingContent var2) throws IOException {
         var2.writeTo(var1);
      }
   }

   interface OutputWriter {
      void write(OutputStream var1, StreamingContent var2) throws IOException;
   }
}
