package com.google.api.client.http;

import com.google.api.client.util.StreamingContent;
import java.io.IOException;

public abstract class LowLevelHttpRequest {
   private String contentEncoding;
   private long contentLength = -1L;
   private String contentType;
   private StreamingContent streamingContent;

   public abstract void addHeader(String var1, String var2) throws IOException;

   public abstract LowLevelHttpResponse execute() throws IOException;

   public final String getContentEncoding() {
      return this.contentEncoding;
   }

   public final long getContentLength() {
      return this.contentLength;
   }

   public final String getContentType() {
      return this.contentType;
   }

   public final StreamingContent getStreamingContent() {
      return this.streamingContent;
   }

   public final void setContentEncoding(String var1) throws IOException {
      this.contentEncoding = var1;
   }

   public final void setContentLength(long var1) throws IOException {
      this.contentLength = var1;
   }

   public final void setContentType(String var1) throws IOException {
      this.contentType = var1;
   }

   public final void setStreamingContent(StreamingContent var1) throws IOException {
      this.streamingContent = var1;
   }

   public void setTimeout(int var1, int var2) throws IOException {
   }

   public void setWriteTimeout(int var1) throws IOException {
   }
}
