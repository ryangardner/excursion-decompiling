package com.google.api.client.http;

import com.google.api.client.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractInputStreamContent implements HttpContent {
   private boolean closeInputStream = true;
   private String type;

   public AbstractInputStreamContent(String var1) {
      this.setType(var1);
   }

   public final boolean getCloseInputStream() {
      return this.closeInputStream;
   }

   public abstract InputStream getInputStream() throws IOException;

   public String getType() {
      return this.type;
   }

   public AbstractInputStreamContent setCloseInputStream(boolean var1) {
      this.closeInputStream = var1;
      return this;
   }

   public AbstractInputStreamContent setType(String var1) {
      this.type = var1;
      return this;
   }

   public void writeTo(OutputStream var1) throws IOException {
      IOUtils.copy(this.getInputStream(), var1, this.closeInputStream);
      var1.flush();
   }
}
