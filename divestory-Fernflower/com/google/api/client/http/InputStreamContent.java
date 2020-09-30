package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.io.InputStream;

public final class InputStreamContent extends AbstractInputStreamContent {
   private final InputStream inputStream;
   private long length = -1L;
   private boolean retrySupported;

   public InputStreamContent(String var1, InputStream var2) {
      super(var1);
      this.inputStream = (InputStream)Preconditions.checkNotNull(var2);
   }

   public InputStream getInputStream() {
      return this.inputStream;
   }

   public long getLength() {
      return this.length;
   }

   public boolean retrySupported() {
      return this.retrySupported;
   }

   public InputStreamContent setCloseInputStream(boolean var1) {
      return (InputStreamContent)super.setCloseInputStream(var1);
   }

   public InputStreamContent setLength(long var1) {
      this.length = var1;
      return this;
   }

   public InputStreamContent setRetrySupported(boolean var1) {
      this.retrySupported = var1;
      return this;
   }

   public InputStreamContent setType(String var1) {
      return (InputStreamContent)super.setType(var1);
   }
}
