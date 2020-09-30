package com.google.api.client.http;

import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class AbstractHttpContent implements HttpContent {
   private long computedLength;
   private HttpMediaType mediaType;

   protected AbstractHttpContent(HttpMediaType var1) {
      this.computedLength = -1L;
      this.mediaType = var1;
   }

   protected AbstractHttpContent(String var1) {
      HttpMediaType var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = new HttpMediaType(var1);
      }

      this(var2);
   }

   public static long computeLength(HttpContent var0) throws IOException {
      return !var0.retrySupported() ? -1L : IOUtils.computeLength(var0);
   }

   protected long computeLength() throws IOException {
      return computeLength(this);
   }

   protected final Charset getCharset() {
      HttpMediaType var1 = this.mediaType;
      Charset var2;
      if (var1 != null && var1.getCharsetParameter() != null) {
         var2 = this.mediaType.getCharsetParameter();
      } else {
         var2 = Charsets.ISO_8859_1;
      }

      return var2;
   }

   public long getLength() throws IOException {
      if (this.computedLength == -1L) {
         this.computedLength = this.computeLength();
      }

      return this.computedLength;
   }

   public final HttpMediaType getMediaType() {
      return this.mediaType;
   }

   public String getType() {
      HttpMediaType var1 = this.mediaType;
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.build();
      }

      return var2;
   }

   public boolean retrySupported() {
      return true;
   }

   public AbstractHttpContent setMediaType(HttpMediaType var1) {
      this.mediaType = var1;
      return this;
   }
}
