package com.syntak.library;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class CountingRequestBody extends RequestBody {
   protected CountingRequestBody.CountingSink countingSink;
   protected RequestBody delegate;
   protected CountingRequestBody.Listener listener;

   public CountingRequestBody(RequestBody var1, CountingRequestBody.Listener var2) {
      this.delegate = var1;
      this.listener = var2;
   }

   public long contentLength() {
      long var1;
      try {
         var1 = this.delegate.contentLength();
      } catch (IOException var4) {
         var4.printStackTrace();
         var1 = 0L;
      }

      return var1;
   }

   public MediaType contentType() {
      return this.delegate.contentType();
   }

   public void writeTo(BufferedSink var1) throws IOException {
      CountingRequestBody.CountingSink var2 = new CountingRequestBody.CountingSink(var1);
      this.countingSink = var2;
      var1 = Okio.buffer((Sink)var2);
      this.delegate.writeTo(var1);
      var1.flush();
   }

   protected final class CountingSink extends ForwardingSink {
      private long bytesWritten = 0L;

      public CountingSink(Sink var2) {
         super(var2);
      }

      public void write(Buffer var1, long var2) throws IOException {
         super.write(var1, var2);
         this.bytesWritten += var2;
         CountingRequestBody.this.listener.onRequestProgress(this.bytesWritten, CountingRequestBody.this.contentLength());
      }
   }

   public interface Listener {
      void onRequestProgress(long var1, long var3);
   }
}
