package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;

public final class HttpEncodingStreamingContent implements StreamingContent {
   private final StreamingContent content;
   private final HttpEncoding encoding;

   public HttpEncodingStreamingContent(StreamingContent var1, HttpEncoding var2) {
      this.content = (StreamingContent)Preconditions.checkNotNull(var1);
      this.encoding = (HttpEncoding)Preconditions.checkNotNull(var2);
   }

   public StreamingContent getContent() {
      return this.content;
   }

   public HttpEncoding getEncoding() {
      return this.encoding;
   }

   public void writeTo(OutputStream var1) throws IOException {
      this.encoding.encode(this.content, var1);
   }
}
